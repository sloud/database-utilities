package com.reynke.sloud.databaseutilities.configuration;

import com.reynke.sloud.databaseutilities.database.*;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
public enum DatabaseType {

    /**
     * @deprecated Use MY_SQL_5 instead.
     *
     * Represents a {@link MySQL5Database}.
     */
    MY_SQL(MySQL5Database.class),

    /**
     * Represents a {@link MySQL5Database}.
     */
    MY_SQL_5(MySQL5Database.class),

    /**
     * Represents a {@link MySQL8Database}.
     */
    MY_SQL_8(MySQL8Database.class),

    /**
     * Represents a {@link MariaDBDatabase}.
     */
    MARIA_DB(MariaDBDatabase.class),

    /**
     * @deprecated Use POSTGRE_SQL_9 instead.
     *
     * Represents a {@link PostgreSQL9Database}.
     */
    POSTGRE_SQL(PostgreSQL9Database.class),

    /**
     * Represents a {@link PostgreSQL9Database}.
     */
    POSTGRE_SQL_9(PostgreSQL9Database.class),

    /**
     * Represents a {@link PostgreSQL10Database}.
     */
    POSTGRE_SQL_10(PostgreSQL10Database.class);

    private final Class<? extends IDatabase> databaseClass;

    DatabaseType(Class<? extends IDatabase> databaseClass) {
        this.databaseClass = databaseClass;
    }

    public Class<? extends IDatabase> getDatabaseClass() {
        return databaseClass;
    }
}
