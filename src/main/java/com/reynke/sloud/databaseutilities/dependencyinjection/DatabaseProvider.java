package com.reynke.sloud.databaseutilities.dependencyinjection;

import com.google.inject.Injector;
import com.reynke.sloud.databaseutilities.configuration.DatabaseType;
import com.reynke.sloud.databaseutilities.configuration.IDatabaseConfiguration;
import com.reynke.sloud.databaseutilities.database.IDatabase;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
public class DatabaseProvider implements Provider<IDatabase> {

    private final IDatabaseConfiguration databaseConfiguration;
    private final Injector injector;

    @Inject
    public DatabaseProvider(IDatabaseConfiguration databaseConfiguration, Injector injector) {
        this.databaseConfiguration = databaseConfiguration;
        this.injector = injector;
    }

    @Override
    public IDatabase get() {
        DatabaseType databaseType = this.databaseConfiguration.getDatabaseType();
        return this.injector.getInstance(databaseType.getDatabaseClass());
    }

}
