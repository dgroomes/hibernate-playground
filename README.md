# hibernate-playground

📚 Learning and exploring Hibernate ORM (the Java-based Object Relational Mapping framework).

> Hibernate ORM
>
> Your relational data. Objectively.
>
> -- <cite>https://hibernate.org/orm</cite>


## Description

I want to lean Hibernate better. It can be complex because ORM itself is tricky and because Hibernate has developed a
a rich feature set over many years (along with a rich set of idiosyncrasies). I will use the JPA (`jakarta`) annotations
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


## WishList

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Implement `basic/`
* [ ] Implement a `joins/` project


## Reference

* [GitHub repo: `jooq-playground`](https://github.com/dgroomes/jooq-playground)
  * This is another project of my own. Compare `jooq-playground` with `hibernate-playground` as a way to compare jOOQ
    and Hibernate. 
