package com.reynke.sloud.databaseutilities.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.reynke.sloud.databaseutilities.configuration.IDatabaseConfiguration;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5Dialect;

import java.sql.Driver;
import java.util.logging.Logger;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public class MySqlDatabase extends AbstractDatabase {

    @Inject
    public MySqlDatabase(IDatabaseConfiguration databaseConfiguration, Logger logger) {
        super(databaseConfiguration, logger);
    }

    @Override
    public Class<? extends Dialect> getDialectClass() {
        return MySQL5Dialect.class;
    }

    @Override
    public Class<? extends Driver> getDriverClass() {
        return com.mysql.cj.jdbc.Driver.class;
    }

    @Override
    public String getJdbcUrl() {
        IDatabaseConfiguration databaseConfiguration = this.getDatabaseConfiguration();
        return "jdbc:mysql://" + databaseConfiguration.getHost() + ":" + databaseConfiguration.getPort() + "/" + databaseConfiguration.getDatabaseName();
    }
}
