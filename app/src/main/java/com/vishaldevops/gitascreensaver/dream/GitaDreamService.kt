package com.vishaldevops.gitascreensaver.dream

import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.service.dreams.DreamService
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.vishaldevops.gitascreensaver.data.Shloka
import com.vishaldevops.gitascreensaver.data.ShlokaRepository
import kotlin.random.Random

/**
 * The screensaver ("Daydream"). Android starts it while the phone is charging or docked,
 * if the user picked "Gita Screensaver" in Settings > Display > Screen saver.
 *
 * Built with plain Android Views instead of Compose: a DreamService is not an Activity,
 * and Views work here without any extra setup.
 */
class GitaDreamService : DreamService() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var shlokas: List<Shloka>
    private var index = 0

    private lateinit var content: LinearLayout
    private lateinit var refView: TextView
    private lateinit var sanskritView: TextView
    private lateinit var translitView: TextView
    private lateinit var hindiView: TextView
    private lateinit var englishView: TextView

    private val showNext = object : Runnable {
        override fun run() {
            // Fade out, swap the text, fade back in.
            content.animate().alpha(0f).setDuration(FADE_MS).withEndAction {
                index = (index + 1) % shlokas.size
                bind(shlokas[index])
                content.animate().alpha(1f).setDuration(FADE_MS).start()
            }.start()
            handler.postDelayed(this, ROTATE_MS)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isInteractive = false   // any touch wakes the phone and closes the screensaver
        isFullscreen = true
        isScreenBright = false  // dimmer screen while charging overnight

        shlokas = ShlokaRepository.load(this)
        index = Random.nextInt(shlokas.size)

        refView = text(14f, SAFFRON)
        sanskritView = text(22f, SAFFRON, lineSpacing = 1.3f)
        translitView = text(15f, GREY, italic = true)
        hindiView = text(18f, LIGHT)
        englishView = text(15f, GREY)

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            val pad = dp(28)
            setPadding(pad, pad, pad, pad)
            addView(refView)
            addView(sanskritView, margins(top = 20))
            addView(translitView, margins(top = 12))
            addView(hindiView, margins(top = 28))
            addView(englishView, margins(top = 12))
        }

        val root = FrameLayout(this).apply {
            setBackgroundColor(Color.BLACK)
            addView(
                content,
                FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER,
                ),
            )
        }
        setContentView(root)
        bind(shlokas[index])
    }

    override fun onDreamingStarted() {
        super.onDreamingStarted()
        handler.postDelayed(showNext, ROTATE_MS)
    }

    override fun onDreamingStopped() {
        handler.removeCallbacks(showNext)
        super.onDreamingStopped()
    }

    override fun onDetachedFromWindow() {
        handler.removeCallbacksAndMessages(null)
        super.onDetachedFromWindow()
    }

    private fun bind(s: Shloka) {
        refView.text = s.reference
        sanskritView.text = s.sanskrit
        translitView.text = s.transliteration
        hindiView.text = s.hindi
        englishView.text = s.english
        // Shift the text a little each time so the same pixels don't stay lit (screen burn-in).
        content.translationY = dp(Random.nextInt(-40, 41)).toFloat()
    }

    private fun text(sizeSp: Float, color: Int, italic: Boolean = false, lineSpacing: Float = 1.15f) =
        TextView(this).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp)
            setTextColor(color)
            gravity = Gravity.CENTER
            setLineSpacing(0f, lineSpacing)
            if (italic) setTypeface(typeface, Typeface.ITALIC)
        }

    private fun margins(top: Int) = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
    ).apply { topMargin = dp(top) }

    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()

    private companion object {
        const val ROTATE_MS = 15_000L   // new shloka every 15 seconds (will become a setting)
        const val FADE_MS = 800L
        val SAFFRON = Color.parseColor("#FFB74D")
        val LIGHT = Color.parseColor("#EEEEEE")
        val GREY = Color.parseColor("#9E9E9E")
    }
}
