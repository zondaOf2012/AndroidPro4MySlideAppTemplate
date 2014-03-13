package com.zonda.template;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import com.umeng.analytics.MobclickAgent;
import com.zonda.cjson.ZondaDJson;
import com.zonda.cxm.Ctm;

public class BaseActivity extends ActionBarActivity {

	protected ZondaDJson spotManager;

	protected Ctm mCtmManager;

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);

		spotManager = ZondaDJson.getIs(getApplicationContext(),
				Contants.DYD_KEY);

		spotManager.lcp(getApplicationContext());

		mCtmManager = Ctm.getInstance();

		mCtmManager.setId(this, Contants.HEIZI_KEY);
	}

	@Override
	protected void onResume() {

		super.onResume();

		MobclickAgent.onResume(this);

		// final ZondaDJson spotManager =
		// ZondaDJson.getIs(getApplicationContext(), Contants.DYD_KEY);
		// spotManager.scp(this);
		
		mCtmManager.show(this);
	}

	@Override
	protected void onPause() {

		super.onPause();

		MobclickAgent.onPause(this);
	}
}
