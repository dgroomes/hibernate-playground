# hql

An illustration of Hibernate's *Hibernate Query Language*.


## Description

The most uniquely impressive features of Hibernate (and other ORMs) is to turn row-based data into graph-based data (and
vice-versa). Specifically, this means turning the row-based data in the table of a SQL database (RDBMS) into object instances
in Java (object-to-object relationships form a graph). Across even a few tables, it would require lots of procedural code
to accomplish something similar if you weren't using an ORM. For single-table information systems, consider using straight
JDBC and SQL.

HQL is a powerful language that lets you re-use your existing SQL skills and get the full benefits of Hibernate's
object-relational mapping. This project implements a basic Java program that reads from a SQL database using HQL. 


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
     22:41:46 [main] WARN org.hibernate.orm.connections.pooling - HHH10001002: Using built-in connection pool (not intended for production use)
     22:41:46 [main] INFO SQL dialect - HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
     22:41:48 [main] INFO dgroomes.App - The HQL query found results...
     22:41:48 [main] INFO dgroomes.App - Observation (id=1, type=Uninteresting observation): The sky is blue
     22:41:48 [main] INFO dgroomes.App - Observation (id=2, type=Interesting observation): The speed of light can circle the earth 7 times in a second
     22:41:48 [main] INFO dgroomes.App -
     ```
