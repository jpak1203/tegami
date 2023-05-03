package com.jpakku.tegami.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupScreenViewModel @Inject constructor(): ViewModel() {

    private var _emailInput = MutableLiveData("")
    val emailInput: LiveData<String>
        get() = _emailInput

    private var _passwordInput = MutableLiveData("")
    val passwordInput: LiveData<String>
        get() = _passwordInput

    private val _showPasswordFlag = MutableLiveData(false)
    val showPasswordFlag: LiveData<Boolean>
        get() = _showPasswordFlag

    fun setEmailAddress(emailAddress: String) {
        _emailInput.postValue(emailAddress)
    }

    fun setPassword(password: String) {
        _passwordInput.value = password
    }

    fun toggleShowPassword() {
        val flag = _showPasswordFlag.value
        _showPasswordFlag.value = !flag!!
    }

    //TODO: add createAccount() using FirebaseAuth

}