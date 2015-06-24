package com.science.coolclock.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.race604.flyrefresh.FlyRefreshLayout;
import com.science.coolclock.R;
import com.science.coolclock.adapter.ScaleInAnimationAdapter;
import com.science.coolclock.bean.Clock;
import com.science.coolclock.ui.SetClockActivity;
import com.science.coolclock.widget.RevealLayout;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

/**
 * @description 闹钟界面
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-6-20
 * 
 */

public class AlarmFragment extends Fragment implements
		FlyRefreshLayout.OnPullRefreshListener {

	private View mRootView;
	private ListView mListView;
	private FlyRefreshLayout mFlyRefreshLayout;
	private Handler mHandler = new Handler();

	private ScaleInAnimationAdapter mScaleInAnimationAdapter;
	private ClockAdapter mClockAdapter;
	private ArrayList<Clock> mListClock;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRootView = inflater.inflate(R.layout.fragment_clock_layout, container,
				false);

		initView();
		initData();

		return mRootView;

	}

	private void initView() {

		mListView = (ListView) mRootView.findViewById(R.id.list);
		mListClock = new ArrayList<Clock>();

		mFlyRefreshLayout = (FlyRefreshLayout) mRootView
				.findViewById(R.id.fly_layout);
		mFlyRefreshLayout.setOnPullRefreshListener(this);
	}

	private void initData() {
		// listview动画'
		mClockAdapter = new ClockAdapter(getActivity());
		mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mClockAdapter,
				0f);
		mScaleInAnimationAdapter.setAbsListView(mListView);
		mListView.setAdapter(mScaleInAnimationAdapter);
	}

	@Override
	public void onRefresh(FlyRefreshLayout view) {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mFlyRefreshLayout.onRefreshFinish();
			}
		}, 1000);
	}

	@Override
	public void onRefreshAnimationEnd(FlyRefreshLayout view) {
		Clock clock = new Clock();
		clock.setTime("06:00");
		mListClock.add(clock);
		mScaleInAnimationAdapter.notifyDataSetChanged();
	}

	private class ClockAdapter extends BaseAdapter {

		private LayoutInflater mInflater = null;

		private ClockAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mListClock.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {

			final ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.clock_item, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Clock clock = mListClock.get(position);
			// 设置时间
			holder.clockTime.setText(clock.getTime());
			// 闹钟开关
			holder.toggleButtonA.setOnToggleChanged(new OnToggleChanged() {

				@Override
				public void onToggle(boolean on) {
					if (on) {
						holder.toggleButtonB.setToggleOn();
						Toast.makeText(getActivity(), "闹钟已开启",
								Toast.LENGTH_SHORT).show();
					} else {
						holder.toggleButtonB.setToggleOff();
						Toast.makeText(getActivity(), "闹钟已关闭",
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			holder.toggleButtonB.setOnToggleChanged(new OnToggleChanged() {

				@Override
				public void onToggle(boolean on) {
					if (on) {
						holder.toggleButtonA.setToggleOn();
						Toast.makeText(getActivity(), "闹钟已开启",
								Toast.LENGTH_SHORT).show();
					} else {
						holder.toggleButtonA.setToggleOff();
						Toast.makeText(getActivity(), "闹钟已关闭",
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			// 闹钟详情设计
			setAlarmImgListener(holder.alarmImgA);
			setAlarmImgListener(holder.alarmImgB);

			holder.revealLayout.setOnClickListener(null);
			holder.revealLayout.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getActionMasked() == MotionEvent.ACTION_UP) {

						holder.revealLayout.next((int) event.getX(),
								(int) event.getY(), 2000);

						return true;
					}
					return false;
				}
			});

			return convertView;
		}
	}

	private void setAlarmImgListener(ImageView img) {
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), SetClockActivity.class));
			}
		});
	}

	class ViewHolder {
		public ImageView alarmImgA;
		public ImageView alarmImgB;
		public TextView clockName;
		public TextView clockTime;
		public RevealLayout revealLayout;
		public TextView clockIntroWeek;
		public ToggleButton toggleButtonA;
		public ToggleButton toggleButtonB;

		public ViewHolder(View convertView) {
			super();
			alarmImgA = (ImageView) convertView.findViewById(R.id.alarm_a);
			alarmImgB = (ImageView) convertView.findViewById(R.id.alarm_b);
			clockName = (TextView) convertView
					.findViewById(R.id.clock_intro_name);
			clockTime = (TextView) convertView
					.findViewById(R.id.clock_intro_time);
			clockIntroWeek = (TextView) convertView
					.findViewById(R.id.clock_intro_week);
			toggleButtonA = (ToggleButton) convertView
					.findViewById(R.id.toggle_button1);
			toggleButtonB = (ToggleButton) convertView
					.findViewById(R.id.toggle_button2);
			revealLayout = (RevealLayout) convertView
					.findViewById(R.id.reveal_layout);
		}

	}

}
