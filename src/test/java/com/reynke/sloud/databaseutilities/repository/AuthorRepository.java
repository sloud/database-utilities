package com.reynke.sloud.databaseutilities.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.reynke.sloud.databaseutilities.entity.Author;
import com.reynke.sloud.databaseutilities.database.IDatabase;

import java.util.logging.Logger;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public class AuthorRepository extends AbstractRepository<Author, Long> {

    @Inject
    public AuthorRepository(IDatabase database, Logger logger) {
        super(database, logger);
    }
}
