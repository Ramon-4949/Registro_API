package edu.ucne.registro_api.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.registro_api.presentation.planets_api.detail.PlanetDetailScreen
import edu.ucne.registro_api.presentation.planets_api.list.PlanetListScreen

@Composable
fun PlanetNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PlanetList
    ) {
        composable<Screen.PlanetList> {
            PlanetListScreen(
                onPlanetClick = { planetId ->
                    navController.navigate(Screen.PlanetDetail(planetId))
                }
            )
        }

        composable<Screen.PlanetDetail> {
            PlanetDetailScreen(
                onBack = { navController.navigateUp() }
            )
        }
    }
}