package com.makinul.bs23104.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.makinul.bs23104.R
import com.makinul.bs23104.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

// Define route constants based on nav_graph.xml IDs
object NavRoutes {
    const val HOME_SCREEN = "home_screen"
    const val DETAILS_SCREEN = "details_screen"
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels() // Keep ViewModel if needed for Activity-level logic

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(id = R.string.app_name)) }
                            // Navigation icon and actions can be added here later
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        // Use createGraph to leverage the existing XML navigation graph
                        // R.id.FirstFragment and R.id.SecondFragment are integer IDs from the XML graph
                        graph = navController.createGraph(
                            startDestination = NavRoutes.HOME_SCREEN, // String route for start destination
                            route = "main_graph" // A string name for this graph
                        ) {
                            // Map R.id.FirstFragment to NavRoutes.HOME_SCREEN
                            composable(NavRoutes.HOME_SCREEN) {
                                HomeScreen(navController = navController)
                            }

                            val detailsRoute = "${NavRoutes.DETAILS_SCREEN}/{productId}"
                            composable(
                                route = detailsRoute,
                                arguments = listOf(navArgument("productId") {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                val productId = backStackEntry.arguments?.getInt("productId")
                                if (productId != null) {
                                    val product =
                                        viewModel.getProductById(productId) // Use ViewModel here
                                    if (product != null) {
                                        // DetailsScreen will be created in a later step
                                        // For now, let's use a temporary Text Composable
                                        // Replace this with DetailsScreen(navController = navController, product = product)
                                        com.makinul.bs23104.ui.home.details.DetailsScreen(
                                            navController = navController,
                                            product = product
                                        )
                                    } else {
                                        Text(
                                            modifier = Modifier.padding(innerPadding),
                                            text = "Error: Product with ID $productId not found."
                                        )
                                    }
                                } else {
                                    Text(
                                        modifier = Modifier.padding(innerPadding),
                                        text = "Error: Product ID not provided."
                                    )
                                }
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Add this import if not already present (it should be if DetailsScreen.kt is created)
// import com.makinul.bs23104.ui.home.details.DetailsScreen