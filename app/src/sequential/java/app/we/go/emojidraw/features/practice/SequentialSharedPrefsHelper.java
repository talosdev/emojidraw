package app.we.go.emojidraw.features.practice;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SequentialSharedPrefsHelper {
    private final SharedPreferences sharedPrefs;
    private static final String CURRENT_SEQUENTIAL_INDEX = "currentSeqIndex";

    @Inject
    public SequentialSharedPrefsHelper(Context context) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public int getCurrentIndex() {
        return sharedPrefs.getInt(CURRENT_SEQUENTIAL_INDEX, 0);
    }

    public void increaseCurrentIndex() {
        int i = getCurrentIndex();
        sharedPrefs.edit().putInt(CURRENT_SEQUENTIAL_INDEX, i+1).apply();
    }

}
