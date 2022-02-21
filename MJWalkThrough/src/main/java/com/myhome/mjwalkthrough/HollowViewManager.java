package com.myhome.mjwalkthrough;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.WINDOW_SERVICE;

public class HollowViewManager {

    private Activity activity;
    private WindowManager windowManager;
    private List<ItemHollowView> viewList;
    private LayoutInflater inflater;
    private ViewGroup rootView;
    private HollowView hollowView;
    private View tooltipHolder;
    private int currentIndex;
    private OnHollowViewManagerListener onHollowViewManagerListener;

    public HollowViewManager(Activity activity) {
        this.activity = activity;
        this.windowManager = (WindowManager) activity.getSystemService(WINDOW_SERVICE);
        this.viewList = new ArrayList<>();
        this.inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        this.rootView = (ViewGroup) activity.getWindow().getDecorView().getRootView();
        this.onHollowViewManagerListener = null;
    }

    public HollowViewManager setViewList(List<ItemHollowView> list){
        viewList.clear();
        viewList.addAll(list);
        return this;
    }

    public HollowViewManager setListener(OnHollowViewManagerListener listener){
        this.onHollowViewManagerListener = listener;
        return this;
    }

    public HollowViewManager start(){
        this.currentIndex = 0;
        showCurrentIndex();
        return this;
    }

    private void clearViews(){
        if(hollowView != null){
            try{
                windowManager.removeView(hollowView);
                hollowView = null;
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(tooltipHolder != null){
            try{
                windowManager.removeView(tooltipHolder);
                tooltipHolder = null;
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void showCurrentIndex(){
        clearViews();
        int[] viewLocation = new int[2];
        if(currentIndex < viewList.size()){
            ItemHollowView hollowView = viewList.get(currentIndex);
            hollowView.getTarget().getLocationInWindow(viewLocation);
            addHollowViewToWindow(
                    hollowView.getTitle(),
                    hollowView.getDescription(),
                    viewLocation[0],
                    viewLocation[1] - getStatusBarHeight(activity),
                    hollowView.getTarget().getWidth(),
                    hollowView.getTarget().getHeight(),
                    ()->{
                        currentIndex++;
                        showCurrentIndex();
                    }
            );
        }
        else if(onHollowViewManagerListener != null){
            onHollowViewManagerListener.onFinished();
        }
    }

    private void addHollowViewToWindow(String title, String desc, int left, int top, int width, int height, OnViewEventListener listener){
        left -= 5;
        top -= 5;
        width += 10;
        height += 10;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                //Build.VERSION.SDK_INT > 25 ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_BASE_APPLICATION,
                WindowManager.LayoutParams.TYPE_BASE_APPLICATION,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSPARENT);
        hollowView = new HollowView(activity);
        hollowView.setParams(width, height, left, top);
        hollowView.setOnClickListener(v -> {
            if(listener != null){
                listener.onViewDismissed();
            }
        });
        hollowView.setAlpha(0f);
        windowManager.addView(hollowView, params);
        hollowView.animate().alpha(1f).setDuration(300).setInterpolator(new DecelerateInterpolator()).withEndAction(()->hollowView.setAlpha(1f)).start();

        WindowManager.LayoutParams tooltipParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                //Build.VERSION.SDK_INT > 25 ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_BASE_APPLICATION,
                WindowManager.LayoutParams.TYPE_BASE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSPARENT);
        tooltipParams.gravity = Gravity.TOP | Gravity.LEFT;
        //int windowHeight = hollowView.getDisplayMetrics().heightPixels;
        tooltipParams.y = top + height;
        tooltipParams.x = left;

        tooltipHolder = inflater.inflate(R.layout.layout_intro_tooltip, rootView, false);
        ((TextView)tooltipHolder.findViewById(R.id.txt_tooltip_title)).setText(title);
        ((TextView)tooltipHolder.findViewById(R.id.txt_tooltip_desc)).setText(desc);
        tooltipHolder.setAlpha(0f);
        tooltipHolder.setTranslationY(100f);
        windowManager.addView(tooltipHolder, tooltipParams);
        tooltipHolder.animate().alpha(1f).translationY(0f).setDuration(300).setInterpolator(new OvershootInterpolator()).withEndAction(()->{
            tooltipHolder.setAlpha(1f);
            tooltipHolder.setTranslationY(0f);
        }).start();
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private interface OnViewEventListener{
        void onViewDismissed();
    }

    public interface OnHollowViewManagerListener{
        void onFinished();
    }
}
