package com.reynke.sloud.databaseutilities.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.reynke.sloud.databaseutilities.configuration.IDatabaseConfiguration;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MariaDBDialect;

import java.sql.Driver;
import java.util.logging.Logger;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public class MariaDbDatabase extends AbstractDatabase {

    @Inject
    public MariaDbDatabase(IDatabaseConfiguration databaseConfiguration, Logger logger) {
        super(databaseConfiguration, logger);
    }

    @Override
    public Class<? extends Dialect> getDialectClass() {
        return MariaDBDialect.class;
    }

    @Override
    public Class<? extends Driver> getDriverClass() {
        return org.mariadb.jdbc.Driver.class;
    }

    @Override
    public String getJdbcUrl() {
        IDatabaseConfiguration databaseConfiguration = this.getDatabaseConfiguration();
        return "jdbc:mariadb://" + databaseConfiguration.getHost() + ":" + databaseConfiguration.getPort() + "/" + databaseConfiguration.getDatabaseName();
    }
}
