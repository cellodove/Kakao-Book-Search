package com.kakaopay.book.di

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    private const val BASE_URL = "https://dapi.kakao.com"

    fun <T> createRetrofit(classes: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .build()
        return retrofit.create(classes)
    }

    private fun getHttpClient() = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                // TODO. 발급받은 API Key를 입력하세요.
                .addHeader("Authorization", "KakaoAK {API_KEY}")
                .build()
            it.proceed(request)
        }.build()

}