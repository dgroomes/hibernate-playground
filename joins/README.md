# joins

An illustration of table joins in Hibernate ORM.


## Description

The most uniquely impressive features of Hibernate (and other ORMs) is to turn row-based data into graph-based data.
Specifically, this means turning the row-based data in the table of SQL database (RDBMS) into object instances in Java
(object-to-object relationships form a graph). Across even a few tables, it would require lots of procedural code
to accomplish something similar if you weren't using an ORM. For single-table information systems, consider using straight
JDBC and SQL.


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
     00:29:35 [main] WARN org.hibernate.orm.connections.pooling - HHH10001002: Using built-in connection pool (not intended for production use)
     00:29:35 [main] INFO SQL dialect - HHH000400: Using dialect: org.hibernate.community.dialect.SQLiteDialect
     00:29:37 [main] INFO dgroomes.App - [Query using HQL] Found results...
     00:29:37 [main] INFO dgroomes.App - Observation (id=1, type=Uninteresting observation): The sky is blue
     00:29:37 [main] INFO dgroomes.App - Observation (id=2, type=Interesting observation): The speed of light can circle the earth 7 times in a second
     00:29:37 [main] INFO dgroomes.App -
     ```


## WishList

General clean-ups, TODOs and things I wish to implement for this project:

* [ ] Is there a way to get a single-fetch for the join query? When you turn on SQL statement logging, Hibernate is
      making two follow up SQL queries to the `observation_types` table. I made some progress with "fetch". From some
      reading, I hope the Criteria API will save me. I was interested in this API anyway.
