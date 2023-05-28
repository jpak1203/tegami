package com.jpakku.tegami.ui.settings

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth): ViewModel() {

    fun getEmail(): String? {
        return firebaseAuth.currentUser?.email
    }

    fun logOut() {
        firebaseAuth.signOut()
    }
}