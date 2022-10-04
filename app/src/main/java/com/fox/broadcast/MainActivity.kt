package com.fox.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fox.broadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    private val receiver = MyReceiver()
    private var count = 0

    val receiver2 = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            if (intent?.action == MyService.LOADED) {
                val loaded = intent.getIntExtra(MyService.EXTRA_KEY, 100500)
                binding.progressBar.progress = loaded
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            sendBroadcast(
                Intent(MyReceiver.ACTION_CLICK).apply {
                    putExtra(MyReceiver.EXTRA_KEY, ++count)

                }
            )
        }

        binding.btnService.setOnClickListener {
            startService(
                Intent(this, MyService::class.java)
            )
        }

        val intentFilter2 = IntentFilter(MyService.LOADED)
        registerReceiver(receiver2, intentFilter2)


        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(Intent.ACTION_BATTERY_OKAY)
            addAction(MyReceiver.ACTION_CLICK)
            addAction(MyService.LOADED)
        }
        registerReceiver(receiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        unregisterReceiver(receiver)
        unregisterReceiver(receiver2)

    }
}