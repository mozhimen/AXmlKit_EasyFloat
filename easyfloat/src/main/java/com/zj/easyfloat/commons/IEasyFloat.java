package com.zj.easyfloat.commons;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import com.mozhimen.xmlk.layoutk.magnet.LayoutKMagnet;
import com.mozhimen.xmlk.layoutk.magnet.commons.ILayoutKMagnetListener;
import com.zj.easyfloat.helpers.EasyFloatProxy;


/**
 * Created by Yunpeng Li on 2018/3/15.
 */

public interface IEasyFloat {

    EasyFloatProxy remove();

    EasyFloatProxy add();

    EasyFloatProxy attach(Activity activity);

    EasyFloatProxy attach(FrameLayout container);

    EasyFloatProxy detach(Activity activity);

    EasyFloatProxy detach(FrameLayout container);

    LayoutKMagnet getView();

//    FloatingView icon(@DrawableRes int resId);

    EasyFloatProxy customView(LayoutKMagnet viewGroup);

    EasyFloatProxy customView(@LayoutRes int resource);

    EasyFloatProxy layoutParams(ViewGroup.LayoutParams params);

    EasyFloatProxy listener(ILayoutKMagnetListener magnetViewListener);

    EasyFloatProxy dragEnable(boolean dragEnable);

    EasyFloatProxy setAutoMoveToEdge(boolean autoMoveToEdge);

}
