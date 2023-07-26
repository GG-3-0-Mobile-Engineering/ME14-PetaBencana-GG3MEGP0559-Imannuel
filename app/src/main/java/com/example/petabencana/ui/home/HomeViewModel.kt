package com.example.petabencana.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.petabencana.data.model.UrunDaya
import com.example.petabencana.data.repository.UrunDayaRepository
import com.example.petabencana.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val urunDayaRepository: UrunDayaRepository): ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query


    private var _result = MutableLiveData<Resource<UrunDaya>>()
    val result : LiveData<Resource<UrunDaya>> = query.switchMap {
        liveData {
            urunDayaRepository.getUrunDaya(query.value).collect{
                emit(it)
            }
        }
    }
    

    init {
        setQuery("")
//        getUrunDaya()
    }
//
//    private fun getUrunDaya(){
//        viewModelScope.launch {
//            urunDayaRepository.getUrunDaya(query.value).collect{
//                _result.postValue(it)
//            }
//        }
//    }

    fun setQuery(value : String){
        _query.value = value
    }

}