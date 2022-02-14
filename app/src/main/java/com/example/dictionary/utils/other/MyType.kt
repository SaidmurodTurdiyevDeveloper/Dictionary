package com.example.dictionary.utils.other

typealias emptyBlock = () -> Unit
typealias sendOneParametreBlock <T> = (T) -> Unit
typealias returnParametreBlock <T> = () -> T
typealias sendAndReturnParametreBlock <T> = (T) -> T
typealias sendAndReturnDifferentParametreBlock <T, K> = (T) -> K
