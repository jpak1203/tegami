package com.jpakku.tegami.ui.letter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteLetterScreenViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth,
                                                     private val firebaseFirestore: FirebaseFirestore): ViewModel() {

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

        val message = hashMapOf(
            "subject" to messageSubject.value,
            "body" to messageBody.value
        )

        if (writeTo.isNullOrEmpty() or writeTo.equals("null")) {
            firebaseFirestore.collection("messageQueue").add(message)
        } else {
            firebaseFirestore.document("users/$writeTo/inbox/$user").collection("messages").add(message)
        }
    }
}