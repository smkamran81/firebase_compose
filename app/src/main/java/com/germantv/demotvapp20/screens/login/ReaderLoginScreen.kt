package com.germantv.demotvapp20.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.germantv.demotvapp20.R
import com.germantv.demotvapp20.components.PasswordInput
import com.germantv.demotvapp20.components.ReaderLogo
import com.germantv.demotvapp20.components.emailInput
import com.germantv.demotvapp20.navigation.ReaderScreens

@Composable
fun ReaderLoginScreen(navController: NavHostController,viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ReaderLogo()
            Spacer(modifier = Modifier.height(10.dp))
            if (showLoginForm.value) {
                UserForm(loading = false, isCreateAccount = false) { email, pass ->
                    Log.d("Form", "$email $pass")
                    viewModel.SignInWithEmailAndPassword(email = email, password = pass){
                        //home trailing lambda
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }
                }
            } else {
                UserForm(loading = false, isCreateAccount = true) { email, pass ->
                    viewModel.CreateUserWithEmailAndPassword(email = email, password = pass){
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text = if (showLoginForm.value) "Sign Up" else "Login"
                Text(text = "New User?")
                Text(text = text, modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        //! will toggle the value
                        showLoginForm.value = !showLoginForm.value
                    })
            }


        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {

    val email = rememberSaveable() {
        mutableStateOf("")
    }
    val password = rememberSaveable() {
        mutableStateOf("")
    }
    val passwordVisibility = rememberSaveable() {
        mutableStateOf(false)
    }
    val passwordFocusRequest = FocusRequester.Default

    val keyboardController = LocalSoftwareKeyboardController.current

    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()

    }
    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(
            rememberScrollState()
        )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isCreateAccount) {
            Text(
                text = stringResource(id = R.string.create_account),
                modifier = Modifier.padding(4.dp)
            )
        } else {
            Text(text = "",modifier = Modifier.padding(4.dp))
        }
        emailInput(emailState = email,
            enabled = true,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            })
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading, //Todo change this
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            })
        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(textId: String, loading: Boolean, validInputs: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))
    }
}

// 3 to 5
// 9-3


