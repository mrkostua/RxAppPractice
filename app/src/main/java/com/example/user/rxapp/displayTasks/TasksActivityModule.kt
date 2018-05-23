package com.example.user.rxapp.displayTasks

import dagger.Binds
import dagger.Module

@Module
abstract class TasksActivityModule {
    @Binds
    abstract fun provideMainAlarmPresenter(presenter : TasksActivityPresenter) : TasksActivityContract.Presenter
    //provide presenter for MainActivity
}
