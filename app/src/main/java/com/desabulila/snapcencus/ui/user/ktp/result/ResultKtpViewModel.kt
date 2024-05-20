package com.desabulila.snapcencus.ui.user.ktp.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desabulila.snapcencus.data.ResultState
import com.desabulila.snapcencus.data.SnapCencusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ResultKtpViewModel(private val snapCencusRepository: SnapCencusRepository) : ViewModel() {

    private val _resultState: MutableStateFlow<ResultState<DataModel?>> =
        MutableStateFlow(ResultState.Loading())
    val resultState: StateFlow<ResultState<DataModel?>> = _resultState

    fun getDataPenduduk() {
        viewModelScope.launch {
            snapCencusRepository.getDataPenduduk().collectLatest {
                _resultState.value = it
            }
        }
    }
}