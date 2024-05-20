package com.desabulila.snapcencus.ui.user.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desabulila.snapcencus.data.Result
import com.desabulila.snapcencus.data.SnapCencusRepository
import com.desabulila.snapcencus.data.model.BaseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PendudukListViewModel(private val snapCencusRepository: SnapCencusRepository) : ViewModel() {

    private val _pendudukListResult:
            MutableStateFlow<Result<BaseModel?>> = MutableStateFlow(Result.Loading())
    val pendudukListResult: StateFlow<Result<BaseModel?>> = _pendudukListResult

    fun getPendudukList() {
        viewModelScope.launch {
            snapCencusRepository.getListPenduduk().collectLatest {
                _pendudukListResult.value = it
            }
        }
    }
}