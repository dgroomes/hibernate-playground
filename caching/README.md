# caching

Illustrating Hibernate's caching behavior by focussing on trace-level logs.


## Description

How exactly does Hibernate hold onto data? When does Hibernate decide to re-fetch data? When does Hibernate decide to
throw an exception because the data is too stale? I don't even know the right questions to ask. I just need to start
exploring and understanding this topic. This is an example project that aims to shine a bright light on the behavior
of Hibernate's caching behavior by supplying a top-down example Java program with SQL-statement logging.

This example leans on logging to illuminate the going-ons of Hibernate's behavior. Please be diligent and patient as
you read the logs. Our eyes often glaze over when looking at the overwhelming wall of framework logs. Especially as your
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
   * Specifically notice the logs for the "cache hit" scenario implemented by the call to `dgroomes.InteractionSimulator#querySameEntityTwiceBySameUser`
     and then the logs for the "cache miss" scenario implemented by the call to `dgroomes.InteractionSimulator#querySameEntityByTwoUsers`.  
   * You'll notice that in the "cache hit" scenario, Hibernate makes only one SQL request to the database even though the
     program made two `EntityManager#find` calls. This is because the object was in the session cache.
   * You'll notice that in the "cache miss" scenario, Hibernate makes two SQL requests to the database This is because the
     object was not in the second user's session cache. The user's have their own sessions. They are not shared.


## WishList

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
* [ ] Do something with the second-level cache. And which cache provider should I use?


## Reference

* [Hibernate docs: *Caching*](https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#caching)
  * > The persistence context is also called the ***first-level cache***, and it’s enabled by default.
  * > At runtime, Hibernate handles moving data into and out of the ***second-level cache*** in response to the operations performed by the Session, which acts as a transaction-level cache of persistent data.
