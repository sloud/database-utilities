package com.reynke.sloud.databaseutilities.entity;

import java.io.Serializable;

/**
 * Class representing an entity in a relational database.
 *
 * @author Nicklas Reincke (contact@reynke.com)
 */
public interface IEntity<I extends Serializable> {

    /**
     * @return The unique identifier of this entity.
     */
    I getId();
}
