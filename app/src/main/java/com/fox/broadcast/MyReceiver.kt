package com.fox.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context, intent: Intent?) {
       when (intent?.action) {
           Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
               val turnOn = intent.getBooleanExtra("state", false)
               Toast.makeText(context, "Airplane mode changed State: $turnOn", Toast.LENGTH_SHORT).show()
           }
           Intent.ACTION_BATTERY_LOW -> {

               Toast.makeText(context, "Low battery", Toast.LENGTH_SHORT).show()
           }
           Intent.ACTION_BATTERY_OKAY -> {
               Toast.makeText(context, "Battery Ok", Toast.LENGTH_SHORT).show()
           }
           ACTION_CLICK -> {
               val count = intent.getIntExtra(EXTRA_KEY, 100500)
               Toast.makeText(context, "Button clicked $count times", Toast.LENGTH_SHORT).show()
           }
           MyService.LOADED -> {
               val loaded = intent.getIntExtra(MyService.EXTRA_KEY, 100500)
               Toast.makeText(context, "Data loaded for $loaded %", Toast.LENGTH_SHORT).show()
           }
       }

    }

    companion object {
        const val ACTION_CLICK = "action_click"
        const val EXTRA_KEY = "extra_key"
    }


}