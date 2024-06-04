package com.desabulila.snapcencus.ui.user.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.desabulila.snapcencus.data.local.pref.UserModel
import com.desabulila.snapcencus.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}