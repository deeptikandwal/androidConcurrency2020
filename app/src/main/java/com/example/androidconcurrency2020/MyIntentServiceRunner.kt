package com.example.androidconcurrency2020

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import androidx.core.app.JobIntentService


private const val ACTION_FOO = "com.example.androidconcurrency2020.action.FOO"
private const val ACTION_BAZ = "com.example.androidconcurrency2020.action.BAZ"

private const val EXTRA_PARAM1 = "com.example.androidconcurrency2020.extra.PARAM1"
private const val EXTRA_PARAM2 = "com.example.androidconcurrency2020.extra.PARAM2"
private const val JOB_ID_RUNNER = 202


class MyIntentServiceRunner : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        when (intent.action) {
            ACTION_FOO -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionFoo(param1, param2)
            }
            ACTION_BAZ -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionBaz(param1, param2)
            }
        }
    }

    private fun handleActionFoo(param1: String?, param2: String?) {
        Log.d(LOG_TAG, "handleActionFoo:  CREATED FOR PRACTICE")
    }


    private fun handleActionBaz(param1: String?, param2: String?) {
        Log.d(LOG_TAG, "handleActionBaz:  CREATED FOR PRACTICE")
    }

    companion object {

        @JvmStatic
        fun startActionFoo(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyIntentServiceRunner::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            enqueueWork(context,MyIntentServiceRunner::class.java, JOB_ID_RUNNER,intent)
        }


        @JvmStatic
        fun startActionBaz(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyIntentServiceRunner::class.java).apply {
                action = ACTION_BAZ
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            enqueueWork(context,MyIntentServiceRunner::class.java, JOB_ID_RUNNER,intent)
        }
    }
}