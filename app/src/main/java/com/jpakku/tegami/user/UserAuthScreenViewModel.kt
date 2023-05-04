package com.jpakku.tegami.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserAuthScreenViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth): ViewModel() {

    private var _emailInput = MutableLiveData("")
    val emailInput: LiveData<String>
        get() = _emailInput

    private var _passwordInput = MutableLiveData("")
    val passwordInput: LiveData<String>
        get() = _passwordInput

    private val _showPasswordFlag = MutableLiveData(false)
    val showPasswordFlag: LiveData<Boolean>
        get() = _showPasswordFlag

    private val _isNewUser = MutableLiveData(true)
    val isNewUser: LiveData<Boolean>
        get() = _isNewUser

    private val _userAuthErrorMessage = MutableLiveData("")
    val userAuthErrorMessage: LiveData<String>
        get() = _userAuthErrorMessage

    private val _userAuthError = MutableLiveData(false)
    val userAuthError: LiveData<Boolean>
        get() = _userAuthError

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

    fun setIsNewUser(isNewUser: Boolean) {
        _isNewUser.postValue(isNewUser)
    }

    fun setUserAuthErrorMessage(message: String) {
        _userAuthErrorMessage.postValue(message)
    }

    fun setUserAuthError(flag: Boolean) {
        _userAuthError.postValue(flag)
    }

    fun doesEmailExist(email: String): Task<SignInMethodQueryResult> {
        return firebaseAuth.fetchSignInMethodsForEmail(email)
    }

    fun userAuthResult(email: String, password: String): Task<AuthResult> {
        return if (isNewUser.value == false) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        }
    }

}