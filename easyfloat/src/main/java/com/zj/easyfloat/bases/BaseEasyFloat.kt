package com.zj.easyfloat.bases

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
import com.zj.easyfloat.commons.IEasyFloat
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @ClassName BaseEasyFloat
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/10/12
 * @Version 1.0
 */
abstract class BaseEasyFloat<T> : BaseActivityLifecycleCallbacks(), IEasyFloat<T>, IUtilK {
    private val _blackList = mutableListOf<Class<*>>()
    private val _isAdd = AtomicBoolean(false)

    ///////////////////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class)
    abstract fun getEasyFloatProxy(): BaseEasyFloatProxy

    ///////////////////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class)
    override fun getLifecycleOwner(): LifecycleOwner {
        return getEasyFloatProxy().getLifecycleOwner()
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun getFloatContainer(): LayoutKMagnet? {
        return getEasyFloatProxy().getFloatContainer()
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun getLayoutId(): Int {
        return getEasyFloatProxy().getLayoutId()
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun getLayout(): View? {
        return getEasyFloatProxy().getLayout()
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun customView(intLayoutId: Int): T {
        getEasyFloatProxy().customView(intLayoutId)
        return this as T
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun customView(view: View): T {
        getEasyFloatProxy().customView(view)
        return this as T
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun layoutParams(layoutParams: FrameLayout.LayoutParams): T {
        getEasyFloatProxy().layoutParams(layoutParams)
        return this as T
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun setInitMargin(margin: RectF): T {
        getEasyFloatProxy().setInitMargin(margin)
        return this as T
    }

    /**
     * 是否可拖拽（位置是否固定）
     */
    @OptIn(OApiInit_ByLazy::class)
    override fun dragEnable(dragEnable: Boolean): T {
        getEasyFloatProxy().dragEnable(dragEnable)
        return this as T
    }

    /**
     * 是否自动靠边
     */
    @OptIn(OApiInit_ByLazy::class)
    override fun setAutoMoveToEdge(autoMoveToEdge: Boolean): T {
        getEasyFloatProxy().setAutoMoveToEdge(autoMoveToEdge)
        return this as T
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun add(context: Context): T {
        getEasyFloatProxy().add(context)
        return this as T
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun remove(): T {
        getEasyFloatProxy().remove()
        return this as T
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun attach(activity: Activity): T {
        getEasyFloatProxy().attach(activity)
        return this as T
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun detach(activity: Activity): T {
        getEasyFloatProxy().detach(activity)
        return this as T
    }

    ///////////////////////////////////////////////////////////////////////////////////

    fun addBlackList(blackList: MutableList<Class<*>>): T {
        _blackList.addAll(blackList)
        return this as T
    }

    fun registerActivityLifecycleCallbacks(application: Application) {
        if (_isAdd.compareAndSet(false, true)) {
            application.registerActivityLifecycleCallbacks(this)
        }
    }

    fun unregisterActivityLifecycleCallbacks(application: Application) {
        if (_isAdd.compareAndSet(true, false)) {
            application.unregisterActivityLifecycleCallbacks(this)
        }
    }

    fun show(activity: Activity) {
        Log.d(TAG, "show: activity $activity")
        attach(activity)
        registerActivityLifecycleCallbacks(activity.application)
    }

    fun dismiss(activity: Activity) {
        unregisterActivityLifecycleCallbacks(activity.application)
        detach(activity)
        remove()
    }

    fun isRegisterActivityLifecycleCallbacks(): Boolean =
        _isAdd.get()

    ///////////////////////////////////////////////////////////////////////////////////

    override fun onActivityStarted(activity: Activity) {
        if (isActivityInValid(activity)) return
        Log.d(TAG, "onActivityStarted: activity $activity")
        attach(activity)
    }

    override fun onActivityStopped(activity: Activity) {
        if (isActivityInValid(activity)) return
        Log.d(TAG, "onActivityStopped: activity $activity")
        detach(activity)
    }

    ///////////////////////////////////////////////////////////////////////////////////

    private fun isActivityInValid(activity: Activity): Boolean {
        return _blackList.contains(activity::class.java)
    }
}