package com.example.user.rxapp.addTask

import dagger.Binds
import dagger.Module

/**
 * @author Kostiantyn Prysiazhnyi on 5/26/2018.
 */
@Module
abstract class AddTaskModule {
    @Binds
    abstract fun provideaddTaskPresenter(presenter: AddTaskPresenter): AddTaskContract.Presenter
}