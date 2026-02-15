package com.malliaridis.univention.registration.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegistrationResultFailureContent(
    onRestart: () -> Unit,
    error: String? = null,
    modifier: Modifier = Modifier,
) = RegistrationCard(
    modifier = modifier,
    contentPadding = PaddingValues(all = 16.dp),
) {
    Text(
        text = "Registration failed!",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.error,
    )

    Text(
        text = "An error occurred during the registration process Please try again.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error,
    )

    error?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
        )
    }

    Button(onClick = onRestart) {
        Text("Start over again")
    }
}
