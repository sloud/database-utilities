package com.reynke.sloud.databaseutilities.repository;

import com.google.inject.ImplementedBy;
import com.reynke.sloud.databaseutilities.entity.IEntity;
import com.reynke.sloud.databaseutilities.exception.DatabaseUtilitiesException;

/**
 * Factory to load Repositories by their entity type.
 * <p>
 * This factory tries to load the desired class and does some extra action
 * to make the repository work.
 * <p>
 * Repositories should be saved in a static context to cache them and load
 * them when they should be retrieved by this factory.
 *
 * @author Nicklas Reincke (contact@reynke.com)
 */
@ImplementedBy(RepositoryFactory.class)
public interface IRepositoryFactory {

    /**
     * Get a repository by its class path from a repository cache or per new instantiation.
     *
     * @param entityType The type of the entity to derice the repository type from.
     * @return The repository on success.
     * @throws DatabaseUtilitiesException Thrown if the desired class is nor a repository.
     */
    IRepository<?, ?> getRepository(Class<? extends IEntity<?>> entityType) throws DatabaseUtilitiesException;
}
