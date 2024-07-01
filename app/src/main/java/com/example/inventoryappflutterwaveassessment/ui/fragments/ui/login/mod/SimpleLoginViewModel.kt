package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.login.mod

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.inventoryappflutterwaveassessment.data.repositories.user.UserRepository
import com.example.inventoryappflutterwaveassessment.data.storage.entities.User
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SimpleLoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {


    private val _interactions = MutableLiveData<LoginActions>()
    val interactions: LiveData<LoginActions> = _interactions

    private val _uiState = MutableLiveData<LoginUIState>()
    val state: LiveData<LoginUIState> = _uiState


    fun loginUser(id: String, code:String){
        _uiState.postValue(LoginUIState.Loading)

        var user = userRepository.getUser(id, code)

        if(user === null){
            userRepository.addUser(id, code)
            user = userRepository.getUser(id, code)
        }

        user?.let {
            _uiState.postValue(LoginUIState.Success(it))
        }

    }

    sealed class LoginActions {

        data object Back : LoginActions()
        data class Navigate(val destination: NavDirections) : LoginActions()
//        data class BottomNavigate(val bottomSheetDialogFragment: BottomSheetDialogFragment) :
//            LoginActions()
    }

    sealed class LoginUIState {

        data object Loading : LoginUIState()
        data object Error : LoginUIState()
        data class Success(val user: User) : LoginUIState()
    }

}