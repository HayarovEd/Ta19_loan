package com.paydayplanner.app.presentation


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.tracker.MyTracker
import com.paydayplanner.app.data.APP_METRICA
import com.paydayplanner.app.data.APY_KEY
import com.paydayplanner.app.data.BACKEND_UNAVAILABLE
import com.paydayplanner.app.data.EXTERNAL_LINK
import com.paydayplanner.app.data.ITEM_ID
import com.paydayplanner.app.data.LOANS
import com.paydayplanner.app.data.MORE_DETAILS
import com.paydayplanner.app.data.OFFER_WALL
import com.paydayplanner.app.data.REQUEST_DB
import com.paydayplanner.app.data.Resource
import com.paydayplanner.app.data.URL
import com.paydayplanner.app.data.setStatusByPush
import com.paydayplanner.app.domain.RepositoryAnalytic
import com.paydayplanner.app.domain.RepositoryServer
import com.paydayplanner.app.domain.Service
import com.paydayplanner.app.domain.SharedKeeper
import com.paydayplanner.app.domain.model.StatusApplication
import com.paydayplanner.app.domain.model.basedto.BaseState
import com.paydayplanner.app.presentation.MainEvent.OnChangeBaseState
import com.paydayplanner.app.presentation.MainEvent.Reconnect
import com.yandex.metrica.YandexMetrica
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: Service,
    private val sharedKeeper: SharedKeeper,
    private val repositoryAnalytic: RepositoryAnalytic,
    private val repositoryServer: RepositoryServer,
) : ViewModel() {
    private var _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()
    private var _lastState = MutableStateFlow<StatusApplication>(StatusApplication.Loading)
    private val _myTracker = MutableStateFlow("")
    private val _appsFlayer = MutableStateFlow("")
    private val _link = MutableStateFlow("")
    private val _yandexMetrikaDeviceId = MutableStateFlow("")
    private val _appsFlayerInstanceId = MutableStateFlow("")

    init {
        loadData()
    }

    fun loadAFDeeplink(deeplink: String) {
        Log.d("ASDFGH", "appsFlayer deeplink -  $deeplink")
        _appsFlayer.value = deeplink
        Log.d("ASDFGH", "appsFlayer start -  ${_appsFlayer.value}")

    }

    fun loadMTDeeplink(deeplink: String) {
        _myTracker.value = deeplink
    }

    fun loadLink(link: String) {
        _link.value = link
    }

    private fun loadData() {
        if (service.checkedInternetConnection()) {
            viewModelScope.launch {
                val sharedYandexMetrica = sharedKeeper.getYandexMetricaDeviceId()
                if (sharedYandexMetrica.isNullOrBlank()) {
                    service.getYandexMetricaDeviceId {
                        _yandexMetrikaDeviceId.value = it ?: ""
                        sharedKeeper.setYandexMetricaDeviceId(it ?: "")
                    }
                } else {
                    _yandexMetrikaDeviceId.value = sharedYandexMetrica
                }
                val instanceIdMyTracker =
                    if (sharedKeeper.getMyTrackerInstanceId().isNullOrBlank()) {
                        val instance = service.instanceIdMyTracker
                        sharedKeeper.setMyTrackerInstanceId(instance)
                        instance
                    } else {
                        sharedKeeper.getMyTrackerInstanceId()
                    }
                val sharedFireBaseToken = sharedKeeper.getFireBaseToken()
                Log.d("GHJIOP", "result fb token $sharedFireBaseToken")
                if (sharedFireBaseToken.isNullOrBlank()) {
                    viewModelScope.launch(Dispatchers.IO)
                    {
                        service.getHmsToken().let { token ->
                            Log.d("GHJIOP", "result fb token? $token")
                            _state.value.copy(
                                fireBaseToken = token
                            )
                                .updateStateUI()
                            sharedKeeper.setFireBaseToken(token ?: "")
                        }
                        getSub3()
                    }
                } else {
                    _state.value.copy(
                        fireBaseToken = sharedFireBaseToken
                    )
                        .updateStateUI()
                    getSub3()
                }

                _appsFlayerInstanceId.value = sharedKeeper.getAppsFlyerInstanceId() ?: ""
                _state.value.copy(
                    instanceIdMyTracker = instanceIdMyTracker
                )
                    .updateStateUI()
            }
            viewModelScope.launch(Dispatchers.IO) {
                val gaid = service.getOAID()
                _state.value.copy(
                    gaid = gaid,
                )
                    .updateStateUI()
                delay(2000)
                getSub1()
            }

            if (sharedKeeper.getSub2().isNullOrBlank()) {
                getFirstSub2()
            }

            getSub5()
            loadDbData()
        } else {
            _state.value.copy(
                statusApplication = StatusApplication.NoConnect
            )
                .updateStateUI()
        }
    }

    private fun MainState.updateStateUI() {
        _state.update {
            this
        }
    }

    fun onEvent(mainEvent: MainEvent) {
        when (mainEvent) {
            Reconnect -> {
                if (service.checkedInternetConnection()) {
                    if (_lastState.value !is StatusApplication.Loading) {
                        _state.value.copy(
                            statusApplication = _lastState.value
                        )
                            .updateStateUI()
                    } else {
                        loadData()
                    }
                } else {
                    _state.value.copy(
                        statusApplication = StatusApplication.NoConnect,
                    )
                        .updateStateUI()
                }
            }

            is OnChangeBaseState -> {
                _state.value.copy(
                    statusApplication = StatusApplication.Connect(mainEvent.baseState),
                )
                    .updateStateUI()
            }


            is MainEvent.OnChangeStatusApplication -> {
                _state.value.copy(
                    statusApplication = mainEvent.statusApplication,
                )
                    .updateStateUI()
            }

            is MainEvent.OnGoToWeb -> {
                _lastState.value = _state.value.statusApplication
                _state.value.copy(
                    statusApplication = StatusApplication.Loading,
                )
                    .updateStateUI()
                if (service.checkedInternetConnection()) {
                    //getSub2()
                    viewModelScope.launch {
                        delay(2000)
                        val completeUrl =
                            "${mainEvent.urlOffer}&aff_sub1=${_state.value.affsub1Unswer}&aff_sub2=${_state.value.affsub2Unswer}&aff_sub3=${_state.value.affsub3Unswer}&aff_sub5=${_state.value.affsub5Unswer}"
                        Log.d("ASDFGH", "url $completeUrl")
                        when (val lastState = _lastState.value) {
                            is StatusApplication.Connect -> {
                                sendGoToOffer(
                                    url = completeUrl,
                                    parameter = OFFER_WALL
                                )
                                when (lastState.baseState) {
                                    BaseState.Loans -> {
                                        sendFromListOffers(
                                            url = completeUrl,
                                            parameter = LOANS
                                        )
                                    }
                                }
                            }

                            is StatusApplication.Info -> {}
                            StatusApplication.Loading -> {}
                            StatusApplication.Mock -> {}
                            StatusApplication.NoConnect -> {}
                            is StatusApplication.Offer -> {
                                sendGoToOffer(
                                    url = completeUrl,
                                    parameter = MORE_DETAILS
                                )
                            }

                            is StatusApplication.Web -> {}
                            StatusApplication.Splash -> {}
                        }
                        _state.value.copy(
                            statusApplication = StatusApplication.Web(
                                url = completeUrl,
                                offerName = mainEvent.nameOffer
                            ),
                        )
                            .updateStateUI()
                    }
                } else {
                    _state.value.copy(
                        statusApplication = StatusApplication.NoConnect,
                    )
                        .updateStateUI()
                }

            }
        }
    }


    private fun getSub1() {
        viewModelScope.launch {
            delay(2000)
            Log.d("ASDFGH", "_yandexMetrikaDeviceId sub1 ${_yandexMetrikaDeviceId.value}")
            Log.d("ASDFGH", "_appsFlayerInstanceId sub1 ${_appsFlayerInstanceId.value}")
            Log.d("ASDFGH", "gaid ${_state.value.gaid}")
            Log.d("ASDFGH", "instanceIdMyTracker ${_state.value.instanceIdMyTracker}")
            when (val result = repositoryAnalytic.getSub1(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                appMetricaId = _yandexMetrikaDeviceId.value,
                appsflyer = _appsFlayerInstanceId.value,
                firebaseToken = "NA",
                myTrackerId = _state.value.instanceIdMyTracker ?: ""
            )) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    Log.d("FGHJJ", "result sub1 ${result.data}")
                    _state.value.copy(
                        affsub1Unswer = result.data?.affsub1 ?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getSub3() {
        viewModelScope.launch {
            delay(2000)
            when (val result = repositoryAnalytic.getSub3(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                appMetricaId = APP_METRICA,
                appsflyer = _appsFlayerInstanceId.value,
                firebaseToken = _state.value.fireBaseToken ?: "",
                myTrackerId = _state.value.instanceIdMyTracker ?: ""
            )) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        affsub3Unswer = result.data?.affsub3 ?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getSub5() {
        viewModelScope.launch {
            delay(2000)
            when (val result = repositoryAnalytic.getSub5(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                gaid = _state.value.gaid ?: ""
            )) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        affsub5Unswer = result.data?.affsub5 ?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getFirstSub2() {
        viewModelScope.launch {
            delay(1000)
            when (val result = repositoryAnalytic.getSub2(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                appsflyer = "",
                myTracker = ""
            )) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    val sub2 = result.data?.affsub2
                    Log.d("ASDFGH", "affsub2UnswerEmpty $sub2")
                    _state.value.copy(
                        affsub2UnswerEmpty = sub2 ?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getSub2(currentMyTracker: String, currentAppsFlyer: String) {
        viewModelScope.launch {
            if (currentMyTracker.isNotBlank()) {
                when (val result = repositoryAnalytic.getSub2(
                    applicationToken = APY_KEY,
                    userId = _state.value.gaid ?: "",
                    appsflyer = "",
                    myTracker = currentMyTracker
                )) {
                    is Resource.Error -> {
                        _state.value.copy(
                            message = result.message ?: "unknown error"
                        )
                            .updateStateUI()
                    }

                    is Resource.Success -> {
                        Log.d("ASDFGH", "currentMyTracker $currentMyTracker")
                        val affsub2Unswer = result.data?.affsub2 ?: ""
                        Log.d("ASDFGH", "affsub2UnswerMT $affsub2Unswer")
                        _state.value.copy(
                            affsub2UnswerMT = affsub2Unswer
                        )
                            .updateStateUI()
                    }
                }
            }
            if (currentAppsFlyer.isNotBlank()) {
                when (val result = repositoryAnalytic.getSub2(
                    applicationToken = APY_KEY,
                    userId = _state.value.gaid ?: "",
                    appsflyer = currentAppsFlyer,
                    myTracker = ""
                )) {
                    is Resource.Error -> {
                        _state.value.copy(
                            message = result.message ?: "unknown error"
                        )
                            .updateStateUI()
                    }

                    is Resource.Success -> {
                        val affsub2Unswer = result.data?.affsub2 ?: ""
                        Log.d("ASDFGH", "affsub2UnswerAF $affsub2Unswer")
                        _state.value.copy(
                            affsub2UnswerAF = affsub2Unswer
                        )
                            .updateStateUI()
                    }
                }
            }
        }
    }

    private fun loadDbData() {
        viewModelScope.launch {
            delay(4000)
            val currentGaid = _state.value.gaid ?: ""

            Log.d("SDFGH", "oaid ${_state.value.gaid}")
            Log.d("SDFGH", "instanceMyTracker ${_state.value.instanceIdMyTracker}")
            Log.d("SDFGH", "-------------------------")
            val db = repositoryServer.getDataDb()
            YandexMetrica.reportEvent(REQUEST_DB, currentGaid)
            MyTracker.trackEvent(REQUEST_DB, mapOf(REQUEST_DB to currentGaid))
            service.sendAppsFlyerEvent(
                key = REQUEST_DB,
                content = mapOf(REQUEST_DB to currentGaid)
            )
            when (db) {
                is Resource.Error -> {
                    YandexMetrica.reportEvent(BACKEND_UNAVAILABLE, currentGaid)
                    MyTracker.trackEvent(
                        BACKEND_UNAVAILABLE,
                        mapOf(BACKEND_UNAVAILABLE to currentGaid)
                    )
                    service.sendAppsFlyerEvent(
                        key = BACKEND_UNAVAILABLE,
                        content = mapOf(BACKEND_UNAVAILABLE to currentGaid)
                    )
                    _state.value.copy(
                        statusApplication = StatusApplication.Mock,
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    if (_link.value.isBlank() || _link.value == " ") {
                        val statusApplication = StatusApplication.Connect(BaseState.Loans)
                        _state.value.copy(
                            statusApplication = statusApplication,
                            dbData = db.data,
                        )
                            .updateStateUI()
                        val sharedSub2 = sharedKeeper.getSub2()
                        if (!sharedSub2.isNullOrBlank()) {
                            _state.value.copy(
                                affsub2Unswer = sharedSub2
                            )
                                .updateStateUI()
                        } else {
                            delay(2000)
                            Log.d("ASDFGH", "myTracker view model2 -  ${_myTracker.value}")
                            Log.d("ASDFGH", "appsFlayer view model -  ${_appsFlayer.value}")
                            getSub2(
                                currentAppsFlyer = _appsFlayer.value,
                                currentMyTracker = _myTracker.value
                            )
                            delay(2000)
                            val tempSub2 = if (_state.value.affsub2UnswerMT.isNotBlank()) {
                                sharedKeeper.setSub2(_state.value.affsub2UnswerMT)
                                _state.value.affsub2UnswerMT
                            } else if (state.value.affsub2UnswerAF.isNotBlank()) {
                                sharedKeeper.setSub2(_state.value.affsub2UnswerAF)
                                _state.value.affsub2UnswerAF
                            } else {
                                sharedKeeper.setSub2(_state.value.affsub2UnswerEmpty)
                                _state.value.affsub2UnswerEmpty
                            }
                            _state.value.copy(
                                affsub2Unswer = tempSub2
                            )
                                .updateStateUI()
                        }
                    } else {
                        delay(1000)
                        val statusApplication = _link.value.setStatusByPush(
                            loans = db.data?.loans ?: emptyList()
                        )
                        _state.value.copy(
                            statusApplication = statusApplication,
                            dbData = db.data,
                        )
                            .updateStateUI()
                        delay(1000)
                    }
                }
            }
        }
    }

    private fun sendGoToOffer(url: String, parameter: String) {
        val sendingData = mapOf(
            ITEM_ID to parameter,
            URL to url
        )
        YandexMetrica.reportEvent(EXTERNAL_LINK, sendingData)
        MyTracker.trackEvent(EXTERNAL_LINK)
        service.sendAppsFlyerEvent(
            key = EXTERNAL_LINK,
            content = sendingData
        )
    }

    private fun sendFromListOffers(url: String, parameter: String) {
        val sendingData = mapOf(
            URL to url
        )
        YandexMetrica.reportEvent(parameter, sendingData)
        MyTracker.trackEvent(parameter, sendingData)
        service.sendAppsFlyerEvent(
            key = parameter,
            content = sendingData
        )
    }
}