package com.example.a30androidwithkotlie

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView

class Ch6_MainActivity : AppCompatActivity() {

    private val remainMinuteTextView : TextView by lazy {
        findViewById(R.id.remainMinutesTextView)
    }

    private val remainSecondsTextView : TextView by lazy {
        findViewById(R.id.remainSecondsTextView)
    }

    private val seekBar : SeekBar by lazy {
        findViewById(R.id.seekBar)
    }

    private var currentCountDownTimer : CountDownTimer? = null

    private val soundPool = SoundPool.Builder().build()
    private var tickingSoundId : Int? = null
    private var bellSoundId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ch6_main)

        bindViews()
        initSounds()
    }

    override fun onResume() {
        super.onResume()
        soundPool.autoResume()
    }

    override fun onPause() {
        super.onPause()
        soundPool.autoPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    private fun bindViews() {
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar : SeekBar?, progress : Int, fromUser : Boolean) {
                    if (fromUser) {
                        updateRemainTime(progress * 60 * 1000L)
                    }
                }

                override fun onStartTrackingTouch(seekBar : SeekBar?) {
                    stopCountDown()
                }

                override fun onStopTrackingTouch(seekBar : SeekBar?) {
                    seekBar ?: return // 좌측 값이 널이면 리턴한다
                    if (seekBar.progress == 0) {
                        stopCountDown()
                    } else {
                        startCountDown()
                    }
                }
            }
        )
    }

    private fun initSounds() {
        tickingSoundId = soundPool.load(this, R.raw.timer_ticking, 1)
        bellSoundId = soundPool.load(this, R.raw.timer_bell, 1)
    }

    private fun createCountDownTimer(initialMillis : Long) =
        object  : CountDownTimer(initialMillis, 1000L) {
            override fun onTick(millisUntilFinished : Long) {
                updateRemainTime(millisUntilFinished)
                updateSeekBar(millisUntilFinished)
            }

            override fun onFinish() {
                completeCountDown()
            }
        }

    private fun startCountDown() {
        currentCountDownTimer = createCountDownTimer(seekBar.progress * 60 * 1000L)
        currentCountDownTimer?.start()

        tickingSoundId?.let { soundId -> // let은 널이 아니면 실행
            soundPool.play(soundId, 1F, 1F, 0, -1, 1F)
        }
    }

    private fun stopCountDown() {
        currentCountDownTimer?.cancel()
        currentCountDownTimer = null
        soundPool.autoPause()
    }

    private fun completeCountDown() {
        updateRemainTime(0)
        updateSeekBar(0)

        soundPool.autoPause()
        bellSoundId?.let { soundId ->
            soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
        }
    }

    private fun updateRemainTime(remainMills : Long) {
        val remainSeconds = remainMills / 1000

        remainMinuteTextView.text = "%02d'".format(remainSeconds / 60)
        remainSecondsTextView.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar(remainMills : Long) {
        seekBar.progress = (remainMills / 1000 / 60).toInt()
    }

}