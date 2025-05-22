package com.makinul.bs23104.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.makinul.bs23104.R
import com.makinul.bs23104.data.Data
import com.makinul.bs23104.data.ProductResponse
import com.makinul.bs23104.data.model.Product
import com.makinul.bs23104.utils.AppConstants
import com.makinul.bs23104.utils.Extensions.formatPrice

@Composable
@Preview
fun FooterPreview() {
    FooterItem(
        Product(
            key = AppConstants.KEY_FOOTER,
            message = "stringResource(R.string.loading_more)",
            state = AppConstants.STATE_LOADING
        )
    )
}

/**
 * FooterItem Composable
 */
@Composable
fun FooterItem(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("footer_item_${product.state}"), // Test tag for footer state
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (product.state == -1) { // Loading state
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .testTag("footer_loading_indicator")
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = product.message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun ProductPreview() {
    ProductItem(
        Product(
            key = AppConstants.KEY_FOOTER,
            message = "stringResource(R.string.loading_more)",
            state = AppConstants.STATE_LOADING
        )
    ) {

    }
}

/**
 * ProductItem Composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(product: Product, onItemClick: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onItemClick(product) }
            .testTag("product_item_${product.id}"), // Unique test tag for each item
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.thumbnail)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_placeholder), // Replace with your placeholder
                error = painterResource(R.drawable.ic_placeholder_error), // Replace with your error placeholder
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 8.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Brand: ${product.brand}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "Category: ${product.category}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = product.price.formatPrice(product.discountPercentage),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (product.discountPercentage > 0) {
                        Text(
                            text = "${product.discountPercentage.toInt()}% off",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.error,
                                    shape = MaterialTheme.shapes.small
                                )
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Basic Rating Display (Text)
                    Text(
                        text = "Rating: ${product.rating}/5",
                        style = MaterialTheme.typography.bodySmall
                    )
                    // For a real RatingBar, you'd implement or use a library.
                    // Example: androidx.compose.material3.Icon for stars if simple
                }
                Text(
                    text = product.availabilityStatus,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (product.availabilityStatus == "In Stock") Color.Green.copy(alpha = 0.7f) else Color.Red.copy(
                        alpha = 0.7f
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun StatusDisplayPreview() {
    StatusDisplay(
        dataState = Data.Error(),
        isListEmpty = true,
    ) {

    }
}

/**
 * StatusDisplay Composable
 */
@Composable
fun StatusDisplay(
    modifier: Modifier = Modifier,
    dataState: Data<*>,
    isListEmpty: Boolean,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("status_display"),
        contentAlignment = Alignment.Center
    ) {
        when (dataState) {
            is Data.Loading -> {
                if (isListEmpty) { // Only show full screen loading if list is empty
                    CircularProgressIndicator(modifier = Modifier.testTag("status_loading_indicator"))
                }
            }

            is Data.Error -> {
                if (isListEmpty) { // Only show full screen error if list is empty
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.testTag("status_error_display")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = dataState.message,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(onClick = onRetry, modifier = Modifier.testTag("retry_button")) {
                            Text(text = stringResource(id = R.string.retry))
                        }
                    }
                }
            }

            is Data.Success<*> -> {
                if (isListEmpty && (dataState.data == null || (dataState.data is List<*> && dataState.data.isEmpty()))) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.testTag("status_empty_display")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudOff,
                            contentDescription = "No Data",
                            modifier = Modifier.size(48.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = stringResource(id = R.string.no_data_found),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}


/**
 * ProductList Composable
 */
@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    products: List<Product>,
    isLoadingMore: Boolean,
    listState: LazyListState = rememberLazyListState(),
    onEndOfListReached: () -> Unit,
    onItemClick: (Product) -> Unit
) {
    LazyColumn(modifier = modifier.testTag("product_list"), state = listState) {
        items(
            items = products,
            key = { product ->
                product.id
            }) { product ->
            if (product.key == AppConstants.KEY_FOOTER) {
                FooterItem(product = product)
            } else {
                ProductItem(product = product, onItemClick = onItemClick)
            }
        }
    }

    // Derived state to check if the end of the list is visible
    val isEndOfListVisible by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.lastOrNull()
                lastVisibleItem != null && lastVisibleItem.index == layoutInfo.totalItemsCount - 1
            }
        }
    }

    // Call onEndOfListReached when the end is visible and not already loading more
    LaunchedEffect(isEndOfListVisible, isLoadingMore) {
        if (isEndOfListVisible && !isLoadingMore && products.any { it.key == AppConstants.KEY_FOOTER && it.state != AppConstants.STATE_NO_MORE_DATA && it.state != AppConstants.STATE_ERROR }) {
            onEndOfListReached()
        }
    }
}


