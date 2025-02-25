package uz.gita.otabek.dictionaryapp.adapters

import android.annotation.SuppressLint
import android.database.Cursor
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.otabek.dictionaryapp.R
import uz.gita.otabek.dictionaryapp.app.App
import uz.gita.otabek.dictionaryapp.databinding.ItemWordBinding
import uz.gita.otabek.dictionaryapp.room.MainDao
import uz.gita.otabek.dictionaryapp.room.MainEntity

class MainAdapter(private val tts: TextToSpeech, private val dao: MainDao) : RecyclerView.Adapter<MainAdapter.MainVH>() {

    private var cursor: Cursor? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitCursor(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    inner class MainVH(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mainEntity: MainEntity) {
            if (App.isUSA) {
                binding.wordTextEnglish.text = mainEntity.english.toString()
                binding.wordTextUzbek.text = mainEntity.uzbek.toString()
                binding.music.setOnClickListener {
                    speakWord(binding.wordTextEnglish.text.toString())
                }
            } else {
                binding.wordTextUzbek.text = mainEntity.english.toString()
                binding.wordTextEnglish.text = mainEntity.uzbek.toString()
                binding.music.setOnClickListener {
                    speakWord(binding.wordTextUzbek.text.toString())
                }
            }
            binding.transcriptionText.text = mainEntity.transcript.toString()
            binding.type.text = mainEntity.type.toString()
            if (mainEntity.isFavourite == 0) {
                binding.favourite.setImageResource(R.drawable.bookmark_border)
            } else {
                binding.favourite.setImageResource(R.drawable.bookmark)
            }
            binding.favourite.setOnClickListener {
                if (mainEntity.isFavourite == 0) {
                    binding.favourite.setImageResource(R.drawable.bookmark)
                    dao.updateIsFavouriteById(mainEntity.id, 1)
                } else {
                    binding.favourite.setImageResource(R.drawable.bookmark_border)
                    dao.updateIsFavouriteById(mainEntity.id, 0)
                }
                submitCursor(dao.getAllDictionary())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVH {
        return MainVH(ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: MainVH, position: Int) {
        cursor?.let {
            cursor!!.moveToPosition(position)
            val id = cursor!!.getInt(cursor!!.getColumnIndex("id"))
            val english = cursor!!.getString(cursor!!.getColumnIndex("english"))
            val type = cursor!!.getString(cursor!!.getColumnIndex("type"))
            val transcript = cursor!!.getString(cursor!!.getColumnIndex("transcript"))
            val uzbek = cursor!!.getString(cursor!!.getColumnIndex("uzbek"))
            val isFavourite = cursor!!.getInt(cursor!!.getColumnIndex("is_favourite"))
            holder.bind(MainEntity(english, uzbek, id, transcript, isFavourite, type, ""))
        }
    }

    private fun speakWord(word: String) {
        tts.speak(word, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}