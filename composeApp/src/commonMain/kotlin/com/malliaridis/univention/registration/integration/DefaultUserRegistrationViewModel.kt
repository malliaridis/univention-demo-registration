package com.malliaridis.univention.registration.integration

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.malliaridis.univention.registration.RegistrationFormViewModel
import com.malliaridis.univention.registration.RegistrationScene
import com.malliaridis.univention.registration.UserRegistrationViewModel

class DefaultUserRegistrationViewModel(
    private val registrationForm: () -> RegistrationFormViewModel = { DefaultRegistrationFormViewModel() },
): ViewModel(), UserRegistrationViewModel {
    final override val backStack: SnapshotStateList<RegistrationScene> =
        mutableStateListOf(RegistrationScene.WelcomeScene)

    override fun onRestart() {
        TODO("Not yet implemented")
    }

    override fun onBack() = backStack.removeLastOrNull()

    override fun onStartRegistration() {
        backStack.add(
            RegistrationScene.RegistrationFormScene(
                viewModel = registrationForm(),
            )
        )
    }

    override fun onCompleteRegistration() {
        backStack.add(RegistrationScene.SuccessScene)
        // TODO Use Store instead and navigate on success / failure
    }
}
