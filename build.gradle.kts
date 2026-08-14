plugins {
    java
    id("org.jetbrains.intellij.platform") version "2.10.5"
}

group = "io.github.kafuuneko.asdiff"
version = "1.2.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        local(providers.gradleProperty("androidStudioPath"))
    }

    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.2")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

intellijPlatform {
    buildSearchableOptions = false

    pluginConfiguration {
        name = "AsDiff"

        ideaVersion {
            sinceBuild = "261"
            untilBuild = "261.*"
        }
    }

    pluginVerification {
        ides {
            local(providers.gradleProperty("androidStudioPath"))
        }
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release = 21
    }

    test {
        useJUnitPlatform()
    }

}
