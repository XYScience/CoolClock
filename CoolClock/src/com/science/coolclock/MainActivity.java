package com.science.coolclock;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.science.coolclock.fragment.GuillotineMenu;

public class MainActivity extends AppCompatActivity {

	private Toolbar mToolbar;
	/** 主界面布局 */
	private FrameLayout mRoot;
	/** 菜单按钮 */
	private View mContentHamburger;
	/** 菜单界面 */
	private GuillotineMenu mGuillotineMenu;
	private TextView mTextTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 沉浸式状态栏设置
		initSystemBar();
		initView();
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
		// 创建状态栏的管理实例
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// 激活状态栏设置
		tintManager.setStatusBarTintEnabled(true);
		// 激活导航栏设置
		tintManager.setNavigationBarTintEnabled(true);
		// 设置一个颜色给系统栏
		// tintManager.setTintColor(Color.parseColor("#15aff1"));
		tintManager.setTintColor(Color.parseColor("#7ecec9"));
	}

	private void initView() {
		mTextTitle = (TextView) findViewById(R.id.title);
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mRoot = (FrameLayout) findViewById(R.id.root);
		mContentHamburger = findViewById(R.id.content_hamburger);

		if (mToolbar != null) {
			setSupportActionBar(mToolbar);
			getSupportActionBar().setTitle(null);
		}

		// 菜单布局
		View guillotineMenu = LayoutInflater.from(this).inflate(
				R.layout.guillotine_base, null);
		mRoot.addView(guillotineMenu);

		mGuillotineMenu = new GuillotineMenu(mToolbar, mContentHamburger,
				mTextTitle);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.guillotine_base_menu, mGuillotineMenu).commit();

	}

}
