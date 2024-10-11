package com.zj.easyfloat.helpers

import android.app.Activity
import android.content.Context
import android.graphics.RectF
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.activity.findViewTreeOnBackPressedDispatcherOwner
import androidx.activity.setViewTreeOnBackPressedDispatcherOwner
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.app.getContentView
import com.mozhimen.kotlin.utilk.android.view.addViewSafe
import com.mozhimen.kotlin.utilk.android.view.isAttachedToWindow_ofCompat
import com.mozhimen.kotlin.utilk.android.view.removeView_ofParent
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.xmlk.basic.widgets.LayoutKFrame
import com.mozhimen.xmlk.layoutk.magnet.LayoutKMagnet
import com.mozhimen.xmlk.layoutk.magnet.LayoutKMagnet2
import com.zj.easyfloat.commons.IEasyFloat
import kotlin.properties.Delegates

/**
 * @ClassName EasyFloatProxy
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/11
 * @Version 1.0
 */
@OApiInit_ByLazy
open class EasyFloatProxy : IEasyFloat<Unit>, IUtilK {

    companion object {
        const val MARGIN = 0//13
    }

    ////////////////////////////////////////////////////////

    @LayoutRes
    private var _layoutId = 0 //R.layout.en_floating_view;
    private var _layout: View? = null
    private var _layoutParams: FrameLayout.LayoutParams = getDefaultLayoutParams()

    private var _dragEnable = true
    private var _autoMoveToEdge = true
    private var _initMargin = RectF()
    private val _easyFloatOwnerProxy: EasyFloatOwnerProxy by lazy { EasyFloatOwnerProxy() }

    ////////////////////////////////////////////////////////

    protected var _layoutKMagnetContainer: LayoutKFrame? = null
    protected var _layoutKMagnet: LayoutKMagnet? by Delegates.observable(null) { property, oldValue, newValue ->
        if (newValue != null) {
            _easyFloatOwnerProxy.onStart(NAME)
        } else {
            _easyFloatOwnerProxy.onStop(NAME)
        }
    }

    ////////////////////////////////////////////////////////

    init {
        _easyFloatOwnerProxy.onCreate(NAME)
    }

    ////////////////////////////////////////////////////////

    override fun getLifecycleOwner(): LifecycleOwner {
        return _easyFloatOwnerProxy
    }

    override fun getLayoutId(): Int {
        return _layoutId
    }

    override fun getLayout(): View? {
        return _layout
    }

    override fun getFloatContainer(): LayoutKMagnet? {
        return _layoutKMagnet
    }

    override fun add(context: Context) {
        if (_layoutKMagnet != null) return
        _layoutKMagnet = if (_layout != null) {
            LayoutKMagnet2(context, _layout!!)
        } else {
            LayoutKMagnet2(context, _layoutId)
        }.apply {
            layoutParams = _layoutParams
            setDragEnable(_dragEnable)
            setAutoMoveToEdge(_autoMoveToEdge)
            setInitMargin(_initMargin)
            if (findViewTreeLifecycleOwner() == null) {
                setViewTreeLifecycleOwner(_easyFloatOwnerProxy)
            }
            if (findViewTreeSavedStateRegistryOwner() == null) {
                setViewTreeSavedStateRegistryOwner(_easyFloatOwnerProxy)
            }
            if (findViewTreeViewModelStoreOwner() == null) {
                setViewTreeViewModelStoreOwner(_easyFloatOwnerProxy)
            }
            if (findViewTreeOnBackPressedDispatcherOwner() == null) {
                setViewTreeOnBackPressedDispatcherOwner(_easyFloatOwnerProxy)
            }
        }
        if (isLayoutParamsMatchParent()) {
            _layoutKMagnetContainer = LayoutKFrame(
                context, _layoutKMagnet!!,
                FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                    setMargins(_layoutParams.leftMargin, _layoutParams.topMargin, _layoutParams.rightMargin, _layoutParams.bottomMargin)
                }
            ).apply {
                setBackgroundColor(0x03000000)
            }
        }
    }

    override fun remove() {
        if (_layoutKMagnet == null) return
        if (_layoutKMagnet!!.isAttachedToWindow_ofCompat() /*&& getFrameLayoutContainer() != null*/) {
            _layoutKMagnet!!.removeView_ofParent()
        }
        _layoutKMagnet = null
        if (_layoutKMagnetContainer == null) return
        if (_layoutKMagnetContainer!!.isAttachedToWindow_ofCompat()) {
            _layoutKMagnetContainer!!.removeView_ofParent()
        }
        _layoutKMagnetContainer = null
    }

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
            activity.windowManager.addViewSafe(_layoutKMagnetContainer!!, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        } else {
            Log.d(TAG, "attach: _layoutKMagnet")
            activity.windowManager.addViewSafe(_layoutKMagnet!!)
        }
    }


    override fun detach(activity: Activity) {
        Log.d(TAG, "detach: ${activity}")
        if (_layoutKMagnetContainer != null && _layoutKMagnetContainer!!.isAttachedToWindow_ofCompat()) {
            Log.d(TAG, "detach: _layoutKMagnetContainer")
            activity.windowManager.removeViewSafe(_layoutKMagnetContainer)
        } else if (_layoutKMagnet != null && _layoutKMagnet!!.isAttachedToWindow_ofCompat()) {
            Log.d(TAG, "detach: _layoutKMagnet")
            activity.windowManager.removeViewSafe(_layoutKMagnet)
        }
    }

    override fun customView(view: View) {
        _layout = view
    }

    override fun customView(intLayoutId: Int) {
        _layoutId = intLayoutId
    }

    override fun layoutParams(layoutParams: FrameLayout.LayoutParams) {
        _layoutParams = layoutParams
        if (_layoutKMagnetContainer != null) {
            _layoutKMagnetContainer?.layoutParams = layoutParams
        } else
            _layoutKMagnet?.layoutParams = layoutParams
    }

    override fun dragEnable(dragEnable: Boolean) {
        _dragEnable = dragEnable
        _layoutKMagnet?.setDragEnable(dragEnable)
    }

    override fun setAutoMoveToEdge(autoMoveToEdge: Boolean) {
        _autoMoveToEdge = autoMoveToEdge
        _layoutKMagnet?.setAutoMoveToEdge(autoMoveToEdge)
    }

    override fun setInitMargin(margin: RectF) {
        _initMargin = margin
        _layoutKMagnet?.setInitMargin(margin)
    }

    ////////////////////////////////////////////////////////

    private fun getDefaultLayoutParams(): FrameLayout.LayoutParams {
        val layoutParams = FrameLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.BOTTOM or Gravity.START
        layoutParams.setMargins(MARGIN, layoutParams.topMargin, layoutParams.rightMargin, 500)
        return layoutParams
    }

    private fun isLayoutParamsMatchParent(): Boolean =
        _layoutParams.width == LayoutParams.MATCH_PARENT && _layoutParams.height == LayoutParams.MATCH_PARENT
}