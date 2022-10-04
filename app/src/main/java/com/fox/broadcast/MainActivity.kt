package com.fox.broadcast

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.fox.broadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding:ActivityMainBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    private val receiver = MyReceiver()
    var count = 0

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
    }
}