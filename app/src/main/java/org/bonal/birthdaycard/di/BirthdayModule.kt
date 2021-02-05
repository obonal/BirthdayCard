package org.bonal.birthdaycard.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.bonal.birthdaycard.BuildConfig
import org.bonal.birthdaycard.MessageSender
import org.bonal.birthdaycard.WhatsAppMessageSender
import org.bonal.birthdaycard.network.BirthdayCardContentApiService
import org.bonal.birthdaycard.network.BirthdayMemoriesApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BirthdayModule {

    @Binds
    abstract fun bindMessageSender(analyticsServiceImpl: WhatsAppMessageSender): MessageSender
}

@Module
@InstallIn(SingletonComponent::class)
class BirthdayProviderModule {
    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BuildConfig.SERVER_BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideMainApiService(retrofit: Retrofit): BirthdayCardContentApiService = retrofit.create(BirthdayCardContentApiService::class.java)

    @Provides
    @Singleton
    fun provideMemoriesApiService(retrofit: Retrofit): BirthdayMemoriesApiService = retrofit.create(BirthdayMemoriesApiService::class.java)

}