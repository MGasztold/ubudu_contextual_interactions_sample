package com.ubudu.contextualsample

import android.location.Location
import android.util.Log
import com.ubudu.sdk.UbuduArea
import com.ubudu.sdk.UbuduAreaDelegate
import com.ubudu.sdk.UbuduEvent
import java.net.URL

class MyAreaDelegate : UbuduAreaDelegate {

    companion object {
        const val TAG = "MyAreaDelegate"
    }

    override fun statusChanged(status: Int): Boolean {
        Log.i(TAG,"statusChanged $status")
        return false
    }

    override fun positionChanged(location: Location?) {
        Log.i(TAG,"positionChanged ${location?.accuracy}")
    }

    override fun areaEntered(area: UbuduArea?) {
        Log.i(TAG,"areaEntered ${area?.id()}")
    }

    override fun areaExited(area: UbuduArea?) {
        Log.i(TAG,"areaExited ${area?.id()}")
    }

    override fun notifyServer(url: URL?): Boolean {
        Log.i(TAG,"notifyServer $url")
        return false
    }

    override fun shouldNotifyUserForEvent(event: UbuduEvent?): Boolean {
        Log.i(TAG,"shouldNotifyUserForEvent ${event?.rule()?.id()}")
        return true
    }

    override fun ruleFiredForEvent(event: UbuduEvent?) {
        Log.i(TAG,"ruleFiredForEvent ${event?.rule()?.id()}")
    }

    override fun notifyUserForEvent(event: UbuduEvent?) {
        Log.i(TAG,"notifyUserForEvent ${event?.rule()?.id()}")
    }

    override fun notificationActionTriggeredForEvent(event: UbuduEvent?, customActionIdentifier: String?) {
        Log.i(TAG,"notificationActionTriggeredForEvent ${event?.rule()?.id()}")
    }
}