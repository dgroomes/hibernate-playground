plugins {
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("hibernateCodeGenPlugin") {
            id = "dgroomes.hibernate-code-gen"
            implementationClass = "dgroomes.HibernateCodeGenPlugin"
        }
    }
}

repositories {
    // I need 'mavenLocal' because the Maven Tools 6.x release is not published to Maven Central. So I've built it on my
    // computer instead.
    mavenLocal()

    mavenCentral()
}

val hibernateVersion = "6.1.1.Final" // Hibernate ORM releases: https://hibernate.org/orm/releases/
val postgresVersion = "42.3.3" // Postgres JDBC releases: https://jdbc.postgresql.org/ and https://search.maven.org/artifact/org.postgresql/postgresql

dependencies {
    implementation("org.hibernate.tool:hibernate-tools-orm:$hibernateVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")
}
