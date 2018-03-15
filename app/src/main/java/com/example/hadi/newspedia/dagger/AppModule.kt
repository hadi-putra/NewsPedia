package com.example.hadi.newspedia.dagger

import android.content.Context
import com.example.hadi.newspedia.App
import com.example.hadi.newspedia.BuildConfig
import com.example.hadi.newspedia.NewsApi
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

/**
 * Created by hadi on 31/01/18.
 */
@Module
class AppModule {

    @Singleton
    @Provides
    internal fun provideContext(app: App): Context = app.applicationContext

    @Singleton
    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        val okHttp = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()

        val addKeyInterceptor = Interceptor{chain ->
            var request = chain.request()
            val httpUrl = request.url().newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.NEWS_KEY).build()
            request = request.newBuilder().url(httpUrl).build()
            chain.proceed(request)
        }

        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttp.addInterceptor(interceptor)

        if (!okHttp.interceptors().contains(addKeyInterceptor))
            okHttp.addInterceptor(addKeyInterceptor)

        return okHttp.build()
    }

    @Singleton
    @Provides
    internal fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
                .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    internal fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    companion object {
        private const val BASE_URL = "https://newsapi.org/"
    }
}