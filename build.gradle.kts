plugins {
    java
    id("org.jetbrains.intellij.platform") version "2.10.5"
}

group = "io.github.kafuuneko.asdiff"
version = "1.2.2"

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

    signing {
        certificateChainFile = layout.projectDirectory.file(".release/chain.crt")
        privateKeyFile = layout.projectDirectory.file(".release/private.pem")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD").orElse(
            providers.fileContents(layout.projectDirectory.file(".release/signing-password"))
                .asText
                .map { it.trim() }
        )
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
    }

    pluginConfiguration {
        name = "AsDiff"

        ideaVersion {
            sinceBuild = "261"
            // Opt out of the default upper bound derived from the compile target.
            untilBuild = provider { null }
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

    named("verifyPluginSignature") {
        dependsOn("signPlugin")
    }
}
