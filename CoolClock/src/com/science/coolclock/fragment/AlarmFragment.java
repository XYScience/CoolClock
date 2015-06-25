package com.science.coolclock.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

	private boolean isNewClock = true;

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
		initListener();

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
		mClockAdapter = new ClockAdapter(getActivity());
		// listview item放大动画'
		// mScaleInAnimationAdapter = new ScaleInAnimationAdapter(mClockAdapter,
		// 0f);
		// mScaleInAnimationAdapter.setAbsListView(mListView);
		// mListView.setAdapter(mScaleInAnimationAdapter);
		mListView.setAdapter(mClockAdapter);
	}

	private void initListener() {
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				isNewClock = false;
				deleteCell(view, position);
				return true;
			}

		});
	}

	private void deleteCell(final View view, final int position) {

		AnimationListener animationListener = new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mListClock.remove(position);
				ViewHolder vh = (ViewHolder) view.getTag();
				vh.needInflate = true;
				mClockAdapter.notifyDataSetChanged();
			}
		};
		collapse(view, animationListener);
	}

	private void collapse(final View view, AnimationListener animationListener) {

		final int initialHeight = view.getMeasuredHeight();

		Animation animation = new Animation() {

			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				if (interpolatedTime == 1) {
					view.setVisibility(View.GONE);
				} else {
					view.getLayoutParams().height = initialHeight
							- (int) (initialHeight * interpolatedTime);
					view.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		if (animationListener != null) {
			animation.setAnimationListener(animationListener);
		}
		animation.setDuration(200);
		view.startAnimation(animation);
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
		mListClock.add(0, clock);
		mClockAdapter.notifyDataSetChanged();
		isNewClock = true;
		// mScaleInAnimationAdapter.notifyDataSetChanged();
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
			final View view;
			Clock clock = mListClock.get(position);

			if (convertView == null) {
				view = mInflater.inflate(R.layout.clock_item, parent, false);
				setViewHolder(view);
			} else if (((ViewHolder) convertView.getTag()).needInflate) {
				view = mInflater.inflate(R.layout.clock_item, parent, false);
				setViewHolder(view);
			} else {
				view = convertView;
			}
			holder = (ViewHolder) view.getTag();

			// 设置时间
			holder.clockTime.setText(clock.getTime());
			// 闹钟开关
			setAlarmToggleChanged(holder.toggleButton);
			// 闹钟详情设计
			setAlarmSetListener(holder.alarmImg);

			if (position == 0 && isNewClock) {
				holder.clock_intro.setBackgroundColor(0xff7ecec9);
				// 新建闹钟动画
				setNewClockAnimation(holder.revealLayout);
			} else {
				holder.clock_intro.setBackgroundColor(0xffffffff);
			}

			return view;
		}

		private void setViewHolder(View convertView) {
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.alarmImg = (ImageView) convertView
					.findViewById(R.id.alarm);
			viewHolder.clockName = (TextView) convertView
					.findViewById(R.id.clock_intro_name);
			viewHolder.clockTime = (TextView) convertView
					.findViewById(R.id.clock_intro_time);
			viewHolder.clockIntroWeek = (TextView) convertView
					.findViewById(R.id.clock_intro_week);
			viewHolder.toggleButton = (ToggleButton) convertView
					.findViewById(R.id.toggle_button);
			viewHolder.revealLayout = (RevealLayout) convertView
					.findViewById(R.id.reveal_layout);
			viewHolder.clock_intro = (RelativeLayout) convertView
					.findViewById(R.id.clock_intro);
			viewHolder.needInflate = false;
			convertView.setTag(viewHolder);
		}
	}

	private void setAlarmToggleChanged(ToggleButton toggleButton) {
		toggleButton.setOnToggleChanged(new OnToggleChanged() {

			@Override
			public void onToggle(boolean on) {
				if (on) {
					Toast.makeText(getActivity(), "闹钟已开启", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(getActivity(), "闹钟已关闭", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	private void setAlarmSetListener(ImageView img) {
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), SetClockActivity.class));
			}
		});
	}

	private void setNewClockAnimation(final RevealLayout revealLayout) {
		revealLayout.setContentShown(false);
		revealLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						revealLayout.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						revealLayout.postDelayed(new Runnable() {
							@Override
							public void run() {
								revealLayout.show();
							}
						}, 50);
					}
				});
	}

	class ViewHolder {
		public boolean needInflate;
		public ImageView alarmImg;
		public TextView clockName;
		public TextView clockTime;
		public RevealLayout revealLayout;
		public RelativeLayout clock_intro;
		public TextView clockIntroWeek;
		public ToggleButton toggleButton;

	}

}
