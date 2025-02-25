package uz.gita.otabek.dictionaryapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("dictionary")
data class MainEntity(
    @ColumnInfo("english")
    val english: String?,
    @ColumnInfo("uzbek")
    val uzbek: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("transcript")
    val transcript: String?,
    @ColumnInfo("is_favourite")
    val isFavourite: Int?,
    @ColumnInfo("type")
    val type: String?,
    @ColumnInfo("countable")
    val countable: String?
)