package com.imannuel.petabencana.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.imannuel.petabencana.data.model.UrunDaya
import com.imannuel.petabencana.data.repository.UrunDayaRepository
import com.imannuel.petabencana.utils.Resource
import kotlinx.coroutines.flow.collectLatest

class HomeViewModel(private val urunDayaRepository: UrunDayaRepository) : ViewModel() {

    private var _area = MutableLiveData<String>()
    private val area: LiveData<String> = _area.distinctUntilChanged()

    private var _disaster = MutableLiveData<String>()
    private val disaster: LiveData<String> = _disaster.distinctUntilChanged()

    private var _timePeriod = MutableLiveData<Int>()
    private val timePeriod: LiveData<Int> = _timePeriod.distinctUntilChanged()

    private val mediatorLiveData = MediatorLiveData<Triple<String?, String?, Int?>>()

    val result: LiveData<Resource<UrunDaya>> = mediatorLiveData.switchMap {
        liveData {
            urunDayaRepository.getUrunDaya(
                admin = it.first,
                disaster = it.second,
                timePeriod = it.third
            ).collectLatest {
                emit(it)
            }
        }
    }


    init {
        setArea("")
        setDisaster("")
        setTimePeriod(0)

        mediatorLiveData.addSource(area) { updateMediatorLiveData() }
        mediatorLiveData.addSource(disaster) { updateMediatorLiveData() }
        mediatorLiveData.addSource(timePeriod) { updateMediatorLiveData() }

    }

    private fun updateMediatorLiveData() {
        val areaValue = area.value
        val disasterValue = disaster.value
        val timePeriodValue = timePeriod.value
        mediatorLiveData.value = Triple(areaValue, disasterValue, timePeriodValue)
    }

    fun setArea(value: String) {
        _area.value = value
    }

    fun setDisaster(value: String) {
        _disaster.value = value
    }

    fun setTimePeriod(value: Int) {
        _timePeriod.value = value
    }


}