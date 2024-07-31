plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple)
    implementation(libs.sqlite)
    implementation(libs.hibernate.core)
    implementation(libs.hibernate.community.dialects)
}

application {
    mainClass.set("dgroomes.sqlite.App")
}
