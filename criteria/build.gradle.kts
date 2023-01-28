plugins {
    application
    id("org.hibernate.orm") version "6.1.6.Final"
}

repositories {
    mavenCentral()
}

dependencies {
    // Generate the metamodel classes
    annotationProcessor(libs.hibernate.jpamodelgen)

    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple)
    implementation(libs.h2)
    implementation(libs.hibernate.core)
}

application {
    mainClass.set("dgroomes.App")
}
