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
 * @author ����Science ������
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
		// ����ǰʱ�������
		mCalendar = Calendar.getInstance();
		// ����Intent��PendingIntent���������ӹ�����
		mIntent = new Intent(mContext, MyClockReceiver.class);
		mPendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0);
		// ��ȡ���ӹ�����
		mAlarmManager = (AlarmManager) mContext
				.getSystemService(mContext.ALARM_SERVICE);
	}

	/**
	 * ��������
	 * 
	 * @param hourOfDay
	 * @param minute
	 */
	public void setClock(int hourOfDay, int minute) {

		// ����������ʱ�䣬��Ҫ���������������պ͵�ǰͬ��
		mCalendar.setTimeInMillis(System.currentTimeMillis());
		/**
		 * ��ʱ������Ϊ��ʱ��ʱ�� ����������Сʱ�ͷ���
		 */
		mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		mCalendar.set(Calendar.MINUTE, minute);
		// ����ͺ�������Ϊ0
		mCalendar.set(Calendar.SECOND, 0);
		mCalendar.set(Calendar.MILLISECOND, 0);

		// ��������
		mAlarmManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(),
				mPendingIntent);
		// ��һ��������ʾ�������ͣ��ڶ�����ʾ�����״�ִ��ʱ�䣬��������ʾ��������ִ�еļ��ʱ�䣬���ĸ���ʾ������Ӧ����
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				mCalendar.getTimeInMillis(), 10 * 1000, mPendingIntent);

		Toast.makeText(
				mContext,
				"�������ӵ�ʱ��Ϊ��" + String.valueOf(hourOfDay) + ":"
						+ String.valueOf(minute), Toast.LENGTH_SHORT).show();
	}

	/**
	 * ȡ������
	 */
	public void cancleClock() {
		mAlarmManager.cancel(mPendingIntent);
		Toast.makeText(mContext, "�����Ѿ�ȡ����", Toast.LENGTH_SHORT).show();
	}
}
