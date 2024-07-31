plugins {
    application
    alias(libs.plugins.hibernate)
    id("dgroomes.hibernate-code-gen")
}

repositories {
    mavenCentral()
}

dependencies {
    // Generate the metamodel classes
    annotationProcessor(libs.hibernate.jpa.modelgen)

    implementation(libs.slf4j.api)
    runtimeOnly(libs.slf4j.simple)
    implementation(libs.postgres.jdbc)
    implementation(libs.hibernate.core)
}

application {
    mainClass.set("dgroomes.codegen.App")
}
