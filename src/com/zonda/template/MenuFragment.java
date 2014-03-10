package com.zonda.template;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends Fragment {

	private OnSwitchItemListener mSwitchItemListener;

	private int textArrayResId;

	public static MenuFragment getInstance(int menuArrayResId) {

		MenuFragment fragment = new MenuFragment();

		fragment.textArrayResId = menuArrayResId;

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// View view = inflater.inflate(R.layout.strategy_menu, container,
		// false);

		final Context context = inflater.getContext();

		final ListView menuLv = new ListView(context);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				context, textArrayResId, android.R.layout.simple_list_item_1);

		menuLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (mSwitchItemListener != null) {

					try {
						MenuItemModel itemModel = new MenuItemModel();

						itemModel.id = position;

						itemModel.title = getResources().getStringArray(
								textArrayResId)[position];

						String[] menuTypeValues = getResources()
								.getStringArray(R.array.slide_item_showtype_ids);

						itemModel.type = ContentTemplateType
								.valueOf(menuTypeValues[position]);

						Bundle args = new Bundle();

						switch (itemModel.type) {
						case isWebView:
							args.putString(WebViewFragment.WEB_URI_KEY,
									getWebAssetUri("parkour_2.html"));
							break;
						case isGrid:

							break;
						case isList:
							args.putInt(TitleFragment.TITLE_SLIDE_INDEX_KEY, 5);
							break;
						default:
							break;
						}
						
						itemModel.args = args;
						
						mSwitchItemListener.onSwitchItemEvent(itemModel);
					} catch (NotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});

		menuLv.setAdapter(adapter);

		return menuLv;
	}

	public String getWebAssetUri(String relativeDir) {

		return "file:///android_asset/" + relativeDir;
	}

	public void setOnSwitchItemListener(OnSwitchItemListener listener) {

		mSwitchItemListener = listener;
	}

	public interface OnSwitchItemListener {

		public void onSwitchItemEvent(MenuItemModel itemModel);
	}

}
