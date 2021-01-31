package org.bonal.birthdaycard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.bonal.birthdaycard.data.BirthdayRepository
import org.bonal.birthdaycard.model.*
import javax.inject.Inject

@HiltViewModel
class BirthdayViewModel @Inject constructor(
    private val birthdayRepository: BirthdayRepository,
    private val birthdayGuestCardDataMapper: BirthdayGuestCardDataMapper
) : ViewModel() {

    private val _birthdayHost = MutableLiveData<BirthdayHost>()
    val birthdayHost: LiveData<BirthdayHost> = _birthdayHost

    private val _guestList = MutableLiveData<List<BirthdayGuest>>()
    val guestList: LiveData<List<BirthdayGuestCardModel>> =
        Transformations.map(_guestList) { guests ->
            guests.map { birthdayGuestCardDataMapper.map(it) }
        }

    private val _birthdayCardMessage = MutableLiveData<String>()
    val birthdayCardMessage: LiveData<String> = _birthdayCardMessage

    fun loadBirthdayData() {
        val birthdayData = birthdayRepository.getBirtdayData()
        _birthdayHost.value = birthdayData.birthdayHost
        _guestList.value = birthdayData.guestList
        _birthdayCardMessage.value = birthdayData.birthdayCardMessage
    }

}

