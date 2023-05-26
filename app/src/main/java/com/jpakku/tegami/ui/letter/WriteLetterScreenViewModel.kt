package com.jpakku.tegami.ui.letter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteLetterScreenViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth): ViewModel() {

    private var _messageSubject = MutableLiveData("")
    val messageSubject: LiveData<String>
        get() = _messageSubject

    private var _messageBody = MutableLiveData("")
    val messageBody: LiveData<String>
        get() = _messageBody

    fun setMessageSubject(subject: String) {
        _messageSubject.postValue(subject)
    }

    fun setMessageBody(body: String) {
        _messageBody.postValue(body)
    }

    fun sendMessage(writeTo: String?) {
        val user = firebaseAuth.currentUser?.uid
        if (writeTo.isNullOrEmpty()) {
            //TODO: send to FCM
        } else {
            //TODO: send to writeTo user's inbox
        }
    }
}