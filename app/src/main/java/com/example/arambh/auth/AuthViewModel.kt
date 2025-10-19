package com.example.arambh.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

// Top-level (outside any class)
val Context.dataStore by preferencesDataStore("user_prefs")

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow(auth.currentUser != null)
    val authState: StateFlow<Boolean> = _authState

    private val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")

    fun initializeRememberMe(context: Context) {
        viewModelScope.launch {
            val rememberMe = context.dataStore.data.map {
                it[REMEMBER_ME_KEY] ?: false
            }.first()

            if (!rememberMe) {
                auth.signOut()
                _authState.value = false
            }
        }
    }

    fun signUp(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        mobile: String,
        dob: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    saveUserData(
                        userId, firstName, lastName, username, email, mobile,dob, onResult
                    )
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    private fun saveUserData(
        userId: String,
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        mobile: String,
        dob: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val userData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "username" to username,
            "email" to email,
            "mobile" to mobile,
            "dob" to dob
        )

        firestore.collection("users")
            .document(userId)
            .set(userData)
            .addOnSuccessListener {
                _authState.value = true
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }

    fun login(
        context: Context,
        email: String,
        password: String,
        rememberMe: Boolean,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = true
                    viewModelScope.launch {
                        context.dataStore.edit { it[REMEMBER_ME_KEY] = rememberMe }
                    }
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun logout(context: Context) {
        auth.signOut()
        viewModelScope.launch {
            context.dataStore.edit { it[REMEMBER_ME_KEY] = false }
        }
        _authState.value = false
    }
}
