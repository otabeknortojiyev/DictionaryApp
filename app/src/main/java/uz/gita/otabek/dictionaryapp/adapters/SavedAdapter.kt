package uz.gita.otabek.dictionaryapp.adapters

import android.annotation.SuppressLint
import android.database.Cursor
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.otabek.dictionaryapp.R
import uz.gita.otabek.dictionaryapp.databinding.ItemWordBinding
import uz.gita.otabek.dictionaryapp.room.MainEntity

class SavedAdapter(private val tts: TextToSpeech) : RecyclerView.Adapter<SavedAdapter.SavedVH>() {
    private var wordClickListener: ((Int) -> Unit)? = null
    fun setWordClickListener(block: ((Int) -> Unit)) {
        wordClickListener = block
    }

    private var cursor: Cursor? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitCursor(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    inner class SavedVH(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mainEntity: MainEntity) {
            binding.wordTextEnglish.text = mainEntity.english.toString()
            binding.wordTextUzbek.text = mainEntity.uzbek.toString()
            binding.transcriptionText.text = mainEntity.transcript.toString()
            binding.type.text = mainEntity.type.toString()
            binding.favourite.setImageResource(R.drawable.bookmark)
            binding.music.setOnClickListener {
                speakWord(binding.wordTextEnglish.text.toString())
            }
            binding.favourite.setOnClickListener {
                wordClickListener!!.invoke(mainEntity.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedVH {
        return SavedVH(ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: SavedVH, position: Int) {
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