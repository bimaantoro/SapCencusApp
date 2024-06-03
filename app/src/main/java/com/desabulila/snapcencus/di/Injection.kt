package com.desabulila.snapcencus.di

import android.content.Context
import com.desabulila.snapcencus.data.local.pref.UserPreference
import com.desabulila.snapcencus.data.local.pref.dataStore
import com.desabulila.snapcencus.data.network.retrofit.ApiConfig
import com.desabulila.snapcencus.data.repository.SnapCencusRepository
import com.desabulila.snapcencus.data.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

object Injection {
    fun provideSnapCencusRepository(context: Context): SnapCencusRepository {
        val apiService = ApiConfig.getApiService()
        return SnapCencusRepository.getInstance(apiService)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val db = Firebase.firestore
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(db, pref)
    }

}