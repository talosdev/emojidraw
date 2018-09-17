package app.we.go.emojidraw.features.practice

import app.we.go.emojidraw.api.EmojiDetectionProvider
import app.we.go.emojidraw.data.EmojiToDraw
import app.we.go.emojidraw.data.EmojiToDrawProvider
import app.we.go.emojidraw.model.Stroke
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mockito.*

class PracticePresenterTest {

    private val detectionProvider = mock<EmojiDetectionProvider>()

    private val emojiToDrawProvider = mock<EmojiToDrawProvider>()

    private val view = mock<PracticeContract.View>()

    private var presenter = mock<PracticePresenter>()

    @Before
    fun setUp() {
        presenter = PracticePresenter(detectionProvider, emojiToDrawProvider, N_EMOJI)

        val list = listOf(
            EmojiToDraw("one o’clock", ONE_O_CLOCK_EMOJI),
            EmojiToDraw("one-thirty", ONE_THIRTY_EMOJI))

        whenever(emojiToDrawProvider.provide(N_EMOJI)).thenReturn(list)
    }


    @Test
    fun whenUserDrawsCorrectly_thenNextEmoji() {

        val guesses = Single.just(listOf(ONE_O_CLOCK_EMOJI, TWO_O_CLOCK_EMOJI))

        whenever(detectionProvider.getEmojis(anyList<Stroke>())).thenReturn(guesses)

        presenter.bindView(view)
        presenter.onStrokeAdded(emptyList())

        verify(view).onEmojiDrawnCorrectly(ONE_O_CLOCK_EMOJI)
    }

    @Test
    fun whenRequestedEmojiInGuesses_thenTooltipIsShown() {

        val guesses = Single.just(listOf(TWO_O_CLOCK_EMOJI, ONE_O_CLOCK_EMOJI))

        whenever(detectionProvider.getEmojis(anyList<Stroke>())).thenReturn(guesses)

        presenter.bindView(view)
        presenter.onStrokeAdded(emptyList())

        verify(view).showTooltip(1)
    }


    @Test
    fun whenRequestedEmojiNoLongerInGuesses_thenTooltipIsHidden() {

        val guesses1 = Single.just(listOf(TWO_O_CLOCK_EMOJI, ONE_O_CLOCK_EMOJI))
        val guesses2 = Single.just(listOf(TWO_O_CLOCK_EMOJI, TWO_THIRTY_EMOJI))

        whenever(detectionProvider.getEmojis(anyList<Stroke>()))
            .thenReturn(guesses1)
            .thenReturn(guesses2)

        presenter.bindView(view)
        presenter.onStrokeAdded(emptyList())

        verify(view).showTooltip(1)

        presenter.onStrokeAdded(emptyList())
        verify(view).hideTooltip()
    }


    @Test
    fun whenUserDrawsAll_thenOnAllEmojisDrawnCalled() {

        val guesses1 = Single.just(listOf(ONE_O_CLOCK_EMOJI, TWO_O_CLOCK_EMOJI))
        val guesses2 = Single.just(listOf(ONE_THIRTY_EMOJI, TWO_THIRTY_EMOJI))


        whenever(detectionProvider.getEmojis(anyList<Stroke>())).thenReturn(guesses1).thenReturn(guesses2)

        presenter.bindView(view)
        presenter.onStrokeAdded(emptyList())

        verify(view).onEmojiDrawnCorrectly(ONE_O_CLOCK_EMOJI)

        presenter.onStrokeAdded(emptyList())
        verify(view).onAllEmojisDrawn()
    }


    @Test
    fun whenYouAreNaughty_thenYouGetCaught() {

        val guesses1 = Single.just(listOf(TWO_THIRTY_EMOJI, TWO_O_CLOCK_EMOJI))
        val guesses2 = Single.just(listOf(ONE_THIRTY_EMOJI, TWO_THIRTY_EMOJI))


        whenever(detectionProvider.getEmojis(anyList<Stroke>()))
            .thenReturn(guesses1)
            .thenReturn(guesses2)

        presenter.bindView(view)
        presenter.onStrokeAdded(emptyList())

        presenter.onSkip()

        presenter.onStrokeAdded(emptyList())
        presenter.onSkip()

        verify(view).onAllEmojisDrawnWithCheat()
        verify(view, never()).onAllEmojisDrawn()
    }

    companion object {

        private val ONE_O_CLOCK_EMOJI = "🕐"
        private val ONE_THIRTY_EMOJI = "🕜"
        private val TWO_O_CLOCK_EMOJI = "🕑"
        private val TWO_THIRTY_EMOJI = "🕝"
        private val N_EMOJI = 2

        @JvmStatic
        @BeforeClass
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        }
    }
}