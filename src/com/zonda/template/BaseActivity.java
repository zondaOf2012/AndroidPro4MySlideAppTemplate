package com.zonda.template;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;
import com.zonda.cjson.ZondaDJson;

public class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		
		final ZondaDJson spotManager = ZondaDJson.getIs(getApplicationContext(), Contants.DYD_KEY);
		spotManager.lcp(getApplicationContext());
	}
	
	@Override
	protected void onResume() {

		super.onResume();
		
		MobclickAgent.onResume(this);
		final ZondaDJson spotManager = ZondaDJson.getIs(getApplicationContext(), Contants.DYD_KEY);
		spotManager.scp(this);
	}
	
	@Override
	protected void onPause() {

		super.onPause();
		
		MobclickAgent.onPause(this);
	}
}
