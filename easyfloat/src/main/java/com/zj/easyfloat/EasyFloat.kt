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
import com.zj.easyfloat.commons.IEasyFloat
import com.zj.easyfloat.helpers.EasyFloatProxy
import java.util.concurrent.atomic.AtomicBoolean

@SuppressLint("StaticFieldLeak")
class EasyFloat : BaseActivityLifecycleCallbacks(), IEasyFloat<EasyFloat>, IUtilK {
    companion object{
        @JvmStatic
        val instance = INSTANCE.holder
    }

    private object INSTANCE {
        val holder = EasyFloat()
    }

    ///////////////////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class)
    private val _easyFloatProxy by lazy { EasyFloatProxy() }
    private val _blackList = mutableListOf<Class<*>>()
    private val _isAdd = AtomicBoolean(false)

    ///////////////////////////////////////////////////////////////////////////////////
    @OptIn(OApiInit_ByLazy::class)
    override fun getLifecycleOwner(): LifecycleOwner {
        return _easyFloatProxy.getLifecycleOwner()
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun getFloatContainer(): LayoutKMagnet? {
        return _easyFloatProxy.getFloatContainer()
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun getLayoutId(): Int {
        return _easyFloatProxy.getLayoutId()
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun getLayout(): View? {
        return _easyFloatProxy.getLayout()
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun customView(intLayoutId: Int): EasyFloat {
        _easyFloatProxy.customView(intLayoutId)
        return this
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun customView(view: View): EasyFloat {
        _easyFloatProxy.customView(view)
        return this
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun layoutParams(layoutParams: FrameLayout.LayoutParams): EasyFloat {
        _easyFloatProxy.layoutParams(layoutParams)
        return this
    }

//    override fun listener(magnetViewListener: ILayoutKMagnetListener): EasyFloat {
//        _easyFloatProxy.listener(magnetViewListener)
//        return this
//    }

    @OptIn(OApiInit_ByLazy::class)
    override fun setInitMargin(margin: RectF) :EasyFloat{
        _easyFloatProxy.setInitMargin(margin)
        return this
    }
    /**
     * 是否可拖拽（位置是否固定）
     */
    @OptIn(OApiInit_ByLazy::class)
    override fun dragEnable(dragEnable: Boolean): EasyFloat {
        _easyFloatProxy.dragEnable(dragEnable)
        return this
    }

    /**
     * 是否自动靠边
     */
    @OptIn(OApiInit_ByLazy::class)
    override fun setAutoMoveToEdge(autoMoveToEdge: Boolean): EasyFloat {
        _easyFloatProxy.setAutoMoveToEdge(autoMoveToEdge)
        return this
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun add(context: Context): EasyFloat {
        _easyFloatProxy.add(context)
        return this
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun remove(): EasyFloat {
        _easyFloatProxy.remove()
        return this
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun attach(activity: Activity): EasyFloat {
        _easyFloatProxy.attach(activity)
        return this
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun attach(container: FrameLayout?): EasyFloat {
        _easyFloatProxy.attach(container)
        return this
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun detach(activity: Activity): EasyFloat {
        _easyFloatProxy.detach(activity)
        return this
    }

    @OptIn(OApiInit_ByLazy::class)
    override fun detach(container: FrameLayout?): EasyFloat {
        _easyFloatProxy.detach(container)
        return this
    }

    ///////////////////////////////////////////////////////////////////////////////////

    fun addBlackList(blackList: MutableList<Class<*>>): EasyFloat {
        _blackList.addAll(blackList)
        return this
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