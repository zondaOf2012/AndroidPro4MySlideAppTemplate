package com.zonda.template;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zonda.template.slide.SlidingFragmentActivity;
import com.zonda.template.slide.SlidingMenu;

public class MainActivity extends SlidingFragmentActivity implements OnItemClickListener {

	Fragment mContentFragment;
	
	@Override
	public void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		
		setContentView(R.layout.activity_main);
		
		setBehindContentView(R.layout.strategy_menu);
		
		mContentFragment = new TitleFragment();
		
		Bundle args = new Bundle();
		
		args.putInt(TitleFragment.TITLE_SLIDE_INDEX_KEY, 5);
		
		mContentFragment.setArguments(args);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_fragment, mContentFragment).commit();

		MenuFragment menuFragment = MenuFragment.getInstance(this,
				R.array.slide_item_types);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.strategy_menu, menuFragment).commit();
		
		ensureSlidMenu();
	}
	
	@Override
	public void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);

		showMenu();
	}
	
	private void ensureSlidMenu() {

		SlidingMenu sm = getSlidingMenu();

		DisplayMetrics metric = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(metric);

		int offset = metric.widthPixels * 3 / 5;

		// sm.setBehindOffsetRes(R.dimen.sns_behind_offset);
		sm.setBehindOffset(offset);

		sm.setShadowWidth(5);
		
		sm.setShadowDrawable(R.drawable.shadow);
		
		sm.setFadeDegree(0.35f);

		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		setSlidingActionBarEnabled(false);

		sm.setMode(SlidingMenu.LEFT);

		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		
	}
}
