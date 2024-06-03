package com.desabulila.snapcencus.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.desabulila.snapcencus.data.repository.UserRepository
import com.desabulila.snapcencus.di.Injection
import com.desabulila.snapcencus.ui.admin.main.MainAdminViewModel
import com.desabulila.snapcencus.ui.auth.PinAuthViewModel

class UserViewModelFactory(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PinAuthViewModel::class.java) -> {
                PinAuthViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(MainAdminViewModel::class.java) -> {
                MainAdminViewModel(userRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: UserViewModelFactory? = null

        fun getInstance(context: Context): UserViewModelFactory = instance ?: synchronized(this) {
            instance ?: UserViewModelFactory(Injection.provideUserRepository(context))
        }.also { instance = it }
    }
}