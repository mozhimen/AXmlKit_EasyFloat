package com.mozhimen.xmlk.easyfloat.utils

import android.widget.FrameLayout
import androidx.annotation.Px
import com.mozhimen.kotlin.elemk.commons.IExt_Listener

/**
 * @ClassName LayoutParamsUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/7/12
 * @Version 1.0
 */
object LayoutParamsUtil {
    @JvmStatic
    fun getLayoutParams(@Px width: Int, @Px height: Int, block: IExt_Listener<FrameLayout.LayoutParams>): FrameLayout.LayoutParams {
        val layoutParams = FrameLayout.LayoutParams(width, height)
//        layoutParams.gravity = Gravity.BOTTOM or Gravity.END
//        layoutParams.setMargins(0, layoutParams.topMargin, layoutParams.rightMargin, 500)
        layoutParams.block()
        return layoutParams
    }
}