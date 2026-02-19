import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    jvm()

    js {
        browser {
            commonWebpackConfig {
                val ds = devServer ?: KotlinWebpackConfig.DevServer()
                val proxies = ds.proxy ?: mutableListOf()

                proxies.add(
                    KotlinWebpackConfig.DevServer.Proxy(
                        context = mutableListOf("/api"),
                        target = "http://127.0.0.1:3000",
                        changeOrigin = true,
                        secure = false,
                    )
                )

                ds.proxy = proxies
                devServer = ds
            }
        }
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                val ds = devServer ?: KotlinWebpackConfig.DevServer()
                val proxies = ds.proxy ?: mutableListOf()

                proxies.add(
                    KotlinWebpackConfig.DevServer.Proxy(
                        context = mutableListOf("/api"),
                        target = "http://127.0.0.1:3000",
                        changeOrigin = true,
                        secure = false,
                    )
                )

                ds.proxy = proxies
                devServer = ds
            }
        }
        binaries.executable()
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexplicit-backing-fields")
    }

    sourceSets {
        sourceSets.all {
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
        }
        commonMain.dependencies {
            implementation(projects.shared)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.lifecycle.viewModelNav3)
            implementation(libs.androidx.navigation3.ui)
            implementation(libs.androidx.material3.adaptive.asProvider())
            implementation(libs.androidx.material3.adaptive.nav3)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.client.engine.cio)
            implementation(libs.ktor.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
        webMain.dependencies {
            implementation(libs.navigation3.browser)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.malliaridis.univention.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.malliaridis.univention"
            packageVersion = "1.0.0"
        }
    }
}
