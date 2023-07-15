# codegen

Use the *Hibernate Tools* library to codegen Java source code (entities) from an existing database schema.


## Description

The [Hibernate Tools](https://hibernate.org/tools/) project contains various tools that make your Hibernate development
more sophisticated. These tools are generally geared to support a suite of Eclipse IDE plugins, but they can be used
in a headless way. That's exactly what the `hibernate-playground/codegen` does. It uses the [`hibernate-tools-orm`](https://github.com/hibernate/hibernate-tools/tree/d362340d5fad529ba25eba7123e2c007d0feb014/orm)
library to generate Hibernate entity classes (Java source code). Specifically, it does this by creating a simple
Gradle plugin and task named `hibernateCodeGen`. In fact, the Hibernate Tools project defines Ant tasks and a Maven
plugin for doing the same thing. I want to use Gradle, so I modeled my plugin after those but ignored all extensibility
features because this plugin is just for me.


## Instructions

Follow these instructions to run the demo:

1. Start the Postgres database:
    * ```shell
      docker-compose up --renew-anon-volumes --detach
      ```
    * This will start the database and initialize the schema and sample data.
2. Use Java 17
3. Codegen the entity classes
   * This step is only needed if you have changed the database schema. For example, if you added a new column to an
     existing table, then you'll need to regenerate the Java entity class for that table so that it can map to that column.
     This project hooks into the *Hibernate Tools* codegen library by way of a Gradle plugin and task. Run the following
     command to generate the entity code.
   * ```shell
     ./gradlew hibernateCodeGen
     ```
4. Run the program:
   * ```shell
     ./gradlew run
     ```
   * Altogether, it should look like this:
     ```text
     ❯ ./gradlew run
     
     > Task :run
     00:17:58 [main] WARN org.hibernate.orm.connections.pooling - HHH10001002: Using built-in connection pool (not intended for production use)
     00:17:58 [main] DEBUG org.hibernate.SQL - 
         /* <criteria> */ select
             o1_0.id,
             o1_0.observation,
             o1_0.type,
             o2_0.id,
             o2_0.description 
         from
             public.observations o1_0 
         join
             public.observation_types o2_0 
                 on o2_0.id=o1_0.type
     00:17:58 [main] INFO dgroomes.App - The Criteria-based query found results...
     00:17:58 [main] INFO dgroomes.App - Observation (id=1, type=Uninteresting observation): The sky is blue
     00:17:58 [main] INFO dgroomes.App - Observation (id=2, type=Interesting observation): The speed of light can circle the earth 7 times in a second
     00:17:58 [main] INFO dgroomes.App -
     ```
5. Stop the database:
    * ```shell
      docker-compose down
      ```


## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* [ ] Use a Gradle version catalog (TOML)


## Reference

* [Hibernate Tools docs](https://hibernate.org/tools/)
