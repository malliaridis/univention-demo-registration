package com.malliaridis.univention.registration.ui

import androidx.compose.runtime.Composable
import com.github.terrakok.navigation3.browser.ChronologicalBrowserNavigation
import com.github.terrakok.navigation3.browser.buildBrowserHistoryFragment
import com.github.terrakok.navigation3.browser.getBrowserHistoryFragmentName
import com.github.terrakok.navigation3.browser.getBrowserHistoryFragmentParameters
import com.malliaridis.univention.registration.RegistrationScene

/**
 * TODO Add state serialization and deserialization
 */
@Composable
internal actual fun UserRegistrationNavigation(
    viewModel: UserRegistrationViewModel,
) = ChronologicalBrowserNavigation(
    backStack = viewModel.backStack,
    saveKey = { scene ->
        when (scene) {
            is RegistrationScene.WelcomeScene ->
                buildBrowserHistoryFragment(name = "welcome")
            is RegistrationScene.RegistrationFormScene ->
                buildBrowserHistoryFragment(name = "register")
            is RegistrationScene.SuccessScene ->
                buildBrowserHistoryFragment(name = "registration-completed")
            is RegistrationScene.FailureScene -> buildBrowserHistoryFragment(
                name = "error",
                parameters = scene.message?.let { mapOf("error" to it) } ?: emptyMap(),
            )
        }
    },
    restoreKey = { fragment ->
        val parameters = getBrowserHistoryFragmentParameters(fragment)
        when (getBrowserHistoryFragmentName(fragment)) {
            "welcome" -> RegistrationScene.WelcomeScene
            "register" -> RegistrationScene.RegistrationFormScene
            "registration-completed" -> RegistrationScene.SuccessScene
            "error" -> RegistrationScene.FailureScene(parameters["error"])
            else -> null
        }
    },
)
