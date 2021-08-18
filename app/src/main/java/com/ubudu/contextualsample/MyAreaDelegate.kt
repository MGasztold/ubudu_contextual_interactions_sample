package com.ubudu.contextualsample

import android.location.Location
import com.ubudu.sdk.UbuduArea
import com.ubudu.sdk.UbuduAreaDelegate
import com.ubudu.sdk.UbuduEvent
import java.net.URL

class MyAreaDelegate : UbuduAreaDelegate {

    override fun statusChanged(p0: Int): Boolean {
        return false
    }

    override fun positionChanged(p0: Location?) {

    }

    override fun areaEntered(p0: UbuduArea?) {

    }

    override fun areaExited(p0: UbuduArea?) {

    }

    override fun notifyServer(p0: URL?): Boolean {
        return false
    }

    override fun shouldNotifyUserForEvent(p0: UbuduEvent?): Boolean {
        return true
    }

    override fun ruleFiredForEvent(p0: UbuduEvent?) {

    }

    override fun notifyUserForEvent(p0: UbuduEvent?) {

    }

    override fun notificationActionTriggeredForEvent(p0: UbuduEvent?, p1: String?) {

    }
}