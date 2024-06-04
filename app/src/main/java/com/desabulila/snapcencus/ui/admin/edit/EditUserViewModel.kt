package com.desabulila.snapcencus.ui.admin.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desabulila.snapcencus.data.Result
import com.desabulila.snapcencus.data.local.pref.UserModel
import com.desabulila.snapcencus.data.repository.UserRepository
import com.desabulila.snapcencus.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _updateUserResult = MutableLiveData<UserModel>()
    val updateUserResult: LiveData<UserModel> = _updateUserResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun updateUser(user: UserModel) {
        _isLoading.value = true

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                userRepository.updateUser(user)
            }

            _isLoading.value = false

            when (result) {
                is Result.Success -> {
                    _updateUserResult.value = user
                }

                is Result.Error -> {
                    _snackbarText.value = Event(result.error)
                }

                is Result.Empty -> {}
            }
        }
    }
}