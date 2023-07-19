package com.example.firestoredemo

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

public class LoginScreenViewModel : ViewModel() {
    val auth = Firebase.auth
    val state = mutableStateOf(User())

    fun setEmail(value: String) {
        state.value = state.value.copy(email = value) // use copy for just changing the email value, the remain properties still intact
    }

    fun setPassword(value: String) {
        state.value = state.value.copy(password = value)
    }

    fun login(
        goToHomePage: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(state.value.email, state.value.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Successfully log in")
                    goToHomePage()
                } else {
                    val exception = task.exception
                    Log.d(TAG, exception.toString())
                }
            }
    }

}
