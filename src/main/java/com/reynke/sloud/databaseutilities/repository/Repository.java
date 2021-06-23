package com.reynke.sloud.databaseutilities.repository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {

    /**
     * @return The type of the repository related to the entity annotated with this annotation.
     */
    Class<? extends IRepository<?, ?>> type();
}
