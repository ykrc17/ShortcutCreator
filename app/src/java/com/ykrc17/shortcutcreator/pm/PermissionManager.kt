package com.ykrc17.shortcutcreator.pm

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build

class PermissionManager(private val activity: Activity) {

    private val callbacks = hashMapOf<Int, () -> Unit>()

    fun requestPermissions(permission: String, callback: () -> Unit) {
        if (Build.VERSION.SDK_INT >= 23 && activity.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(permission)
            val requestCode = permissions.hashCode()
            callbacks[requestCode] = callback
            activity.requestPermissions(permissions, requestCode)
        } else {
            callback()
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        callbacks[requestCode]?.also { callback ->
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                callback()
            }
        }
        callbacks.remove(requestCode)
    }
}