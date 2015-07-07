package com.science.coolclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.science.coolclock.ui.ClockStartActivity;
import com.science.coolclock.utils.ClockUtils;

/**
 * @description 闹钟广播接收
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-6-28
 * 
 */

public class MyClockReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ClockUtils clockUtils = new ClockUtils(context);
		clockUtils.cancleClock();

		Intent i = new Intent(context, ClockStartActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

}
