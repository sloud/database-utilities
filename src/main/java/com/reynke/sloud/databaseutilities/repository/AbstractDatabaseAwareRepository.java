package com.reynke.sloud.databaseutilities.repository;

import com.google.inject.Inject;
import com.reynke.sloud.databaseutilities.database.IDatabase;
import com.reynke.sloud.databaseutilities.entity.IEntity;
import com.reynke.sloud.databaseutilities.logging.ILoggerAware;

import java.util.logging.Logger;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
public abstract class AbstractDatabaseAwareRepository<T extends IEntity> implements IRepository<T>, ILoggerAware {

    private Logger logger;
    private IDatabase database;

    /**
     * @param database The injected database instance.
     * @param logger   The injected logger instance.
     */
    @Inject
    AbstractDatabaseAwareRepository(IDatabase database, Logger logger) {
        this.database = database;
        this.logger = logger;
    }

    @Override
    public IDatabase getDatabase() {
        return database;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
