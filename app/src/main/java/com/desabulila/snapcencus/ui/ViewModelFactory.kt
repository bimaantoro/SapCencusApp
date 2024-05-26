package com.desabulila.snapcencus.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.desabulila.snapcencus.data.SnapCencusRepository
import com.desabulila.snapcencus.di.Injection
import com.desabulila.snapcencus.ui.user.detail.DetailPendudukViewModel
import com.desabulila.snapcencus.ui.user.list.PendudukListViewModel
import com.desabulila.snapcencus.ui.user.ocr.ktp.viewmodel.ResultKtpViewModel

class ViewModelFactory(private val snapCencusRepository: SnapCencusRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PendudukListViewModel::class.java) -> {
                PendudukListViewModel(snapCencusRepository) as T
            }

            modelClass.isAssignableFrom(ResultKtpViewModel::class.java) -> {
                ResultKtpViewModel(snapCencusRepository) as T
            }

            modelClass.isAssignableFrom(DetailPendudukViewModel::class.java) -> {
                DetailPendudukViewModel(snapCencusRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideSnapCencusRepository(context))
        }.also { instance = it }
    }
}