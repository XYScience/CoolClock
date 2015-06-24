package com.science.coolclock.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.science.coolclock.MainActivity;
import com.science.coolclock.R;
import com.science.coolclock.widget.RippleView;
import com.yalantis.guillotine.animation.GuillotineAnimation;

/**
 * @description 菜单界面
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-6-20
 * 
 */

public class GuillotineMenu extends Fragment {

	private static final int ALARM = 0;
	private static final int SETTINGS = 1;
	private static final int ABOUT = 2;
	private static final long RIPPLE_DURATION = 250;
	private Toolbar mToolbar;
	/** 菜单按钮 */
	private View mContentHamburger;
	private GuillotineAnimation mGuillotineAnimation;
	private TextView mTextTitle;

	private View mRootView; // 缓存Fragment view
	private FragmentManager mFragmentManager;
	private MainActivity mActivity;
	private Fragment mFragmentAlarm, mFragmentSettings, mFragmentAbout;

	private RippleView mRippleAlarm, mRippleSettings, mRippleAbout;

	public GuillotineMenu(Toolbar toolbar, View contentHamburger,
			TextView textTitle) {

		this.mToolbar = toolbar;
		this.mContentHamburger = contentHamburger;
		this.mTextTitle = textTitle;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		if (mRootView == null) {
			mRootView = inflater.inflate(R.layout.guillotine, container, false);
			// mRootView = inflater.inflate(R.layout.frame_menu, null);
			mGuillotineAnimation = new GuillotineAnimation.GuillotineBuilder(
					mRootView,
					mRootView.findViewById(R.id.guillotine_hamburger),
					mContentHamburger).setStartDelay(RIPPLE_DURATION)
					.setActionBarViewForAnimation(mToolbar)
					.setClosedOnStart(true).build();
		}

		initView();
		initListener();
		OnFragmentSelected(ALARM);

		return mRootView;
	}

	private void initView() {
		mRippleAlarm = (RippleView) mRootView.findViewById(R.id.ripple_alarm);
		mRippleSettings = (RippleView) mRootView
				.findViewById(R.id.ripple_settings);
		mRippleAbout = (RippleView) mRootView.findViewById(R.id.ripple_about);
		mFragmentManager = this.getFragmentManager();
		mTextTitle.setText("闹钟");
	}

	private void initListener() {
		mRippleAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				OnFragmentSelected(ALARM);
				mGuillotineAnimation.close();
				mTextTitle.setText("闹钟");
			}
		});
		mRippleSettings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				OnFragmentSelected(SETTINGS);
				mGuillotineAnimation.close();
				mTextTitle.setText("设置");
			}
		});
		mRippleAbout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				OnFragmentSelected(ABOUT);
				mGuillotineAnimation.close();
				mTextTitle.setText("关于");
			}
		});
	}

	private void OnFragmentSelected(int index) {

		FragmentTransaction fragmentTransaction = mFragmentManager
				.beginTransaction();
		hideFragments(fragmentTransaction);

		switch (index) {
		case 0:
			if (null == mFragmentAlarm) {
				mFragmentAlarm = new AlarmFragment();
				fragmentTransaction.add(R.id.content, mFragmentAlarm);
			} else {
				fragmentTransaction.show(mFragmentAlarm);
			}
			break;

		case 1:
			if (null == mFragmentSettings) {
				mFragmentSettings = new SettingsFragment();
				fragmentTransaction.add(R.id.content, mFragmentSettings);
			} else {
				fragmentTransaction.show(mFragmentSettings);
			}
			break;

		case 2:
			if (null == mFragmentAbout) {
				mFragmentAbout = new AboutFragment();
				fragmentTransaction.add(R.id.content, mFragmentAbout);
			} else {
				fragmentTransaction.show(mFragmentAbout);
			}
			break;
		}
		fragmentTransaction.commit();
	}

	/**
	 * 将所有fragment都置为隐藏状态
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (mFragmentAlarm != null) {
			transaction.hide(mFragmentAlarm);
		}
		if (mFragmentSettings != null) {
			transaction.hide(mFragmentSettings);
		}
		if (mFragmentAbout != null) {
			transaction.hide(mFragmentAbout);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}
}
