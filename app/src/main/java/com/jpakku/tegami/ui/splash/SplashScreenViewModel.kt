package com.jpakku.tegami.ui.splash

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {

    fun getUser() : FirebaseUser? {
        return firebaseAuth.currentUser
    }

}