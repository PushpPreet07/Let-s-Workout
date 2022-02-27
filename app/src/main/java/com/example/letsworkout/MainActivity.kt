package com.example.letsworkout

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.example.letsworkout.databinding.ActivityMainBinding
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //val flStartButton: FrameLayout=findViewById(R.id.flStart)
        binding?.flStart?.setOnClickListener{
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              vibrator.vibrate(
                  VibrationEffect.createOneShot(
                      100,
                      VibrationEffect.DEFAULT_AMPLITUDE
                  )
              )
          }else{
              vibrator.vibrate(100)
          }
            val mediaPlayer = MediaPlayer.create(this,R.raw.clicksound)
            mediaPlayer.start()
            val intent=Intent(this,ExerciseActivity::class.java)
            startActivity(intent)



        }
        binding?.flBMI?.setOnClickListener {
            // Launching the BMI Activity
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        100,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }else{
                vibrator.vibrate(100)
            }
            val mediaPlayer = MediaPlayer.create(this,R.raw.clicksound)
            mediaPlayer.start()
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        binding?.flHistory?.setOnClickListener{
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        100,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }else{
                vibrator.vibrate(100)
            }
            val mediaPlayer = MediaPlayer.create(this,R.raw.clicksound)
            mediaPlayer.start()
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}