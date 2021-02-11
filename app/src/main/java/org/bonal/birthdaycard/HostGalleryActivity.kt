package org.bonal.birthdaycard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.bonal.birthdaycard.component.GalleryView
import org.bonal.birthdaycard.presentation.BirthdayMemoriesViewModel

@AndroidEntryPoint
class HostGalleryActivity : AppCompatActivity() {

    private val memoriesViewModel: BirthdayMemoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memoriesViewModel.loadMemories()
        setContent { GalleryView(memoriesViewModel) }
    }
}