package com.dwh.arproject.product_ar.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ArProductViewModel@Inject constructor() : ViewModel() {

    private val _modelName: MutableStateFlow<String> = MutableStateFlow("")
    val modelName: StateFlow<String> get() = _modelName.asStateFlow()

    fun setModelName(name: String) {
        _modelName.value = name
    }
}