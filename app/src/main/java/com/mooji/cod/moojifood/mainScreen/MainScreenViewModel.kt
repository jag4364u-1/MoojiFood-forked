package com.mooji.cod.moojifood.mainScreen

import androidx.lifecycle.ViewModel
import com.mooji.cod.moojifood.model.Food
import com.mooji.cod.moojifood.model.FoodDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel(
    private val foodDao: FoodDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    data class UiState(
        val items: List<Food> = emptyList(),
        val page: Int = 0,
        val isLoading: Boolean = false,
        val query: String = "",
        val error: String? = null,
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    companion object {
        const val PAGE_SIZE = 10
    }

    fun firstRun() { }
    fun refresh() { }
    fun onSearch(query: String) { }
    fun loadNextPage() { }
    fun addFood(food: Food) { }
    fun updateFood(food: Food, pos: Int) { }
    fun deleteFood(food: Food, pos: Int) { }
    fun deleteAllFoods() { }
}
