package com.example.e_book.ui_layer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.e_book.ui_layer.nav.Routes
import com.example.e_book.viewModel.AppViewModel

@Composable
fun CategoryScreens(viewModel: AppViewModel = hiltViewModel(), navController: NavController) {
    val state = viewModel.getAllCategory.collectAsState()
    val data = state.value.data ?: emptyList()

    LaunchedEffect(key1 = Unit) {
        viewModel.get_all_category()
    }

    when {
        state.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.value.error != null -> {
            Text(text = "Error : ${state.value.error}")
        }
        state.value.data != null -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(data.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowItems.forEach { item ->
                            CategoryCard(
                                categoryImageUrl = item.categoryImageUrl,
                                name = item.name,
                                onItemClick = {
                                    navController.navigate(Routes.bookByCategory(item.name))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    categoryImageUrl: String,
    name: String,
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp)
            .clickable { onItemClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = categoryImageUrl,
                contentDescription = null,
                modifier = Modifier.size(120.dp)

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = name,
                modifier = Modifier,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun CategoryCardPreview() {
    CategoryCard(
        categoryImageUrl = "https://example.com/image.jpg",
        name = "Category Name"
    )
}
