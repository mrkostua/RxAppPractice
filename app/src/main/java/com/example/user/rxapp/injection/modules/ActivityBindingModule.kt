package com.example.user.rxapp.injection.modules

import com.example.user.rxapp.injection.ActivityScope
import com.example.user.rxapp.mainScreen.MainActivity
import com.example.user.rxapp.mainScreen.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */
@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    public abstract fun getMainActivity(): MainActivity
}