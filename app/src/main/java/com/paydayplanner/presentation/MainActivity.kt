package com.paydayplanner.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.gson.Gson
import com.my.tracker.MyTracker
import com.paydayplanner.data.APPS_FLYER
import com.paydayplanner.data.LINK
import com.paydayplanner.data.SHARED_APPSFLYER_INSTANCE_ID
import com.paydayplanner.data.SHARED_DATA
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val requestPermissionLauncherFireBase = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(
                this,
                "FCM can't post notifications without POST_NOTIFICATIONS permission",
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        askNotificationPermission()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                if (key== LINK) {
                    viewModel.loadLink(
                        link = value.toString(),
                    )
                }
            }
        }

        MyTracker.setAttributionListener {
            Log.d("ASDFGH", "myTracker activity $it")
            viewModel.loadMTDeeplink(
                deeplink = it.deeplink
            )
        }

        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                val gson = Gson()
                val appsFlayer = gson.toJson(conversionData)
                    //conversionData.entries.joinToString(separator = ", ") { "${it.key}=${it.value}" }
                Log.d("ASDFGH", "temp -  $appsFlayer")
                viewModel.loadAFDeeplink(appsFlayer)
            }

            override fun onConversionDataFail(error: String?) {
                println("ASDFGH conversion error $error")
            }

            override fun onAppOpenAttribution(attributionData: MutableMap<String, String>?) {
                attributionData?.forEach {
                    println("ASDFGH attribution key ${it.key} valur ${it.value}")
                }
            }

            override fun onAttributionFailure(error: String?) {
                println("ASDFGH conversion error $error")
            }
        }
        val instanceId = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        val sharedPref = application.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
        sharedPref.edit().putString(SHARED_APPSFLYER_INSTANCE_ID, instanceId).apply()

        AppsFlyerLib.getInstance().init(APPS_FLYER, conversionDataListener, this)
        AppsFlyerLib.getInstance().start(this)
        setContent {
            Sample(
                viewModel = viewModel,
            )

        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                requestPermissionLauncherFireBase.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

}