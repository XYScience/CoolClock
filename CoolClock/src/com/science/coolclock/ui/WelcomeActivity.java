package com.science.coolclock.ui;

import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;

import com.science.coolclock.AppManager;
import com.science.coolclock.MainActivity;
import com.science.coolclock.R;
import com.science.coolclock.adapter.ModelPagerAdapter;
import com.science.coolclock.fragment.FirstFragment;
import com.science.coolclock.utils.PagerModelManager;
import com.science.coolclock.widget.JazzyViewPager;
import com.science.coolclock.widget.JazzyViewPager.TransitionEffect;
import com.science.coolclock.widget.OutlineContainer;

/**
 * @description 欢迎界面
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-6-30
 * 
 */

public class WelcomeActivity extends AppCompatActivity implements
		OnTouchListener {

	private ScrollerViewPager viewPager;

	private JazzyViewPager mJazzy;

	private int flaggingWidth;
	private int lastX = 0;
	private int size = 0;
	private int currentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 不显示系统的标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.welcome);

		// 将activity加入到AppManager堆栈中
		AppManager.getAppManager().addActivity(this);

		// 沉浸式状态栏设置
		// initSystemBar();
		// initView();

		setupJazziness(TransitionEffect.CubeIn);
	}

	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(new MainAdapter());
		mJazzy.setPageMargin(30);

		SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
		springIndicator.setViewPager(mJazzy);
		springIndicator.setOnPageChangeListener(new MypageChangeListener());
		mJazzy.setOnTouchListener(WelcomeActivity.this);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		flaggingWidth = dm.widthPixels / 3;
		size = 4;

	}

	private class MainAdapter extends PagerAdapter {
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			TextView text = new TextView(WelcomeActivity.this);
			text.setGravity(Gravity.CENTER);
			text.setTextSize(30);
			text.setTextColor(Color.WHITE);
			text.setText("Page " + position);
			text.setPadding(30, 30, 30, 30);
			int bg = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
					(int) Math.floor(Math.random() * 128) + 64,
					(int) Math.floor(Math.random() * 128) + 64);
			text.setBackgroundColor(bg);
			container.addView(text, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			mJazzy.setObjectForPosition(text, position);
			return text;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object obj) {
			container.removeView(mJazzy.findViewFromObject(position));
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			if (view instanceof OutlineContainer) {
				return ((OutlineContainer) view).getChildAt(0) == obj;
			} else {
				return view == obj;
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return getTitles().get(position);
		}
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void initSystemBar() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	private void initView() {
		viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
		SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);

		PagerModelManager manager = new PagerModelManager();
		manager.addCommonFragment(getFragment(), getTitles());
		ModelPagerAdapter adapter = new ModelPagerAdapter(
				getSupportFragmentManager(), manager);
		viewPager.setAdapter(adapter);
		viewPager.fixScrollSpeed();

		// just set viewPager
		springIndicator.setViewPager(viewPager);
		springIndicator.setOnPageChangeListener(new MypageChangeListener());

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		flaggingWidth = dm.widthPixels / 3;
		size = getFragment().size();

		viewPager.setOnTouchListener(WelcomeActivity.this);
	}

	private class MypageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			currentIndex = arg0;
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if ((lastX - event.getX()) > flaggingWidth
					&& (currentIndex == size - 1)) {
				gotoMain();
			}
			break;
		default:
			break;
		}
		return false;
	}

	private void gotoMain() {

		Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
		startActivity(intent);
		AppManager.getAppManager().finishActivity(WelcomeActivity.this);
		overridePendingTransition(R.anim.alpha_in_anim, R.anim.alpha_out_anim);
	}

	private List<String> getTitles() {
		List<String> titlesList = new ArrayList<String>();
		titlesList.add("1");
		titlesList.add("2");
		titlesList.add("3");
		titlesList.add("4");
		return titlesList;
	}

	private List<Fragment> getFragment() {
		FirstFragment firstFragment = new FirstFragment();
		// SecondFragment secondFragment = new SecondFragment();
		// ThirdFragment thirdFragment = new ThirdFragment();
		// FourthFragment fourthFragment = new FourthFragment();
		List<Fragment> fragmentList = new ArrayList<Fragment>();
		fragmentList.add(firstFragment);
		// fragmentList.add(secondFragment);
		// fragmentList.add(thirdFragment);
		// fragmentList.add(fourthFragment);

		return fragmentList;
	}
}
