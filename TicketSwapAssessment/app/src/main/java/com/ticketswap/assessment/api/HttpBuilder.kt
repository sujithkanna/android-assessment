package com.ticketswap.assessment.api

import com.squareup.moshi.Moshi
import com.ticketswap.assessment.utils.await
import com.ticketswap.assessment.utils.fromJsonAwait
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

abstract class HttpBuilder(val client: OkHttpClient, private val baseUrl: String) {

    fun newRequest(path: String = "") = Request.Builder().url(baseUrl + path)

    fun newHttpBuilder(path: String = "") = HttpUrl.parse(baseUrl)!!.newBuilder()!!

    @ExperimentalCoroutinesApi
    @Throws(Exception::class)
    suspend inline fun <reified T> execute(request: Request): T? {
        val response = client.newCall(request).await()
        val moshi = Moshi.Builder().build()
        return moshi.fromJsonAwait(response, T::class.java)
    }
}

data class Data(val d: String)