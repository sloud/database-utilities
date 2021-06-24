package com.reynke.sloud.databaseutilities.configuration;

import com.google.inject.Singleton;
import com.reynke.sloud.databaseutilities.entity.IEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public class DatabaseConfiguration implements IDatabaseConfiguration {

    private DatabaseType databaseType;
    private String host;
    private int port;
    private String databaseName;
    private String username;
    private String password;
    private List<String> packages;
    private List<Class<? extends IEntity<?>>> annotatedClasses;
    private Map<String, String> extraProperties;
    private Hbm2ddlOption hbm2ddlOption;

    public DatabaseConfiguration() {
        // Set default attribute values.
        databaseType = DatabaseType.MY_SQL_5;
        host = "localhost";
        port = 3306;
        username = "root";
        packages = new ArrayList<>();
        annotatedClasses = new ArrayList<>();
        extraProperties = new HashMap<>();
        hbm2ddlOption = Hbm2ddlOption.VALIDATE;
    }

    @Override
    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    @Override
    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public List<String> getPackages() {
        return packages;
    }

    @Override
    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    @Override
    public void addPackage(String packagePath) {
        if (this.packages.contains(packagePath)) {
            return;
        }

        this.packages.add(packagePath);
    }

    @Override
    public List<Class<? extends IEntity<?>>> getAnnotatedClasses() {
        return annotatedClasses;
    }

    @Override
    public void setAnnotatedClasses(List<Class<? extends IEntity<?>>> annotatedClasses) {
        this.annotatedClasses = annotatedClasses;
    }

    @Override
    public void addAnnotatedClass(Class<? extends IEntity<?>> annotatedClass) {
        if (this.annotatedClasses.contains(annotatedClass)) {
            return;
        }

        this.annotatedClasses.add(annotatedClass);
    }

    @Override
    public Map<String, String> getExtraProperties() {
        return extraProperties;
    }

    @Override
    public void setExtraProperties(Map<String, String> extraProperties) {
        this.extraProperties = extraProperties;
    }

    @Override
    public void addExtraProperty(String key, String value) {
        extraProperties.put(key, value);
    }

    @Override
    public Hbm2ddlOption getHbm2ddlOption() {
        return hbm2ddlOption;
    }

    @Override
    public void setHbm2ddlOption(Hbm2ddlOption hbm2ddlOption) {
        this.hbm2ddlOption = hbm2ddlOption;
    }
}
