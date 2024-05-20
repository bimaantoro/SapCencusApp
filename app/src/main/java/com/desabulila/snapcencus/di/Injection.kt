package com.desabulila.snapcencus.di

import android.content.Context
import com.desabulila.snapcencus.data.SnapCencusRepository
import com.desabulila.snapcencus.data.network.retrofit.ApiConfig
import com.desabulila.snapcencus.data.network.retrofit.ApiService

object Injection {
    fun provideSnapCencusRepository(context: Context): SnapCencusRepository {
        return SnapCencusRepository.getInstance(
            provideApiService()
        )
    }

    private fun provideApiService(): ApiService = ApiConfig.getApiService()
}