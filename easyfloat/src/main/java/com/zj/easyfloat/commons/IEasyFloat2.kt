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
interface IEasyFloat2<T>  {
    fun attach(container: FrameLayout?): T

    fun detach(container: FrameLayout?): T
}