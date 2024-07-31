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

val hibernateVersion = "6.5.2.Final" // Hibernate ORM releases: https://hibernate.org/orm/releases/
val postgresVersion = "42.7.3" // Postgres JDBC releases: https://jdbc.postgresql.org/changelogs/

dependencies {
    implementation("org.hibernate.tool:hibernate-tools-orm:$hibernateVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")
}
