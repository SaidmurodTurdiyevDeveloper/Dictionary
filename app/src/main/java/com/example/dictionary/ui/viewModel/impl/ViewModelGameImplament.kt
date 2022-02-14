package com.example.dictionary.ui.viewModel.impl

import com.example.dictionary.domen.UseCaseGame
import com.example.dictionary.utils.other.ViewModelHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelGameImplament @Inject constructor(private var usecase: UseCaseGame) : ViewModelHelper() {

}