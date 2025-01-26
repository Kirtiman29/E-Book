package com.example.e_book.data_layer.repo

import com.example.e_book.ResultState
import com.example.e_book.data_layer.response.BookCategoryModels
import com.example.e_book.data_layer.response.BookModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class Repo @Inject constructor(private val firebaseDatabase: FirebaseDatabase) {

    suspend fun getAllBooks(): Flow<ResultState<List<BookModels>>> = callbackFlow {

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookModels> = emptyList()
                items = snapshot.children.map {
                    it.getValue<BookModels>()!!

                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))

            }

        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }


    }
    suspend fun getAllCategory(): Flow<ResultState<List<BookCategoryModels>>> = callbackFlow {

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookCategoryModels> = emptyList()
                items = snapshot.children.map {
                    it.getValue<BookCategoryModels>()!!

                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))

            }

        }

        firebaseDatabase.reference.child("BookCategory").addValueEventListener(valueEvent)

        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }


    }


    suspend fun getBookByCategory(bookCategory : String): Flow<ResultState<List<BookModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookModels> = emptyList()
                items = snapshot.children.filter {
                    it.getValue<BookModels>()!!.category == bookCategory
                }.map {
                    it.getValue<BookModels>()!!

                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))

            }


        }
        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)
        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }
}