package app.we.go.emojidraw.data;

import java.util.List;

public interface EmojiToDrawProvider {

    List<EmojiToDraw> provide(int n);

}
