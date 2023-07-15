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
    mavenCentral()
}

val hibernateVersion = "6.2.6.Final" // Hibernate ORM releases: https://hibernate.org/orm/releases/
val postgresVersion = "42.6.0" // Postgres JDBC releases: https://jdbc.postgresql.org/ and https://search.maven.org/artifact/org.postgresql/postgresql

dependencies {
    implementation("org.hibernate.tool:hibernate-tools-orm:$hibernateVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")
}
