package com.zj.easyfloat.helpers

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.app.getContentView
import com.mozhimen.kotlin.utilk.android.view.addViewSafe
import com.mozhimen.kotlin.utilk.android.view.removeViewSafe
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.zj.easyfloat.commons.IEasyFloatView

/**
 * @ClassName EasyFloatProxy
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/11
 * @Version 1.0
 */
@OApiInit_ByLazy
class EasyFloatProxyView : EasyFloatProxyWinMgr(), IEasyFloatView<Unit>, IUtilK {

    override fun attach(activity: Activity) {
        Log.d(TAG, "attach: ${activity}")
        attach(activity.getContentView<View>() as? FrameLayout)//getActivityRoot(activity))
    }

    override fun attach(container: FrameLayout?) {
        if (container == null) {
            Log.w(TAG, "attach: container == null")
            return
        } else if (_layoutKMagnet == null) {
            Log.w(TAG, "attach: _layoutKMagnet == null generate")
            add(container.context)
        }
        Log.d(TAG, "attach: ")
        if (_layoutKMagnetContainer != null) {
            Log.d(TAG, "attach: _layoutKMagnetContainer")
            container.addViewSafe(_layoutKMagnetContainer!!, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            _layoutKMagnetContainer!!.bringToFront()
        } else {
            Log.d(TAG, "attach: _layoutKMagnet")
            container.addViewSafe(_layoutKMagnet!!)
            _layoutKMagnet!!.bringToFront()
        }
    }

    override fun detach(activity: Activity) {
        Log.d(TAG, "detach: ${activity}")
        detach(activity.getContentView<View>() as? FrameLayout)
    }

    override fun detach(container: FrameLayout?) {
        if (_layoutKMagnetContainer != null && container != null ) {
            Log.d(TAG, "detach: _layoutKMagnetContainer")
            container.removeViewSafe(_layoutKMagnetContainer!!)
        } else if (_layoutKMagnet != null && container != null) {
            Log.d(TAG, "detach: _layoutKMagnet")
            container.removeViewSafe(_layoutKMagnet!!)
        }
    }
}