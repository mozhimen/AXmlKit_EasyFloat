package com.mozhimen.xmlk.easyfloat

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.kotlin.elemk.commons.IExt_AListener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.xmlk.layoutk.magnet.LayoutKMagnet
import com.zj.easyfloat.EasyFloatView

/**
 * @ClassName XmlKEasyFloatProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/7/12
 * @Version 1.0
 */
@OApiInit_ByLazy
@OApiCall_BindLifecycle
class XmlKEasyFloatProxy(private var _activity: Activity?) : BaseWakeBefDestroyLifecycleObserver() {

    private val _eastFloat: EasyFloatView by lazy { EasyFloatView.instance }

    ////////////////////////////////////////////////////////////////////////

    fun init(block: IExt_AListener<EasyFloatView, EasyFloatView>): EasyFloatView {
        //        EasyFloat
        //            .layout(R.layout.layout_float_view)
        //            .blackList(mutableListOf(ThirdActivity::class.java))
        //            .layoutParams(initLayoutParams())
        //            .dragEnable(true)
        //            .setAutoMoveToEdge(true)
        //            .listener {
        //                initListener(it)
        //            }
        //            .show(this)
        return _eastFloat.block()
    }

    fun show() {
        if (_activity != null) {
            _eastFloat.show(_activity!!)
        } else {
            UtilKLogWrapper.e(TAG, "show: _activity != null && !_isShow ${_activity != null} && ${!_eastFloat.isRegisterActivityLifecycleCallbacks()}")
        }
    }

    fun getEasyFloatView(): LayoutKMagnet? =
        if (_eastFloat.isRegisterActivityLifecycleCallbacks())
            _eastFloat.getFloatContainer()
        else null

    fun dismiss() {
        if (_activity != null) {
            _eastFloat.dismiss(_activity!!)
        }
    }

    ////////////////////////////////////////////////////////////////////////

    override fun onDestroy(owner: LifecycleOwner) {
        dismiss()
        _activity = null
        super.onDestroy(owner)
    }
}