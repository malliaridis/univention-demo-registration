package com.malliaridis.univention.registration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Content for displaying a registration success result with a restart button.
 *
 * @param onRestart Callback invoked when the restart button is clicked.
 * @param modifier Modifier to be applied to the layout.
 */
@Composable
fun RegistrationResultSuccessContent(
    onRestart: () -> Unit,
    modifier: Modifier = Modifier,
) = RegistrationCard(
    modifier = modifier,
    contentPadding = PaddingValues(all = 16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
) {
    Text(
        text = "Registration successful!",
        style = MaterialTheme.typography.titleLarge,
    )

    Text(
        text = "You have successfully registered on our platform.",
        style = MaterialTheme.typography.bodyMedium,
    )

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onRestart,
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        Text("Start over again")
    }
}
