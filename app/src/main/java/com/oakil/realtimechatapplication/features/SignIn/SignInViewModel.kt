package com.oakil.realtimechatapplication.features.SignIn

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel : ViewModel(){

    private val _state = MutableStateFlow<SignInState> (SignInState.Nothing)
    val state = _state.asStateFlow()


    fun signIn(email: String, password: String){
        _state.value = SignInState.Loading
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener{
            task->
            if(task.isSuccessful){
                _state.value = SignInState.Success
            }else{
                _state.value = SignInState.Error
            }
        }
        _state.value = SignInState.Success
    }

}


sealed class SignInState{
    object Nothing: SignInState()
    object Success: SignInState()
    object Error : SignInState()
    object Loading : SignInState()
}