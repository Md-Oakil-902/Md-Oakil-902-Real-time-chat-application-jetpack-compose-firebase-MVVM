package com.oakil.realtimechatapplication.features.SignUp

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SignUpViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)
    val state = _state.asStateFlow()


    fun signUp(name: String, email: String, password: String) {
        _state.value = SignUpState.Loading
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let {
                        it.updateProfile(
                            UserProfileChangeRequest.Builder().setDisplayName(name).build()
                        )
                        _state.value = SignUpState.Success
                        return@addOnCompleteListener
                    }

                } else {
                    _state.value = SignUpState.Error
                }
            }
    }
}

sealed class SignUpState {
    object Nothing : SignUpState()
    object Success : SignUpState()
    object Error : SignUpState()
    object Loading : SignUpState()
}