# sqlite

Connect Hibernate to a SQLite database.


## Description

This project defines a Java program that connects to a local SQLite database using Hibernate.

Notably, Hibernate does not have first-class support for SQLite. Thankfully though, the Hibernate project is generous
enough to carve out a special `hibernate-community-dialects` project where dialects for database like SQLite
exist and are maintained mostly by non-core members of the Hibernate project. Read more about Hibernate's support for
community dialects [here](https://github.com/hibernate/hibernate-orm/blob/main/dialects.adoc#community-dialects).


## Instructions

Follow these instructions to run the demo:

1. Initialize a SQLite db with the schema and sample data:
   * ```shell
     sqlite3 observations.db < schema/1-observations-schema.sql
     sqlite3 observations.db < schema/2-observations-data.sql
     ```
3. Use Java 17
4. Run the program:
   * ```shell
     ./gradlew run
     ```
   * Altogether, it should look like this:
     ```text
     $ ./gradlew run
     
     > Task :run
     13:00:23 [main] WARN org.hibernate.orm.connections.pooling - HHH10001002: Using built-in connection pool (not intended for production use)
     13:00:23 [main] INFO SQL dialect - HHH000400: Using dialect: org.hibernate.community.dialect.SQLiteDialect
     13:00:24 [main] INFO dgroomes.App - [Query using HQL] Found results...
     13:00:24 [main] INFO dgroomes.App - Observation (1): The sky is blue
     13:00:24 [main] INFO dgroomes.App - Observation (2): The speed of light can circle the earth 7 times in a second
     13:00:24 [main] INFO dgroomes.App - 
     ```


## Reference

* [`xerial/sqlite-jdbc`: JDBC driver for SQLite](https://github.com/xerial/sqlite-jdbc)
