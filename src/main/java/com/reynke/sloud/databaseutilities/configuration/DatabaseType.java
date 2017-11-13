package com.reynke.sloud.databaseutilities.configuration;

import com.reynke.sloud.databaseutilities.database.IDatabase;
import com.reynke.sloud.databaseutilities.database.MySqlDatabase;
import com.reynke.sloud.databaseutilities.database.PostgreSqlDatabase;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
public enum DatabaseType {

    /**
     * Represents a {@link MySqlDatabase}.
     */
    MY_SQL(MySqlDatabase.class),

    /**
     * Represents a {@link PostgreSqlDatabase}.
     */
    POSTGRE_SQL(PostgreSqlDatabase.class);

    private Class<? extends IDatabase> databaseClass;

    DatabaseType(Class<? extends IDatabase> databaseClass) {
        this.databaseClass = databaseClass;
    }

    public Class<? extends IDatabase> getDatabaseClass() {
        return databaseClass;
    }
}
