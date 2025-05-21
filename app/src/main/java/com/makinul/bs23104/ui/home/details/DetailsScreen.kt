package com.makinul.bs23104.ui.home.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.makinul.bs23104.R
import com.makinul.bs23104.data.model.Product
import com.makinul.bs23104.ui.home.RatingBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController, product: Product) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product.title) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier.testTag("details_back_button") // Test tag for back button
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp) // Similar to detail_padding in XML
                .verticalScroll(rememberScrollState())
                .testTag("details_screen_scroll_column") // Test tag for scrollable column
        ) {
            // Product Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.thumbnail)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder_error),
                contentDescription = product.title,
                contentScale = ContentScale.Crop, // Was centerCrop in XML
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Similar to detail_image_height
                    .padding(bottom = 16.dp) // Corresponds to detail_margin_top for title
                    .testTag("details_product_image") // Test tag for product image
            )

            // Product Title
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineMedium, // Was TextAppearance.AppCompat.Headline
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 4.dp) // Corresponds to detail_margin_top_small for brand
                    .testTag("details_product_title") // Test tag for product title
            )

            // Product Brand
            Text(
                text = "Brand: ${product.brand}",
                style = MaterialTheme.typography.titleMedium, // Was TextAppearance.AppCompat.Subhead
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp) // Corresponds to detail_margin_top for price
            )

            // Product Price and Discount
            Row(verticalAlignment = Alignment.CenterVertically) {
                val originalPrice = product.price.toDouble()
                val discountPercentage = product.discountPercentage
                val discountedPrice = originalPrice * (1 - (discountPercentage / 100.0))

                Text(
                    text = "$${String.format("%.2f", discountedPrice)}",
                    style = MaterialTheme.typography.titleLarge, // Was TextAppearance.AppCompat.Title
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary // Was price_color
                )
                if (discountPercentage > 0) {
                    Text(
                        text = "$${String.format("%.2f", originalPrice)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = "${product.discountPercentage.toInt()}% off",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White, // Was discount_text_color
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.error, // Was discount_background_color (approximated)
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp)) // Corresponds to detail_margin_top for rating

            // Rating
            Row(verticalAlignment = Alignment.CenterVertically) {
                RatingBar(rating = product.rating.toFloat())
                Text(
                    text = "(${product.rating})",
                    style = MaterialTheme.typography.bodySmall, // Was TextAppearance.AppCompat.Caption
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp)) // Corresponds to detail_margin_top_small for availability

            // Availability and Category
            Row {
                Text(
                    text = product.availabilityStatus ?: "Availability: ${product.stock ?: 0} left",
                    style = MaterialTheme.typography.bodySmall, // Was TextAppearance.AppCompat.Caption
                    color = if (product.availabilityStatus == "In Stock" ||
                        (product.stock ?: 0) > 0
                    )
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error, // Was availability_in_stock
                )
                Spacer(modifier = Modifier.width(16.dp)) // Was detail_margin_large
                Text(
                    text = "Category: ${product.category}",
                    style = MaterialTheme.typography.bodySmall, // Was TextAppearance.AppCompat.Caption
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Corresponds to detail_margin_top for description

            // Product Description
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge, // Was TextAppearance.AppCompat.Body1
                modifier = Modifier.testTag("details_product_description") // Test tag for product description
            )

            Spacer(modifier = Modifier.height(16.dp)) // Extra space at the bottom
        }
    }
}
