package com.example.user.rxapp.mainScreen

import dagger.Binds
import dagger.BindsInstance
import dagger.Module

@Module
abstract class MainActivityModule {
    @Binds
    abstract fun provideMainAlarmPresenter(presenter : MainActivityPresenter) : MainActivityContract.Presenter
    //provide presenter for MainActivity
}
