package app.we.go.emojidraw.features.practice

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SequentialSharedPrefsHelper @Inject constructor(context: Context) {

    private val sharedPrefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    val currentIndex: Int
        get() = sharedPrefs.getInt(CURRENT_SEQUENTIAL_INDEX, 0)

    fun increaseCurrentIndex() {
        sharedPrefs.edit().putInt(CURRENT_SEQUENTIAL_INDEX, currentIndex + 1).apply()
    }

    companion object {
        private const val CURRENT_SEQUENTIAL_INDEX = "currentSeqIndex"
    }

}
