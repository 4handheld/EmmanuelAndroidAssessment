package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventoryappflutterwaveassessment.data.repositories.items.ItemsRepository
import com.example.inventoryappflutterwaveassessment.data.storage.entities.Items
import com.example.inventoryappflutterwaveassessment.data.storage.entities.User
import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.edit.EditInventoryViewModel
import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.login.mod.SimpleLoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddInventoryViewModel @Inject constructor(private val itemsRepository: ItemsRepository) : ViewModel() {

    private val _uiState = MutableLiveData<AddInventoryState>()
    val state: LiveData<AddInventoryState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _uiState.postValue(AddInventoryState.Error(exception.message.toString()))
    }

    fun addItem(name: String, description : String, price: Double, qty: Int, ownerId: Int){
        _uiState.postValue(AddInventoryState.Loading)

        viewModelScope.launch(exceptionHandler) {
            val info = withContext(Dispatchers.IO) {
                val nameExists = itemsRepository.nameExists(name)

                if(!nameExists){
                    itemsRepository.addItem(Items( ownerId, name, description, price, qty))
                }

                nameExists
            }
            val result = if(info){
                AddInventoryState.Error("Inventory name already exists. Use a unique name")
            }else{
                AddInventoryState.Success
            }
            _uiState.postValue(result)
        }

    }

    sealed class AddInventoryState {

        data object Loading : AddInventoryState()
        data class Error(val error: String)  : AddInventoryState()
        data object Success : AddInventoryState()

    }

}