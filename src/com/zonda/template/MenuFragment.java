package com.zonda.template;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuFragment extends BaseFragment {

	public static final String MENU_PARAMS_KEY = "menu_params";
	
	private OnSwitchItemListener mSwitchItemListener;

	private MenuAdapter mAdapter;

	private ArrayList<MenuItemModel> mDatas;

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// View view = inflater.inflate(R.layout.strategy_menu, container,
		// false);

		final Context context = inflater.getContext();

		final ListView menuLv = new ListView(context);
		
		mDatas = (ArrayList<MenuItemModel>) getArguments().getSerializable(MENU_PARAMS_KEY);

		mAdapter = new MenuAdapter(mDatas, context);

		menuLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (mSwitchItemListener != null) {

					mSwitchItemListener.onSwitchItemEvent(mAdapter
							.getItem(position));
				}
			}
		});

		menuLv.setAdapter(mAdapter);

		return menuLv;
	}

	public void setOnSwitchItemListener(OnSwitchItemListener listener) {

		mSwitchItemListener = listener;
	}

	public interface OnSwitchItemListener {

		public void onSwitchItemEvent(MenuItemModel itemModel);
	}

}
