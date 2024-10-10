package com.dwh.arproject.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.arproject.R
import com.dwh.arproject.core.presentation.ArScaffold
import com.dwh.arproject.core.presentation.clickableSingle
import com.dwh.arproject.home.domain.model.ArProductModel
import com.dwh.arproject.home.domain.model.Product
import com.dwh.arproject.ui.theme.YellowCardBackground
import com.dwh.arproject.ui.theme.YellowText

@Composable
fun ProductScreen(
    products: List<Product>,
    isSnackBarVisible: Boolean,
    onDismissSnackBar: () -> Unit,
    onShowZoomDialog: (Int) -> Unit,
    onNavigateToArScreen: (List<ArProductModel>) -> Unit
) {
    ArScaffold(
        isSnackBarVisible = isSnackBarVisible,
        onDismissSnackBar = onDismissSnackBar
    ) {
        ProductsLazyColumn(
            products = products,
            onClickNavigateToArScreen = onNavigateToArScreen,
            onShowZoomDialog = onShowZoomDialog
        )
    }
}

@Composable
private fun ProductsLazyColumn(
    products: List<Product>,
    onShowZoomDialog: (Int) -> Unit,
    onClickNavigateToArScreen: (List<ArProductModel>) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = products,
            key = { it.id }
        ) { product ->
            ProductItem(
                name = product.name,
                image = product.image,
                price = product.price,
                numberModels = product.arProductModels.size,
                onShowZoomDialog = onShowZoomDialog
            ) {
                onClickNavigateToArScreen(product.arProductModels)
            }
        }
    }
}

@Composable
private fun ProductItem(
    name: String,
    image: Int,
    price: String,
    numberModels: Int,
    onShowZoomDialog: (Int) -> Unit,
    onClickNavigateToArScreen: () -> Unit
) {
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
            AsyncImage(
                modifier = Modifier
                    .clickable { onShowZoomDialog(image) }
                    .background(Color.LightGray)
                    .size(80.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(500)
                    .build(),
                contentDescription = "product image",
                placeholder = painterResource(id = R.drawable.ic_image),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = name.replaceFirstChar { it.uppercase() }, fontSize = 20.sp, color = YellowText)

                Text(text = "${stringResource(id = R.string.products_price)}: $price", fontSize = 15.sp)

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 10.sp)) {
                            append(stringResource(id = R.string.products_variety_of_models))
                        }
                        withStyle(SpanStyle(YellowText, fontSize = 10.sp)) {
                            append("$numberModels")
                        }
                    },
                    textAlign = TextAlign.End
                )
            }

            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickableSingle { onClickNavigateToArScreen() }
                    .padding(5.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_view_in_ar),
                contentDescription = "ar icon",
                tint = Color.Black
            )
        }
    }
}