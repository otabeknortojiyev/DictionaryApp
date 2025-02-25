package uz.gita.otabek.dictionaryapp.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import uz.gita.otabek.dictionaryapp.R
import uz.gita.otabek.dictionaryapp.utils.moveTo

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val timer = object : CountDownTimer(2500, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                moveTo(MainScreen())
            }
        }
        timer.start()
    }
}