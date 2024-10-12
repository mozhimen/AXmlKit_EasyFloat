package com.zj.easyfloat.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.view.addViewSafe
import com.mozhimen.kotlin.utilk.android.view.removeViewSafe
import com.zj.easyfloat.bases.BaseEasyFloatProxy

/**
 * @ClassName EasyFloatProxy
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/11
 * @Version 1.0
 */
@OApiInit_ByLazy
open class EasyFloatProxyWinMgr : BaseEasyFloatProxy() {

    override fun attach(activity: Activity) {
        Log.d(TAG, "attach: ${activity}")
        if (_layoutKMagnet == null) {
            Log.w(TAG, "attach: _layoutKMagnet == null generate")
            add(activity.applicationContext)
        }
//        _contentViewRef = WeakReference(container)
        Log.d(TAG, "attach: ")
        if (_layoutKMagnetContainer != null) {
            Log.d(TAG, "attach: _layoutKMagnetContainer")
            activity.windowManager.addViewSafe(_layoutKMagnetContainer!!, activity.window.attributes/*WindowManager.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)*/.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
                generateWindowManagerParams(this)
            })
        } else {
            Log.d(TAG, "attach: _layoutKMagnet")
            activity.windowManager.addViewSafe(_layoutKMagnet!!, activity.window.attributes/*WindowManager.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)*/.apply {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                generateWindowManagerParams(this)
            })
        }
    }

    @SuppressLint("WrongConstant")
    private fun generateWindowManagerParams(layoutParams: WindowManager.LayoutParams) {
        layoutParams.format = PixelFormat.TRANSLUCENT//1->RGB
        layoutParams.gravity = Gravity.LEFT or Gravity.TOP//51
        // 设置触摸外层布局（除 WindowManager 外的布局，默认是 WindowManager 显示的时候外层不可触摸）
        // 需要注意的是设置了 FLAG_NOT_TOUCH_MODAL 必须要设置 FLAG_NOT_FOCUSABLE，否则就会导致用户按返回键无效
//        layoutParams.flags = 263208
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    }

    override fun detach(activity: Activity) {
        Log.d(TAG, "detach: ${activity}")
        if (_layoutKMagnetContainer != null) {
            Log.d(TAG, "detach: _layoutKMagnetContainer")
            activity.windowManager.removeViewSafe(_layoutKMagnetContainer!!)
        } else if (_layoutKMagnet != null) {
            Log.d(TAG, "detach: _layoutKMagnet")
            activity.windowManager.removeViewSafe(_layoutKMagnet!!)
        }
    }
}