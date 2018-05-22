package com.example.user.rxapp

import com.example.user.rxapp.injection.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */
class TasksApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}