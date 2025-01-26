package com.example.e_book.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_book.ResultState
import com.example.e_book.data_layer.repo.Repo
import com.example.e_book.data_layer.response.BookCategoryModels
import com.example.e_book.data_layer.response.BookModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    private val _getAllBooksState = MutableStateFlow(GetAllBooksState())
    val getAllBooksState = _getAllBooksState.asStateFlow()

    private val _getAllCategory = MutableStateFlow(GetAllCategoryState())
    val getAllCategory = _getAllCategory.asStateFlow()

    private val _getBookByCategory = MutableStateFlow(GetBookByCategoryState())
    val getBookByCategory = _getBookByCategory.asStateFlow()

    fun get_book_by_category(bookCategory: String) {

        viewModelScope.launch(Dispatchers.IO) {
            repo.getBookByCategory(bookCategory).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getBookByCategory.value = GetBookByCategoryState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getBookByCategory.value =
                            GetBookByCategoryState(isLoading = false, data = it.data)

                    }

                    is ResultState.Error -> {
                        _getBookByCategory.value =
                            GetBookByCategoryState(isLoading = false, error = it.exception)
                    }

                }
            }
        }
    }


    fun get_all_category() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllCategory().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getAllCategory.value = GetAllCategoryState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllCategory.value =
                            GetAllCategoryState(isLoading = false, data = it.data)
                    }

                    is ResultState.Error -> {
                        _getAllCategory.value =
                            GetAllCategoryState(isLoading = false, error = it.exception)
                    }
                }
            }

        }

    }

    fun get_all_books() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllBooks().collect {
                when (it) {

                    is ResultState.Loading -> {
                        _getAllBooksState.value = GetAllBooksState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllBooksState.value =
                            GetAllBooksState(isLoading = false, data = it.data)
                    }

                    is ResultState.Error -> {
                        _getAllBooksState.value =
                            GetAllBooksState(isLoading = false, error = it.exception)
                    }


                }

            }


        }

    }
}

data class GetAllBooksState(
    val isLoading: Boolean = false,
    val data: List<BookModels> = emptyList(),
    val error: Throwable? = null,

    )

data class GetAllCategoryState(
    val isLoading: Boolean = false,
    val data: List<BookCategoryModels> = emptyList(),
    val error: Throwable? = null,

    )

data class GetBookByCategoryState(
    val isLoading: Boolean = false,
    val data: List<BookModels> = emptyList(),
    val error: Throwable? = null,

    )

