package com.malliaridis.univention.registration

import androidx.compose.runtime.snapshots.SnapshotStateList

interface UserRegistrationViewModel {
    val backStack: SnapshotStateList<RegistrationScene>

    fun onRestart()

    fun onBack(): RegistrationScene?

    fun onStartRegistration()

    fun onCompleteRegistration()
}
