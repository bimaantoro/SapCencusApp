package com.desabulila.snapcencus.ui.admin.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.desabulila.snapcencus.data.local.pref.UserModel
import com.desabulila.snapcencus.data.repository.UserRepository

class MainAdminViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

}