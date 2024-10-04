package com.example.paperball.utilities

import android.content.Context
import android.media.MediaPlayer
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class soundManager(private val context: Context) {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private var mediaPlayer: MediaPlayer? = null


    fun playSound(resId: Int) {
        executor.execute {
            mediaPlayer = MediaPlayer.create(context, resId).apply {
                isLooping = false
                setVolume(1.0f, 1.0f)
                start()

                setOnCompletionListener {
                    release()
                    mediaPlayer = null
                }
            }
        }
    }

    fun stopSound() {
        mediaPlayer?.let {
            executor.execute {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
                mediaPlayer = null
            }
        }
    }
}
