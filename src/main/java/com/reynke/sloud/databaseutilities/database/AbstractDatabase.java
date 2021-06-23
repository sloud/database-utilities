package com.reynke.sloud.databaseutilities.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.reynke.sloud.databaseutilities.configuration.IDatabaseConfiguration;
import com.reynke.sloud.databaseutilities.entity.IEntity;
import com.reynke.sloud.databaseutilities.exception.DatabaseUtilitiesException;
import com.reynke.sloud.databaseutilities.logging.ILoggerAware;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;

import java.sql.Driver;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public abstract class AbstractDatabase implements IDatabase, ILoggerAware {

    private final Logger logger;
    private final IDatabaseConfiguration databaseConfiguration;

    private SessionFactory sessionFactory = null;

    @Inject
    AbstractDatabase(IDatabaseConfiguration databaseConfiguration, Logger logger) {
        this.databaseConfiguration = databaseConfiguration;
        this.logger = logger;

        try {
            logger.log(Level.INFO, "Trying to set up the database ...");
            this.configure();
        } catch (DatabaseUtilitiesException e) {
            logger.log(Level.SEVERE, "Failed to set up database: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            logger.log(Level.INFO, "Successfully set up database.");
        }
    }

    @Override
    public boolean isOpen() {
        return sessionFactory != null && !sessionFactory.isClosed();
    }

    @Override
    public boolean isClosed() {
        return sessionFactory == null || sessionFactory.isClosed();
    }

    @Override
    public IDatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    @Override
    public void configure() throws DatabaseUtilitiesException {
        if (this.isOpen()) {
            throw new DatabaseUtilitiesException("This database connection is already up and running.");
        }

        // Set properties retrieved by the configuration and
        // extra properties necessary to configure Hibernate.
        Properties hibernateProperties = new Properties();

        logger.log(Level.INFO, "Setting database up using dialect: " + this.getDialectClass().getName());
        logger.log(Level.INFO, "Setting database up using provider: " + this.getProviderClass().getName());
        logger.log(Level.INFO, "Setting database up using driver: " + this.getDriverClass().getName());

        hibernateProperties.put(AvailableSettings.DIALECT, this.getDialectClass().getName());
        hibernateProperties.put(AvailableSettings.CONNECTION_PROVIDER, this.getProviderClass().getName());
        hibernateProperties.put(AvailableSettings.DRIVER, this.getDriverClass().getName());
        hibernateProperties.put(AvailableSettings.URL, this.getJdbcUrl());
        hibernateProperties.put(AvailableSettings.USER, databaseConfiguration.getUsername());
        hibernateProperties.put(AvailableSettings.PASS, databaseConfiguration.getPassword());
        hibernateProperties.put(AvailableSettings.HBM2DDL_AUTO, databaseConfiguration.getHbm2ddlOption().getValue());

        // Apply extra properties by the database configuration (may override existing properties)
        for (Map.Entry<String, String> extraProperty : this.getDatabaseConfiguration().getExtraProperties().entrySet()) {
            hibernateProperties.put(extraProperty.getKey(), extraProperty.getValue());
        }

        // Apply extra properties defined for this database (may override existing properties)
        for (Map.Entry<String, String> extraProperty : this.getExtraProperties().entrySet()) {
            hibernateProperties.put(extraProperty.getKey(), extraProperty.getValue());
        }

        Configuration configuration = new Configuration();

        logger.log(Level.INFO, "Adding packages and annotated classes from configuration ...");

        // Load packages containing annotated classes
        for (String packageName : databaseConfiguration.getPackages()) {
            logger.log(Level.INFO, "Adding package \"" + packageName + "\" ...");
            configuration.addPackage(packageName);
        }

//        Collection<ClassLoader> classLoaders = new ArrayList<>();

        // Load annotated classes dynamically
        for (Class<? extends IEntity<?>> annotatedClass : databaseConfiguration.getAnnotatedClasses()) {
            logger.log(Level.INFO, "Adding annotated class \"" + annotatedClass.getName() + "\" ...");

            // https://stackoverflow.com/questions/27304580/mapping-entities-from-outside-classpath-loaded-dynamically
//            Thread.currentThread().setContextClassLoader(annotatedClass.getClassLoader());

//            try {
//                annotatedClass.getClassLoader().loadClass(annotatedClass.getName());
//            } catch (ClassNotFoundException e) {
//                logger.log(Level.SEVERE, "Errooooor");
//                e.printStackTrace();
//            }

//            classLoaders.add(annotatedClass.getClassLoader());
            configuration.addAnnotatedClass(annotatedClass);
        }

        // @TODO Remove debug log
//        System.out.println(hibernateProperties.get(AvailableSettings.CLASSLOADERS));

//        hibernateProperties.put(AvailableSettings.CLASSLOADERS, classLoaders);

        configuration.addProperties(hibernateProperties);

        try {
            // Finally build the session factory ... nice!
            logger.log(Level.INFO, "Building session factory ...");
            sessionFactory = configuration.buildSessionFactory();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Error building session factory: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            logger.log(Level.INFO, "Successfully built session factory!");
        }
    }

    @Override
    public Session openSession() throws DatabaseUtilitiesException {
        if (!this.isOpen()) {
            throw new DatabaseUtilitiesException("This database is not yet configured. Call the \"configure()\" method on this database to set it up.");
        }

        if (sessionFactory == null) {
            throw new NullPointerException("There is no session factory available to open a session from.");
        }

        return sessionFactory.openSession();
    }

    @Override
    public void closeDatabaseConnection() throws DatabaseUtilitiesException {
        if (!this.isOpen()) {
            throw new DatabaseUtilitiesException("This database is not configured. Closing it wouldn't make sense. Call the \"configure()\" method on this database to set it up.");
        }

        if (sessionFactory == null) {
            throw new NullPointerException("There is no session factory available to open a session from.");
        }

        sessionFactory.close();
    }

    @Override
    public Class<? extends ConnectionProvider> getProviderClass() {
        return HikariCPConnectionProvider.class;
    }

    @Override
    public Map<String, String> getExtraProperties() {

        Map<String, String> extraHibernateProperties = new HashMap<>();
        extraHibernateProperties.put("hibernate.hikari.dataSource.serverTimezone", "UTC");

        return extraHibernateProperties;
    }

    @Override
    public abstract Class<? extends Dialect> getDialectClass();

    @Override
    public abstract Class<? extends Driver> getDriverClass();

    @Override
    public abstract String getJdbcUrl();

    @Override
    public Logger getLogger() {
        return logger;
    }
}
