plugins {
    application
    id("org.hibernate.orm") version "6.1.1.Final"
    id("dgroomes.hibernate-code-gen")
}

repositories {
    mavenCentral()
}

val slf4jVersion = "1.7.36" // SLF4J releases: http://www.slf4j.org/news.html
val postgresVersion = "42.3.3" // Postgres JDBC releases: https://jdbc.postgresql.org/ and https://search.maven.org/artifact/org.postgresql/postgresql
val hibernateVersion = "6.1.1.Final" // Hibernate ORM releases: https://hibernate.org/orm/releases/

dependencies {
    // Generate the metamodel classes
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:$hibernateVersion")

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")
    implementation("org.hibernate.orm:hibernate-core:$hibernateVersion")
}

application {
    mainClass.set("dgroomes.App")
}
