package com.example.androidconcurrency2020

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.androidconcurrency2020.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.net.URL
import java.nio.charset.Charset
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private val fileurl: String? = "https://www.w3.org/TR/PNG/iso_8859-1.txt"
    val key = "msg_key"
    lateinit var viewmodel: MainViewModel
    private lateinit var myService: MyService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {

            log("Service Connecting")
            val binder = binder as MyService.MyServiceBinder
            myService = binder.getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }
    private lateinit var binding: ActivityMainBinding

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {

            val bundle = msg.data
            val message = bundle.get(key)
            binding.logDisplay.append("$message \n")
            scrollTextToEnd()
        }
    }

    override fun onStart() {
        super.onStart()

        bindService(Intent(this, MyService::class.java), connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding for view object references
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize button click handlers
        with(binding) {
            runButton.setOnClickListener { runCode() }
            clearButton.setOnClickListener {
                viewmodel.cancelJob()
                clearOutput()
            }
        }

        viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewmodel.mydata.observe(this, Observer {
            binding.logDisplay.text = it.toString()
        })
    }

    /**
     * Run some code
     */
    private fun runCode() {

        clearOutput()
        myService.doSomething()

//        val constraints =
//            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
//        val request = OneTimeWorkRequestBuilder<MyWorker>().setConstraints(constraints).build()
//        val manager = WorkManager.getInstance(this)
//        manager.enqueue(request)
//        manager.getWorkInfoByIdLiveData(request.id).observe(this, Observer {
//            when (it.state) {
//                WorkInfo.State.SUCCEEDED -> {
//                    log("work finished")
//                    val result = it.outputData.getString(WORKER_KEY) ?: " null data"
//                }
//
//                WorkInfo.State.RUNNING -> {
//                    val progress = it.outputData.getString(MESSAGE_KEY)
//                    if (progress != null) {
//                        log(progress)
//                    }
//                }
//
//
//            }
//
//
//        })

//        MyIntentServiceRunner.startActionFoo(this@MainActivity,"Come","Soon")

//        val receiver = MyResultReceiver(handler)
//        MyJobIntentService.startAction(this, FILE_URL, receiver)

//        viewmodel.doWork()
//        CoroutineScope(Dispatchers.Main).launch {
//            val result = fetchSomething()
//            log(result?:" nothing returned")
//        }

//        Handler().post{ log("Running code from runnable") }
//        log("Running code 1")
//        log("Running code 2")


//        thread(start = true) {
//            for (i in 1..10) {
//                val bundle = Bundle()
//                bundle.putString(key, "runnable loop value $i")
//                 Message().also {
//                    it.data=bundle
//                    handler.sendMessage(it)
//                }
//                Thread.sleep(1000)
//            }
//            log("All done!!")
//
//        }

    }

    /**
     * Clear log display
     */
    private fun clearOutput() {
        binding.logDisplay.text = ""
        scrollTextToEnd()
    }

    /**
     * Log output to logcat and the screen
     */
    @Suppress("SameParameterValue")
    private fun log(message: String) {
        Log.d(LOG_TAG, message)
        binding.logDisplay.append(message + "\n")
        scrollTextToEnd()
//        runOnUiThread {
//            binding.logDisplay.append(message + "\n")
//            scrollTextToEnd()
//        }

    }


    /**
     * Scroll to end. Wrapped in post() function so it's the last thing to happen
     */
    private fun scrollTextToEnd() {
        Handler().post { binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private suspend fun fetchSomething(): String? {

//        delay(1000)
        var contents: String? = null
        return withContext(Dispatchers.IO) {
            val url = URL(fileurl)
            return@withContext url.readText(Charset.defaultCharset())
        }

    }

    private inner class MyResultReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {


            if (resultCode == Activity.RESULT_OK) {
                val filecontents: String = resultData?.getString(FILE_CONTENTS_KEY) ?: "null"
                log(filecontents)
                binding.logDisplay.text = filecontents
            }
        }
    }

/*  coroutine contexts"
   1. Dispatchers.Main
   2. Dispatchers.IO
   3. Dispatchers.Default
   * */
}
