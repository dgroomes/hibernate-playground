# codegen

Use the *Hibernate Tools* library to codegen Java source code (entities) from an existing database schema.

---
**WARNING**: This project builds the Maven Tools project locally. In general, this is a major complexity that you should
avoid for "normal" projects. Instead, you should follow a "paved road" and use only software tooling that is generally
available, tested, documented and has a commitment to maintenance. The Maven Tools 6.x line is not published to Maven
Central.

For this reason, this project is not included in the `settings.gradle.kts` file in the root of `hibernate-playground`.
This project is not a good example to follow.

Hopefully, this fact changes. I would like to leverage the good tooling in Hibernate Tools for normal projects

---

## Description

The [Hibernate Tools](https://hibernate.org/tools/) project contains various tools that make your Hibernate development
more sophisticated. These tools are generally geared to support a suite of Eclipse IDE plugins, but they can be used
in a headless way. That's exactly what the `hibernate-playground/codegen` does. It uses the [`hibernate-tools-orm`](https://github.com/hibernate/hibernate-tools/tree/d362340d5fad529ba25eba7123e2c007d0feb014/orm)
library to generate Hibernate entity classes (Java source code). Specifically, it does this by creating a simple
Gradle plugin and task named `hibernateCodeGen`. In fact, the Hibernate Tools project defines Ant tasks and a Maven
plugin for doing the same thing. I want to use Gradle, so I modeled my plugin after those but ignored all extensibility
features because this plugin is just for me.

For some reason, the [Hibernate Tools artifacts end at 5.x](https://mvnrepository.com/artifact/org.hibernate/hibernate-tools)
even though the project matches the same release scheme as the core Hibernate project which is on version 6.1.1.Final as
of July 2022. On closer inspection, it looks like the Maven coordinates changed and that the `hibernate-tools-orm` artifact
is [published here](https://mvnrepository.com/artifact/org.hibernate.tool/hibernate-tools-orm) but artifacts exist only
for beta versions of 6.x. I think this is where the artifacts are designed to be published (if you look at the [main branch POM file](https://github.com/hibernate/hibernate-tools/blob/d362340d5fad529ba25eba7123e2c007d0feb014/orm/pom.xml#L31))
but maybe something in the deployment and release process changed.

So, long story short, I've built the `hibernate-tools` project on my computer, at version 6.1.1.Final, and am depending
on the artifact via a Gradle `mavenLocal` repository (this means the `.m2/` directory).


## Instructions

Follow these instructions to run the demo:

1. Build the *Maven Tools* project
    * This is an unfortunate requirement. I won't bother with instructions because you shouldn't actually do this in a
      real project. In other words, *don't try this at home*. But, I've already done the work. 
2. Start the Postgres database:
    * ```shell
      docker-compose up --renew-anon-volumes --detach
      ```
    * This will start the dataabse and initialize the schema and sample data.
3. Use Java 17
4. Codegen the entity classes
   * This step is only needed if you have changed the database schema. For example, if you added a new column to an
     existing table, then you'll need to regenerate the Java entity class for that table so that it can map to that column.
     This project hooks into the *Hibernate Tools* codegen library by way of a Gradle plugin and task. Run the following
     command to generate the entity code.
   * ```shell
     ./gradlew hibernateCodeGen
     ```
5. Run the program:
   * ```shell
     ./gradlew run
     ```
   * Altogether, it should look like this:
     ```text
     ❯ ./gradlew run
     
     > Task :run
     11:58:16 [main] WARN org.hibernate.orm.connections.pooling - HHH10001002: Using built-in connection pool (not intended for production use)
     11:58:16 [main] INFO SQL dialect - HHH000400: Using dialect: org.hibernate.dialect.PostgreSQLDialect
     11:58:17 [main] DEBUG org.hibernate.SQL - 
         /* <criteria> */ select
             o1_0.id,
             o1_0.observation,
             o2_0.id,
             o2_0.description 
         from
             public.observations o1_0 
         join
             public.observation_types o2_0 
                 on o2_0.id=o1_0.type
     11:58:17 [main] INFO dgroomes.App - [Query using HQL] Found results...
     11:58:17 [main] INFO dgroomes.App - Observation (id=1, type=Uninteresting observation): The sky is blue
     11:58:17 [main] INFO dgroomes.App - Observation (id=2, type=Interesting observation): The speed of light can circle the earth 7 times in a second
     11:58:17 [main] INFO dgroomes.App - 
     ```
6. Stop the database:
    * ```shell
      docker-compose down
      ```


## Reference

* [Hibernate Tools docs](https://hibernate.org/tools/)
