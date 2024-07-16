package com.mozhimen.xmlk.easyfloat

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.elemk.commons.IExt_AListener
import com.mozhimen.basick.elemk.commons.IExt_Listener
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.zj.easyfloat.EasyFloat
import com.zj.easyfloat.floatingview.FloatingMagnetView

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

    private var _eastFloat: EasyFloat? = null
    private var _isShow: Boolean = false

    ////////////////////////////////////////////////////////////////////////

    fun init(block: IExt_AListener<EasyFloat, EasyFloat>): EasyFloat {
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
        return EasyFloat.block().also { _eastFloat = it }
    }

    fun show() {
        if (_activity != null && !_isShow) {
            _isShow = true
            _eastFloat?.show(_activity!!)
        } else {
            UtilKLogWrapper.e(TAG, "show: _activity != null && !_isShow ${_activity != null} && ${!_isShow}")
        }
    }

    fun getEasyFloatView(): FloatingMagnetView? =
        if (_isShow) EasyFloat.getView() else null

    fun dismiss() {
        if (_activity != null) {
            _isShow = false
            EasyFloat.dismiss(_activity!!)
        }
    }

    ////////////////////////////////////////////////////////////////////////

    override fun onDestroy(owner: LifecycleOwner) {
        dismiss()
        _activity = null
        _eastFloat = null
        super.onDestroy(owner)
    }
}