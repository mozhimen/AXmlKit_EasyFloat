package com.zj.easyfloat.helpers;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.annotation.LayoutRes;
import androidx.core.view.ViewCompat;

import com.mozhimen.kotlin.utilk.android.app.UtilKApplicationWrapper;
import com.mozhimen.xmlk.layoutk.magnet.LayoutKMagnet;
import com.mozhimen.xmlk.layoutk.magnet.LayoutKMagnet2;
import com.mozhimen.xmlk.layoutk.magnet.commons.ILayoutKMagnetListener;
import com.zj.easyfloat.commons.IEasyFloat;
import java.lang.ref.WeakReference;


/**
 * @ClassName FloatingView
 * @Description 悬浮窗管理器
 * @Author Yunpeng Li
 * @Creation 2018/3/15 下午5:05
 * @Mender Yunpeng Li
 * @Modification 2018/3/15 下午5:05
 */
public class EasyFloatProxy implements IEasyFloat {

    private static volatile EasyFloatProxy mInstance;

    private EasyFloatProxy() {
    }

    public static EasyFloatProxy get() {
        if (mInstance == null) {
            synchronized (EasyFloatProxy.class) {
                if (mInstance == null) {
                    mInstance = new EasyFloatProxy();
                }
            }
        }
        return mInstance;
    }

    ////////////////////////////////////////////////////////

    private LayoutKMagnet mEnFloatingView;
    @LayoutRes
    private int mLayoutId = 0;//R.layout.en_floating_view;
    //    @DrawableRes
//    private int mIconRes = R.drawable.imuxuan;
    private ViewGroup.LayoutParams mLayoutParams = getParams();
    private WeakReference<FrameLayout> mContainer;


    @Override
    public EasyFloatProxy remove() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mEnFloatingView == null) {
                    return;
                }
                if (ViewCompat.isAttachedToWindow(mEnFloatingView) && getContainer() != null) {
                    getContainer().removeView(mEnFloatingView);
                }
                mEnFloatingView = null;
            }
        });
        return this;
    }

    private void ensureFloatingView() {
        synchronized (this) {
            if (mEnFloatingView != null) {
                return;
            }
            LayoutKMagnet2 enFloatingView = new LayoutKMagnet2(UtilKApplicationWrapper.getInstance().get(), mLayoutId);
            mEnFloatingView = enFloatingView;
            enFloatingView.setLayoutParams(mLayoutParams);
//            enFloatingView.setIconImage(mIconRes);
            addViewToWindow(enFloatingView);
        }
    }

    @Override
    public EasyFloatProxy add() {
        ensureFloatingView();
        return this;
    }

    @Override
    public EasyFloatProxy attach(Activity activity) {
        attach(getActivityRoot(activity));
        return this;
    }

    @Override
    public EasyFloatProxy attach(FrameLayout container) {
        if (container == null || mEnFloatingView == null) {
            mContainer = new WeakReference<>(container);
            return this;
        }
        if (mEnFloatingView.getParent() == container) {
            return this;
        }
        if (mEnFloatingView.getParent() != null) {
            ((ViewGroup) mEnFloatingView.getParent()).removeView(mEnFloatingView);
        }
        mContainer = new WeakReference<>(container);
        container.addView(mEnFloatingView);
        return this;
    }

    @Override
    public EasyFloatProxy detach(Activity activity) {
        detach(getActivityRoot(activity));
        return this;
    }

    @Override
    public EasyFloatProxy detach(FrameLayout container) {
        if (mEnFloatingView != null && container != null && ViewCompat.isAttachedToWindow(mEnFloatingView)) {
            container.removeView(mEnFloatingView);
        }
        if (getContainer() == container) {
            mContainer = null;
        }
        return this;
    }

    @Override
    public LayoutKMagnet getView() {
        return mEnFloatingView;
    }

//    @Override
//    public FloatingView icon(@DrawableRes int resId) {
//        mIconRes = resId;
//        return this;
//    }

    @Override
    public EasyFloatProxy customView(LayoutKMagnet viewGroup) {
        mEnFloatingView = viewGroup;
        return this;
    }

    @Override
    public EasyFloatProxy customView(@LayoutRes int resource) {
        mLayoutId = resource;
        return this;
    }

    @Override
    public EasyFloatProxy layoutParams(ViewGroup.LayoutParams params) {
        mLayoutParams = params;
        if (mEnFloatingView != null) {
            mEnFloatingView.setLayoutParams(params);
        }
        return this;
    }

    @Override
    public EasyFloatProxy listener(ILayoutKMagnetListener magnetViewListener) {
        if (mEnFloatingView != null) {
            mEnFloatingView.setMagnetViewListener(magnetViewListener);
        }
        return this;
    }

    @Override
    public EasyFloatProxy dragEnable(boolean dragEnable) {
        if (mEnFloatingView != null) {
            mEnFloatingView.updateDragState(dragEnable);
        }
        return this;
    }

    @Override
    public EasyFloatProxy setAutoMoveToEdge(boolean autoMoveToEdge) {
        if (mEnFloatingView != null) {
            mEnFloatingView.setAutoMoveToEdge(autoMoveToEdge);
        }
        return this;
    }

    private void addViewToWindow(final View view) {
        if (getContainer() == null) {
            return;
        }
        getContainer().addView(view);
    }

    private FrameLayout getContainer() {
        if (mContainer == null) {
            return null;
        }
        return mContainer.get();
    }

    private FrameLayout.LayoutParams getParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.START;
        params.setMargins(13, params.topMargin, params.rightMargin, 500);
        return params;
    }

    private FrameLayout getActivityRoot(Activity activity) {
        if (activity == null) {
            return null;
        }
        try {
            return (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}