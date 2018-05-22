package com.example.user.rxapp.injection.modules

import android.content.Context
import com.example.user.rxapp.data.local.dbRoom.TasksDB
import dagger.Module
import dagger.Provides

/**
 * @author Kostiantyn Prysiazhnyi on 5/22/2018.
 */
@Module
class DataModule {
    @Provides
    fun provideTasksDB(context: Context): TasksDB = TasksDB.getInstance(context)
}