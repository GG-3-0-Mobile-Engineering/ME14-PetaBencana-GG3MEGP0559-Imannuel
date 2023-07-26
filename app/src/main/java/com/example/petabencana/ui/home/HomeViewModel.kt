package com.example.petabencana.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.petabencana.data.model.UrunDaya
import com.example.petabencana.data.repository.UrunDayaRepository
import com.example.petabencana.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val urunDayaRepository: UrunDayaRepository): ViewModel() {

    private val _area = MutableLiveData<String>()
    val area: LiveData<String> = _area.distinctUntilChanged()

    private val _disaster = MutableLiveData<String>()
    val disaster: LiveData<String> = _disaster.distinctUntilChanged()

    private val _timePeriod = MutableLiveData<Int>()
    val timePeriod: LiveData<Int> = _timePeriod.distinctUntilChanged()

    private val mediatorLiveData = MediatorLiveData<Triple<String?, String?, Int?>>()

    val result : LiveData<Resource<UrunDaya>> = mediatorLiveData.switchMap {
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

    fun setArea(value:String){
        _area.value = value
    }
    fun setDisaster(value : String){
        _disaster.value = value
    }

    fun setTimePeriod(value : Int){
        _timePeriod.value = value
    }


}