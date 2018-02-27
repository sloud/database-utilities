package com.reynke.sloud.databaseutilities.database;

import com.google.inject.ImplementedBy;
import com.reynke.sloud.databaseutilities.configuration.IDatabaseConfiguration;
import com.reynke.sloud.databaseutilities.exception.DatabaseUtilitiesException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.sql.Driver;
import java.util.Map;

/**
 * TODO: 04.07.17 Add missing documentation
 *
 * @author Nicklas Reincke (contact@reynke.com)
 */
@ImplementedBy(MySqlDatabase.class)
public interface IDatabase {

    /**
     * @return true, if this database is up and running. false otherwise.
     */
    boolean isOpen();

    /**
     * @return true, if this database is not up and running. false otherwise.
     */
    boolean isClosed();

    /**
     * @return The {@link IDatabaseConfiguration} injected in this database.
     */
    IDatabaseConfiguration getDatabaseConfiguration();

    /**
     * <strong>Must</strong> initially be run in the constructor of derived classes.
     *
     * @throws DatabaseUtilitiesException Thrown if this database is already up and running.
     */
    void configure() throws DatabaseUtilitiesException;

    /**
     * @return A new {@link Session} created by a {@link SessionFactory}.
     * @throws DatabaseUtilitiesException Thrown if this database is not yet up and running.
     */
    Session openSession() throws DatabaseUtilitiesException;

    /**
     * @throws DatabaseUtilitiesException Thrown if this database is not up and running.
     */
    void closeDatabaseConnection() throws DatabaseUtilitiesException;

    Map<String, String> getExtraProperties();

    Class<? extends ConnectionProvider> getProviderClass();

    Class<? extends Dialect> getDialectClass();

    Class<? extends Driver> getDriverClass();

    String getJdbcUrl();
}
