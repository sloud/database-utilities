package com.reynke.sloud.databaseutilities.configuration;

import com.google.inject.ImplementedBy;
import com.reynke.sloud.databaseutilities.entity.IEntity;

import java.util.List;
import java.util.Map;

/**
 * TODO: 24.06.17 Add documentation
 *
 * @author Nicklas Reincke (contact@reynke.com)
 */
@ImplementedBy(DatabaseConfiguration.class)
public interface IDatabaseConfiguration {

    DatabaseType getDatabaseType();

    void setDatabaseType(DatabaseType databaseType);

    String getHost();

    void setHost(String host);

    int getPort();

    void setPort(int port);

    String getDatabaseName();

    void setDatabaseName(String databaseName);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    List<String> getPackages();

    void setPackages(List<String> packages);

    void addPackage(String packagePath);

    List<Class<? extends IEntity<?>>> getAnnotatedClasses();

    void setAnnotatedClasses(List<Class<? extends IEntity<?>>> annotatedClasses);

    void addAnnotatedClass(Class<? extends IEntity<?>> annotatedClass);

    Map<String, String> getExtraProperties();

    void setExtraProperties(Map<String, String> extraProperties);

    void addExtraProperty(String key, String value);

    default Hbm2ddlOption getHbm2ddlOption() {
        return Hbm2ddlOption.VALIDATE;
    }

    void setHbm2ddlOption(Hbm2ddlOption hbm2ddlOption);
}
