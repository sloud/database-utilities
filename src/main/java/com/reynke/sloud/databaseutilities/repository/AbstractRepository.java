package com.reynke.sloud.databaseutilities.repository;

import com.google.inject.Inject;
import com.reynke.sloud.databaseutilities.database.IDatabase;
import com.reynke.sloud.databaseutilities.entity.IEntity;
import com.reynke.sloud.databaseutilities.exception.DatabaseUtilitiesException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
public abstract class AbstractRepository<T extends IEntity<I>, I extends Serializable> extends AbstractDatabaseAwareRepository<T, I> {

    @Inject
    public AbstractRepository(IDatabase database, Logger logger) {
        super(database, logger);
    }

    @Override
    public void create(T entity) {
        Session session;

        try {
            session = getDatabase().openSession();
        } catch (DatabaseUtilitiesException e) {
            throw new RuntimeException(e);
        }

        Transaction transaction = session.beginTransaction();

        session.persist(entity);

        transaction.commit();
        session.close();
    }

    @Override
    public T findOneById(Class<T> entityType, I id) {
        Session session;

        try {
            session = getDatabase().openSession();
        } catch (DatabaseUtilitiesException e) {
            throw new RuntimeException(e);
        }

        T entity = null;

        try {
            entity = entityType.cast(session.get(entityType, id));
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        session.close();

        return entity;
    }

    @Override
    public Set<T> findAll(Class<T> entityType) {
        Session session;

        try {
            session = getDatabase().openSession();
        } catch (DatabaseUtilitiesException e) {
            throw new RuntimeException(e);
        }

        List list = session.createQuery("FROM " + entityType.getName()).list();
        session.close();

        Set<T> set = new HashSet<>();

        // @todo implement
//        for (Object object : list) {
//
//            IEntity entity = (IEntity) object;
//
//            set.add(entity);
//        }

        return set;
    }

    @Override
    public T update(T entity) {
        Session session;

        try {
            session = getDatabase().openSession();
        } catch (DatabaseUtilitiesException e) {
            throw new RuntimeException(e);
        }

        Transaction transaction = session.beginTransaction();

        // Evict to make sure it's a detached object
        session.evict(entity);

        // Merge the detached object (only detached objects can be merged)
        session.merge(entity);

        transaction.commit();
        session.close();

        return entity;
    }

    @Override
    public void delete(T entity) {
        Session session;

        try {
            session = getDatabase().openSession();
        } catch (DatabaseUtilitiesException e) {
            throw new RuntimeException(e);
        }

        Transaction transaction = session.beginTransaction();

        session.delete(entity);

        transaction.commit();
        session.close();
    }
}
