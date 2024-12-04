package com.roh.aidl

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.roh.my.IMyAidlInterface

class MainActivity : AppCompatActivity() {
    private var sum = 0

    private lateinit var myReceiver: MyBroadCastReceiver
    private val MY_BROADCAST_TEST1 = "com.roh.navi.BOOT_COMPLETED"
    private val MY_BROADCAST_TEST2 = "com.roh.navi.BOOT_ENDED"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btBind: Button = findViewById<Button>(R.id.bt_bind)
        val btUnBind: Button = findViewById<Button>(R.id.bt_unbind)
        val btCheck: Button = findViewById<Button>(R.id.bt_check)
        val tvResult: TextView = findViewById<TextView>(R.id.tv_result)

        setFilter()

        btBind.setOnClickListener {
            Log.e("MainActivity", "btBind--setOnClickListener")
            sendBroadcast(Intent(MY_BROADCAST_TEST1))
//            tvResult.text = sum.toString()
        }
        btUnBind.setOnClickListener {
            Log.e("MainActivity", "btUnBind--setOnClickListener")
            sendBroadcast(Intent(MY_BROADCAST_TEST2))
        }
        btCheck.setOnClickListener {
            Log.e("MainActivity", "btCheck--setOnClickListener")
            val result = isServiceRunning(this, IMyAidlService::class.java)
            Log.e("MainActivity", "btCheck--$result")
        }
    }

    private fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services = manager.getRunningServices(Int.MAX_VALUE)

        for (serviceInfo in services) {
            if (serviceClass.name == serviceInfo.service.className) {
                return true
            }
        }
        return false
    }

    @SuppressLint("SetTextI18n", "NewApi")
    private fun setFilter() {
        val filter = IntentFilter()
        filter.addAction(MY_BROADCAST_TEST1)
        filter.addAction(MY_BROADCAST_TEST2)
        myReceiver = MyBroadCastReceiver(mContext = this)
        registerReceiver(myReceiver, filter, RECEIVER_NOT_EXPORTED)
    }
}


