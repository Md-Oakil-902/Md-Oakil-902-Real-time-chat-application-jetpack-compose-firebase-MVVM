package com.oakil.realtimechatapplication.features.SignIn

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val state = _state.asStateFlow()


    fun signIn(email: String, password: String) {
        _state.value = SignInState.Loading
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.user != null) {
                    _state.value = SignInState.Success
                } else {
                    _state.value = SignInState.Error
                }
            }
    }
}

sealed class SignInState {
    object Nothing : SignInState()
    object Success : SignInState()
    object Error : SignInState()
    object Loading : SignInState()
}