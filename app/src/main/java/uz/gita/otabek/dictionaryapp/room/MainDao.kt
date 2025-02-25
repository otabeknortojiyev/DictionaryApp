package uz.gita.otabek.dictionaryapp.room

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query

@Dao
interface MainDao {

    @Query("SELECT * FROM dictionary")
    fun getAllDictionary(): Cursor

    @Query("SELECT * FROM DICTIONARY WHERE DICTIONARY.english LIKE '%' || :query || '%'")
    fun searchByEnglishWord(query: String): Cursor

    @Query("SELECT * FROM DICTIONARY WHERE DICTIONARY.uzbek LIKE '%' || :query || '%'")
    fun searchByUzbekWord(query: String): Cursor

    @Query("UPDATE DICTIONARY SET is_favourite = :newValue WHERE id = :id")
    fun updateIsFavouriteById(id: Int, newValue: Int)

    @Query("SELECT * FROM DICTIONARY WHERE DICTIONARY.is_favourite = 1")
    fun getAllFavourite(): Cursor
}