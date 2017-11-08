package com.reynke.sloud.databaseutilities;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.reynke.sloud.databaseutilities.configuration.IDatabaseConfiguration;
import com.reynke.sloud.databaseutilities.dependencyinjection.DatabaseUtilitiesModule;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
public class DatabaseUtilities {

    private static Injector injector;

    public static Injector createInjector(IDatabaseConfiguration databaseConfiguration) {
        injector = Guice.createInjector(new DatabaseUtilitiesModule(databaseConfiguration));
        return injector;
    }

    public static Injector getInjector() {
        return injector;
    }
}
