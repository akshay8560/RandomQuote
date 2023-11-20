package com.cheezycode.randomquote

import android.annotation.SuppressLint
import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cheezycode.randomquote.api.QuoteService
import com.cheezycode.randomquote.api.RetrofitHelper
import com.cheezycode.randomquote.db.QuoteDatabase
import com.cheezycode.randomquote.repository.QuoteRepository
import com.cheezycode.randomquote.worker.QuoteWorker
import java.util.concurrent.TimeUnit

class QuoteApplication : Application() {

    lateinit var quoteRepository: QuoteRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
        setWorker()
    }


    @SuppressLint("InvalidPeriodicWorkRequestInterval")
    private fun setWorker() {
        val constraint=Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest= PeriodicWorkRequest.Builder(QuoteWorker::class.java,10,TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun initialize() {
        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
        quoteRepository = QuoteRepository(quoteService, database, applicationContext)
    }
}