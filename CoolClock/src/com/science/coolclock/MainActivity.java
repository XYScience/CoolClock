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
	/** �����沼�� */
	private FrameLayout mRoot;
	/** �˵���ť */
	private View mContentHamburger;
	/** �˵����� */
	private GuillotineMenu mGuillotineMenu;
	private TextView mTextTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ����ʽ״̬������
		initSystemBar();
		initView();
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void initSystemBar() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// ͸��״̬��
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// ͸��������
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		// ����״̬���Ĺ���ʵ��
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// ����״̬������
		tintManager.setStatusBarTintEnabled(true);
		// �����������
		tintManager.setNavigationBarTintEnabled(true);
		// ����һ����ɫ��ϵͳ��
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

		// �˵�����
		View guillotineMenu = LayoutInflater.from(this).inflate(
				R.layout.guillotine_base, null);
		mRoot.addView(guillotineMenu);

		mGuillotineMenu = new GuillotineMenu(mToolbar, mContentHamburger,
				mTextTitle);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.guillotine_base_menu, mGuillotineMenu).commit();

	}

}
