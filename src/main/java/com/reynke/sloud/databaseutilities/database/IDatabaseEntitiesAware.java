package com.reynke.sloud.databaseutilities.database;

import com.reynke.sloud.databaseutilities.configuration.IDatabaseConfiguration;
import com.reynke.sloud.databaseutilities.entity.IEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * IDatabaseEntitiesAware
 * <p>
 * Interface that can be used to tell that a class holds packages and annotated classes that should be added
 * to a {@link IDatabaseConfiguration} for example.
 *
 * @author Nicklas Reincke (contact@reynke.com)
 */
public interface IDatabaseEntitiesAware {

    /**
     * @return A list containing fully qualified package names whose packages are containing annotated classes.
     */
    default List<String> getPackages() {
        return new ArrayList<>();
    }

    /**
     * @return A list containing annotated classes.
     */
    default List<Class<? extends IEntity<?>>> getAnnotatedClasses() {
        return new ArrayList<>();
    }
}
