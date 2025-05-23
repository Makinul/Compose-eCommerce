package com.makinul.bs23104.di

import com.google.gson.GsonBuilder
import com.makinul.bs23104.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     *  Base URL for API requests.
     *  This property defines the root endpoint for all network calls within the application.
     *  It allows easy switching between different environments (e.g., development, staging, UAT, production)
     *  by simply changing this single value.
     *
     *  - **Development Server (dummyjson.com):** Used for local development and testing purposes. It often uses mock or dummy data.
     *  - **Staging Server (stgjson.com - commented out):**  A pre-production environment that closely mirrors the production setup.
     *  - **UAT Server (uatjson.com - commented out):** User Acceptance Testing environment where the client/users test the application before production.
     *  - **Production Server (prdjson.com - commented out):** The live, customer-facing environment.
     *
     *  **Important Notes:**
     *  - Uncomment the desired server URL and comment out the others to switch environments.
     *  - Ensure that only one `API_BASE_URL` is uncommented at any given time.
     *  - Replace the example URLs (stgjson.com, uatjson.com, prdjson.com) with actual server addresses if you are using real environments.
     *  - In a real application you should use build config field instead of commented code.
     */
    @Singleton
//    val API_BASE_URL = "https://prdjson.com/" // PRODUCTION server
//    val API_BASE_URL = "https://uatjson.com/" // UAT server
//    val API_BASE_URL = "https://stgjson.com/" // STG server
    val API_BASE_URL = "https://dummyjson.com/" // DEV server

    /**
     * The API key used for authenticating with the external service.
     *
     * This key is retrieved from the `BuildConfig` class, which is automatically
     * generated by the Android build system. The value should be defined in your
     * `gradle.properties` file or environment variables during the build process.
     *
     * **Security Note:**  While stored in BuildConfig, avoid directly hardcoding this key in your source code.
     *  It is best practice to configure this key in your CI/CD pipeline or a secure configuration file for release builds.
     *
     * **Example configuration in `gradle.properties`:**
     * ```
     * API_KEY=your_actual_api_key
     * ```
     * @see BuildConfig
     */
//    val API_KEY = BuildConfig.API_KEY


    /**
     * The HTTP header key for the User-Agent.
     *
     * This constant represents the standard HTTP header field name "User-Agent",
     * which is used to identify the client application, its type, operating system,
     * vendor, and/or version.  It is commonly used in HTTP requests.
     */
    private const val HEADER_KEY_USER_AGENT = "User-Agent"
    private const val USER_AGENT = "Android"

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val gson = GsonBuilder().serializeNulls().create()
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val interceptor = Interceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader(HEADER_KEY_USER_AGENT, USER_AGENT)
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addNetworkInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}