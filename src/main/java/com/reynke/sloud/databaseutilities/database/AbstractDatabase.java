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
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public abstract class AbstractDatabase implements IDatabase, ILoggerAware {

    private Logger logger;
    private IDatabaseConfiguration databaseConfiguration;
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
        return sessionFactory != null && sessionFactory.isOpen();
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
            throw new DatabaseUtilitiesException("This database connections is already up and running.");
        }

        // Set properties retrieved by the configuration and
        // extra properties necessary to configure Hibernate.
        Properties hibernateProperties = new Properties();

        logger.log(Level.INFO, "Setting database up using dialect: " + this.getDialectClass().getName());
        logger.log(Level.INFO, "Setting database up using provider: " + this.getProviderClass().getName());
        logger.log(Level.INFO, "Setting database up using driver: " + this.getDriverClass().getName());

        hibernateProperties.setProperty("hibernate.dialect", this.getDialectClass().getName());
        hibernateProperties.setProperty("hibernate.connection.provider_class", this.getProviderClass().getName());
        hibernateProperties.setProperty("hibernate.connection.driver_class", this.getDriverClass().getName());
        hibernateProperties.setProperty("hibernate.connection.url", this.getJdbcUrl());
        hibernateProperties.setProperty("hibernate.connection.username", databaseConfiguration.getUsername());
        hibernateProperties.setProperty("hibernate.connection.password", databaseConfiguration.getPassword());
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", databaseConfiguration.getHbm2ddlOption().getValue());

        // Apply extra properties by the database configuration (may override existing properties)
        for (Map.Entry<String, String> extraProperty : this.getDatabaseConfiguration().getExtraProperties().entrySet()) {
            hibernateProperties.setProperty(extraProperty.getKey(), extraProperty.getValue());
        }

        // Apply extra properties defined for this database (may override existing properties)
        for (Map.Entry<String, String> extraProperty : this.getExtraProperties().entrySet()) {
            hibernateProperties.setProperty(extraProperty.getKey(), extraProperty.getValue());
        }

        Configuration configuration = new Configuration();
        configuration.addProperties(hibernateProperties);

        logger.log(Level.INFO, "Adding packages and annotated classes from configuration ...");

        // Load packages containing annotated classes
        for (String packageName : databaseConfiguration.getPackages()) {
            logger.log(Level.INFO, "Adding package \"" + packageName + "\" ...");
            configuration.addPackage(packageName);
        }

        // Load annotated classes
        for (Class<? extends IEntity> annotatedClass : databaseConfiguration.getAnnotatedClasses()) {
            logger.log(Level.INFO, "Adding annotated class \"" + annotatedClass.getName() + "\" ...");
            configuration.addAnnotatedClass(annotatedClass);
        }

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
