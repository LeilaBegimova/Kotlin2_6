package com.example.kotlin2_6

import android.content.Context

class PreferenceHelper (context: Context) {

private val preferenceHelper = context.getSharedPreferences("Me pref",Context.MODE_PRIVATE)

    var isHasPermission: Boolean
        get() = preferenceHelper.getBoolean(IS_HAS_PERMISSION,false)

        set(value) {
            preferenceHelper.edit().putBoolean(IS_HAS_PERMISSION, value).apply()
        }

    companion object{
        private val IS_HAS_PERMISSION = "permission"
    }
}