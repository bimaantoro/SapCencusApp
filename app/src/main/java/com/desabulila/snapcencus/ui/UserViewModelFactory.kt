package com.desabulila.snapcencus.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.desabulila.snapcencus.data.repository.UserRepository
import com.desabulila.snapcencus.di.Injection
import com.desabulila.snapcencus.ui.admin.add.AddUserViewModel
import com.desabulila.snapcencus.ui.admin.edit.EditUserViewModel
import com.desabulila.snapcencus.ui.admin.list.UserListViewModel
import com.desabulila.snapcencus.ui.admin.main.MainAdminViewModel
import com.desabulila.snapcencus.ui.auth.PinAuthViewModel
import com.desabulila.snapcencus.ui.user.main.MainUserViewModel

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

            modelClass.isAssignableFrom(AddUserViewModel::class.java) -> {
                AddUserViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(UserListViewModel::class.java) -> {
                UserListViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(EditUserViewModel::class.java) -> {
                EditUserViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(MainUserViewModel::class.java) -> {
                MainUserViewModel(userRepository) as T
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