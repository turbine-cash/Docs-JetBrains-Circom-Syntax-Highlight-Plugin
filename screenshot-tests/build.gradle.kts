plugins {
    kotlin("jvm") version "1.9.22"
}

group = "cash.turbine.circom.docs"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { url = uri("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies") }
}

dependencies {
    testImplementation("com.intellij.remoterobot:remote-robot:0.11.21")
    testImplementation("com.intellij.remoterobot:remote-fixtures:0.11.21")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    systemProperty("screenshot.output.dir", "$projectDir/output/images")
    systemProperty("screenshot.manifest", "$projectDir/output/screenshots.json")
    systemProperty("robot.host", System.getProperty("robot.host", "127.0.0.1"))
    systemProperty("robot.port", System.getProperty("robot.port", "8082"))
}

kotlin {
    jvmToolchain(21)
}

tasks.register("captureScreenshots") {
    group = "documentation"
    description = "Capture all screenshots for documentation"
    dependsOn("test")
}
