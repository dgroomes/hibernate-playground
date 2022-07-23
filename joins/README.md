# joins

An illustration of table joins in Hibernate ORM. Comparing and contrasting HQL with the Criteria API.


## Description

The most uniquely impressive features of Hibernate (and other ORMs) is to turn row-based data into graph-based data (and
vice-versa). Specifically, this means turning the row-based data in the table of a SQL database (RDBMS) into object instances
in Java (object-to-object relationships form a graph). Across even a few tables, it would require lots of procedural code
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
     12:37:06 [main] WARN org.hibernate.orm.connections.pooling - HHH10001002: Using built-in connection pool (not intended for production use)
     12:37:07 [main] INFO SQL dialect - HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
     12:37:08 [main] DEBUG org.hibernate.SQL - 
         /* select
             o 
         from
             Observation o 
         join
             fetch ObservationType ot */ select
                 o1_0.id,
                 o1_0.observation,
                 o1_0.type 
             from
                 observations o1_0 
             join
                 observation_types o2_0 
                     on true
     12:37:08 [main] DEBUG org.hibernate.SQL - 
         select
             o1_0.id,
             o1_0.description 
         from
             observation_types o1_0 
         where
             o1_0.id=?
     12:37:08 [main] DEBUG org.hibernate.SQL - 
         select
             o1_0.id,
             o1_0.description 
         from
             observation_types o1_0 
         where
             o1_0.id=?
     12:37:08 [main] INFO dgroomes.App - [Query using HQL] Found results...
     12:37:08 [main] INFO dgroomes.App - Observation (id=1, type=Uninteresting observation): The sky is blue
     12:37:08 [main] INFO dgroomes.App - Observation (id=2, type=Interesting observation): The speed of light can circle the earth 7 times in a second
     12:37:08 [main] INFO dgroomes.App - 
     12:37:08 [main] DEBUG org.hibernate.SQL - 
         /* <criteria> */ select
             o1_0.id,
             o1_0.observation,
             t1_0.id,
             t1_0.description 
         from
             observations o1_0 
         join
             observation_types t1_0 
                 on t1_0.id=o1_0.type
     12:37:08 [main] INFO dgroomes.App - [Query using HQL] Found results...
     12:37:08 [main] INFO dgroomes.App - Observation (id=1, type=Uninteresting observation): The sky is blue
     12:37:08 [main] INFO dgroomes.App - Observation (id=2, type=Interesting observation): The speed of light can circle the earth 7 times in a second
     12:37:08 [main] INFO dgroomes.App - 
     ```


## WishList

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Is there a way to get a single-fetch for the join query? When you turn on SQL statement logging, Hibernate is
      making two follow up SQL queries to the `observation_types` table. I made some progress with "fetch". From some
      reading, I hope the Criteria API will save me. I was interested in this API anyway.

## Reference

* [Hibernate docs: *Static Metamodel Generator*](https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#tooling-modelgen)
  * Idiomatic usage of the Criteria API is to use *static metamodel classes*. This must be code generated.
* [Blog post: *N+1 query problem with JPA and Hibernate*](https://vladmihalcea.com/n-plus-1-query-problem/)
