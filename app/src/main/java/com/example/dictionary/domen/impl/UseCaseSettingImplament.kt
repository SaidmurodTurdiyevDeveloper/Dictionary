package com.example.dictionary.domen.impl

import com.example.dictionary.domen.UseCaseSetting
import com.example.dictionary.domen.repository.RepositorySetting
import javax.inject.Inject

class UseCaseSettingImplament @Inject constructor(private var repository: RepositorySetting):UseCaseSetting {
}