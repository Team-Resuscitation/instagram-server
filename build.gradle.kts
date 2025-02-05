plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
<<<<<<< Updated upstream
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("plugin.jpa") version "1.9.25"
}


=======
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

>>>>>>> Stashed changes
group = "com.resuscitation"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
<<<<<<< Updated upstream
        languageVersion = JavaLanguageVersion.of(17)
    }
}


=======
        languageVersion = JavaLanguageVersion.of(21)
    }
}

>>>>>>> Stashed changes
repositories {
    mavenCentral()
}

dependencies {
<<<<<<< Updated upstream
=======
    // Spring Boot
>>>>>>> Stashed changes
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
<<<<<<< Updated upstream
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    val springDocVersion = "2.6.0"
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")
=======

    // database driver
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // JJWT
>>>>>>> Stashed changes
    val jjwtVersion = "0.12.6"
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")
<<<<<<< Updated upstream
=======

    // oauth
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // OpenAPI
    val swaggerVersion = "2.8.3"
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")
>>>>>>> Stashed changes
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
