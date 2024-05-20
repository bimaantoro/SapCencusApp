package com.desabulila.snapcencus.ui.user.ktp.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desabulila.snapcencus.data.Result
import com.desabulila.snapcencus.data.SnapCencusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ResultKtpViewModel(private val snapCencusRepository: SnapCencusRepository) : ViewModel() {

    private val _result: MutableStateFlow<Result<DataModel?>> =
        MutableStateFlow(Result.Loading())
    val result: StateFlow<Result<DataModel?>> = _result

    fun getDataPenduduk() {
        viewModelScope.launch {
            snapCencusRepository.getDataPenduduk().collectLatest {
                _result.value = it
            }
        }
    }
}