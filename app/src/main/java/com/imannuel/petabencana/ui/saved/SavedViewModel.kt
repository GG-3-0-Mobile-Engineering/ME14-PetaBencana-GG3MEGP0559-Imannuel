package com.imannuel.petabencana.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imannuel.petabencana.data.repository.UrunDayaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val urunDayaRepository: UrunDayaRepository): ViewModel() {

    fun getSavedUrunDaya() = urunDayaRepository.getAllSavedUrunDaya()

    fun deleteSavedUrunDaya(id: String) {
        viewModelScope.launch {
            urunDayaRepository.deleteUrunDaya(id)
        }
    }


}