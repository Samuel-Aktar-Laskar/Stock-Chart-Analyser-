package com.example.tradingai.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.tradingai.model.Stock
import com.example.tradingai.model.StockEndpoint
import com.example.tradingai.model.Watchlist
import com.example.tradingai.repository.MainRepository
import com.example.tradingai.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "MainViewModelLog"

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _stocks: MutableLiveData<DataState<List<Stock>>> = MutableLiveData()
    val stocks: LiveData<DataState<List<Stock>>>
        get() = _stocks

    private val _watchListStocks: MutableLiveData<DataState<List<Watchlist>>> = MutableLiveData()
    val watchListStocks: LiveData<DataState<List<Watchlist>>>
        get() = _watchListStocks

    fun getStocks(name: String) {
        viewModelScope.launch {
            Log.d(TAG, "getStocks: Before calling getStock and name :$name")
            mainRepository.getStocks(name).onEach {
                Log.d(TAG, "getStocks: Inside getStocks it :$it")
                _stocks.value = it
            }.launchIn(viewModelScope)
        }

        // mainRepository.getStockList(name)
    }

    suspend fun getEndpoint(symbol : String) : StockEndpoint? {
            return mainRepository.getStockEndpoint(symbol = symbol)
    }

    fun getWatchList(){
        viewModelScope.launch {
            Log.d(TAG, "getWatchList: Inside it")
            mainRepository.getWatchList().onEach {
                _watchListStocks.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun addStockInWatchList(stock: Stock){
        viewModelScope.launch {
            mainRepository.addStockInWatchList(stock).launchIn(viewModelScope)
            getWatchList()
        }
    }

}