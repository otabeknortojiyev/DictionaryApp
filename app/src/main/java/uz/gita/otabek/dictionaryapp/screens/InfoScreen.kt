package uz.gita.otabek.dictionaryapp.screens

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import uz.gita.otabek.dictionaryapp.R
import uz.gita.otabek.dictionaryapp.databinding.ScreenInfoBinding
import uz.gita.otabek.dictionaryapp.utils.moveTo

class InfoScreen : Fragment(R.layout.screen_info) {
    private var _binding: ScreenInfoBinding? = null
    private val binding: ScreenInfoBinding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = ScreenInfoBinding.bind(view)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveTo(MainScreen())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}