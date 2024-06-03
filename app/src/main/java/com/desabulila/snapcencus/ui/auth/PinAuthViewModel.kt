package com.desabulila.snapcencus.ui.auth

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

class PinAuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    private val _pinResult = MutableLiveData<Result<UserModel>>()
    val pinResult: LiveData<Result<UserModel>> = _pinResult

    fun checkPin(pin: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                userRepository.checkPin(pin)
            }
            _isLoading.value = false
            when (result) {
                is Result.Success -> {
                    _pinResult.value = result
                }

                is Result.Error -> {
                    _snackbarText.value = Event(result.error)
                }

                is Result.Empty -> {
                    _snackbarText.value = Event("Pin yang anda masukkan salah")
                }
            }
        }
    }
}