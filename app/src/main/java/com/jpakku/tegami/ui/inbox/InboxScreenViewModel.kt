package com.jpakku.tegami.ui.inbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InboxScreenViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth,
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

    private fun getLetters(writeTo: List<String>){
        val tasks = writeTo.map {
            getInbox().document(it).collection("messages").get()
        }

        Tasks.whenAllComplete(tasks).addOnCompleteListener {
            val messageMap =  if (messages.value.isNullOrEmpty()) mutableMapOf() else _messages.value

            for (i in tasks.indices) {
                val sortedMessages = tasks[i].result.sortedBy { message ->
                    message["timestamp"] as Timestamp
                }
                messageMap?.set(writeTo[i], sortedMessages)
                _messages.postValue(messageMap)
            }
        }.addOnFailureListener { exception ->
            Timber.e("Error getting letters: ", exception)
        }
    }

    fun getMessages() {
        getInbox().get().addOnCompleteListener {
            val ids = it.result.map { snapshot ->
                snapshot.id
            }
            getLetters(ids)
        }.addOnFailureListener { exception ->
            Timber.e("Error getting messages: ", exception)
        }
    }
}