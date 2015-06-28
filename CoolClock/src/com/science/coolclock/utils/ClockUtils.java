package com.science.coolclock.utils;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.science.coolclock.receiver.MyClockReceiver;

/**
 * @description
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-6-28
 * 
 */

public class ClockUtils {

	private Context mContext;
	private final Calendar mCalendar;
	private Intent mIntent;
	private PendingIntent mPendingIntent;
	private AlarmManager mAlarmManager;

	public ClockUtils(Context mContext) {
		super();
		this.mContext = mContext;
		// 代表当前时间的日历
		mCalendar = Calendar.getInstance();
		// 建立Intent和PendingIntent来调用闹钟管理器
		mIntent = new Intent(mContext, MyClockReceiver.class);
		mPendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0);
		// 获取闹钟管理器
		mAlarmManager = (AlarmManager) mContext
				.getSystemService(mContext.ALARM_SERVICE);
	}

	/**
	 * 设置闹钟
	 * 
	 * @param hourOfDay
	 * @param minute
	 */
	public void setClock(int hourOfDay, int minute) {

		// 设置日历的时间，主要是让日历的年月日和当前同步
		mCalendar.setTimeInMillis(System.currentTimeMillis());
		/**
		 * 将时间设置为定时的时间 设置日历的小时和分钟
		 */
		mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		mCalendar.set(Calendar.MINUTE, minute);
		// 将秒和毫秒设置为0
		mCalendar.set(Calendar.SECOND, 0);
		mCalendar.set(Calendar.MILLISECOND, 0);

		// 设置闹钟
		mAlarmManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(),
				mPendingIntent);
		// 第一个参数表示闹钟类型，第二个表示闹钟首次执行时间，第三个表示闹钟两次执行的间隔时间，第四个表示闹钟响应动作
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				mCalendar.getTimeInMillis(), 10 * 1000, mPendingIntent);

		Toast.makeText(
				mContext,
				"设置闹钟的时间为：" + String.valueOf(hourOfDay) + ":"
						+ String.valueOf(minute), Toast.LENGTH_SHORT).show();
	}

	/**
	 * 取消闹钟
	 */
	public void cancleClock() {
		mAlarmManager.cancel(mPendingIntent);
		Toast.makeText(mContext, "闹钟已经取消！", Toast.LENGTH_SHORT).show();
	}
}
