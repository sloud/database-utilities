package com.reynke.sloud.databaseutilities.repository;

import com.reynke.sloud.databaseutilities.database.IDatabase;
import com.reynke.sloud.databaseutilities.entity.IEntity;

import java.util.Set;

/**
 * // TODO: 02.07.17 Add documentation
 *
 * @author Nicklas Reincke (contact@reynke.com)
 */
public interface IRepository<T extends IEntity> {

    IDatabase getDatabase();

    /**
     * Persists and commits an new entity to the database and sets an id for the entity.
     *
     * @param entity The entity to persist and commit.
     */
    void create(T entity);

    /**
     * todo Maybe simplify this method to only accept an id since the type is already given since its a generic class.
     *
     * @param entityType The entity type.
     * @param id         The unique identifier of the entity.
     * @return The entity from the database. "null" otherwise.
     */
    T findOneById(Class<T> entityType, long id);

    Set<T> findAll(Class<T> entityType);

    T update(T entity);

    void delete(T entity);
}
