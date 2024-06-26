package com.example.bachelordegreeproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelordegreeproject.core.nfc.NfcReader
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.core.util.constants.RfidStatus
import com.example.bachelordegreeproject.core.util.constants.UiState
import com.example.bachelordegreeproject.data.remote.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ActivityScoped private val nfcReader: NfcReader,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _rfidStatus = MutableLiveData<RfidStatus>()
    val rfidStatus: LiveData<RfidStatus>
        get() = _rfidStatus

    private val _authResult = MutableLiveData<UiState>()
    val authResult: LiveData<UiState>
        get() = _authResult

    init {
        viewModelScope.launch {
            nfcReader.rfidIdStateFlow.collect { result ->
                _rfidStatus.postValue(result)
            }
        }
    }

    fun authPerson(login: String, password: String?) = viewModelScope.launch {
        _rfidStatus.postValue(RfidStatus.Idle)
        _authResult.postValue(UiState.Loading)
        val uiState = when (val result = authRepository.authPerson(login, password)) {
            is Result.Success -> UiState.Success()
            is Result.Fail -> UiState.Error(result.text)
            else -> {
                UiState.Idle
            }
        }
        _authResult.postValue(uiState)

        //TODO delete
        if (login == "admin") _authResult.postValue(UiState.Success())
    }

    fun resetParams() {
        _authResult.postValue(UiState.Idle)
        _rfidStatus.postValue(RfidStatus.Idle)
    }
}