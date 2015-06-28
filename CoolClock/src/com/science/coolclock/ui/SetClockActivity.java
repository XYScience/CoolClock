package com.science.coolclock.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import me.drakeet.materialdialog.MaterialDialog;
import mirko.android.datetimepicker.time.RadialPickerLayout;
import mirko.android.datetimepicker.time.TimePickerDialog;
import mirko.android.datetimepicker.time.TimePickerDialog.OnTimeSetListener;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.science.coolclock.R;
import com.science.coolclock.utils.ClockUtils;
import com.science.coolclock.widget.RevealLayout;
import com.science.coolclock.widget.SwipeDismissTouchListener;
import com.skyfishjy.library.RippleBackground;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

/**
 * @description �����������ý���
 * 
 * @author ����Science ������
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-6-25
 * 
 */

public class SetClockActivity extends AppCompatActivity {

	private Toolbar mToolbar;
	private RevealLayout mRevealLayout;
	private RelativeLayout mSetClockLayout;
	private ImageView mBackImage;
	private RippleBackground mRippleBackground;
	private ImageView mVoiceRecordImg;

	private TextView mSetClockTime;
	private TextView mSetClockWeek;
	private final Calendar mCalendar = Calendar.getInstance();
	private int hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY);
	private int minute = mCalendar.get(Calendar.MINUTE);
	private ImageView mBtnChangeTime;
	private ImageView mBtnChangeWeek;

	private List<String> mWeekList;
	private String[] week = { "һ", "��", "��", "��", "��", "��", "��" };
	// TreeSet��Set��һ�ֱ���,����ʵ���Զ�����ȥ�صȹ��ܵļ���
	private TreeSet<Integer> weekList = new TreeSet<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_set);
		// ����ʽ״̬������
		initSystemBar();
		initView();
		resetTime();
		resetWeek();
		initListener();

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

	private void resetTime() {
		mSetClockTime.setText(new StringBuilder().append(pad(hourOfDay))
				.append(":").append(pad(minute)));
		mSetClockTime.setTextColor(getResources().getColor(
				android.R.color.darker_gray));

	}

	private void resetWeek() {
		mSetClockWeek.setText("δ����");
		mSetClockWeek.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	private void initView() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mSetClockLayout = (RelativeLayout) findViewById(R.id.set_clock_layout);
		mSetClockLayout.setBackgroundColor(0xffffffff);
		mRevealLayout = (RevealLayout) findViewById(R.id.reveal_layout);
		mBackImage = (ImageView) findViewById(R.id.back);
		mRippleBackground = (RippleBackground) findViewById(R.id.voice_record_ripple);
		mVoiceRecordImg = (ImageView) findViewById(R.id.voice_record);
		if (mToolbar != null) {
			setSupportActionBar(mToolbar);
			getSupportActionBar().setTitle(null);
		}

		mSetClockTime = (TextView) findViewById(R.id.tv_time);
		mSetClockWeek = (TextView) findViewById(R.id.tv_week);
		mBtnChangeTime = (ImageView) findViewById(R.id.btn_change_time);
		mBtnChangeWeek = (ImageView) findViewById(R.id.btn_change_week);
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

		mBackImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mVoiceRecordImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mRippleBackground.startRippleAnimation();
			}
		});

		// ʱ�们��ɾ��
		mSetClockTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// nothing to do, just to let onTouchListener work

			}
		});
		// ʱ�们��ɾ��
		mSetClockTime.setOnTouchListener(new SwipeDismissTouchListener(
				mSetClockTime, null,
				new SwipeDismissTouchListener.DismissCallbacks() {
					@Override
					public boolean canDismiss(Object token) {
						return true;
					}

					@Override
					public void onDismiss(View view, Object token) {
						resetTime();
					}
				}));
		// ѡ��ʱ��
		mBtnChangeTime.setOnClickListener(new OnClickListener() {
			private String tag;

			@Override
			public void onClick(View v) {
				timePickerDialog24h.show(getFragmentManager(), tag);
			}
		});

		// ��������ɾ��
		mSetClockWeek.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// nothing to do, just to let onTouchListener work

			}
		});
		// ��������ɾ��
		mSetClockWeek.setOnTouchListener(new SwipeDismissTouchListener(
				mSetClockWeek, null,
				new SwipeDismissTouchListener.DismissCallbacks() {
					@Override
					public boolean canDismiss(Object token) {
						return true;
					}

					@Override
					public void onDismiss(View view, Object token) {
						resetWeek();
					}
				}));
		// ѡ������
		mBtnChangeWeek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showSelectWeekDialog();
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

		mMaterialDialog.setTitle("�Զ���").setContentView(listView);
		mMaterialDialog.setPositiveButton("ȷ��", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMaterialDialog.dismiss();

				setWeek();
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
			View view = View.inflate(SetClockActivity.this,
					R.layout.set_clock_week, null);
			// ʹ��ÿһ�ζ�findviewById�ķ��������listview_item�ڲ������
			TextView item = (TextView) view.findViewById(R.id.week);
			item.setText(mWeekList.get(position));
			ToggleButton weekBtn = (ToggleButton) view
					.findViewById(R.id.week_button);
			weekBtn.setOnToggleChanged(new OnToggleChanged() {

				@Override
				public void onToggle(boolean on) {
					if (on) {
						weekList.add(position);
					} else {
						setUnSelectWeek(position);
					}
				}
			});
			return view;
		}
	}

	// ��ʾѡ�������
	private void setWeek() {
		mSetClockWeek.setText("");
		if (weekList.size() < 7) {
			Iterator it = weekList.iterator();
			while (it.hasNext()) {
				mSetClockWeek.setText(mSetClockWeek.getText() + "��"
						+ week[(int) it.next()]);
			}
		} else {
			mSetClockWeek.setText("ÿ��");
		}
		mSetClockWeek.setTextColor(getResources().getColor(
				android.R.color.holo_blue_light));
		weekList.clear();
	}

	// ��ѡ��ʱ
	private void setUnSelectWeek(int position) {
		mSetClockWeek.setText("δ����");
		mSetClockWeek.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
	}

	/** ���һ���õ����ݵķ���������ʹ�� */
	private void getWeek() {
		mWeekList = new ArrayList<String>();
		/** Ϊ��̬����������� */
		for (int i = 0; i < 7; i++) {
			mWeekList.add("��" + week[i]);
		}
	}

	final TimePickerDialog timePickerDialog24h = TimePickerDialog.newInstance(
			new OnTimeSetListener() {

				@Override
				public void onTimeSet(RadialPickerLayout view, int hourOfDay,
						int minute) {

					mSetClockTime.setText(new StringBuilder()
							.append(pad(hourOfDay)).append(":")
							.append(pad(minute)));
					mSetClockTime.setTextColor(getResources().getColor(
							android.R.color.holo_blue_light));

					// ��������
					ClockUtils clockUtils = new ClockUtils(
							SetClockActivity.this);
					clockUtils.setClock(hourOfDay, minute);
				}
			}, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar
					.get(Calendar.MINUTE), true);

}
