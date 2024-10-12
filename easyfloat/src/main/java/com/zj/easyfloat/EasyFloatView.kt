package com.zj.easyfloat

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.RectF
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.kotlin.elemk.android.app.bases.BaseActivityLifecycleCallbacks
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.xmlk.layoutk.magnet.LayoutKMagnet
import com.zj.easyfloat.bases.BaseEasyFloat
import com.zj.easyfloat.bases.BaseEasyFloatProxy
import com.zj.easyfloat.commons.IEasyFloat
import com.zj.easyfloat.commons.IEasyFloatView
import com.zj.easyfloat.helpers.EasyFloatProxyView
import java.util.concurrent.atomic.AtomicBoolean

@SuppressLint("StaticFieldLeak")
class EasyFloatView : BaseEasyFloat<EasyFloatView>() {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    private object INSTANCE {
        val holder = EasyFloatView()
    }

    ///////////////////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class)
    private val _easyFloatProxyView by lazy { EasyFloatProxyView() }

    ///////////////////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class)
    override fun getEasyFloatProxy(): BaseEasyFloatProxy {
        return _easyFloatProxyView
    }
}