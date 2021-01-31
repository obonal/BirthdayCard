package org.bonal.birthdaycard.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.components.SingletonComponent
import org.bonal.birthdaycard.MessageSender
import org.bonal.birthdaycard.WhatsAppMessageSender

@Module
@InstallIn(SingletonComponent::class)
abstract class BirthdayModule {

    @Binds
    abstract fun bindMessageSender(analyticsServiceImpl: WhatsAppMessageSender): MessageSender

}