package com.paydayplanner

import android.app.Application
import com.my.tracker.MyTracker
import com.paydayplanner.data.APP_METRICA
import com.paydayplanner.data.MY_TRACKER
import com.paydayplanner.data.USER_X
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp
import pro.userx.UserX

@HiltAndroidApp
class Ta19App: Application() {
    override fun onCreate() {
        super.onCreate()

        val config = YandexMetricaConfig.newConfigBuilder(APP_METRICA).build()


        MyTracker.initTracker(MY_TRACKER, this)
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
        UserX.init(USER_X)
    }
}