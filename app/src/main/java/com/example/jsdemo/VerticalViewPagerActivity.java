package com.example.jsdemo;

import android.app.Activity;
import android.app.FragmentBreadCrumbs;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jsdemo.R;
import com.example.jsdemo.view.VerticalViewPager;

import java.util.ArrayList;

public class VerticalViewPagerActivity extends AppCompatActivity {


    VerticalViewPager mViewPager;
    Activity act;

    ArrayList<ImageView> mImageViews = new ArrayList<>();
    private int[] mImgIds = new int[]{R.drawable.tu1,
            R.drawable.tu2, R.drawable.tu3,R.drawable.tu4};


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mViewPager.getCurrentItem() == mImageViews.size() - 1) {
                mViewPager.setCurrentItem(0,false);
            } else {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1,false);
            }

            mHandler.sendEmptyMessageDelayed(0,3000);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_view_pager);
        act = this;

        initData();

        mViewPager = (VerticalViewPager) findViewById(R.id.view_pager);


        adapter = new MyAdapter();
        mViewPager.setAdapter(adapter);
    //    mHandler.sendEmptyMessageDelayed(0,3000);
    }


    private MyAdapter adapter;

    class MyAdapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            container.addView(mImageViews.get(position));

            return mImageViews.get(position);
        }

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //   super.destroyItem(container, position, object);
            container.removeView(mImageViews.get(position));
        }
    }

    private void initData() {
        for (int imgId : mImgIds) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgId);

            mImageViews.add(imageView);
        }
    }

}
