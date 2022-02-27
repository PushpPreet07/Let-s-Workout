package com.example.letsworkout

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsworkout.databinding.ActivityExerciseBinding
import com.example.letsworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciseBinding?=null

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0
    private var restTimeDuration: Long = 1

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration : Long = 1

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts : TextToSpeech? = null
    private var mMediaPlayer: MediaPlayer?=null


    private var exerciseAdapter : ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        tts = TextToSpeech(this,this)

        playSound()
        setRestView()
        setUpExerciseStatusRecyclerView()

    }
    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.exercisesound)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)

        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)


        customDialog.setContentView(dialogBinding.root)

        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {

            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }


    private fun setUpExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter=exerciseAdapter
    }

    private fun setRestView(){
        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvQuote?.visibility= View.VISIBLE
        binding?.tvExercise?.visibility= View.INVISIBLE
        binding?.flProgressBarExercise?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility= View.INVISIBLE
        binding?.tvUpcoming?.visibility= View.VISIBLE
        binding?.tvUpComingExerciseName?.visibility=View.VISIBLE


        if (restTimer !=null) {
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvQuote?.text = exerciseList!![currentExercisePosition + 1 ].getQuote()
        binding?.tvUpComingExerciseName?.text = exerciseList!![currentExercisePosition + 1 ].getName()
        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress= restProgress

        restTimer = object : CountDownTimer(restTimeDuration*10000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10- restProgress).toString()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {

                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()


        }

    private fun setupExerciseView(){
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvQuote?.visibility=View.INVISIBLE
        binding?.tvExercise?.visibility= View.VISIBLE
        binding?.flProgressBarExercise?.visibility = View.VISIBLE
        binding?.ivImage?.visibility= View.VISIBLE
        binding?.tvUpcoming?.visibility= View.INVISIBLE
        binding?.tvUpComingExerciseName?.visibility=View.INVISIBLE

        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }

        binding?.tvExercise?.text= exerciseList!![currentExercisePosition].getName()
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())

        setExerciseProgressBar()

        speakOut(binding?.tvExercise?.text.toString())
    }

    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress= exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration*30000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30- exerciseProgress).toString()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {


               if (currentExercisePosition <exerciseList!!.size!! -1){
                   exerciseList!![currentExercisePosition].setIsSelected(false)
                   exerciseList!![currentExercisePosition].setIsCompleted(true)
                   exerciseAdapter!!.notifyDataSetChanged()
                   setRestView()
               }else{


                   val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                   startActivity(intent)
                   mMediaPlayer!!.stop()
                   finish()


               }
            }
        }.start()


        }

    override fun onBackPressed() {

        customDialogForBackButton()
        mMediaPlayer!!.stop()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer !=null){
            restTimer?.cancel()
            restProgress = 0
    }
        if (exerciseTimer !=null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }

        binding = null
    }
    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
        if (tts != null) {
            tts!!.stop()
            tts = null
        }
    }


    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {

            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}