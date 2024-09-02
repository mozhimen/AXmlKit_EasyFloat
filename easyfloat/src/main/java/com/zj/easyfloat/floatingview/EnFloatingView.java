package com.zj.easyfloat.floatingview;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.mozhimen.basick.utilk.android.view.UtilKViewGroup;
import com.mozhimen.basick.utilk.android.view.UtilKViewGroupWrapper;
import com.zj.easyfloat.R;

/**
 * @ClassName EnFloatingView
 * @Description 悬浮窗
 * @Author Yunpeng Li
 * @Creation 2018/3/15 下午5:04
 * @Mender Yunpeng Li
 * @Modification 2018/3/15 下午5:04
 */
public class EnFloatingView extends FloatingMagnetView {

//    private final ImageView mIcon;

//    public EnFloatingView(@NonNull Context context) {
//        this(context, R.layout.en_floating_view);
//    }

    public EnFloatingView(@NonNull Context context, @LayoutRes int resource) {
        super(context, null);
        inflate(context, resource, this);
//        mIcon = findViewById(R.id.icon);
    }

    public EnFloatingView(@NonNull Context context, View view) {
        this(context, view, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    public EnFloatingView(@NonNull Context context, View view, FrameLayout.LayoutParams layoutParams) {
        super(context, null);
        UtilKViewGroup.addViewSafe(this, view, layoutParams);
    }

//    public void setIconImage(@DrawableRes int resId){
//        mIcon.setImageResource(resId);
//    }
}
