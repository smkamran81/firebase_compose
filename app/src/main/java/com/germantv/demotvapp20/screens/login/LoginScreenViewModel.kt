package com.germantv.demotvapp20.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.germantv.demotvapp20.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

class LoginScreenViewModel : ViewModel() {

    //val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun CreateUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {

        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val displayName = it.result.user?.email?.split('@')?.get(0)
                    createUser(displayName)
                    home()
                    Log.d("Login", "Create OK ${it.result}")
                } else {
                    Log.d("Login", "Create Error ${it.result}")
                }
                _loading.value = false
            }
        }

    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = MUser(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Try again and again",
            profession = "Android Developer",
            id=null
        )
        /*
          val user = mutableMapOf<String,Any>()
          user["user_id"] = userId.toString()
          user["display_name"] = displayName.toString()
          */

        FirebaseFirestore.getInstance().collection("users").add(user)
    }

    fun SignInWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("Login", "isSuccessful ok ${it.result}")
                        home()
                    } else {
                        Log.d("Login", "isSuccessful ${it.result}")
                    }
                }
        } catch (ex: Exception) {
            Log.d("Tag", "SignInWithEmailAndPassword: ${ex.message}")
        }
    }
}