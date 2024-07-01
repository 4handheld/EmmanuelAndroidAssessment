package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.edit

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
class EditInventoryViewModel @Inject constructor(private val itemsRepository: ItemsRepository) : ViewModel() {
    private val _uiState = MutableLiveData<EditInventoryState>()
    val state: LiveData<EditInventoryState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _uiState.postValue(EditInventoryState.Error(exception.message.toString()))
    }

    fun deleteItem(item: Items){
        _uiState.postValue(EditInventoryState.Loading)
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                itemsRepository.deleteItem(item)
            }
            _uiState.postValue(EditInventoryState.SuccessDelete(item))
        }

    }

    fun updateItem(update: Items){
        _uiState.postValue(EditInventoryState.Loading)

        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                itemsRepository.updateItem(update)
            }
            _uiState.postValue(EditInventoryState.SuccessUpdate(update))
        }

    }

    sealed class EditInventoryState {

        data object Loading : EditInventoryState()
        data class Error(val error: String) : EditInventoryState()
        data class SuccessDelete(val item: Items) : EditInventoryState()
        data class SuccessUpdate(val item: Items) : EditInventoryState()

    }

}