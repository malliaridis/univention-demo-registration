package com.malliaridis.univention.registration.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Content for displaying a registration failure result with an error message and restart button.
 *
 * This composable is used for critical registration failures that cannot be handled by the registration form directly.
 *
 * @param onRestart Callback invoked when the restart button is clicked.
 * @param error Optional error message to display.
 * @param modifier Modifier to be applied to the layout.
 */
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

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onRestart,
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        Text("Start over again")
    }
}
