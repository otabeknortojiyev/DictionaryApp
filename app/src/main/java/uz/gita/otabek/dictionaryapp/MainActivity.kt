package uz.gita.otabek.dictionaryapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import uz.gita.otabek.dictionaryapp.screens.SplashScreen
import uz.gita.otabek.dictionaryapp.utils.createFragment

class MainActivity : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createFragment(R.id.main_container, SplashScreen())
    }
}