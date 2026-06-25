package com.mooji.cod.moojifood.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//منظور ::class یعنی نشان میدهد این را بعنوان یک کلاس در نظر بگیر
@Database(version = 1, entities = [Food::class], exportSchema = false)
abstract class MyDatabase : RoomDatabase() {



    abstract val foodDao: FoodDao


    companion object {

        @Volatile
        private var dataBase: MyDatabase? = null
        fun getDatabase(context: Context): MyDatabase {
            synchronized(this) {
                if (dataBase == null) {

                    dataBase = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "myDatabase.db"
                    )
                        //اجازه میده دیتابیس رو رشته اصلی هم اجرا بشه که استفاده ازش مناسب نیست و اینجا جنبه اموزشی داره
                        .allowMainThreadQueries()
                        .build()

                }

                return dataBase!!
            }


        }
    }
}

//
//return INSTANCE ?: synchronized(this) {
//    val instance = Room.databaseBuilder(
//        context.applicationContext,
//        DatabaseClass::class.java,
//        "database_name",
//    )
//        .fallbackToDestructiveMigration()
//        .build()