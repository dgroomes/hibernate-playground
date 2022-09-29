# basic

A basic demo of Hibernate ORM.


## Description

This project defines a Java program that connects to an embedded H2 database using Hibernate.


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
     22:53:03 [main] WARN org.hibernate.orm.connections.pooling - HHH10001002: Using built-in connection pool (not intended for production use)
     22:53:03 [main] INFO SQL dialect - HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
     22:53:04 [main] DEBUG org.hibernate.SQL - select o1_0.id,o1_0.observation from observations o1_0
     22:53:04 [main] INFO dgroomes.App - The HQL query found results...
     22:53:04 [main] INFO dgroomes.App - Observation (1): The sky is blue
     22:53:04 [main] INFO dgroomes.App - Observation (2): The speed of light can circle the earth 7 times in a second
     22:53:04 [main] INFO dgroomes.App - 
     22:53:04 [main] DEBUG org.hibernate.SQL - select * from observations
     22:53:04 [main] INFO dgroomes.App - The hand-written SQL query found results...
     22:53:04 [main] INFO dgroomes.App - Observation (1): The sky is blue
     22:53:04 [main] INFO dgroomes.App - Observation (2): The speed of light can circle the earth 7 times in a second
     22:53:04 [main] INFO dgroomes.App -
     ```


## Notes

At first glance (about 30 minutes into it), Hibernate is looking pretty good to me. At least, the project appears highly
active, the docs are extensive and more navigable than I remember, the website makes it clear what the different top-level
projects are (ORM, Tools, Search, Validator), etc. It's definitely still a verbose tool to use, in terms of boilerplate
setup code. And that's not necessarily a bad thing because it also comes with flexibility. But, I'm having to dive into
the [Native Bootstrapping](https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#bootstrap-native)
part of the user guide just to figure out "How do I set the JDBC url to my local SQLite file?". There's lots of info I
don't need in the doc, but I'll read it.

Here is a sign of age: <https://github.com/hibernate/hibernate-orm/releases>. The GitHub repo page still includes the
"Releases" section. This is a common pitfall for projects that once used GitHub "Releases" to demarcate new releases
but have since adopted a different strategy. The "Releases" section is thus a trap for newcomers who think that the 6 year
old release on the frontpage is actually the latest release, when it's really not.

Another sign of age. The [*Hibernate Getting Started Guide*](https://docs.jboss.org/hibernate/orm/current/quickstart/html_single/#obtaining)
says to download the Hibernate ZIP file from Sourceforge. But it looks like Sourceforge only went up to Hibernate 6.0.0
and maybe they've since abandoned it. Hibernate 6.1.1.Final was releases July 2022. I really want to download the ZIP
because it apparently has a runnable example in a directory named `basic/`. I really want a runnable example because I
can't figure out where to put my JDBC URL (I'm lost. I am supposed to use `hibernate.cfg.xml`? I can't find an example of
that in the docs, so I'm thinking it's not idiomatic to use one anymore?). UPDATE: ok I finally gave up and just googled
it and yes you can, [here is the answer](https://stackoverflow.com/a/33067329/).

Here is my first exception I'm not sure what do about (before Googling, that is):

> Exception in thread "main" java.lang.UnsupportedOperationException: The application must supply JDBC connections

StackOverflow let's me know that this just means you need the configuration `hibernate.connection.url`. I was missing the
`hibernate.` leading part because this is how it is shown in the [*Hibernate Getting Started Guide*](https://docs.jboss.org/hibernate/orm/current/quickstart/html_single/#hibernate-gsg-tutorial-basic-config). 

Why does Hibernate have its own logging sub-system? I see logs like this:

```text
Jul 21, 2022 8:28:05 PM org.hibernate.Version logVersion
INFO: HHH000412: Hibernate ORM core version 6.1.1.Final
```

But I'm using SLF4J (and `slf4j-simple`) and a specific logging configuration in `simplelogger.properties`. In general,
you expect that mature Java libraries and frameworks interop with traditional logging setups like this. 

I don't see anything about the unwelcome Hibernate logging in the user guide. Update: again, someone else to the rescue
via a StackOverflow question and [answer](https://stackoverflow.com/a/19488546).

I was assuming `MetadataSources.addPackage` would work, but it didn't. I also can't find docs on it. I did `.addAnnotatedClass(Observation.class)`
which worked.


## WishList

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Implement a raw SQL example
* [x] NOT POSSIBLE (Apparently this is not a feature of Hibernate. See [this post](https://stackoverflow.com/q/1413190))
      Can we specify all the entity types by package instead of class-by-class?


## Reference

* [Hibernate ORM docs: *Hibernate Getting Started Guide*](https://docs.jboss.org/hibernate/orm/6.1/quickstart/html_single/#tutorial_annotations)
* [Hibernate ORM: *User Guide* > *Native Bootstrapping*](https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#bootstrap-native)
  > You will almost always need to configure the `StandardServiceRegistry`, which is done through
    `org.hibernate.boot.registry.StandardServiceRegistryBuilder`.
