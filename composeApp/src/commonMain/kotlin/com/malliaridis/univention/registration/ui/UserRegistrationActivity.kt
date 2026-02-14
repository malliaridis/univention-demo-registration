package com.malliaridis.univention.registration.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.malliaridis.univention.registration.RegistrationScene
import com.malliaridis.univention.registration.di.RegistrationComponent
import org.jetbrains.compose.resources.painterResource
import univention_demo_registration.composeapp.generated.resources.Res
import univention_demo_registration.composeapp.generated.resources.github

/**
 * Activity for the user registration flow.
 *
 * This activity provides the top-level navigation and UI for the user registration process.
 *
 * @param component Registration component providing dependencies for the registration form.
 * @param modifier Modifier to be applied to the layout.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRegistrationActivity(
    component: RegistrationComponent,
    modifier: Modifier = Modifier,
) = Scaffold(
    modifier = modifier,
    topBar = { UserRegistrationHeader() },
    contentWindowInsets = WindowInsets.systemBars.exclude(WindowInsets.navigationBars),
) { innerPadding ->
    val viewModel = viewModel { component.createUserRegistrationViewModel() }

    UserRegistrationNavigation(viewModel)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding),
    ) {
        val cardModifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)

        NavDisplay(
            modifier = Modifier
                .widthIn(max = 512.dp)
                .align(Alignment.Center),
            backStack = viewModel.backStack,
            onBack = viewModel::onBack,
            entryProvider = entryProvider {
                entry<RegistrationScene.WelcomeScene> {
                    RegistrationWelcomeContent(
                        modifier = cardModifier,
                        onRegister = viewModel::onStartRegistration,
                    )
                }

                entry<RegistrationScene.RegistrationFormScene> {
                    RegistrationFormRoute(
                        viewModel = viewModel { component.createRegistrationFormViewModel() },
                        modifier = cardModifier,
                        onRegistrationSuccess = viewModel::onCompleteRegistration,
                        onRegistrationFailure = viewModel::onFailRegistration,
                    )
                }
                entry<RegistrationScene.SuccessScene> {
                    RegistrationResultSuccessContent(
                        modifier = cardModifier,
                        onRestart = viewModel::onRestart,
                    )
                }
                entry<RegistrationScene.FailureScene> {
                    RegistrationResultFailureContent(
                        modifier = cardModifier,
                        onRestart = viewModel::onRestart,
                        error = it.message,
                    )
                }
            }
        )
    }
}

/**
 * Top-level header for the user registration activity.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserRegistrationHeader(
    modifier: Modifier = Modifier,
) = TopAppBar(
    modifier = modifier,
    title = { Text("Univention - User Registration") },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ),
    actions = {
        val uriHandler = LocalUriHandler.current
        IconButton(onClick = {
            uriHandler.openUri("https://github.com/malliaridis/univention-demo-registration")
        }) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(Res.drawable.github),
                contentDescription = "GitHub",
            )
        }
    }
)
