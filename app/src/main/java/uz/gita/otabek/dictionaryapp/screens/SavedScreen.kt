package uz.gita.otabek.dictionaryapp.screens

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import uz.gita.otabek.dictionaryapp.R
import uz.gita.otabek.dictionaryapp.adapters.SavedAdapter
import uz.gita.otabek.dictionaryapp.databinding.ScreenSavedBinding
import uz.gita.otabek.dictionaryapp.room.AppDataBase
import uz.gita.otabek.dictionaryapp.utils.moveTo
import java.util.Locale

class SavedScreen : Fragment(R.layout.screen_saved), TextToSpeech.OnInitListener {
    private var _binding: ScreenSavedBinding? = null
    private val binding: ScreenSavedBinding get() = _binding!!
    private val db by lazy { AppDataBase.getInstance() }
    private var adapter: SavedAdapter? = null
    private lateinit var tts: TextToSpeech

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = ScreenSavedBinding.bind(view)
        tts = TextToSpeech(requireContext(), this)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveTo(MainScreen())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        if (tts.isSpeaking) {
            tts.stop()
        }
        tts.shutdown()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.US)
            adapter = SavedAdapter(tts)
            adapter!!.setWordClickListener {
                db.mainDao().updateIsFavouriteById(it, 0)
                val cursor = db.mainDao().getAllFavourite()
                if (cursor.count == 0) {
                    binding.ivPlaceholder.visibility = View.VISIBLE
                } else binding.ivPlaceholder.visibility = View.INVISIBLE
                adapter!!.submitCursor(cursor)
            }
            val cursor = db.mainDao().getAllFavourite()
            if (cursor.count == 0) {
                binding.ivPlaceholder.visibility = View.VISIBLE
            } else binding.ivPlaceholder.visibility = View.INVISIBLE
            adapter!!.submitCursor(cursor)
            binding.rv2.adapter = adapter
        }
    }
}