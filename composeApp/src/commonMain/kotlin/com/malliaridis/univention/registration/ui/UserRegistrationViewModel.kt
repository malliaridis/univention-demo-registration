package com.malliaridis.univention.registration.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.malliaridis.univention.registration.RegistrationScene

class UserRegistrationViewModel: ViewModel() {
    val backStack: SnapshotStateList<RegistrationScene> =
        mutableStateListOf(RegistrationScene.WelcomeScene)

    fun onRestart() = backStack.apply {
        clear()
        add(RegistrationScene.WelcomeScene)
    }

    fun onBack() = backStack.removeLastOrNull()

    fun onStartRegistration() {
        backStack.add(RegistrationScene.RegistrationFormScene)
    }

    fun onCompleteRegistration() {
        // Replace the last element (registration form) to prevent back navigation to form
        backStack[backStack.lastIndex] = RegistrationScene.SuccessScene
    }

    fun onFailRegistration(message: String) {
        // Replace the last element (registration form) to prevent back navigation to form
        // This case of failure is critical and should prevent the user from retrying.
        backStack[backStack.lastIndex] = RegistrationScene.FailureScene(message)
    }
}
