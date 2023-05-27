package com.app.widgets.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.EXTRA_WIFI_STATE
import android.net.wifi.WifiManager.WIFI_STATE_DISABLED
import android.net.wifi.WifiManager.WIFI_STATE_DISABLING
import android.net.wifi.WifiManager.WIFI_STATE_ENABLED
import android.net.wifi.WifiManager.WIFI_STATE_ENABLING
import android.net.wifi.WifiManager.WIFI_STATE_UNKNOWN
import com.app.widgets.ui.widgets.connectivity.ConnectivityWidgetReceiver
import com.app.widgets.ui.widgets.connectivity.WifiState

fun wifiStateReceiver(): BroadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action != WifiManager.WIFI_STATE_CHANGED_ACTION) return
        val state = when (intent.getIntExtra(EXTRA_WIFI_STATE, WIFI_STATE_UNKNOWN)) {
            WIFI_STATE_ENABLED -> WifiState.ENABLED
            WIFI_STATE_ENABLING -> WifiState.ENABLING
            WIFI_STATE_DISABLED -> WifiState.DISABLED
            WIFI_STATE_DISABLING -> WifiState.DISABLING
            else -> null
        }

        val widgetIntent = Intent(context, ConnectivityWidgetReceiver::class.java).apply {
            this.action = state?.name
        }

        context?.sendBroadcast(widgetIntent)
    }
}