package com.example.dictionary.ui.viewModel.impl

import com.example.dictionary.domen.UseCaseSetting
import com.example.dictionary.utils.other.ViewModelHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelSettingImplament @Inject constructor(private var useCase: UseCaseSetting) : ViewModelHelper() {

}