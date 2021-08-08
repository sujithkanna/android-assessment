package com.ticketswap.assessment.api

import android.security.keystore.UserNotAuthenticatedException
import com.squareup.moshi.Moshi
import com.ticketswap.assessment.utils.await
import com.ticketswap.assessment.utils.fromJsonSuspend
import com.ticketswap.assessment.utils.stringReadSuspend
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

abstract class HttpBuilder(val client: OkHttpClient, private val baseUrl: String) {

    fun newRequest(path: String = "") = Request.Builder().url(baseUrl + path)

    fun newHttpBuilder(path: String = "") = HttpUrl.parse(baseUrl + path)!!.newBuilder()!!

    @ExperimentalCoroutinesApi
    @Throws(Exception::class)
    suspend inline fun <reified T> execute(request: Request): T? {
        val response = client.newCall(request).await()
        val responseString = response.stringReadSuspend()
        if (response.isSuccessful) {
            val moshi = Moshi.Builder().build()
            return moshi.fromJsonSuspend(responseString, T::class.java)
        }

        when (response.code()) {
            401 -> throw UserNotAuthenticatedException("Spotify api user unauthorised")
            else -> throw UnknownError("Api failed: $responseString")
        }
    }
}

data class Data(val d: String)