package com.malliaridis.gradle

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.DetektCreateBaselineTask
import dev.detekt.gradle.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

fun Project.setupDetekt() {
    allprojects {
        plugins.apply("dev.detekt")

        extensions.configure<DetektExtension> {
            // Builds the AST in parallel. Rules are always executed in parallel.
            // Can lead to speedups in larger projects. `false` by default.
            parallel.set(true)

            // Applies the config files on top of detekt's default config file. `false` by default.
            buildUponDefaultConfig.set(true)

            // Define the detekt configuration(s) you want to use.
            // Defaults to the default detekt configuration.
            config.setFrom(rootDir.resolve("config/detekt/detekt.yml"))

            // Specifying a baseline file. All findings stored in this file in
            // subsequent runs of detekt.
            baseline.set(rootDir.resolve("config/detekt/baseline.xml"))

            // Specify the base path for file paths in the formatted reports.
            // If not set, all file paths reported will be absolute file path.
            basePath.set(rootDir)
        }

        tasks.withType<Detekt> {
            setSource(files(project.projectDir))
            exclude("**/build/**")
            exclude {
                it.file.relativeTo(projectDir).startsWith("build")
            }
        }

        dependencies {
            // TODO Use reference instead of hardcoded value
            dependencies.add("detektPlugins", "io.nlopez.compose.rules:detekt:0.5.6")
        }
    }

    registerDetektTasks()
}

private fun Project.registerDetektTasks() {
    tasks.register("detektAll") {
        description = "Run detekt analysis for all modules"
        group = "verification"

        dependsOn(tasks.withType<Detekt>())
    }

    tasks.register<DetektCreateBaselineTask>("detektProjectBaseline") {
        description = "Overrides current baseline."
        group = "verification"

        buildUponDefaultConfig.set(true)
        ignoreFailures.set(true)
        parallel.set(true)
        setSource(files(rootDir))
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        baseline.set(file("$rootDir/config/detekt/baseline.xml"))
        include("**/*.kt")
        include("**/*.kts")
        exclude("**/resources/**")
        exclude("**/build/**")
    }
}
