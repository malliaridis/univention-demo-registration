package com.malliaridis.univention

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.malliaridis.univention.registration.di.DefaultRegistrationComponent
import com.malliaridis.univention.registration.ui.UserRegistrationActivity

/**
 * Shared application entry point used by platform-specific entry points.
 */
@Composable
fun App(modifier: Modifier = Modifier) {
    val registrationComponent by lazy {
        DefaultRegistrationComponent(httpClient = getHttpClient())
    }

    MaterialTheme {
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UserRegistrationActivity(
                component = registrationComponent,
            )
        }
    }
}
