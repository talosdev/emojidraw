package app.we.go.emojidraw.features.practice;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.we.go.emojidraw.data.EmojiToDraw;
import app.we.go.emojidraw.data.EmojiToDrawProvider;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PracticePresenterTest {

    private static final String ONE_O_CLOCK_EMOJI = "🕐";
    private static final String ONE_THIRTY_EMOJI = "🕜";
    private static final String TWO_O_CLOCK_EMOJI = "🕑";
    private static final String TWO_THIRTY_EMOJI = "🕝";
    private static final Integer N_EMOJI = 2;

    @Mock
    EmojiDetectionProvider detectionProvider;

    @Mock
    EmojiToDrawProvider emojiToDrawProvider;

    @Mock
    PracticeContract.View view;

    private PracticePresenter presenter;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                a -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(
                a -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(
                a -> Schedulers.trampoline());

    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new PracticePresenter(detectionProvider, emojiToDrawProvider, N_EMOJI);

        List<EmojiToDraw> list = new ArrayList<>();
        list.add(EmojiToDraw.create("one o’clock", ONE_O_CLOCK_EMOJI));
        list.add(EmojiToDraw.create("one-thirty", ONE_THIRTY_EMOJI));

        when(emojiToDrawProvider.provide(N_EMOJI)).thenReturn(list);
    }



    @Test
    public void whenUserDrawsCorrectly_thenNextEmoji() throws Exception {

        final Single<List<String>> guesses = Single.just(Arrays.asList(ONE_O_CLOCK_EMOJI, TWO_O_CLOCK_EMOJI));


        when(detectionProvider.getEmojis(anyList())).thenReturn(guesses);

        presenter.bindView(view);
        presenter.onStrokeAdded(new ArrayList<>());

        verify(view).onEmojiDrawnCorrectly(ONE_O_CLOCK_EMOJI);
    }

    @Test
    public void whenRequestedEmojiInGuesses_thenTooltipIsShown() throws Exception {

        final Single<List<String>> guesses = Single.just(Arrays.asList(TWO_O_CLOCK_EMOJI, ONE_O_CLOCK_EMOJI));

        when(detectionProvider.getEmojis(anyList())).thenReturn(guesses);

        presenter.bindView(view);
        presenter.onStrokeAdded(new ArrayList<>());

        verify(view).showTooltip(1);
    }


    @Test
    public void whenRequestedEmojiNoLongerInGuesses_thenTooltipIsHidden() throws Exception {

        final Single<List<String>> guesses1 = Single.just(Arrays.asList(TWO_O_CLOCK_EMOJI, ONE_O_CLOCK_EMOJI));
        final Single<List<String>> guesses2 = Single.just(Arrays.asList(TWO_O_CLOCK_EMOJI, TWO_THIRTY_EMOJI));

        when(detectionProvider.getEmojis(anyList())).thenReturn(guesses1).thenReturn(guesses2);

        presenter.bindView(view);
        presenter.onStrokeAdded(new ArrayList<>());

        verify(view).showTooltip(1);

        presenter.onStrokeAdded(new ArrayList<>());
        verify(view).hideTooltip();
    }


    @Test
    public void whenUserDrawsAll_thenOnAllEmojisDrawnCalled() throws Exception {

        final Single<List<String>> guesses1 = Single.just(Arrays.asList(ONE_O_CLOCK_EMOJI, TWO_O_CLOCK_EMOJI));
        final Single<List<String>> guesses2 = Single.just(Arrays.asList(ONE_THIRTY_EMOJI, TWO_THIRTY_EMOJI));


        when(detectionProvider.getEmojis(anyList())).thenReturn(guesses1).thenReturn(guesses2);

        presenter.bindView(view);
        presenter.onStrokeAdded(new ArrayList<>());

        verify(view).onEmojiDrawnCorrectly(ONE_O_CLOCK_EMOJI);

        presenter.onStrokeAdded(new ArrayList<>());
        verify(view).onAllEmojisDrawn();
    }


    @Test
    public void whenYouAreNaughty_thenYouGetCaught() throws Exception {

        final Single<List<String>> guesses1 = Single.just(Arrays.asList(TWO_THIRTY_EMOJI, TWO_O_CLOCK_EMOJI));
        final Single<List<String>> guesses2 = Single.just(Arrays.asList(ONE_THIRTY_EMOJI, TWO_THIRTY_EMOJI));


        when(detectionProvider.getEmojis(anyList())).thenReturn(guesses1).thenReturn(guesses2);

        presenter.bindView(view);
        presenter.onStrokeAdded(new ArrayList<>());

        presenter.onSkip();

        presenter.onStrokeAdded(new ArrayList<>());
        presenter.onSkip();

        verify(view).onAllEmojisDrawnWithCheat();
        verify(view, never()).onAllEmojisDrawn();
    }
}