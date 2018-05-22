package com.example.user.rxapp.injection.components

import android.app.Application
import com.example.user.rxapp.TasksApp
import com.example.user.rxapp.data.local.LocalDataHelper
import com.example.user.rxapp.injection.modules.ActivityBindingModule
import com.example.user.rxapp.injection.modules.AppModule
import com.example.user.rxapp.injection.modules.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */
@Singleton
@Component(modules = [(AppModule::class),
    (AndroidSupportInjectionModule::class),
    (ActivityBindingModule::class),
    (DataModule::class)])
interface AppComponent : AndroidInjector<TasksApp> {
    fun getLocalDataHelper(): LocalDataHelper
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}