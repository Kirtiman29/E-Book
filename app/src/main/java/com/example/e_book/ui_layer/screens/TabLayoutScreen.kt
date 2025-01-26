package com.example.e_book.ui_layer.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayoutScreen(navController: NavHostController) {


    val tabItems = listOf(
        TabItems(
            title = "Category",
            unselectedIcon = Icons.Outlined.Category,
            selectedIcon = Icons.Filled.Category
        ),
        TabItems(
            title = "Search",
            unselectedIcon = Icons.Outlined.Book,
            selectedIcon = Icons.Filled.Book
        )

    )
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    var scop = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {tabItems.size})

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = pagerState.currentPage == index,
                    modifier = Modifier.fillMaxSize(),
                    onClick = {
                        scop.launch {
                            pagerState.animateScrollToPage(index)
                        }

                    },
                    text = { Text(text = tabItem.title) },
                    icon = {
                        Icon(imageVector = if (pagerState.currentPage == index) {
                            tabItem.selectedIcon
                        } else {
                            tabItem.unselectedIcon
                        }
                        , contentDescription = null)


                    }
                )
            }
        }
        HorizontalPager(state = pagerState) {
            when(it){
                0 -> CategoryScreens(navController = navController)
                1 -> AllBookScreen(navController = navController)
            }

        }



    }
}




data class TabItems(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
)

