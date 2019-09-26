plugins {
    java
    id("org.jetbrains.intellij") version "0.4.10"
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    group = "com.alan"
    version = "1.0-SNAPSHOT"
}

val projectRuntime: Configuration by configurations.creating

dependencies {
    projectRuntime(group = "org.easymock", name = "easymock", version = "2.0")
    projectRuntime(group = "org.mockito", name = "mockito-all", version = "1.10.19")
}

intellij {
    version = "2019.2"
    setPlugins("java")
}

val cleanProjectRuntime by tasks.registering(Delete::class) {
    delete("projectRuntimeLibs")
}

val copyProjectRuntime by tasks.registering(Copy::class) {
    into("projectRuntimeLibs")
    from(projectRuntime)
}

tasks {
    clean {
        dependsOn(cleanProjectRuntime)
    }
    test {
        dependsOn(copyProjectRuntime)
    }
}