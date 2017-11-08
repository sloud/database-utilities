package com.reynke.sloud.databaseutilities.configuration;

/**
 * Source of options available: https://stackoverflow.com/a/1689769/5996699
 *
 * @author Nicklas Reincke (contact@reynke.com)
 */
public enum Hbm2ddlOption {

    /**
     * Validate the schema, makes no changes to the database.
     * <p>
     * This is the <strong>default type</strong> and should be set if possible.
     * <p>
     * Use this in a production environment.
     */
    VALIDATE("validate"),

    /**
     * Update the schema.
     * <p>
     * You shouldn't use this in a production environment!
     */
    UPDATE("update"),

    /**
     * Creates the schema, destroying previous data.
     */
    CREATE("create"),

    /**
     * Creates the schema, destroying previous data and drop the schema when the
     * SessionFactory is closed explicitly, typically when the application is stopped.
     * <p>
     * This option might be especially useful for testing purposes.
     */
    CREATE_DROP("create-drop");

    private String value;

    Hbm2ddlOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
