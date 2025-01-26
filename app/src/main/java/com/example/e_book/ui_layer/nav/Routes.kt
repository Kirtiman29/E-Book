package com.example.e_book.ui_layer.nav

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {


    @Serializable
    object home

    @Serializable
    data class pdfView(
        val pdfUrl: String,
    )

    @Serializable
    data class bookByCategory(
        val category: String,
    )


}