package app.we.go.emojidraw.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.we.go.emojidraw.model.Emoji;

public class EmojiFileReader {

    private List<Emoji> emojiList;

    @Inject
    public EmojiFileReader(Context context) {
        try (InputStream is =context.getAssets().open("emoji-selected.json", Context.MODE_PRIVATE) ){
            Reader reader = new InputStreamReader(is);
            Type listType = new TypeToken<ArrayList<Emoji>>() {}.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            emojiList = gson.fromJson(reader, listType);
        } catch (IOException e) {
            Log.e("IO", "Error opening asset with selected emojis");
            emojiList = new ArrayList<>();
        }
    }

    @NonNull
    public List<Emoji> getList() {
        return emojiList;
    }

}
