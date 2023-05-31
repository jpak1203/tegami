package com.jpakku.tegami.ui.inbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth,
                                         private val firebaseFirestore: FirebaseFirestore): ViewModel() {

    private val _messages = MutableLiveData<MutableMap<String, List<QueryDocumentSnapshot>>>()
    val messages: LiveData<MutableMap<String, List<QueryDocumentSnapshot>>>
        get() = _messages

    private fun getInbox(): CollectionReference {
        val userId = firebaseAuth.currentUser?.uid ?: ""

        return firebaseFirestore
            .collection("users")
            .document(userId)
            .collection("inbox")
    }

    fun getMessages() {
        getInbox().get().addOnSuccessListener {
            val messageMap =  if (_messages.value.isNullOrEmpty()) mutableMapOf() else _messages.value

            for (writeTo in it) {
                getInbox().document(writeTo.id).collection("messages")
                    .get().addOnSuccessListener {messages ->
                        val sortedMessages = messages.sortedBy { message ->
                            message["timestamp"] as Timestamp
                        }
                        messageMap?.set(writeTo.id, sortedMessages)
                        _messages.postValue(messageMap)
                    }.addOnFailureListener { exception ->
                        Timber.e("Error getting messages: ", exception)
                    }
            }
        }.addOnFailureListener { exception ->
            Timber.e("Error getting inbox: ", exception)
        }
    }
}