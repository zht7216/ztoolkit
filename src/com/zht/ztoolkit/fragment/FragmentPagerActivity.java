package com.zht.ztoolkit.fragment;

import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.zht.ztoolkit.R;

public class FragmentPagerActivity extends FragmentActivity{
    private static final String TAG = "MainActivity";
    
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private View vIndicator;
    private TextView tvTab1, tvTab2, tvTab3, tvTab4;
    private Resources resources;
    
    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int partition = 0;
    private int position_one;
    private int position_two;
    private int position_three;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_pager);
		resources = getResources();
		initTextView();
		initWidth();
		initViewPager();
	}
	
	private void initTextView() {
        tvTab1 = (TextView) findViewById(R.id.tab1);
        tvTab2 = (TextView) findViewById(R.id.tab2);
        tvTab3 = (TextView) findViewById(R.id.tab3);
        tvTab4 = (TextView) findViewById(R.id.tab4);

        tvTab1.setOnClickListener(new MyOnClickListener(0));
        tvTab2.setOnClickListener(new MyOnClickListener(1));
        tvTab3.setOnClickListener(new MyOnClickListener(2));
        tvTab4.setOnClickListener(new MyOnClickListener(3));
    }
	
	 private void initWidth() {
	        vIndicator = (View) findViewById(R.id.indicator);
	        bottomLineWidth = vIndicator.getLayoutParams().width;
	        Log.d(TAG, "cursor imageview width=" + bottomLineWidth);
	        DisplayMetrics dm = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(dm);
	        int screenW = dm.widthPixels;
	        offset = (int) ((screenW / 4.0 - bottomLineWidth) / 2);
	        Log.i("MainActivity", "offset=" + offset);
	        
	        vIndicator.setX(offset);
	        partition = (int)(screenW / 4.0);
	        position_one = partition;
	        position_two = partition*2;
	        position_three = partition*3;
	    }

    private void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        fragmentsList = new ArrayList<Fragment>();
        //LayoutInflater mInflater = getLayoutInflater();
        //View activityView = mInflater.inflate(R.layout.fragment_content, null);

        Fragment tab1fragment = MyFragment.newInstance("Hello Activity.");
        Fragment tab2Fragment = MyFragment.newInstance("Hello Group.");
        Fragment tab3Fragment = MyFragment.newInstance("Hello Friends.");
        Fragment tab4Fragment = MyFragment.newInstance("Hello Chat.");

        fragmentsList.add(tab1fragment);
        fragmentsList.add(tab2Fragment);
        fragmentsList.add(tab3Fragment);
        fragmentsList.add(tab4Fragment);
        
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
    
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };
    
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, 0, 0, 0);
                    tvTab2.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, 0, 0, 0);
                    tvTab3.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, 0, 0, 0);
                    tvTab4.setTextColor(resources.getColor(R.color.black));
                }
                tvTab1.setTextColor(resources.getColor(R.color.red));
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, position_one, 0, 0);
                    tvTab1.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, position_one, 0, 0);
                    tvTab3.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, position_one, 0, 0);
                    tvTab4.setTextColor(resources.getColor(R.color.black));
                }
                tvTab2.setTextColor(resources.getColor(R.color.red));
                break;
            case 2:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, position_two, 0, 0);
                    tvTab1.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, position_two, 0, 0);
                    tvTab2.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(position_three, position_two, 0, 0);
                    tvTab4.setTextColor(resources.getColor(R.color.black));
                }
                tvTab3.setTextColor(resources.getColor(R.color.red));
                break;
            case 3:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, position_three, 0, 0);
                    tvTab1.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(position_one, position_three, 0, 0);
                    tvTab2.setTextColor(resources.getColor(R.color.black));
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(position_two, position_three, 0, 0);
                    tvTab3.setTextColor(resources.getColor(R.color.black));
                }
                tvTab4.setTextColor(resources.getColor(R.color.red));
                break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            vIndicator.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };
}
