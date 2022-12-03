# caching

NOT YET IMPLEMENTED

Supporting read-heavy applications with Hibernate's *second-level caching* feature.


## Description

How exactly does Hibernate hold onto data? When does Hibernate decide to re-fetch data? When does Hibernate decide to
throw an exception because the data is too stale? I don't even know the right questions to ask. I just need to start
exploring and understanding this topic. This is an example project that aims to shine a bright light on the behavior
of Hibernate's caching behavior by supplying a top-down example Java program with SQL-statement logging. 


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
     
     ... TODO ...
     ```


## Reference

* [Hibernate docs: *Caching*](https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#caching)
  * > The persistence context is also called the ***first-level cache***, and it’s enabled by default.
  * > At runtime, Hibernate handles moving data into and out of the ***second-level cache*** in response to the operations performed by the Session, which acts as a transaction-level cache of persistent data.
