package com.makinul.bs23104.ui.home.details

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makinul.bs23104.MainActivity
import com.makinul.bs23104.ui.home.NavRoutes // Assuming NavRoutes is accessible here or move it
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class DetailsScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        // Navigate to DetailsScreen by clicking an item in HomeScreen first
        // This setup is common for both tests
        navigateToDetailsScreen()
    }

    private fun navigateToDetailsScreen() {
        // Wait for product items in HomeScreen to be loaded and displayed
        val productItemExists = composeTestRule.waitUntil(timeoutMillis = 20000) {
            composeTestRule
                .onAllNodes(hasTestTagPrefix("product_item_"), useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        assert(productItemExists) { "HomeScreen did not load product items." }


        // Click on the first product item to navigate to DetailsScreen
        composeTestRule.onAllNodes(hasTestTagPrefix("product_item_"), useUnmergedTree = true)
            .onFirst()
            .performClick()

        // Wait for DetailsScreen to appear by checking for one of its specific tags
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithTag("details_screen_scroll_column")
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    /**
     * Test Case 1: Verify Product Details are Displayed.
     */
    @Test
    fun testProductDetails_areDisplayed() {
        // At this point, we should already be on DetailsScreen due to setUp()

        // Verify product title is displayed
        composeTestRule.onNodeWithTag("details_product_title").assertIsDisplayed()

        // Verify product image is displayed (optional, but good to have)
        composeTestRule.onNodeWithTag("details_product_image").assertIsDisplayed()
        
        // Verify product description is displayed
        composeTestRule.onNodeWithTag("details_product_description").assertIsDisplayed()
    }

    /**
     * Test Case 2: Verify Back Navigation.
     */
    @Test
    fun testBackNavigation_returnsToHomeScreen() {
        // At this point, we should already be on DetailsScreen due to setUp()

        // Click the back button on DetailsScreen
        composeTestRule.onNodeWithTag("details_back_button").performClick()

        // Verify that we are back on HomeScreen by checking for the product list
        // Increased timeout to allow for navigation and recomposition.
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithTag("product_list")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("product_list").assertIsDisplayed()
    }
    
    // Helper function to create a matcher for test tags with a specific prefix
    private fun hasTestTagPrefix(prefix: String): SemanticsMatcher {
        return SemanticsMatcher("${prefix}TestTagPrefix") { node ->
            node.config.getOrNull(SemanticsProperties.TestTag)?.startsWith(prefix) ?: false
        }
    }
}
