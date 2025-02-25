package uz.gita.otabek.dictionaryapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uz.gita.otabek.dictionaryapp.R

fun AppCompatActivity.createFragment(id: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(id, fragment)
        .commit()
}

fun Fragment.moveTo(fragment: Fragment) {
    parentFragmentManager.beginTransaction()
        .replace(R.id.main_container, fragment)
        .commit()
}