package com.example.letsworkout

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.letsworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishActivity)

        if (supportActionBar!= null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            val mediaPlayer = MediaPlayer.create(this,R.raw.clicksound)
            mediaPlayer.start()
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener{
            val mediaPlayer = MediaPlayer.create(this,R.raw.clicksound)
            mediaPlayer.start()
            finish()

        }

        val dao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao){
        val c= Calendar.getInstance()
        val dateTime = c.time
//        Log.e("Date: ", ""+ dateTime)

        val sdf = SimpleDateFormat("dd MM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
//        Log.e("Formatted Date",date)

        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(date))
        }
    }
}