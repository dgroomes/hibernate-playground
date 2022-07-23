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

1. Use Java 17
2. Run the program:
   * ```shell
     ./gradlew run
     ```
   * Altogether, it should look like this:
     ```text
     $ ./gradlew run

     > Task :run
     22:24:01 [main] WARN org.hibernate.orm.connections.pooling - HHH10001002: Using built-in connection pool (not intended for production use)
     22:24:01 [main] INFO SQL dialect - HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
     22:24:02 [main] INFO dgroomes.App - [Query using HQL] Found results...
     22:24:02 [main] INFO dgroomes.App - Observation (id=1, type=Uninteresting observation): The sky is blue
     22:24:02 [main] INFO dgroomes.App - Observation (id=2, type=Interesting observation): The speed of light can circle the earth 7 times in a second
     22:24:02 [main] INFO dgroomes.App -
     ```


## WishList

General clean-ups, TODOs and things I wish to implement for this project:

* [ ] Is there a way to get a single-fetch for the join query? When you turn on SQL statement logging, Hibernate is
      making two follow up SQL queries to the `observation_types` table. I made some progress with "fetch". From some
      reading, I hope the Criteria API will save me. I was interested in this API anyway.
