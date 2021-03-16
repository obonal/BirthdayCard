package org.bonal.birthdaycard.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bonal.birthdaycard.domain.FetchMemoriesUseCase
import org.bonal.birthdaycard.data.model.BirthdayMemory
import javax.inject.Inject

@HiltViewModel
class BirthdayMemoriesViewModel @Inject constructor(
    private val fetchMemoriesUseCase: FetchMemoriesUseCase
) : ViewModel() {

    private val _memoriesList = MutableLiveData<List<MemoryCardViewState>>()
    val memoriesList: LiveData<List<MemoryCardViewState>> = _memoriesList

    private val _galleryViewTitle = MutableLiveData<String>()
    val galleryViewTitle: LiveData<String> = _galleryViewTitle

    fun loadMemories() =
        viewModelScope.launch {
            try {
                val galleryData = fetchMemoriesUseCase.fetchBirthdayMemories()
                _memoriesList.value = galleryData.memories.map {
                    mapBirthdayMemoryToMemoryCardViewState(it)
                }
                _galleryViewTitle.value = galleryData.galleryViewTitle
            } catch (t: Throwable) {
                t.printStackTrace()
                _memoriesList.value = listOf(
                    MemoryCardViewState.TextCardState(
                        title = "ERROR",
                        description = t.localizedMessage
                    )
                )
            }
        }
}

private fun mapBirthdayMemoryToMemoryCardViewState(memory: BirthdayMemory) =
    when {
        memory.pictureUrl != null -> {
            MemoryCardViewState.PhotoCardState(
                title = memory.title,
                description = memory.description,
                pictureUrl = memory.pictureUrl
            )
        }
        memory.videoUrl != null -> {
            MemoryCardViewState.VideoCardState(
                title = memory.title,
                description = memory.description,
                videoUrl = memory.videoUrl
            )
        }
        else -> {
            MemoryCardViewState.TextCardState(
                title = memory.title,
                description = memory.description
            )
        }
    }


