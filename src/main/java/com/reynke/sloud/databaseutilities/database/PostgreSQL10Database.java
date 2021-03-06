package com.reynke.sloud.databaseutilities.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.reynke.sloud.databaseutilities.configuration.IDatabaseConfiguration;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.PostgreSQL10Dialect;

import java.sql.Driver;
import java.util.logging.Logger;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public class PostgreSQL10Database extends AbstractDatabase {

    @Inject
    public PostgreSQL10Database(IDatabaseConfiguration databaseConfiguration, Logger logger) {
        super(databaseConfiguration, logger);
    }

    @Override
    public Class<? extends Dialect> getDialectClass() {
        return PostgreSQL10Dialect.class;
    }

    @Override
    public Class<? extends Driver> getDriverClass() {
        return org.postgresql.Driver.class;
    }

    @Override
    public String getJdbcUrl() {
        IDatabaseConfiguration databaseConfiguration = this.getDatabaseConfiguration();
        return "jdbc:postgresql://" + databaseConfiguration.getHost() + ":" + databaseConfiguration.getPort() + "/" + databaseConfiguration.getDatabaseName();
    }
}
