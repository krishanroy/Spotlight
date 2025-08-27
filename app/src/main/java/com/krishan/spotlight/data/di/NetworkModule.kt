package com.krishan.spotlight.data.di

import com.krishan.spotlight.BuildConfig
import com.krishan.spotlight.data.Constants
import com.krishan.spotlight.data.remote.api.NewsApi
import com.krishan.spotlight.data.repository.NewsRepositoryImpl
import com.krishan.spotlight.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesBaseUrl(): String = Constants.BASE_URL

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain
            .request()
            .newBuilder()
            .addHeader("X-Api-Key", BuildConfig.NEWS_API_KEY)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        //  toMediaType() parses the string into an OkHttp MediaType object.
        //  This tells Retrofit which MIME type to attach to request bodies and expect in responses.
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys =
                true // lets the parser skip extra fields coming from the server that aren’t in your data classes.
            coerceInputValues = true // fills in default values for missing or null fields instead of throwing an error.
        }
        /*
        * This is how all these work
       [ Your Kotlin Model for request]
          ↓ serialize
       [ json.encodeToString(...) ]
          ↓ Converter.Factory
       [ RequestBody (application/json) ]
          ↓ OkHttp → Server → OkHttp
       [ ResponseBody (application/json) ]
          ↓ Converter.Factory
       [ json.decodeFromString(...) ]
          ↓ deserialize
       [ Your Kotlin Model for Response ]

        * */
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun providesNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Provides
    fun providesNewsRepository(newsApi: NewsApi): NewsRepository = NewsRepositoryImpl(newsApi = newsApi)
}