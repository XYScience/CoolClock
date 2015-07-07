package com.science.coolclock.ui;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.science.coolclock.R;

/**
 * @description
 * 
 * @author ÐÒÔËScience ³ÂÍÁŸö
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-7-7
 * 
 */

public class ClockStartActivity extends Activity {

	private MediaPlayer mMediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.clock_start);

		initView();
	}

	private void initView() {
		mMediaPlayer = MediaPlayer.create(ClockStartActivity.this,
				R.raw.a_little_story);
		mMediaPlayer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMediaPlayer.stop();
		mMediaPlayer.release();
	}
}
