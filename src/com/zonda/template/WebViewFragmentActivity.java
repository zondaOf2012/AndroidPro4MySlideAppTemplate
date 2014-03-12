package com.zonda.template;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

public class WebViewFragmentActivity extends BaseActivity {

	public static final String WEBVIEW_PARAMS_KEY = "WEBVIEW_PARAMS_KEY";
	
	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);

		if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {

			WebViewFragment webFragment = new WebViewFragment();

			webFragment.initInstance((MenuItemModel) getIntent()
					.getSerializableExtra(WEBVIEW_PARAMS_KEY));

			final FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();

			ft.add(android.R.id.content, webFragment).commit();
		}
	}
}
