package com.desabulila.snapcencus.ui.user.ocr.ktp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desabulila.snapcencus.data.ResultState
import com.desabulila.snapcencus.data.SnapCencusRepository
import com.desabulila.snapcencus.data.model.ListDataModel
import kotlinx.coroutines.launch

class ResultKtpViewModel(private val snapCencusRepository: SnapCencusRepository) : ViewModel() {
    private val _listResult = MutableLiveData<ResultState<ListDataModel>>()
    val listResult: LiveData<ResultState<ListDataModel>> = _listResult

    fun getDataPenduduk() {
        viewModelScope.launch {
            snapCencusRepository.getListData().collect {
                _listResult.value = it
            }
        }
    }
}