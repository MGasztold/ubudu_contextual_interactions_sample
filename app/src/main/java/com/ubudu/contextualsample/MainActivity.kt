package com.ubudu.contextualsample

import android.Manifest
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.ubudu.contextualsample.databinding.ActivityMainBinding
import com.ubudu.sdk.UbuduBeaconManager
import com.ubudu.sdk.UbuduSDK
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.os.Build
import android.util.Log
import com.ubudu.sdk.UbuduCompletionCallback
import com.ubudu.sdk.UbuduUser

class MainActivity : AppCompatActivity() {

    private val ASK_GEOLOCATION_PERMISSION_REQUEST = 1

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var mSDK: UbuduSDK
    private lateinit var mBeaconManager: UbuduBeaconManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        checkPermissions()
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                    ASK_GEOLOCATION_PERMISSION_REQUEST
                )
            } else {
                initUbuduContextualSDK()
            }
        } else {
            initUbuduContextualSDK()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ASK_GEOLOCATION_PERMISSION_REQUEST -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initUbuduContextualSDK()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun initUbuduContextualSDK() {
        mSDK = UbuduSDK.getSharedInstance(applicationContext)
        mSDK.setAutoRestartDelayMillis(10000L)
        mSDK.setNamespace("bb80ac3a01b73f039fdd0155f0b06d0cc1996742")
        mSDK.setUserInformation(object : UbuduUser {
            override fun userId(): String {
                return ""
            }

            override fun properties(): Map<String, String> {
                return mapOf()
            }

            override fun tags(): Collection<String> {
                val tags: MutableList<String> = ArrayList()
                tags.add("LANG_EN")
                return tags
            }
        }, object : UbuduCompletionCallback {
            override fun onSuccess() {
                mBeaconManager = mSDK.beaconManager
                mBeaconManager.setEnableAutomaticUserNotificationSending(false)
                mBeaconManager.setAreaDelegate(MyAreaDelegate())

                mBeaconManager.setRangedBeaconsNotifier {
                    Log.i("test","scanned beacons ${it.size}")
                }

                mBeaconManager.start()
            }

            override fun onError(message: String) {

            }
        })
    }

}