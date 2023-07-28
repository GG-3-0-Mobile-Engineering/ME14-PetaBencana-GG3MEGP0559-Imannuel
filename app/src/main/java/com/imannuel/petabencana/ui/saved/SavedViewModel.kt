package com.imannuel.petabencana.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imannuel.petabencana.data.repository.UrunDayaRepository
import kotlinx.coroutines.launch

class SavedViewModel(private val urunDayaRepository: UrunDayaRepository): ViewModel() {

    fun getSavedUrunDaya() = urunDayaRepository.getAllSavedUrunDaya()

    fun deleteSavedUrunDaya(id: String) {
        viewModelScope.launch {
            urunDayaRepository.deleteUrunDaya(id)
        }
    }


}