package com.zj.sample

import android.animation.ValueAnimator
import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Recomposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.compositionContext
import androidx.compose.ui.unit.dp
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.kotlin.utilk.android.util.dp2px
import com.mozhimen.kotlin.utilk.android.util.dp2pxI
import com.mozhimen.kotlin.utilk.android.widget.showToast
import com.zj.easyfloat.EasyFloat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val _composeView by lazy {
        val coroutineContext = AndroidUiDispatcher.CurrentThread
        val coroutineScope = CoroutineScope(coroutineContext)
        val reRecomposer = Recomposer(coroutineContext)
        coroutineScope.launch {
            reRecomposer.runRecomposeAndApplyChanges()
        }
        ComposeView(this@MainActivity).apply {
            compositionContext = reRecomposer
            setContent {
                Box(
                    modifier = Modifier.size(50.dp)
                ) {
                    Text(
                        text = "Android",
                        modifier = Modifier.clickable {
                            "Hello".showToast()
                        }
                    )
                }
            }
        }
    }

    fun show(view: View) {
        EasyFloat.instance
            .customView(/*R.layout.layout_float_view*/
                _composeView
            )
            .addBlackList(mutableListOf(ThirdActivity::class.java))
            .layoutParams(initLayoutParams())
            .dragEnable(true)
            .setAutoMoveToEdge(true)
            .show(this)
        EasyFloat.instance.getFloatContainer()?.let {
            initListener(it)
        }
    }

    fun dismiss(view: View) {
        EasyFloat.instance.dismiss(this)
    }

    fun jumpOne(view: View) {
        startContext<SecondActivity>()
    }

    fun jumpTwo(view: View) {
        startContext<ThirdActivity>()
    }

    private fun initLayoutParams(): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.TOP or Gravity.START
        params.setMargins(params.leftMargin,100.dp2pxI(),params.rightMargin,params.bottomMargin)
        //params.setMargins(0, params.topMargin, params.rightMargin, 500)
        return params
    }

    private fun initListener(root: View?) {
        val rootView = root?.findViewById<View>(R.id.ll_root)
        root?.findViewById<View>(R.id.floating_ball)?.setOnClickListener {
            if (rootView != null) {
                if (rootView.getTag(R.id.animate_type) == null) {
                    rootView.setTag(R.id.animate_type, true)
                }
                val isCollapse = rootView.getTag(R.id.animate_type) as Boolean
                animScale(rootView, isCollapse)
                rootView.setTag(R.id.animate_type, isCollapse.not())
            }
        }
        root?.findViewById<View>(R.id.floating_ball_one)?.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }
    }

    private fun animScale(view: View, isCollapse: Boolean) {
        val start = if (isCollapse) 172f.dp2px() else 60f.dp2px()
        val end = if (isCollapse) 60f.dp2px() else 172f.dp2px()

        val scaleBig = ValueAnimator.ofFloat(start, end)
        scaleBig.duration = 1000
        scaleBig.addUpdateListener {
            val layoutParams = view.layoutParams
            layoutParams.height = (it.animatedValue as Float).toInt()
            view.layoutParams = layoutParams
        }
        scaleBig.start()
    }
}