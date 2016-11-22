package com.example.jsdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ONE Goal,ONE Passion!
 * Created by ${PK_Night} on 2016/10/24.
 * comment:
 */
public class VerticalViewPager extends ViewPager {


    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        setPageTransformer(true, new VerticalPageTransformer());

        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /**
     * 自定义PageTransformer 改变viewpager的切换动画
     */
    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(final View view, final float position) {


            System.out.println("--position--" + position);
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);

                // Counteract the default slide transition
                ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {


                        float fraction = (float) animation.getAnimatedValue();


                    }
                });
                animator.setDuration(2000);
             //   animator.start();
                view.setTranslationX((view.getWidth() * -position));

                //set Y position to swipe in from top
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     * <p/>
     * 将touch事件的 x, y交换
     */
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev); // return touch coordinates to original reference frame for any child views
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }


}
