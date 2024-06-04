package com.desabulila.snapcencus.ui.admin.add

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

class AddUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _addUserResult = MutableLiveData<UserModel>()
    val addUserResult: LiveData<UserModel> = _addUserResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun addUser(data: UserModel) {
        _isLoading.value = true

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                userRepository.addUser(data)
            }
            _isLoading.value = false

            when (result) {
                is Result.Success -> _addUserResult.value = result.data
                is Result.Error -> {
                    _snackbarText.value = Event(result.error)
                }

                is Result.Empty -> {}
            }
        }
    }

}