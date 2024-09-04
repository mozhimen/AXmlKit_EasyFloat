package com.zj.sample

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.kotlin.utilk.android.util.dp2px
import com.zj.easyfloat.EasyFloat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun show(view: View) {
        EasyFloat
            .layout(R.layout.layout_float_view)
            .blackList(mutableListOf(ThirdActivity::class.java))
            .layoutParams(initLayoutParams())
            .dragEnable(true)
            .setAutoMoveToEdge(true)
            .listener {
                initListener(it)
            }
            .show(this)
    }

    fun dismiss(view: View) {
        EasyFloat.dismiss(this)
    }

    fun jumpOne(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    fun jumpTwo(view: View) {
        val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
    }

    private fun initLayoutParams(): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.CENTER_VERTICAL or Gravity.START
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