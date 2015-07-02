package com.science.coolclock.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;

import me.drakeet.materialdialog.MaterialDialog;
import mirko.android.datetimepicker.time.RadialPickerLayout;
import mirko.android.datetimepicker.time.TimePickerDialog;
import mirko.android.datetimepicker.time.TimePickerDialog.OnTimeSetListener;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar.OnProgressChangeListener;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.science.coolclock.AppManager;
import com.science.coolclock.R;
import com.science.coolclock.utils.ClockUtils;
import com.science.coolclock.widget.RevealLayout;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

/**
 * @description 闹钟详情设置界面
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-6-25
 * 
 */

public class SetClockActivity extends AppCompatActivity {

	private Toolbar mToolbar;
	private RevealLayout mRevealLayout, mRevealTopLayout;
	private RelativeLayout mSetClockLayout, mVoiceRecordLayout, mTimeLayout;
	private ImageView mBackImage;
	// private RippleBackground mRippleBackground;
	private ImageView mVoiceRecordImg;

	private TextView mTimeSet;
	private ImageView mTextSelectMusic, mTextSelectDay, mTextSetVoice;
	private final Calendar mCalendar = Calendar.getInstance();
	private int hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY);
	private int minute = mCalendar.get(Calendar.MINUTE);

	private List<String> mWeekList;
	private String[] week = { "一", "二", "三", "四", "五", "六", "日" };
	// TreeSet是Set的一种变体,可以实现自动排序并去重等功能的集合
	private TreeSet<Integer> weekList = new TreeSet<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_set_clock);

		// 将activity加入到AppManager堆栈中
		AppManager.getAppManager().addActivity(this);

		// 沉浸式状态栏设置
		initSystemBar();
		initView();
		resetTime();
		// resetWeek();
		initListener();

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
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mSetClockLayout = (RelativeLayout) findViewById(R.id.set_clock_layout);
		mSetClockLayout.setBackgroundColor(0xffffffff);
		mVoiceRecordLayout = (RelativeLayout) findViewById(R.id.voice_record_layout);
		mTimeLayout = (RelativeLayout) findViewById(R.id.time_layout);
		mRevealLayout = (RevealLayout) findViewById(R.id.reveal_layout);
		mRevealTopLayout = (RevealLayout) findViewById(R.id.reveal_top_layout);
		mBackImage = (ImageView) findViewById(R.id.back);
		// mRippleBackground = (RippleBackground)
		// findViewById(R.id.voice_record_ripple);
		mVoiceRecordImg = (ImageView) findViewById(R.id.voice_record);
		if (mToolbar != null) {
			setSupportActionBar(mToolbar);
			getSupportActionBar().setTitle(null);
		}

		mTimeSet = (TextView) findViewById(R.id.time_set);
		mTextSelectMusic = (ImageView) findViewById(R.id.select_music);
		mTextSelectDay = (ImageView) findViewById(R.id.select_day);
		mTextSetVoice = (ImageView) findViewById(R.id.set_voice);
	}

	private void resetTime() {
		mTimeSet.setText(new StringBuilder().append(pad(hourOfDay)).append(":")
				.append(pad(minute)));
		mTimeSet.setTextColor(getResources().getColor(
				android.R.color.darker_gray));

	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	private void initListener() {
		mRevealLayout.setContentShown(false);
		mRevealLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						mRevealLayout.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						mRevealLayout.postDelayed(new Runnable() {
							@Override
							public void run() {
								mRevealLayout.show();
							}
						}, 50);
					}
				});
		mRevealLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		// 声音和时间选择
		mRevealTopLayout.setOnClickListener(null);
		mRevealTopLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

					mRevealTopLayout.next((int) event.getX(),
							(int) event.getY(), 2000);

					return true;
				}
				return false;
			}
		});

		mBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mVoiceRecordImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// mRippleBackground.startRippleAnimation();
			}
		});

		// 选择时间
		mTimeSet.setOnClickListener(new OnClickListener() {
			private String tag;

			@Override
			public void onClick(View v) {
				timePickerDialog24h.show(getFragmentManager(), tag);
			}
		});

		// 选择音乐
		mTextSelectMusic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(SetClockActivity.this, "选择音乐", Toast.LENGTH_LONG)
						.show();
			}
		});

		// 选择周期
		mTextSelectDay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showSelectWeekDialog();
			}
		});

		// 调节音量
		mTextSetVoice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showSetVoiceDialog();
			}
		});
	}

	private void showSelectWeekDialog() {

		final MaterialDialog mMaterialDialog = new MaterialDialog(
				SetClockActivity.this);
		getWeek();
		ListView listView = new ListView(SetClockActivity.this);
		listView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		float scale = getResources().getDisplayMetrics().density;
		int dpAsPixels = (int) (7 * scale + 0.5f);
		listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
		listView.setDividerHeight(0);
		listView.setAdapter(new MyWeekAdapter());

		mMaterialDialog.setTitle("自定义").setContentView(listView);
		mMaterialDialog.setPositiveButton("确定", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMaterialDialog.dismiss();
				// setWeek();
			}

		});
		mMaterialDialog.show();
	}

	private class MyWeekAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mWeekList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = View.inflate(SetClockActivity.this, R.layout.set_week,
					null);
			// 使用每一次都findviewById的方法来获得listview_item内部的组件
			TextView item = (TextView) view.findViewById(R.id.monday);
			item.setText(mWeekList.get(position));
			ToggleButton weekBtn = (ToggleButton) view
					.findViewById(R.id.monday_button);
			weekBtn.setOnToggleChanged(new OnToggleChanged() {

				@Override
				public void onToggle(boolean on) {
					if (on) {
						weekList.add(position);
					} else {
						// setUnSelectWeek(position);
					}
				}
			});
			return view;
		}
	}

	// 设置音量
	private void showSetVoiceDialog() {
		View view = LayoutInflater.from(this).inflate(R.layout.set_voice, null);
		final MaterialDialog alert = new MaterialDialog(this).setTitle("闹铃音量")
				.setContentView(view);

		DiscreteSeekBar setVoiceSeekBar = (DiscreteSeekBar) view
				.findViewById(R.id.set_voice_seekbar);
		final TextView currentValues = (TextView) view
				.findViewById(R.id.current_values);
		setVoiceSeekBar
				.setOnProgressChangeListener(new OnProgressChangeListener() {

					@Override
					public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

					}

					@Override
					public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

					}

					@Override
					public void onProgressChanged(DiscreteSeekBar seekBar,
							int value, boolean fromUser) {
						currentValues.setText(value + "");
					}
				});

		alert.setPositiveButton("确定", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});

		alert.show();
	}

	// 显示选择的星期
	// private void setWeek() {
	// mTextSlectWeek.setText("");
	// if (weekList.size() < 7) {
	// Iterator it = weekList.iterator();
	// while (it.hasNext()) {
	// mTextSlectWeek.setText(mTextSlectWeek.getText() + "周"
	// + week[(int) it.next()]);
	// }
	// } else {
	// mTextSlectWeek.setText("每天");
	// }
	// mTextSlectWeek.setTextColor(getResources().getColor(
	// android.R.color.holo_blue_light));
	// weekList.clear();
	// }
	//
	// // 不选择时
	// private void setUnSelectWeek(int position) {
	// mTextSlectWeek.setText("未开启");
	// mTextSlectWeek.setTextColor(getResources().getColor(
	// android.R.color.darker_gray));
	// }

	/** 添加一个得到数据的方法，方便使用 */
	private void getWeek() {
		mWeekList = new ArrayList<String>();
		/** 为动态数组添加数据 */
		for (int i = 0; i < 7; i++) {
			mWeekList.add("周" + week[i]);
		}
	}

	final TimePickerDialog timePickerDialog24h = TimePickerDialog.newInstance(
			new OnTimeSetListener() {

				@Override
				public void onTimeSet(RadialPickerLayout view, int hourOfDay,
						int minute) {

					mTimeSet.setText(new StringBuilder().append(pad(hourOfDay))
							.append(":").append(pad(minute)));
					mTimeSet.setTextColor(getResources().getColor(
							android.R.color.holo_blue_light));

					// 设置闹钟
					ClockUtils clockUtils = new ClockUtils(
							SetClockActivity.this);
					clockUtils.setClock(hourOfDay, minute);
				}
			}, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar
					.get(Calendar.MINUTE), true);

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {// 按下的如果是BACK，同时没有重复
			AppManager.getAppManager().finishActivity(SetClockActivity.this);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
