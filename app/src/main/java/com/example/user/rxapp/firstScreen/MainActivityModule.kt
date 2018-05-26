package com.example.user.rxapp.firstScreen

import dagger.Binds
import dagger.Module

/**
 * @author Kostiantyn Prysiazhnyi on 5/24/2018.
 */
@Module
abstract class MainActivityModule {
    @Binds
    abstract fun provideMainActivityPresenter(presenter: MainActivityPresenter): MainActivityContract.Presenter
}