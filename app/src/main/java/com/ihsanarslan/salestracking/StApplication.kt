package com.ihsanarslan.salestracking

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}