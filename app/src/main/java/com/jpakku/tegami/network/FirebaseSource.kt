package com.jpakku.tegami.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jpakku.tegami.models.MessageModel
import com.jpakku.tegami.models.UserModel
import javax.inject.Inject

class FireBaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth,
                                         private val database: FirebaseDatabase) {

    //TODO: treat this like an API. replace all calls in viewModel to these functions
    fun signUpUser(email:String, password:String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun signInUser(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    fun saveUser(email: String, username: String) =
        database.getReference("users").child(firebaseAuth.uid!!).setValue(UserModel(email = email, username = username))

    fun saveMessage(message: MessageModel) =
        database.getReference("messages").setValue(MessageModel(
            subject = message.subject,
            body = message.body,
            read = message.read,
            timestamp = System.currentTimeMillis()
        ))
}