plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple)
    implementation(libs.h2)
    implementation(libs.hibernate.core)
}

application {
    mainClass.set("dgroomes.App")
}
