plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

group = "com.malliaridis.univention"

dependencies {
    compileOnly(libs.detekt.plugin)
}

gradlePlugin {
    plugins.create(project.name) {
        id = "com.malliaridis.gradle.setup"
        implementationClass = "com.malliaridis.gradle.GradleSetupPlugin"
    }
}
