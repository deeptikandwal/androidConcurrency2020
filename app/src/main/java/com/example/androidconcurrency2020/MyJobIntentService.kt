package com.example.androidconcurrency2020

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import androidx.core.app.JobIntentService
import java.net.URL
import java.nio.charset.Charset

// TODO: Rename actions, choose action names that describe tasks that this
// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
private const val ACTION_FOO = "com.example.androidconcurrency2020.action.FOO"
private const val ACTION_BAZ = "com.example.androidconcurrency2020.action.BAZ"

// TODO: Rename parameters
private const val EXTRA_PARAM1 = "com.example.androidconcurrency2020.extra.PARAM1"
private const val URL = "com.example.androidconcurrency2020.extra.URL"
private const val EXTRA_PARAM2 = "com.example.androidconcurrency2020.extra.PARAM2"
const val JOB_ID = 101

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.

 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.

 */
class MyJobIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        when (intent?.action) {
            ACTION_FOO -> {
                val url = URL(intent.getStringExtra(URL))
                val contents = url.readText(Charset.defaultCharset())
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)

                val bundle = Bundle()
                bundle.putString(FILE_CONTENTS_KEY, contents)
                val receiver = intent.getParcelableExtra<ResultReceiver>(RECEIVER_KEY)
                receiver.send(Activity.RESULT_OK, bundle)
                handleActionFoo(param1, param2)
            }
            ACTION_BAZ -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionBaz(param1, param2)
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String?, param2: String?) {
        Log.i(LOG_TAG, "handleActionFoo: $param1 $param2")

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz(param1: String?, param2: String?) {
    }

    companion object {
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startActionFoo(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyJobIntentService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            enqueueWork(context, MyJobIntentService::class.java, JOB_ID, intent)
        }

        @JvmStatic
        fun startAction(context: Context, url: String, receiver: ResultReceiver) {
            val intent = Intent(context, MyJobIntentService::class.java).apply {
                action = ACTION_FOO
                putExtra(URL, url)
                putExtra(RECEIVER_KEY, receiver)
            }
            enqueueWork(context, MyJobIntentService::class.java, JOB_ID, intent)
        }

        /**
         * Starts this service to perform action Baz with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startActionBaz(context: Context, param1: String, param2: String) {
            val intent = Intent(context, MyJobIntentService::class.java).apply {
                action = ACTION_BAZ
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            enqueueWork(context, MyJobIntentService::class.java, JOB_ID, intent)
        }
    }
}