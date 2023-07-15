plugins {
    application
    id("org.hibernate.orm") version "6.2.6.Final"
    id("dgroomes.hibernate-code-gen")
}

repositories {
    mavenCentral()
}

val slf4jVersion = "2.0.7" // SLF4J releases: http://www.slf4j.org/news.html
val postgresVersion = "42.6.0" // Postgres JDBC releases: https://jdbc.postgresql.org/ and https://search.maven.org/artifact/org.postgresql/postgresql
val hibernateVersion = "6.2.6.Final" // Hibernate ORM releases: https://hibernate.org/orm/releases/

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
