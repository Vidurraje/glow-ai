/**
 * Copyright (c) 2023 Mindstix Software Labs
 * All rights reserved.
 */
package com.mindstix.baseline.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mindstix.capabilities.presentation.navigation.BaseComponentState
import com.mindstix.capabilities.presentation.navigation.Destinations
import com.mindstix.home.view.HomeScreen
import com.mindstix.home.view.ProfileScreen
import com.mindstix.home.view.SettingsScreen
import com.mindstix.onboarding.navigation.LoginScreenDestination
import com.mindstix.onboarding.view.SplashScreen
import com.mindstix.onboarding.viewModels.LoginViewModel
import org.json.JSONObject

/**
 * Defines the navigation graph for the Splash screen.
 *
 * @author Abhijeet Kokane
 */
fun NavGraphBuilder.splashNavigationGraph(
    navController: NavHostController,
    baseComponentState: BaseComponentState,
) {
    composable(
        route = Destinations.SplashDestination.route,
        // Enable Deeplink to this screen
        // deepLinks = listOf(navDeepLink { uriPattern = "example://splash" }),
        // Arguments to be passed from the current screen to the next destination
        arguments =
        listOf(
            navArgument("exampleArg") {
                type = NavType.StringType
            },
        ),
        // Animation for this screen
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End,
            )
        },
    ) { _ ->

        // Hide bottom bar and floating action button for the splash screen
        hideBottomBar(baseComponentState)
        hideFloatingActionButton(baseComponentState)

        // Display the SplashScreen composable
        SplashScreen()
    }
}

/**
 * Defines the navigation graph for the Login screen.
 */
fun NavGraphBuilder.loginNavigationGraph(
    navController: NavHostController,
    baseComponentState: BaseComponentState,
) {
    composable(Destinations.LoginDestination.route) { _ ->
        // Hide bottom bar and floating action button for the login screen
        hideBottomBar(baseComponentState)
        hideFloatingActionButton(baseComponentState)

        val viewModel: LoginViewModel = hiltViewModel()
        val viewState by viewModel.viewState.collectAsState()
        val effect = viewModel.effect
        val context = LocalContext.current
        val jsonArguments = JSONObject()
        val args = jsonArguments.toString()

        // Display the LoginScreenDestination composable
        LoginScreenDestination(
            context = context,
            loginViewModel = viewModel,
            loginViewState = viewState.loginViewState,
            navEffect = effect,
            navController = navController,
        )
    }
}

/**
 * Defines the navigation graph for the Home screen.
 */
fun NavGraphBuilder.homeNavigationGraph(
    navController: NavHostController,
    baseComponentState: BaseComponentState,
) {
    composable(Destinations.HomeDestination.route) { _ ->

        // Show bottom bar and hide floating action button for the home screen
        hideFloatingActionButton(baseComponentState)

        // Display the HomeScreen composable
        HomeScreen()
    }
}

/**
 * Defines the navigation graph for the Profile screen.
 */
fun NavGraphBuilder.profileNavigationGraph(
    navController: NavHostController,
    baseComponentState: BaseComponentState,
) {
    composable(Destinations.ProfileDestination.route) { _ ->

        // Show bottom bar and floating action button for the profile screen
        showBottomBar(baseComponentState)
        showFloatingActionButton(baseComponentState)

        // Display the ProfileScreen composable
        ProfileScreen()
    }
}

/**
 * Defines the navigation graph for the Settings screen.
 */
fun NavGraphBuilder.settingsNavigationGraph(
    navController: NavHostController,
    baseComponentState: BaseComponentState,
) {
    composable(Destinations.SettingsDestination.route) { _ ->

        // Show bottom bar and hide floating action button for the settings screen
        showBottomBar(baseComponentState)
        hideFloatingActionButton(baseComponentState)

        // Display the SettingsScreen composable
        SettingsScreen()
    }
}

/**
 * Utility function to hide the bottom navigation bar.
 */
fun hideBottomBar(baseComponentState: BaseComponentState) {
    baseComponentState.displayBottomNavigationBar.value = false
}

/**
 * Utility function to show the bottom navigation bar.
 */
fun showBottomBar(baseComponentState: BaseComponentState) {
    baseComponentState.displayBottomNavigationBar.value = true
}

/**
 * Utility function to hide the floating action button.
 */
fun hideFloatingActionButton(baseComponentState: BaseComponentState) {
    baseComponentState.displayFloatingActionButton.value = false
}

/**
 * Utility function to show the floating action button.
 */
fun showFloatingActionButton(baseComponentState: BaseComponentState) {
    baseComponentState.displayFloatingActionButton.value = true
}
