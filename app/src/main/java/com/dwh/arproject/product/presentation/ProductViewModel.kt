package com.dwh.arproject.product.presentation

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.arproject.product.domain.model.ArProductModel
import com.dwh.arproject.product.domain.model.Product
import com.dwh.arproject.product.domain.use_case.GetModelsUseCase
import com.dwh.arproject.product.domain.use_case.RequestARServiceInstallUseCase
import com.google.ar.core.ArCoreApk
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getModelsUseCase: GetModelsUseCase,
    private val requestARServiceInstallUseCase: RequestARServiceInstallUseCase
) : ViewModel() {

    private var _products: MutableStateFlow<List<Product>> = MutableStateFlow(listOf(Product()))
    val products: StateFlow<List<Product>> get() = _products.asStateFlow()

    private var _arProductModels: MutableStateFlow<List<ArProductModel>> = MutableStateFlow(listOf(ArProductModel()))
    val arProductModels: StateFlow<List<ArProductModel>> get() = _arProductModels.asStateFlow()

    private var _productImage: MutableStateFlow<Int> = MutableStateFlow(_products.value.first().image)
    val productImage: StateFlow<Int> get() = _productImage.asStateFlow()

    private var _isZoomDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isZoomDialogVisible: StateFlow<Boolean> get() = _isZoomDialogVisible.asStateFlow()

    private var _isSnackBarVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSnackBarVisible: StateFlow<Boolean> get() = _isSnackBarVisible.asStateFlow()

    fun getProducts() = viewModelScope.launch(Dispatchers.IO) {
        _products.value = getModelsUseCase()
    }

    fun setArProductModels(arProductModels: List<ArProductModel>) {
        _arProductModels.value = arProductModels
    }

    private fun setProductImage(image: Int) {
        _productImage.value = image

    }

    fun showImageZoomDialog(image: Int) {
        setProductImage(image)
        _isZoomDialogVisible.value = true
    }

    fun hideImageZoomDialog() {
        _isZoomDialogVisible.value = false
    }

    private fun showSnackBar() {
        _isSnackBarVisible.value = true
    }

    fun hideSnackBar() {
        _isSnackBarVisible.value = false
    }

    fun isDeviceSupportArCore(context: Context, activity: Activity, onSuccess: (Boolean) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            ArCoreApk.getInstance().checkAvailabilityAsync(context) { availability ->
                if (availability.isSupported) {
                    onSuccess(requestARServiceInstall(activity))
                } else {
                    showSnackBar()
                    onSuccess(false)
                }
            }
        }

    private fun requestARServiceInstall(activity: Activity): Boolean {
        return requestARServiceInstallUseCase(activity)
    }
}