package com.zj.easyfloat

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.utilk.commons.IUtilK
import com.zj.easyfloat.floatingview.EnFloatingView
import com.zj.easyfloat.floatingview.FloatingMagnetView
import com.zj.easyfloat.floatingview.FloatingView
import com.zj.easyfloat.floatingview.MagnetViewListener
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

@SuppressLint("StaticFieldLeak")
object EasyFloat : Application.ActivityLifecycleCallbacks, IUtilK {
    private var mLayoutParams = getFloatingLayoutParams()
    private val blackList = mutableListOf<Class<*>>()
    private var mLayout: Int = 0
    private var mView: View? = null

    //编辑添加点击和移除事件, 拖动状态，靠边状态
    private var onRemoveListener: IA_Listener<FloatingMagnetView>? = null
    private var onClickListener: IA_Listener<FloatingMagnetView>? = null
    private var dragEnable = true
    private var autoMoveToEdge = true

    ///////////////////////////////////////////////////////////////////////////////////

    fun getView(): FloatingMagnetView? =
        FloatingView.get().view

    fun layout(layout: Int): EasyFloat {
        mLayout = layout
        return this
    }

    fun layout(view: View): EasyFloat {
        mView = view
        return this
    }

    fun layoutParams(layoutParams: FrameLayout.LayoutParams): EasyFloat {
        mLayoutParams = layoutParams
        return this
    }

    fun blackList(blackList: MutableList<Class<*>>): EasyFloat {
        EasyFloat.blackList.addAll(blackList)
        return this
    }

    fun listener(onRemoveListener: IA_Listener<View?>? = null, onClickListener: IA_Listener<View>? = null): EasyFloat {
        this.onRemoveListener = onRemoveListener
        this.onClickListener = onClickListener
        return this
    }

    /**
     * 是否可拖拽（位置是否固定）
     */
    fun dragEnable(dragEnable: Boolean): EasyFloat {
        this.dragEnable = dragEnable
        FloatingView.get().view?.updateDragState(dragEnable)
        return this
    }

    fun isDragEnable(): Boolean {
        return dragEnable
    }

    /**
     * 是否自动靠边
     */
    fun setAutoMoveToEdge(autoMoveToEdge: Boolean): EasyFloat {
        this.autoMoveToEdge = autoMoveToEdge
        FloatingView.get().view?.setAutoMoveToEdge(autoMoveToEdge)
        return this
    }

    fun isAutoMoveToEdge(): Boolean {
        return autoMoveToEdge
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
        initShow(activity)
        registerActivityLifecycleCallbacks(activity.application)
    }

    fun dismiss(activity: Activity) {
        FloatingView.get().remove()
        FloatingView.get().detach(activity)
        unregisterActivityLifecycleCallbacks(activity.application)
    }

    fun isRegisterActivityLifecycleCallbacks(): Boolean =
        _isAdd.get()

    ///////////////////////////////////////////////////////////////////////////////////

    private val _isAdd = AtomicBoolean(false)

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        if (isActivityInValid(activity)) {
            return
        }
        initShow(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        if (isActivityInValid(activity)) {
            return
        }
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        if (isActivityInValid(activity)) {
            return
        }
        FloatingView.get().detach(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    ///////////////////////////////////////////////////////////////////////////////////

    private fun isActivityInValid(activity: Activity): Boolean {
        return blackList.contains(activity::class.java)
    }

    private fun getFloatingLayoutParams(): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM or Gravity.START
        params.setMargins(0, params.topMargin, params.rightMargin, 500)
        return params
    }

    private fun initShow(activity: Activity) {
        if (mLayout == 0 && mView == null) {
            Log.e(TAG, "initShow: return")
            return
        }
        activity.let {
            if (FloatingView.get().view == null) {
                if (mView != null) {
                    FloatingView.get().customView(
                        EnFloatingView(activity, mView)
                    )
                } else {
                    FloatingView.get().customView(
                        EnFloatingView(activity, mLayout)
                    )
                }
            }
            FloatingView.get().run {
                layoutParams(mLayoutParams)
                attach(it)
                dragEnable(dragEnable)
                this.listener(object : MagnetViewListener {
                    override fun onRemove(magnetView: FloatingMagnetView) {
                        onRemoveListener?.invoke(magnetView)
                    }

                    override fun onClick(magnetView: FloatingMagnetView) {
                        onClickListener?.invoke(magnetView)
                    }
                })
            }
        }
    }
}