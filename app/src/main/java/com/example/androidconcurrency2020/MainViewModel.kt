package com.example.androidconcurrency2020

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.net.URL
import java.nio.charset.Charset

class MainViewModel : ViewModel() {
    private val fileurl: String? = "https://www.w3.org/TR/PNG/iso_8859-1.txt"
    private lateinit var job : Job
    val mydata = MutableLiveData<String>()
    fun doWork() {
       job= viewModelScope.launch {
            mydata.value = fetchSomething()
        }
    }

    fun cancelJob(){
        try {
            job.cancel()
            mydata.value="job cancelled"
        }catch (e:UninitializedPropertyAccessException){

        }

    }

    private suspend fun fetchSomething(): String? {

        delay(5000)
        var contents: String? = null
        return withContext(Dispatchers.IO) {
            val url = URL(fileurl)
            return@withContext url.readText(Charset.defaultCharset())
        }

    }
}