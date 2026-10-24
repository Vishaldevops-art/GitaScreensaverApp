package com.vishaldevops.gitascreensaver.data

import android.content.Context
import org.json.JSONObject

/** One verse of the Gita, in all the forms we show. */
data class Shloka(
    val chapter: Int,
    val verse: Int,
    val sanskrit: String,
    val transliteration: String,
    val hindi: String,
    val english: String,
) {
    /** Label like "अध्याय 2 · श्लोक 47  (Bhagavad Gita 2.47)". */
    val reference: String get() = "अध्याय $chapter · श्लोक $verse  (Bhagavad Gita $chapter.$verse)"
}

/**
 * Reads shlokas from app/src/main/assets/shlokas.json.
 * Uses Android's built-in org.json, so no extra library is needed.
 */
object ShlokaRepository {
    private const val FILE_NAME = "shlokas.json"

    @Volatile
    private var cache: List<Shloka>? = null

    fun load(context: Context): List<Shloka> {
        cache?.let { return it }
        val text = context.assets.open(FILE_NAME).bufferedReader(Charsets.UTF_8).use { it.readText() }
        val array = JSONObject(text).getJSONArray("shlokas")
        val list = List(array.length()) { i ->
            val o = array.getJSONObject(i)
            Shloka(
                chapter = o.getInt("chapter"),
                verse = o.getInt("verse"),
                sanskrit = keepDandaWithWord(o.getString("sanskrit")),
                transliteration = o.getString("transliteration"),
                hindi = o.getString("hindi"),
                english = o.getString("english"),
            )
        }
        cache = list
        return list
    }

    /**
     * Swaps the space before "।" / "॥" for a non-breaking space,
     * so the danda never wraps onto a line of its own on narrow or large-font screens.
     */
    private fun keepDandaWithWord(text: String): String =
        text.replace(" ।", " ।").replace(" ॥", " ॥")
}
