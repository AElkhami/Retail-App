package com.elkhami.retailapp.application

import android.app.Application
import com.elkhami.retailapp.BuildConfig.DEBUG
import com.elkhami.retailapp.logging.PrefixedDebugTree
import com.elkhami.retailapp.utils.AppConstants.LOGGING_PREFIX
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class RetailApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (DEBUG) {
            Timber.plant(PrefixedDebugTree(prefix = LOGGING_PREFIX))
        }
    }
}