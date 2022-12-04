plugins {
    application
}

repositories {
    mavenCentral()
}

val slf4jVersion = "2.0.4" // SLF4J releases: http://www.slf4j.org/news.html
val logbackVersion = "1.4.5" // Logback releases: http://logback.qos.ch/download.html
val h2Version = "2.1.214" // H2 releases: https://github.com/h2database/h2database/releases
val hibernateVersion = "6.1.5.Final" // Hibernate ORM releases: https://hibernate.org/orm/releases/
val caffeineVersion = "3.1.2" // Caffeine releases: https://github.com/ben-manes/caffeine/releases

dependencies {
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("com.h2database:h2:$h2Version")
    implementation("org.hibernate.orm:hibernate-core:$hibernateVersion")
    implementation("org.hibernate.orm:hibernate-jcache:$hibernateVersion")
    implementation("com.github.ben-manes.caffeine:jcache:$caffeineVersion")
}

application {
    mainClass.set("dgroomes.App")
}
