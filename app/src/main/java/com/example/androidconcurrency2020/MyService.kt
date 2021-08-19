package com.example.androidconcurrency2020

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    private val binder=MyServiceBinder()
    override fun onBind(intent: Intent): IBinder =binder


   public fun doSomething(){
        Log.d(LOG_TAG, "doSomething: ")
    }
    inner class MyServiceBinder:Binder(){
        fun getService():MyService{
            return this@MyService
        }
    }

}