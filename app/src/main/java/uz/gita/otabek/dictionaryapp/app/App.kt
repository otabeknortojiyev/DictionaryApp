package uz.gita.otabek.dictionaryapp.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        var isUSA: Boolean = true
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}