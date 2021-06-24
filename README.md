# Sloud Database Utilities

Sloud component utilizing basic database interaction functionality, such as:

* Handling different kinds of database types
  * MariaDB
  * MySQL 5 and 8
  * PostgreSQL 9 and 10
* Database configuration options for pooling and connectivity
* Dynamic loading of entities by class and package names
* Providing a dependency injection module for the DI framework "Guice"

## Usage example

```java
class Example {
  public void setupDatabase() {
    IDatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();

    databaseConfiguration.setDatabaseType(DatabaseType.MY_SQL_8);
    databaseConfiguration.setHost("host");
    databaseConfiguration.setPort(3306);
    databaseConfiguration.setDatabaseName("databaseName");
    databaseConfiguration.setUsername("username");
    databaseConfiguration.setPassword("password");
    databaseConfiguration.setHbm2ddlOption(Hbm2ddlOption.VALIDATE);

    databaseConfiguration.setPackages(new ArrayList<>());
    databaseConfiguration.setAnnotatedClasses(new ArrayList<>());

    // 1. When using dependency injection:
    // Automatically detects database type by database type option in config.
    Module databaseUtilitiesModule = new DatabaseUtilitiesModule(databaseConfiguration);
    Injector injector = Guice.createInjector(databaseUtilitiesModule);
    IDatabase database = injector.getInstance(IDatabase.class);
    // ...

    // 2. When not using dependency injection:
    // You manually need to create a database instance from the type you choose.
    IDatabase database = new MySQL8Database(databaseConfiguration, /* your logger */);
    // ...
  }
}
```

## Install Java 16

Install Homebrew and run the following:

```shell
brew tap AdoptOpenJDK/openjdk
brew install --cask adoptopenjdk16-jre
```

## Changelog

**1.0.5**

* Better documentation
* Add new database types and deprecate generic ones

**1.0.4**

* Set source and target compatibility Java version to `16`

**1.0.3**

* Change artifact and group IDs

**1.0.2**

* Code cleanup
* Make `IEntity`s id field more generic

**1.0.1**

* Added dependencies resolving issues with JAXB classes not being found

**1.0.0**

* Initital release
