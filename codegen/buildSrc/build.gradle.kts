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

// For some reason, the Hibernate Tools artifacts end at 5.x even though the project matches the same release scheme
// as the core Hibernate project which is on 6.1.1.Final as of July 2022.
//
// So, just use the latest available.
//
// Hibernate Tools releases: https://mvnrepository.com/artifact/org.hibernate/hibernate-tools
val hibernateToolsVersion = "5.6.2.Final"

val postgresVersion = "42.3.3" // Postgres JDBC releases: https://jdbc.postgresql.org/ and https://search.maven.org/artifact/org.postgresql/postgresql

dependencies {
    implementation("org.hibernate:hibernate-tools:$hibernateToolsVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")
}
