package com.zonda.template;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.SparseArrayCompat;
import android.util.DisplayMetrics;

import com.zonda.template.MenuFragment.OnSwitchItemListener;
import com.zonda.template.slide.SlidingFragmentActivity;
import com.zonda.template.slide.SlidingMenu;

public class ZondaActivity extends SlidingFragmentActivity implements
		OnSwitchItemListener {

	int mCurContentId = 0;

	Fragment mContentFragment;

	SparseArrayCompat<RestoreInfo> mRestoreInfos = new SparseArrayCompat<RestoreInfo>(
			5);

	@Override
	public void onCreate(Bundle arg0) {

		super.onCreate(arg0);

		setContentView(R.layout.activity_main);

		setBehindContentView(R.layout.strategy_menu);

		mContentFragment = ImageGridFragment.getInstance(
				ImageGridFragment.GRID_PETS_TYPE, "");

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_fragment, mContentFragment).commit();

		MenuFragment menuFragment = MenuFragment
				.getInstance(R.array.slide_item_types);

		menuFragment.setOnSwitchItemListener(this);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.strategy_menu, menuFragment).commit();

		ensureSlidMenu();
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);

		showMenu();
	}

	@Override
	public void onSwitchItemEvent(MenuItemModel itemModel) {

		if (mCurContentId != itemModel.id) {

			Fragment fragment = null;
			

			switch (itemModel.type) {
			case isGrid:

				fragment = ImageGridFragment.getInstance(
						ImageGridFragment.GRID_PETS_TYPE, "");
				break;
			case isList:

				fragment = new TitleFragment();

				fragment.setArguments(itemModel.args);

				break;
			case isWebView:

				fragment = new WebViewFragment();
				
				fragment.setArguments(itemModel.args);
				break;
			default:
				break;
			}

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

	private void ensureSlidMenu() {

		SlidingMenu sm = getSlidingMenu();

		DisplayMetrics metric = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(metric);

		int offset = metric.widthPixels * 2 / 5;

		// sm.setBehindOffsetRes(R.dimen.sns_behind_offset);
		sm.setBehindOffset(offset);

		sm.setShadowWidth(5);

		sm.setShadowDrawable(R.drawable.shadow);

		sm.setFadeDegree(0.35f);

		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		setSlidingActionBarEnabled(true);

		sm.setMode(SlidingMenu.LEFT);

		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

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
