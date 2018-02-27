package com.reynke.sloud.databaseutilities.repository;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.reynke.sloud.databaseutilities.entity.IEntity;
import com.reynke.sloud.databaseutilities.exception.DatabaseUtilitiesException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public class RepositoryFactory implements IRepositoryFactory {

    private Injector injector;

    private static Map<String, IRepository<?>> repositoryCache;

    static {
        repositoryCache = new HashMap<>();
    }

    @Inject
    public RepositoryFactory(Injector injector) {
        this.injector = injector;
    }

    @Override
    public IRepository<?> getRepository(Class<? extends IEntity> entityType) throws DatabaseUtilitiesException {

        // Load repository from cache if it was found by its entity types class path.
        if (repositoryCache.containsKey(entityType.getName())) {
            return repositoryCache.get(entityType.getName());
        }

        // Get the @Repository annotation from the entity class.
        Repository repositoryAnnotation = entityType.getAnnotation(Repository.class);

        // Check if it is even there ...
        if (repositoryAnnotation == null) {
            throw new DatabaseUtilitiesException("The desired entity type is not annotated with a repository annotation.");
        }

        // Get the repository type related to the entity to tell the injector where
        // to inject dependencies and finally return the created repository.
        IRepository repository = injector.getInstance(repositoryAnnotation.type());

        // Add repository to cache.
        repositoryCache.put(entityType.getName(), repository);

        return repository;
    }
}
