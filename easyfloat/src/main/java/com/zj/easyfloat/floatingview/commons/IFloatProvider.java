package com.zj.easyfloat.floatingview.commons;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import com.zj.easyfloat.floatingview.FloatingMagnetView;
import com.zj.easyfloat.floatingview.FloatingView;


/**
 * Created by Yunpeng Li on 2018/3/15.
 */

public interface IFloatProvider {

    FloatingView remove();

    FloatingView add();

    FloatingView attach(Activity activity);

    FloatingView attach(FrameLayout container);

    FloatingView detach(Activity activity);

    FloatingView detach(FrameLayout container);

    FloatingMagnetView getView();

//    FloatingView icon(@DrawableRes int resId);

    FloatingView customView(FloatingMagnetView viewGroup);

    FloatingView customView(@LayoutRes int resource);

    FloatingView layoutParams(ViewGroup.LayoutParams params);

    FloatingView listener(IMagnetViewListener magnetViewListener);

    FloatingView dragEnable(boolean dragEnable);

    FloatingView setAutoMoveToEdge(boolean autoMoveToEdge);

}
