package com.example.e_book.ui_layer.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.e_book.ui_layer.nav.Routes
import com.example.e_book.viewModel.AppViewModel

@Composable
fun AllBookScreen(viewModel: AppViewModel = hiltViewModel(), navController: NavHostController) {
    val state = viewModel.getAllBooksState.collectAsState()
    val data = state.value.data ?: emptyList()

    LaunchedEffect(Unit) {
        viewModel.get_all_books()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.value.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.value.error != null -> {
                Text(
                    text = "Error: ${state.value.error}",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
            state.value.data != null -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(data) { book ->
                        BookCard(
                            bookName = book.bookName,
                            bookAuthor = book.bookAuthor,
                            bookImageUrl = book.bookImage,
                            category = book.category,
                            bookUrl = book.bookUrl,
                            onItemClick = {
                                navController.navigate(Routes.pdfView(book.bookUrl))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookCard(
    bookName: String,
    bookAuthor: String,
    bookImageUrl: String,
    category: String,
    bookUrl: String,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onItemClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = bookImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = bookName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = bookAuthor,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
