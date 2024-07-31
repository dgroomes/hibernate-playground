plugins {
    application
    alias(libs.plugins.hibernate)
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