/**
 * HomeScreen Composable - Main screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val productData by viewModel.products.collectAsStateWithLifecycle()
    var currentDataState by remember { mutableStateOf<Data<ProductResponse>>(Data.Loading) }
    val items = remember { mutableStateListOf<Product>() }
    var currentPage by remember { mutableIntStateOf(0) } // Corresponds to initialPage in HomeFragment
    var isLoading by remember { mutableStateOf(false) } // For pull-to-refresh and initial load
    var isLoadingMore by remember { mutableStateOf(false) } // For pagination footer

    val pullToRefreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

//    if (pullToRefreshState.isRefreshing) {
//        LaunchedEffect(true) {
//            items.clear()
//            currentPage = 0
//            viewModel.fetchProducts(currentPage) // Fetch first page
//            // pullToRefreshState.endRefresh() will be called when data comes
//        }
//    }
//
    // Handle product data events
    LaunchedEffect(productData) {
        productData.let { data ->
            currentDataState = data // Update current data state for StatusDisplay
            isLoading = data is Data.Loading && items.isEmpty()
            isLoadingMore = data is Data.Loading && items.isNotEmpty()
//            pullToRefreshState.endRefresh() // Ensure refresh indicator stops

            when (data) {
                is Data.Success -> {
                    val productResponse = data.data
                    if (productResponse != null) {
                        if (currentPage == 0) { // If it's the first page (after refresh or initial)
                            items.clear()
                        }

                        // Remove old footer if it exists
                        items.removeAll { it.key == AppConstants.KEY_FOOTER }
                        items.addAll(productResponse.products)

                        currentPage++ // Increment page for next fetch

                        if (productResponse.products.isEmpty() || items.size >= productResponse.total) {
                            items.add(
                                Product(
                                    key = AppConstants.KEY_FOOTER,
                                    message = "stringResource(R.string.no_more_data_to_load)",
                                    state = AppConstants.STATE_NO_MORE_DATA
                                )
                            )
                        } else {
                            items.add(
                                Product(
                                    key = AppConstants.KEY_FOOTER,
                                    message = "stringResource(R.string.loading_more)",
                                    state = AppConstants.STATE_LOADING
                                )
                            )
                        }
                    } else {
                        // Handle null data in success case, perhaps show "no data" footer
                        items.removeAll { it.key == AppConstants.KEY_FOOTER }
                        items.add(
                            Product(
                                key = AppConstants.KEY_FOOTER,
                                message = "stringResource(R.string.no_data_found)",
                                state = AppConstants.STATE_ERROR
                            )
                        )
                    }
                }

                is Data.Error -> {
                    items.removeAll { it.key == AppConstants.KEY_FOOTER }
                    items.add(
                        Product(
                            key = AppConstants.KEY_FOOTER,
                            message = data.message,
                            state = AppConstants.STATE_ERROR
                        )
                    )
                }

                is Data.Loading -> {
                    if (items.none { it.key == AppConstants.KEY_FOOTER }) {
                        if (items.isNotEmpty()) { // Only add loading footer if there are items already
                            items.add(
                                Product(
                                    key = AppConstants.KEY_FOOTER,
                                    message = "stringResource(R.string.loading_more)",
                                    state = AppConstants.STATE_LOADING
                                )
                            )
                        }
                    } else {
                        items.indexOfFirst { it.key == AppConstants.KEY_FOOTER }.takeIf { it != -1 }
                            ?.let { index ->
                                items[index] = Product(
                                    key = AppConstants.KEY_FOOTER,
                                    state = AppConstants.STATE_LOADING,
                                    message = "stringResource(R.string.loading_more)"
                                )
                            }
                    }
                }
            }
        }
    }

    // Initial data load
    LaunchedEffect(Unit) {
        if (items.isEmpty()) {
            viewModel.fetchProducts(currentPage)
        }
    }

    Box(
        modifier = Modifier
    ) {
        if (items.isEmpty() && (currentDataState is Data.Loading || currentDataState is Data.Error)) {
            StatusDisplay( // StatusDisplay itself has a root testTag
                dataState = currentDataState,
                isListEmpty = items.isEmpty(),
                onRetry = {
                    items.clear()
                    currentPage = 0
                    viewModel.fetchProducts(currentPage)
                }
            )
        } else {
            ProductList( // ProductList itself has a root testTag
                products = items,
                isLoadingMore = isLoadingMore,
                onEndOfListReached = {
                    if (!isLoadingMore && items.lastOrNull()?.state != AppConstants.STATE_NO_MORE_DATA && items.lastOrNull()?.state != AppConstants.STATE_ERROR) {
                        viewModel.fetchProducts(currentPage)
                    }
                },
                onItemClick = { product ->
                    // Navigate to details screen, passing the product ID.
                    if (product.id != null) { // Ensure product.id is not null before navigating
                        navController.navigate("${NavRoutes.DETAILS_SCREEN}/${product.id}")
                    } else {
                        // Handle cases where product.id might be null, e.g., show a Toast or log error
                        // For now, we'll assume valid IDs for navigation
                    }
                }
            )
        }

//        PullToRefreshContainer(
//            state = pullToRefreshState,
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .testTag("pull_to_refresh_container")
//        )
    }
}

// Dummy RatingBar - replace with a real one or use Text as in ProductItem
@Composable
fun RatingBar(modifier: Modifier = Modifier, rating: Float, maxRating: Int = 5) {
    Row(modifier = modifier) {
        for (i in 1..maxRating) {
            Icon(
                painter = painterResource(id = if (i <= rating) R.drawable.ic_star_filled else R.drawable.ic_star_outline),
                contentDescription = null, // Decorative
                tint = if (i <= rating) MaterialTheme.colorScheme.primary else Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
