package com.example.dictionary.domen.impl

import com.example.dictionary.domen.UseCaseGame
import com.example.dictionary.domen.repository.RepositoryGame
import javax.inject.Inject

class UseCaseGameImplament @Inject constructor(private var repository:RepositoryGame):UseCaseGame {
}