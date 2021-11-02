package com.github.justalexandeer.qrcode.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.justalexandeer.qrcode.data.db.entity.QRCodeEntity
import com.github.justalexandeer.qrcode.repository.QRCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryFragmentViewModel @Inject constructor(
    repository: QRCodeRepository
): ViewModel() {

    val stateListQRCode: MutableStateFlow<List<QRCodeEntity>?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            stateListQRCode.value = repository.getAllQRCodeFromDB()
        }
    }

}