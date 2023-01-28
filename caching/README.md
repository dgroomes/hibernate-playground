# caching

Illustrating Hibernate's caching behavior by focussing on trace-level logs.


## Description

How exactly does Hibernate hold onto data? When does Hibernate decide to re-fetch data? When does Hibernate decide to
throw an exception because the data is too stale? I don't even know the right questions to ask. I just need to start
exploring and understanding this topic. This is an example project that aims to shine a bright light on the behavior
of Hibernate's caching behavior by supplying a top-down example Java program with SQL-statement logging.

This example leans on logging to illuminate the going-ons of Hibernate's behavior. Please be diligent and patient as
you read the logs. Our eyes often glaze over when looking at an overwhelming wall of framework logs. Especially as your
usage of Hibernate extends into features like HQL, the Criteria API and other things, the logs become obtuse. But, if
you can be patient, you will be rewarded with a deeper understanding of how Hibernate works.


## Instructions

Follow these instructions to run the demo:

1. Use Java 17
2. Run the program:
   * ```shell
     ./gradlew run
     ```
3. Observe the logs
   * Specifically, get an understanding of the simulation scenarios written in the Java code and map the execution of
     these scenarios to the log output. You'll notice three distinct scenarios: 1) A **first-level cache hit** 2) a **first-level cache miss** and 3) a **second-level cache hit** 
   * You'll notice that in the first-level cache hit scenario, Hibernate makes only one SQL request to the database even
     though the program made two `EntityManager#find` calls. This is because the scenario executed over only one session
     and the object was cached in the first-level (session) cache.
   * The first-level cache miss scenario follows a similar pattern, but there is no cache hit because the scenario executes
     over two different sessions. This has the effect that the second request starts with a fresh session and therefore
     an empty first-level (session) cache. The second request realizes no benefit from the first request.
   * The final scenario executes similarly to the previous scenario except now the second-level cache is in the mix.
     Specifically, the first request causes the entity to be saved in the second-level cache (JCache/Caffeine). Notice
     the log:
     > 20:20:23 [main] DEBUG o.h.orm.results.loading.entity - (ENTITYRESULTINITIALIZER) Adding entityInstance to second-level cache: dgroomes.db.Observation#1
     
     Then, when the second request occurs, there is a cache hit. Notice the log:
     > 20:20:23 [main] DEBUG o.h.c.s.s.AbstractReadWriteAccess - Cache hit : region = `dgroomes.db.Observation`, key = `dgroomes.db.Observation#1`


## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Drop Criteria and just use simple `EntityManager#getReference` calls. This is more focused than the "find all"
  approach I was doing (I think).
* [x] DONE (That worked. I didn't commit the change though) Try to break the caching behavior by using `EntityManager#clear`.
* [x] DONE Illustrate "Same-session cache hits" and "Separate session cache misses". This is a building block for
  illustrating the second-level cache.
* [x] DONE (The [Hibernate docs](https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#architecture-current-session) explain it well. Usually a session is used for a user-driven action, but can also be used as user-level "conversations", and beyond that you can even misuse sessions like the 'session-per-application' anti-pattern) What is the expected use case of the session? (And, if I understand correctly, this is the same question as "What
  is the expected use case of the second-level cache?"). Is it only meant to serve a one-shot workflow, like a transactional
  workflow? Or, can you truly cache things in the traditional sense. Where a cached object serves later requests, where
  these requests are made by totally different users?
* [x] DONE Enable the second-level cache using the JCache/Caffeine provider. Showcase how the "cache miss" scenario now
  actually is a "cache hit". It hits the second-level cache.


## Reference

* [Hibernate docs: *Caching*](https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#caching)
  * > The persistence context is also called the *first-level cache*, and it’s enabled by default.
  * > At runtime, Hibernate handles moving data into and out of the *second-level cache* in response to the operations performed by the Session, which acts as a transaction-level cache of persistent data.
