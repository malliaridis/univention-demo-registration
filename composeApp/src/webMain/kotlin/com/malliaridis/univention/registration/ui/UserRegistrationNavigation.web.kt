package com.malliaridis.univention.registration.ui

import androidx.compose.runtime.Composable
import com.github.terrakok.navigation3.browser.ChronologicalBrowserNavigation
import com.github.terrakok.navigation3.browser.buildBrowserHistoryFragment
import com.github.terrakok.navigation3.browser.getBrowserHistoryFragmentName
import com.malliaridis.univention.registration.RegistrationScene
import com.malliaridis.univention.registration.UserRegistrationViewModel
import com.malliaridis.univention.registration.integration.DefaultRegistrationFormViewModel

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
            is RegistrationScene.WelcomeScene -> buildBrowserHistoryFragment("welcome")
            is RegistrationScene.RegistrationFormScene -> buildBrowserHistoryFragment("register")
            is RegistrationScene.SuccessScene -> buildBrowserHistoryFragment("registration-completed")
            is RegistrationScene.FailureScene -> buildBrowserHistoryFragment("error")
        }
    },
    restoreKey = { fragment ->
        when (getBrowserHistoryFragmentName(fragment)) {
            "welcome" -> RegistrationScene.WelcomeScene
            "register" -> RegistrationScene.RegistrationFormScene(DefaultRegistrationFormViewModel())
            "registration-completed" -> RegistrationScene.SuccessScene
            "error" -> RegistrationScene.FailureScene
            else -> null
        }
    },
)
