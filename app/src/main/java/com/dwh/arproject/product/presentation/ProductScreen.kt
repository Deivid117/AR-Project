package com.dwh.arproject.product.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.arproject.product.presentation.cart_state.AddToCartState
import com.dwh.arproject.product.presentation.cart_state.CartItemTarget
import com.dwh.arproject.R
import com.dwh.arproject.core.presentation.ArScaffold
import com.dwh.arproject.core.presentation.clickableSingle
import com.dwh.arproject.product.domain.model.ArProductModel
import com.dwh.arproject.product.domain.model.Product
import com.dwh.arproject.ui.theme.OrangeAddToCart
import com.dwh.arproject.ui.theme.Rubik
import com.dwh.arproject.ui.theme.YellowCardBackground
import com.dwh.arproject.ui.theme.YellowText

@Composable
fun ProductScreen(
    products: List<Product>,
    addToCardState: AddToCartState,
    isSnackBarVisible: Boolean,
    onDismissSnackBar: () -> Unit,
    onShowZoomDialog: (Int) -> Unit,
    onNavigateToArScreen: (List<ArProductModel>) -> Unit
) {
    ArScaffold(
        isSnackBarVisible = isSnackBarVisible,
        onDismissSnackBar = onDismissSnackBar
    ) {
        ProductView(
            products = products,
            addToCardState = addToCardState,
            onShowZoomDialog = onShowZoomDialog,
            onNavigateToArScreen = onNavigateToArScreen
        )
    }
}

@Composable
private fun ProductView(
    addToCardState: AddToCartState,
    products: List<Product>,
    onShowZoomDialog: (Int) -> Unit,
    onNavigateToArScreen: (List<ArProductModel>) -> Unit
) {
    var count by remember { mutableIntStateOf(0) }

    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

        AddToCarBox(
            addToCardState = addToCardState,
            count = count
        )

        ProductsLazyColumn(
            products = products,
            onClickAddToCart = {
                count++
                addToCardState.add(it)
            },
            onClickNavigateToArScreen = onNavigateToArScreen,
            onShowZoomDialog = onShowZoomDialog
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddToCarBox(
    addToCardState: AddToCartState,
    count: Int
){
    val isBadgeBoxVisible = count > 0
    val totalProducts = if (count > 9) "9+" else "$count"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, end = 30.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        BadgedBox(
            badge = { if (isBadgeBoxVisible) Badge { Text(text = totalProducts) } }
        ) {
            Icon(
                modifier = Modifier.onGloballyPositioned {
                    addToCardState.targetPosition = it.localToWindow(Offset.Zero)
                },
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "shopping cart icon"
            )
        }
    }
}

@Composable
private fun ProductsLazyColumn(
    products: List<Product>,
    onShowZoomDialog: (Int) -> Unit,
    onClickAddToCart: (String) -> Unit,
    onClickNavigateToArScreen: (List<ArProductModel>) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = products,
            key = { it.id }
        ) { product ->
            ProductItem(
                product = product,
                onShowZoomDialog = onShowZoomDialog,
                onClickAddToCart = {
                    onClickAddToCart(it)
                }
            ) { onClickNavigateToArScreen(product.arProductModels) }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onShowZoomDialog: (Int) -> Unit,
    onClickAddToCart: (String) -> Unit,
    onClickNavigateToArScreen: () -> Unit
) {
    val numberModels = product.arProductModels.size

    Card(
        shape = CutCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = YellowCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
        ) {

            CartItemTarget(key = product.id) {
                AsyncImage(
                    modifier = Modifier
                        .clickable { onShowZoomDialog(product.image) }
                        .background(Color.LightGray)
                        .size(80.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.image)
                        .crossfade(500)
                        .build(),
                    contentDescription = "product image",
                    placeholder = painterResource(id = R.drawable.ic_image),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    contentScale = ContentScale.Crop,
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleLarge,
                    color = YellowText
                )

                Text(
                    text = "${stringResource(id = R.string.products_price)}: ${product.price}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = Rubik,
                    fontWeight = FontWeight.Medium,
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 12.sp)) {
                            append(stringResource(id = R.string.products_variety_of_models))
                        }
                        withStyle(SpanStyle(YellowText, fontSize = 12.sp)) {
                            append("$numberModels")
                        }
                    },
                    textAlign = TextAlign.End
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickableSingle { onClickNavigateToArScreen() }
                        .padding(5.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_view_in_ar),
                    contentDescription = "ar icon",
                    tint = Color.Black
                )

                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onClickAddToCart(product.id) }
                        .padding(5.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_shopping_cart),
                    contentDescription = "add shopping cart icon",
                    tint = OrangeAddToCart
                )
            }
        }
    }
}