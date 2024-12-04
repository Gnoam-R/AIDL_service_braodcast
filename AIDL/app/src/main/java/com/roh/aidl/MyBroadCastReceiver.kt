package com.roh.aidl

import android.app.Activity
import android.app.ActivityManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.roh.my.IMyAidlInterface

class MyBroadCastReceiver(
    private val mContext: Context,
): BroadcastReceiver() {
    private val MY_BROADCAST_TEST1 = "com.roh.navi.BOOT_COMPLETED"
    private val MY_BROADCAST_TEST2 = "com.roh.navi.BOOT_ENDED"

    private var aidlService: IMyAidlInterface? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null) {
            if (intent.action != null) {
                if (intent.action.equals(MY_BROADCAST_TEST1)) {
                    Log.e("MyBroadCastReceiver", "onClicked TEST1")
                    bindAidlService()
                } else if(intent.action.equals(MY_BROADCAST_TEST2)) {
                    Log.e("MyBroadCastReceiver", "onClicked TEST2")
                    unbindAidlService()
                } else {
                    Log.e("MyBroadCastReceiver", "onClicked TEST3")
                }
            }
        }
    }

    private fun bindAidlService() {
        mContext.bindService(Intent(mContext, IMyAidlService::class.java), aidlConnection, Service.BIND_AUTO_CREATE)
    }

    private fun unbindAidlService() {
        if (aidlService != null) {
            Log.e("unbindAidlService", "unbindAidlService")
            mContext.unbindService(aidlConnection)
        }
    }

    private var aidlConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            aidlService = IMyAidlInterface.Stub.asInterface(service)
            Log.e("ServiceConnection", "onServiceConnected")
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            aidlService = null
            Log.e("ServiceConnection", "onServiceDisconnected")
        }
    }

}