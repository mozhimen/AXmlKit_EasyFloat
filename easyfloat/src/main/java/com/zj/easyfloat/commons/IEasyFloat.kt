package com.zj.easyfloat.commons

import android.app.Activity
import android.content.Context
import android.graphics.RectF
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.xmlk.layoutk.magnet.LayoutKMagnet

/**
 * @ClassName IEasyFloat
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/11
 * @Version 1.0
 */
interface IEasyFloat<T> {
    fun getLayout(): View?

    fun getLayoutId(): Int

    fun getFloatContainer(): FrameLayout?

    fun getLifecycleOwner(): LifecycleOwner

    //////////////////////////////////////////////////

    fun add(context: Context): T

    fun remove(): T

    fun attach(activity: Activity): T

    fun detach(activity: Activity): T

    fun customView(view: View): T

    fun customView(@LayoutRes intLayoutId: Int): T

    fun layoutParams(layoutParams: FrameLayout.LayoutParams): T

//    fun listener(magnetViewListener: ILayoutKMagnetListener): T

    fun dragEnable(dragEnable: Boolean): T

    fun setAutoMoveToEdge(autoMoveToEdge: Boolean): T

    fun setInitMargin(margin: RectF): T
}