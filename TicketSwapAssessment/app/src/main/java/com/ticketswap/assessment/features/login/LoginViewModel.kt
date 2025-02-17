package com.ticketswap.assessment.features.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.ticketswap.assessment.utils.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    private val _spotifyAuthRequest = MutableLiveData<AuthenticationRequest>()
    val spotifyAuthRequest = _spotifyAuthRequest.asLiveData()

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult = _loginResult.asLiveData()

    fun doLogin() {
        _spotifyAuthRequest.value = loginRepository.getAuthRequest()
    }

    fun processLoginResponse(response: AuthenticationResponse) {
        _loginResult.value = when (response.type) {
            AuthenticationResponse.Type.TOKEN -> {
                loginRepository.saveAccessToken(response.accessToken)
                true
            }
            else -> false
        }
    }

}