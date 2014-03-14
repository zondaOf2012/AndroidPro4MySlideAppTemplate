package com.zonda.template;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.SparseArrayCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Interpolator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.zonda.template.MenuFragment.OnSwitchItemListener;
import com.zonda.template.slide.SlidingFragmentActivity;
import com.zonda.template.slide.SlidingMenu;
import com.zonda.template.slide.SlidingMenu.CanvasTransformer;

public class ZondaActivity extends SlidingFragmentActivity implements
		OnSwitchItemListener {

	int mCurContentId = 0;

	BaseFragment mContentFragment;

	SparseArrayCompat<RestoreInfo> mRestoreInfos = new SparseArrayCompat<RestoreInfo>(
			5);
	
	private ArrayList<MenuItemModel> mDatas;

	@Override
	public void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		
		init();
		
		setContentView(R.layout.activity_main);
		
		setBehindContentView(R.layout.strategy_menu);

		mContentFragment = buildContentFragment(mDatas.get(0));

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_fragment, mContentFragment).commit();

		MenuFragment menuFragment = new MenuFragment();
		
		Bundle args = new Bundle();
		
		args.putSerializable(MenuFragment.MENU_PARAMS_KEY, mDatas);
		
		menuFragment.setArguments(args);

		menuFragment.setOnSwitchItemListener(this);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.strategy_menu, menuFragment).commit();

		ensureSlidMenu();
	}
	
	protected boolean isActionBarFinish(){
		
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			
			showMenu();
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	void init(){
		
		try {
			
			InputStream in = getAssets().open("template.json");
			
			Gson gson = new GsonBuilder().serializeNulls().create();
			
			Type type = new TypeToken<ArrayList<MenuItemModel>>(){}.getType();
			
			mDatas = gson.fromJson(new JsonReader(new InputStreamReader(in)), type);

			MenuItemModel aboutUsItem = new MenuItemModel();
			
			aboutUsItem.id = mDatas.size();
			
			aboutUsItem.title = "用户反馈";
			
			aboutUsItem.type = ContentTemplateType.isAboutUs;
			
			mDatas.add(aboutUsItem);
			
			Log.i("TAG", " init : " + (mDatas != null ? mDatas.size() : 0));
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);

		showMenu();
	}

	@Override
	public void onSwitchItemEvent(MenuItemModel itemModel) {
		
		if(itemModel.type == ContentTemplateType.isAboutUs){
			
			mCurContentId = itemModel.id;
			
			Intent intent = new Intent(this, FeedBackActivity.class);
			
			startActivity(intent);
			
			return;
		}

		if (mCurContentId != itemModel.id) {

			BaseFragment fragment = buildContentFragment(itemModel);
			
			if (fragment != null) {
				
				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();

				ft.replace(R.id.content_fragment, fragment).commit();

				mContentFragment = fragment;

				mCurContentId = itemModel.id;
			}
		}

		showContent();
	}
	
	BaseFragment buildContentFragment(MenuItemModel itemModel){
		
		BaseFragment fragment = null;
		

		switch (itemModel.type) {
		case isGrid:

			fragment = new ImageGridFragment();
			break;
		case isList:

			fragment = new TitleFragment();
			break;
		case isWebView:

			fragment = new WebViewFragment();
			break;
		default:
			break;
		}

		if(fragment != null){
			
			fragment.initInstance(itemModel);
		}
		
		return fragment;
	}

	private void ensureSlidMenu() {

		SlidingMenu sm = getSlidingMenu();

		DisplayMetrics metric = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(metric);

		int offset = metric.widthPixels * 1 / 4;

		// sm.setBehindOffsetRes(R.dimen.sns_behind_offset);
		sm.setBehindOffset(offset);

		sm.setShadowWidth(5);

		sm.setShadowDrawable(R.drawable.shadow);

		sm.setFadeDegree(0.35f);

		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		setSlidingActionBarEnabled(true);

		sm.setMode(SlidingMenu.LEFT);

		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		sm.setBehindScrollScale(0.0f);
		
		sm.setBehindCanvasTransformer(mTransformer);
	}
	
	private CanvasTransformer mTransformer = new CanvasTransformer() {
		@Override
		public void transformCanvas(Canvas canvas, float percentOpen) {
			canvas.translate(0, canvas.getHeight()*(1-interp.getInterpolation(percentOpen)));
		}			
	};
	
	private static Interpolator interp = new Interpolator() {
		@Override
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t + 1.0f;
		}		
	};

	static class RestoreInfo {

		private String tag;

		private Fragment fragment;

		public RestoreInfo(String tag, Fragment fragment) {

			this.tag = tag;

			this.fragment = fragment;
		}

		public final String getTag() {
			return tag;
		}

		public final Fragment getFragment() {
			return fragment;
		}
	}

}
