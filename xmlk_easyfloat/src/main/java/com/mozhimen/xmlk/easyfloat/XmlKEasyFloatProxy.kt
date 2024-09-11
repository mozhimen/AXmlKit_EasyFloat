package com.mozhimen.xmlk.easyfloat

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.kotlin.elemk.commons.IExt_AListener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.xmlk.layoutk.magnet.LayoutKMagnet
import com.zj.easyfloat.EasyFloat

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

    fun getEasyFloatView(): LayoutKMagnet? =
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