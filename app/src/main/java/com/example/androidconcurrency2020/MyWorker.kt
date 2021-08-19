package com.example.androidconcurrency2020

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.nio.charset.Charset
import java.security.AccessControlContext
import kotlin.math.log

class MyWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {


        val contents= withContext(Dispatchers.IO){


            setProgress(workDataOf(MESSAGE_KEY to "doing some work"))
            delay(1000)
            setProgress (workDataOf(MESSAGE_KEY to "doing some more work"))
            delay(1000)

            Log.i(LOG_TAG, "doWork: ")
            val url = java.net.URL(FILE_URL)
            return@withContext url.readText(Charset.defaultCharset())
        }
        val result = workDataOf(WORKER_KEY to contents)

        return Result.success(result)
    }
}
//}class MyWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
//    override fun doWork(): Result {
//        Log.i(LOG_TAG, "doWork: ")
//        val url = java.net.URL(FILE_URL)
//        val  contents=url.readText(Charset.defaultCharset())
//        val result= workDataOf(WORKER_KEY to contents)
//        return Result.success(result)
//    }
//}