package com.example.user.rxapp.injection.modules

import com.example.user.rxapp.displayMain.MainActivity
import com.example.user.rxapp.displayMain.MainActivityModule
import com.example.user.rxapp.injection.ActivityScope
import com.example.user.rxapp.displayTasks.TasksActivity
import com.example.user.rxapp.displayTasks.TasksActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */
@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [(TasksActivityModule::class)])
    abstract fun getTasksActivity(): TasksActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun getMainActivity(): MainActivity
}