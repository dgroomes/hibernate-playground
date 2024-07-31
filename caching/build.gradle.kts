plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.slf4j.api)
    implementation(libs.logback)
    implementation(libs.h2)
    implementation(libs.hibernate.core)
    implementation(libs.hibernate.jcache)
    implementation(libs.caffeine.jcache)
}

application {
    mainClass.set("dgroomes.caching.App")
}
