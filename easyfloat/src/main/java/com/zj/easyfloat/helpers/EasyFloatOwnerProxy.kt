package com.zj.easyfloat.helpers

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import com.mozhimen.kotlin.elemk.androidx.lifecycle.LifecycleOwnerProxy
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy

/**
 * @ClassName SavedStateRegistryOwnerProxy
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/12
 * @Version 1.0
 */
@OApiInit_ByLazy
class EasyFloatOwnerProxy : SavedStateRegistryOwner, LifecycleOwnerProxy(), ViewModelStoreOwner {
    protected val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    init {
        savedStateRegistryController.performAttach()
    }

    override fun onCreate(name: String) {
        savedStateRegistryController.performRestore(null)
        super.onCreate(name)
    }

    override fun onDestroy(name: String) {
        viewModelStore.clear()
        super.onDestroy(name)
    }

    private var mViewModelStore: ViewModelStore? = null

    override val viewModelStore: ViewModelStore
        get() {
            ensureViewModelStore()
            return mViewModelStore!!
        }

    private fun ensureViewModelStore() {
        if (mViewModelStore == null) {
            mViewModelStore = ViewModelStore()
        }
    }
}