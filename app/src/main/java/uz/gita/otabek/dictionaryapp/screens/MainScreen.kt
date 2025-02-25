package uz.gita.otabek.dictionaryapp.screens

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.otabek.dictionaryapp.R
import uz.gita.otabek.dictionaryapp.adapters.MainAdapter
import uz.gita.otabek.dictionaryapp.app.App
import uz.gita.otabek.dictionaryapp.databinding.ScreenMainBinding
import uz.gita.otabek.dictionaryapp.room.AppDataBase
import uz.gita.otabek.dictionaryapp.utils.moveTo
import java.util.Locale

class MainScreen : Fragment(R.layout.screen_main), TextToSpeech.OnInitListener {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val db by lazy { AppDataBase.getInstance() }
    private var adapter: MainAdapter? = null
    private lateinit var tts: TextToSpeech

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setActions()
        tts = TextToSpeech(requireContext(), this)
        binding.ivLanguage.setOnClickListener {
            if (App.isUSA) {
                binding.ivLanguage.setImageResource(R.drawable.flag_uz)
                val cursor = db.mainDao().getAllDictionary()
                adapter?.submitCursor(cursor)
            } else {
                binding.ivLanguage.setImageResource(R.drawable.flag_usa)
                val cursor = db.mainDao().getAllDictionary()
                adapter?.submitCursor(cursor)
            }
            App.isUSA = !App.isUSA
        }
        binding.ivSaved.setOnClickListener {
            moveTo(SavedScreen())
        }
        binding.ivInfo.setOnClickListener {
            moveTo(InfoScreen())
        }
        binding.sv.setOnSearchClickListener {
            startVoiceInput()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.US)
            adapter = MainAdapter(tts, db.mainDao())
            val cursor = db.mainDao().getAllDictionary()
            adapter!!.submitCursor(cursor)
            binding.rv.adapter = adapter
            setActions()
        }
    }

    private fun setActions() {
        binding.sv.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (App.isUSA) {
                        performSearchByEng(it)
                    } else performSearchByUzb(it)
                }
                return true
            }
        })
    }

    private fun performSearchByEng(query: String) {
        val filteredCursor = db.mainDao().searchByEnglishWord(query)
        this.adapter!!.submitCursor(filteredCursor)
    }

    private fun performSearchByUzb(query: String) {
        val filteredCursor = db.mainDao().searchByUzbekWord(query)
        adapter!!.submitCursor(filteredCursor)
    }

    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Говорите...")

        try {
            startActivityForResult(intent, 100)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Ваше устройство не поддерживает голосовой ввод", Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result?.let {
                binding.sv.setQuery(it[0], false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tts.isSpeaking) {
            tts.stop()
        }
        tts.shutdown()
    }
}