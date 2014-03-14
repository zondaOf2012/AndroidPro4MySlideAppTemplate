package com.zonda.template;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;
import com.zonda.cjson.ZondaDJson;
import com.zonda.cxm.Ctm;
import com.zonda.template.component.ZondaShareDB;

public class BaseActivity extends ActionBarActivity {

	protected ZondaDJson spotManager;

	protected Ctm mCtmManager;

	protected ZondaShareDB mShareDB;

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);

		spotManager = ZondaDJson.getIs(getApplicationContext(),
				Contants.DYD_KEY);

		spotManager.lcp(getApplicationContext());

		mCtmManager = Ctm.getInstance();

		mCtmManager.setId(this, Contants.HEIZI_KEY);

		mShareDB = ZondaShareDB.getInstance(this);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		getSupportActionBar().setHomeButtonEnabled(true);
	}
	
	protected boolean isActionBarFinish(){
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			
			if(isActionBarFinish()){
				
				finish();
			}
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	protected void onResume() {

		super.onResume();

		MobclickAgent.onResume(this);

		if (mShareDB.incrementCount() % 2 == 0) {

			final ZondaDJson spotManager = ZondaDJson.getIs(
					getApplicationContext(), Contants.DYD_KEY);
			spotManager.scp(this);
		} else {

			mCtmManager.show(this);
		}
	}

	@Override
	protected void onPause() {

		super.onPause();

		MobclickAgent.onPause(this);
	}
}
