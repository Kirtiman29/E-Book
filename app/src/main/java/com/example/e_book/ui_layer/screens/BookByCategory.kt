package com.example.e_book.ui_layer.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_book.ui_layer.nav.Routes

import com.example.e_book.viewModel.AppViewModel

@Composable
fun BookByCategory(viewModel: AppViewModel = hiltViewModel(),navController: NavController,category: String) {

    val state = viewModel.getBookByCategory.collectAsState()
    val data = state.value.data ?: emptyList()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        viewModel.get_book_by_category(category)
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
            Toast.makeText(context, "Error ${state.value.error}", Toast.LENGTH_SHORT).show()

        }

        state.value.data != null -> {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(top = 60.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(data){
                        BookCard(
                            bookName = it.bookName,
                            bookAuthor = it.bookAuthor,
                            bookImageUrl = it.bookImage,
                            bookUrl = it.bookUrl,
                            category = it.category,
                            onItemClick = {
                                navController.navigate(Routes.pdfView(it.bookUrl))
                            }
                        )

                    }
                }
            }


        }
    }
}