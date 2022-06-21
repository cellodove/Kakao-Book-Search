package com.kakaopay.book.di

import com.cellodove.data.service.KakaoBookService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://dapi.kakao.com"

    @Provides
    @Singleton
    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .build()
    }

    @Provides
    @Singleton
    fun getHttpClient() = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "KakaoAK {API_KEY}")
                .build()
            it.proceed(request)
        }.build()

    @Provides
    @Singleton
    fun provideDeliveryService(retrofit: Retrofit): KakaoBookService {
        return retrofit.create(KakaoBookService::class.java)
    }

}