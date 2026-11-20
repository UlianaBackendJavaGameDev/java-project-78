plugins {
    java
    id("com.diffplug.spotless") version "6.25.0"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Зависимости для тестов (JUnit 5)
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

spotless {
    java {
        googleJavaFormat()
    }
}