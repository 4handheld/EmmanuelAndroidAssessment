package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventoryappflutterwaveassessment.data.repositories.items.ItemsRepository
import com.example.inventoryappflutterwaveassessment.data.storage.entities.Items
import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.add.AddInventoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ItemFragmentViewModel @Inject constructor(private val itemsRepository: ItemsRepository): ViewModel() {

    private val _uiState = MutableLiveData<ItemFragmentState>()
    val state: LiveData<ItemFragmentState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _uiState.postValue(ItemFragmentState.Error(exception.message.toString()))
    }

    fun fetchInventory(){
        _uiState.postValue(ItemFragmentState.Loading)
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                itemsRepository.getItems().let {
                    _uiState.postValue(ItemFragmentState.Success(it))
                }
            }
        }

    }

    sealed class ItemFragmentState {
        data object Loading : ItemFragmentState()
        data class Error(val error: String)  : ItemFragmentState()
        data class Success(val list: List<Items>) : ItemFragmentState()
    }

}