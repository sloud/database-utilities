package com.reynke.sloud.databaseutilities.dependencyinjection;

import com.google.inject.PrivateModule;
import com.reynke.sloud.databaseutilities.configuration.IDatabaseConfiguration;
import com.reynke.sloud.databaseutilities.database.IDatabase;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
public class DatabaseUtilitiesModule extends PrivateModule {

    private final IDatabaseConfiguration databaseConfiguration;

    public DatabaseUtilitiesModule(IDatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    @Override
    protected void configure() {

        bind(IDatabaseConfiguration.class).toInstance(databaseConfiguration);
        bind(IDatabase.class).to(databaseConfiguration.getDatabaseType().getDatabaseClass());

        expose(IDatabaseConfiguration.class);
        expose(IDatabase.class);
    }
}
