# criteria

An illustration of the Criteria API within the Jakarta Persistence API.


## Description

Hibernate introduced the original Criteria API and then Jakarta (formerly JEE) improved upon it in the formal project named
[*Jakarta Persistence API*](https://projects.eclipse.org/projects/ee4j.jpa).

This project implements a basic Java program that showcases the Criteria API using Hibernate and a simple SQL schema. 


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
     17:25:55 [main] WARN org.hibernate.orm.connections.pooling - HHH10001002: Using built-in connection pool (not intended for production use)
     17:25:55 [main] INFO SQL dialect - HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
     17:25:56 [main] DEBUG org.hibernate.SQL - 
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
     17:25:56 [main] INFO dgroomes.App - [Query using HQL] Found results...
     17:25:56 [main] INFO dgroomes.App - Observation (id=1, type=Uninteresting observation): The sky is blue
     17:25:56 [main] INFO dgroomes.App - Observation (id=2, type=Interesting observation): The speed of light can circle the earth 7 times in a second
     17:25:56 [main] INFO dgroomes.App -
     ```


## WishList

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Is there a way to get a single-fetch for the join query? When you turn on SQL statement logging, Hibernate is
      making two follow up SQL queries to the `observation_types` table. I made some progress with "fetch". From some
      reading, I hope the Criteria API will save me. I was interested in this API anyway.
* [X] OBSOLETE (This question was investigated in the `codegen/` subproject of this repo. See it.) Codegen the entity classes. Can Hibernate codegen from DDL files or does it need a server? This poses a problem
      because we like ephemeral databases for demos.


## Reference

* [Hibernate docs: *Static Metamodel Generator*](https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#tooling-modelgen)
  * Idiomatic usage of the Criteria API is to use *static metamodel classes*. This must be code generated.
* [Blog post: *N+1 query problem with JPA and Hibernate*](https://vladmihalcea.com/n-plus-1-query-problem/)
