package com.science.coolclock.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.science.coolclock.utils.PagerModelManager;

/**
 * @description Created by chenupt@gmail.com on 2014/8/9. Description TODO
 * 
 * @author ÐÒÔËScience ³ÂÍÁŸö
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-6-30
 * 
 */

public class ModelPagerAdapter extends FragmentStatePagerAdapter {

	protected PagerModelManager pagerModelManager;

	public ModelPagerAdapter(FragmentManager fm,
			PagerModelManager pagerModelManager) {
		super(fm);
		this.pagerModelManager = pagerModelManager;
	}

	@Override
	public Fragment getItem(int position) {
		return pagerModelManager.getItem(position);
	}

	@Override
	public int getCount() {
		return pagerModelManager.getFragmentCount();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (pagerModelManager.hasTitles()) {
			return pagerModelManager.getTitle(position);
		}
		return super.getPageTitle(position);
	}
}
