package com.example.templateapp.di

import com.example.templateapp.api.ApiInterface
import com.example.templateapp.api.AuthApiInterface
import com.example.templateapp.network.TokenAuthenticator
import com.example.templateapp.preferences.SharedPrefManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    @Named("requestInterceptor")
    fun provideRequestInterceptor(): Interceptor {
        return Interceptor.invoke {
            val request = it.request()
            val builder = request.newBuilder()

            // Checking if request does not require "authorization". [Sending this temporary header(skip-authorization) from ApiInterface class]
            val skipAuthorization = request.headers["skip-authorization"] == "true"
            // Remove the temporarily added header from actual request
            builder.removeHeader("skip-authorization")

            val headers = getRequestHeaders(skipAuthorization)
            headers.forEach { (key, value) ->
                builder.addHeader(key, value)
            }

            return@invoke it.proceed(builder.build())
        }
    }

    private fun getRequestHeaders(skipAuthorization: Boolean): HashMap<String, String> {
        val map = HashMap<String, String>()
        val token = SharedPrefManager.token
        // S3 request will not work when we send "Authorization" header,
        // for these cases we need to skip the "Authorization" header
        if (token.isNotEmpty() && skipAuthorization.not()) {
            map["Authorization"] = "Bearer $token"
        }
        map["accept"] = "application/json"
        return map
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(authApiInterface: AuthApiInterface): TokenAuthenticator {
        return TokenAuthenticator(authApiInterface)
    }

    @Provides
    @Singleton
    fun provideOkhttp(
        @Named("requestInterceptor") requestInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .connectTimeout(45, TimeUnit.SECONDS)
            .addInterceptor(requestInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .authenticator(authenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("http://localhost/")// We are providing URL in each calls so we don't need to specify base-url here
                                    // Adding a dummy URL to avoid crash as Retrofit requires base-url while initialization
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

    @Provides
    @Singleton
    fun provideAuthApiInterface(gsonConverterFactory: GsonConverterFactory): AuthApiInterface =
        Retrofit.Builder()
            .baseUrl("http://localhost/")
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(AuthApiInterface::class.java)
}