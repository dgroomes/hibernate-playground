# sqlite

Connect Hibernate to a SQLite database.


## Description

This project defines a Java program that connects to a local SQLite database using Hibernate.

Notably, Hibernate does not have first-class support for SQLite. Thankfully though, the Hibernate project is generous
enough to carve out a special `hibernate-community-dialects` project where dialects for database like SQLite
exist and are maintained mostly by non-core members of the Hibernate project. Read more about Hibernate's support for
community dialects [here](https://github.com/hibernate/hibernate-orm/blob/main/dialects.adoc#community-dialects).


## Instructions

Follow these instructions to run the demo.

1. Pre-requisite: Java 21
2. Initialize a SQLite db with the schema and sample data:
   * ```shell
     sqlite3 observations.db < schema/1-observations-schema.sql
     sqlite3 observations.db < schema/2-observations-data.sql
     ```
3. Run the program:
   * ```shell
     ./gradlew run
     ```
   * Altogether, it should look like this:
     ```text
     $ ./gradlew run
     
     > Task :run
     23:57:42 [main] WARN org.hibernate.orm.connections.pooling - HHH10001002: Using built-in connection pool (not intended for production use)
     23:57:43 [main] INFO dgroomes.App - The HQL query found results...
     23:57:43 [main] INFO dgroomes.App - Observation (1): The sky is blue
     23:57:43 [main] INFO dgroomes.App - Observation (2): The speed of light can circle the earth 7 times in a second
     23:57:43 [main] INFO dgroomes.App -
     ```


## Reference

* [`xerial/sqlite-jdbc`: JDBC driver for SQLite](https://github.com/xerial/sqlite-jdbc)
