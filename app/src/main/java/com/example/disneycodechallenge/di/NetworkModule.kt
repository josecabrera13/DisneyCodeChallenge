package com.example.disneycodechallenge.di

import com.example.disneycodechallenge.BuildConfig
import com.example.disneycodechallenge.apis.ComicApi
import com.example.disneycodechallenge.extensions.md5
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://gateway.marvel.com:443/"
private const val QUERY_PARAMETER_API_KEY = "apikey"
private const val QUERY_PARAMETER_HASH_KEY = "hash"
private const val QUERY_PARAMETER_TIMESTAMP_KEY = "ts"
private const val QUERY_PARAMETER_TIMESTAMP_VALUE = "1"


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val queryParameterHash =
            (QUERY_PARAMETER_TIMESTAMP_VALUE + BuildConfig.PRIVATE_KEY + BuildConfig.API_KEY).md5()
        val okHttpClintBuilder = OkHttpClient.Builder()
        okHttpClintBuilder.addInterceptor { chain ->
            val request = chain.request().newBuilder()
            val originalUrl = chain.request().url
            val newUrl = originalUrl.newBuilder()
                .addQueryParameter(QUERY_PARAMETER_TIMESTAMP_KEY, QUERY_PARAMETER_TIMESTAMP_VALUE)
                .addQueryParameter(QUERY_PARAMETER_API_KEY, BuildConfig.API_KEY)
                .addQueryParameter(QUERY_PARAMETER_HASH_KEY, queryParameterHash).build()
            request.url(newUrl)
            chain.proceed(request.build())
        }
        return okHttpClintBuilder.build()
    }

    @Provides
    fun provideWeatherApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): ComicApi = Retrofit.Builder()
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build().create(ComicApi::class.java)
}
