package com.desabulila.snapcencus.ui.user.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desabulila.snapcencus.data.ResultState
import com.desabulila.snapcencus.data.repository.SnapCencusRepository
import com.desabulila.snapcencus.data.model.ListDataModel
import kotlinx.coroutines.launch

class PendudukListViewModel(private val snapCencusRepository: SnapCencusRepository) : ViewModel() {

    private val _pendudukListResult = MutableLiveData<ResultState<ListDataModel>>()
    val pendudukListResult: LiveData<ResultState<ListDataModel>> = _pendudukListResult

    fun getPendudukList() {
        viewModelScope.launch {
            snapCencusRepository.getPenduduk().collect {
                _pendudukListResult.value = it
            }
        }
    }
}