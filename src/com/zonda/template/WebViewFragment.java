package com.zonda.template;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
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
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		
//		WebSettings webSettings = mWebView.getSettings();
//        webSettings.setSupportZoom(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setBuiltInZoomControls(true);// support zoom
//        webSettings.setPluginsEnabled(true);// support flash
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//
//        DisplayMetrics metrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int mDensity = metrics.densityDpi;
//        // DebugLog.d(TAG, "densityDpi = " + mDensity);
//        if (mDensity == 240) {
//            webSettings.setDefaultZoom(ZoomDensity.FAR);
//        } else if (mDensity == 160) {
//            webSettings.setDefaultZoom(ZoomDensity.MEDIUM);
//        } else if (mDensity == 120) {
//            webSettings.setDefaultZoom(ZoomDensity.CLOSE);
//        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
//            webSettings.setDefaultZoom(ZoomDensity.FAR);
//        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
//            webSettings.setDefaultZoom(ZoomDensity.FAR);
//        }
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