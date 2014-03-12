package com.zonda.template;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class WebViewFragment extends BaseFragment {

	private WebView mWebView;

	private boolean mIsWebViewAvailable;

	public WebViewFragment() {
		
		super();
	}
	
	public String getWebAssetUri(String relativeDir) {

		return "file:///android_asset/" + relativeDir;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mWebView != null) {
			mWebView.destroy();
		}
		mWebView = new WebView(getActivity());
		mIsWebViewAvailable = true;

		if (getArguments() != null) {

			mWebView.loadUrl(getWebAssetUri(getParams().web_uri));
		}

		return mWebView;
	}

	@Override
	public void onPause() {
		super.onPause();
		mWebView.onPause();
	}

	@Override
	public void onResume() {
		mWebView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroyView() {
		mIsWebViewAvailable = false;
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		if (mWebView != null) {
			mWebView.destroy();
			mWebView = null;
		}
		super.onDestroy();
	}

	public WebView getWebView() {
		return mIsWebViewAvailable ? mWebView : null;
	}
}