package com.makinul.bs23104.utils

import android.util.Log

object AppConstants {
    const val productFetchInitialLimit = 10
    const val KEY_HEADER = -100
    const val KEY_FOOTER = -99

    const val STATE_LOADING = 201
    const val STATE_NO_MORE_DATA = 400
    const val STATE_ERROR = 404

    fun showLog(tag: String = TAG, message: String = "Test Message") {
        Log.v(tag, message)
    }
    const val COMMA_SEPARATOR = ","

    private const val TAG = "AppConstants"
}