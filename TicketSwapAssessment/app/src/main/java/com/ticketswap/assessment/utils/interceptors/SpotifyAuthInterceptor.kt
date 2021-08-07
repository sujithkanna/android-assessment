package com.ticketswap.assessment.utils.interceptors

import com.ticketswap.assessment.user.UserManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class SpotifyAuthInterceptor @Inject constructor(private val userManager: UserManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
            .newBuilder()
            .addAuthHeader(userManager.spotifyAuthToken)
        return chain.proceed(builder.build())
    }

    companion object {
        private const val AUTH_HEADER = "Authorization"
    }

    private fun Request.Builder.addAuthHeader(authToken: String?) = apply {
        if (!authToken.isNullOrBlank()) addHeader(AUTH_HEADER, "Bearer $authToken")
    }
}