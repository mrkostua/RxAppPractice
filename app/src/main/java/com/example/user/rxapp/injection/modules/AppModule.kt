package com.example.user.rxapp.injection.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */

@Module
abstract class AppModule {
    @Binds
    abstract fun provideAppContext(app: Application): Context

}