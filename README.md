# hibernate-playground

📚 Learning and exploring Hibernate ORM (the Java-based Object Relational Mapping framework).

> Hibernate ORM
>
> Your relational data. Objectively.
>
> -- <cite>https://hibernate.org/orm</cite>


## Description

I want to lean Hibernate better. It can be complex because ORM itself is tricky and because Hibernate has developed a
rich feature set over many years (along with a rich set of idiosyncrasies). I will use the JPA (`jakarta`) annotations
when it is idiomatic but I will generally prefer to learn Hibernate directly instead of by sticking strictly to the JPA
APIs.

**NOTE**: This project was developed on macOS. It is for my own personal use.


## Standalone sub-projects

This repository illustrates different concepts, patterns and examples via standalone sub-projects. Each sub-project is
completely independent of the others and do not depend on the root project. This _standalone sub-project constraint_
forces the sub-projects to be complete and maximizes the reader's chances of successfully running, understanding, and
re-using the code.

The sub-projects include:


### `basic/`

A basic demo of Hibernate ORM.

See the README in [basic/](basic/).


### `hql/`

An illustration of Hibernate's *Hibernate Query Language* (HQL).

See the README in [hql/](hql/).


### `criteria/`

An illustration of the Criteria API within the Jakarta Persistence API.

See the README in [criteria/](criteria/).


### `caching/`

NOT YET IMPLEMENTED

Supporting read-heavy applications with Hibernate's *second-level caching* feature.

See the README in [caching/](caching/).


### `sqlite/`

Connect Hibernate to a SQLite database.

See the README in [sqlite/](sqlite/).


## WishList

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Implement `basic/`
* [x] DONE Implement a `joins/` project
* [x] DONE Consider using Hibernate Tools, try out the codegen. I like the jOOQ codegen, for example.
* [x] DONE Create a SQLite-specific project to showcase the interesting things with a Hibernate-to-SQLite integration:
      specifically the community dialects.
* [x] DONE Consider splitting `joins/` into subprojects `hql/` and `criteria/`. This way, we can contrast the differences more easily.
      Specifically, I want to contrast the "N + 1 selection problem". With the Criteria API, we have the option to get
      a nice "full fetch". But with HQL, we are stuck with subsequent fetches.
* [ ] Create a `caching/` project. I want to learn second-level caching. This is an advanced topic and I know it is full
  of pitfalls. I want to explore it in a controlled environment and understand how I can leverage second-level caching
  for read-heavy systems that can tolerate minutes/hours/days duration of stale data.


## Reference

* [GitHub repo: `jooq-playground`](https://github.com/dgroomes/jooq-playground)
  * This is another project of my own. Compare `jooq-playground` with `hibernate-playground` as a way to compare jOOQ
    and Hibernate. 
