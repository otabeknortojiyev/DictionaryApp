package uz.gita.otabek.dictionaryapp.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.otabek.dictionaryapp.app.App

@Database(entities = [MainEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun mainDao(): MainDao

    companion object {
        private lateinit var instance: AppDataBase
        fun getInstance(): AppDataBase {
            if (!(Companion::instance.isInitialized)) {
                instance =
                    Room.databaseBuilder(App.context, AppDataBase::class.java, "DictionaryApp.db")
                        .createFromAsset("dictionary2.db")
                        .allowMainThreadQueries()
                        .build()
            }
            return instance
        }
    }
}