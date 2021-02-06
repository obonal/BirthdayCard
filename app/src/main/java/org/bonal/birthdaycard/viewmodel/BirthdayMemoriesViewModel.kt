package org.bonal.birthdaycard.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bonal.birthdaycard.data.BirthdayRepository
import org.bonal.birthdaycard.model.*
import javax.inject.Inject

@HiltViewModel
class BirthdayMemoriesViewModel @Inject constructor(
    private val birthdayRepository: BirthdayRepository) : ViewModel() {

    private val _memoriesList = MutableLiveData<List<MemoryCardViewState>>()
    val memoriesList: LiveData<List<MemoryCardViewState>> = _memoriesList

    fun loadMemories() =
        viewModelScope.launch {
            try {
                _memoriesList.value = birthdayRepository.getMemories().mapNotNull {
                    when {
                        it.pictureUrl != null -> {
                            MemoryCardViewState.PhotoCardState(
                                title = it.title,
                                description = it.description,
                                pictureUrl = it.pictureUrl
                            )
                        }
                        it.videoUrl != null -> {
                            MemoryCardViewState.VideoCardState(
                                title = it.title,
                                description = it.description,
                                videoUrl = it.videoUrl
                            )
                        }
                        else -> {
                            null
                        }
                    }
                }
            } catch (t: Throwable) {
                // TODO: Error handling
            }
        }

}

