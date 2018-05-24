package com.example.user.rxapp.data.local.dbRoom

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.example.user.rxapp.data.Utils.DateConvertors

/**
 * @author Kostiantyn Prysiazhnyi on 5/21/2018.
 */
@Database(entities = [(TaskDo::class)], version = 1)
@TypeConverters(DateConvertors::class)
abstract class TasksDB : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        private val migrationFrom2To3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE tasks")
            }
        }
        @Volatile
        private var instance: TasksDB? = null

        fun getInstance(context: Context): TasksDB {
            val inst = this.instance
            if (inst != null) {
                return inst
            } else {
                return synchronized(this) {
                    val inst2 = this.instance
                    if (inst2 != null) {
                        inst2
                    } else {
                        val created = Room.databaseBuilder(context.applicationContext,
                                TasksDB::class.java, "TasksApp.db")
                                .build()
                        this.instance = created
                        created
                    }
                }
            }
        }
    }
}