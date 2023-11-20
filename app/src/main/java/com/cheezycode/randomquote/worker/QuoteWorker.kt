package com.cheezycode.randomquote.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cheezycode.randomquote.QuoteApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteWorker(private val context:Context,parameter:WorkerParameters):Worker(context,parameter) {
    override fun doWork(): Result {
        val repository=(context as QuoteApplication).quoteRepository
        CoroutineScope(Dispatchers.IO).launch {
            repository.getquoteBackgroun()
        }
        return  Result.success()
    }
}