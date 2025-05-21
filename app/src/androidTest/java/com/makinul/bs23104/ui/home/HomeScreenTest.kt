package com.makinul.bs23104.ui.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makinul.bs23104.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    /**
     * Test Case 2: Verify items are displayed in LazyColumn after successful load.
     * This test relies on the actual network call and data fetching.
     * It waits for the product list to appear and checks if at least one item is displayed.
     */
    @Test
    fun testItemsDisplayed_afterSuccessfulLoad() {
        // Wait for the product list to appear (check for the LazyColumn's testTag)
        // Increased timeout to allow for network and rendering.
        composeTestRule.waitUntil(timeoutMillis = 20000) {
            composeTestRule
                .onAllNodesWithTag("product_list")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Assert that the product list itself is displayed
        composeTestRule.onNodeWithTag("product_list").assertIsDisplayed()

        // Wait until at least one product item is found.
        // This uses a generic approach by checking for any node starting with "product_item_"
        val productItemExists = composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule
                .onAllNodes(hasTestTagPrefix("product_item_"), useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // If productItemExists is true, then at least one product item was found.
        assert(productItemExists) { "No product items found in the list." }

        // Optional: Further assert that the first item is displayed
        // This is a bit more robust than checking for specific text which might change.
        composeTestRule.onAllNodes(hasTestTagPrefix("product_item_"), useUnmergedTree = true)
            .onFirst()
            .assertIsDisplayed()
    }

    /**
     * Test Case 3: Verify navigation to details (placeholder).
     * This test clicks on the first available product item and checks if the
     * placeholder text for the details screen is displayed.
     */
    @Test
    fun testNavigationToDetailsScreen_showsDetailsPlaceholder() {
        // Wait for product items to be loaded and displayed
        composeTestRule.waitUntil(timeoutMillis = 20000) {
            composeTestRule
                .onAllNodes(hasTestTagPrefix("product_item_"), useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Get the first product item and click on it
        composeTestRule.onAllNodes(hasTestTagPrefix("product_item_"), useUnmergedTree = true)
            .onFirst()
            .performClick()

        // After click, verify that the placeholder text for the Details Screen is shown.
        // This text is defined in MainActivity's NavHost for the DETAILS_SCREEN route.
        // Increased timeout for navigation and recomposition.
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithText("Details Screen Placeholder")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Details Screen Placeholder").assertIsDisplayed()
    }

    // Helper function to create a matcher for test tags with a specific prefix
    private fun hasTestTagPrefix(prefix: String): SemanticsMatcher {
        return SemanticsMatcher("${prefix}TestTagPrefix") { node ->
            node.config.getOrNull(SemanticsProperties.TestTag)?.startsWith(prefix) ?: false
        }
    }
}
