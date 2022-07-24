# codegen

NOT YET IMPLEMENTED

Use the *Hibernate Tools* library to codegen Java source code (entities) from an existing database schema.


## Instructions

Follow these instructions to run the demo:

1. Start the Postgres database:
    * ```shell
      docker-compose up --renew-anon-volumes --detach
      ```
    * This will start the dataabse and initialize the schema and sample data.
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
     todo
     ```
5. Stop the database:
    * ```shell
      docker-compose down
      ```


## Reference

* [Hibernate Tools docs](https://hibernate.org/tools/)
