package edu.ucne.registro_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registro_api.presentation.navigation.PlanetNavHost
import edu.ucne.registro_api.ui.theme.Registro_APITheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Registro_APITheme {
                PlanetNavHost()
            }
        }
    }
}