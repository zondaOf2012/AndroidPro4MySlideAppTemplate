package com.zonda.template;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zonda.cjson.ZondaDJson;
import com.zonda.second.R;

public class MenuFragment extends BaseFragment {

	public static final String MENU_PARAMS_KEY = "menu_params";

	private OnSwitchItemListener mSwitchItemListener;

	private MenuAdapter mAdapter;

	private ArrayList<MenuItemModel> mDatas;

	private View mHeaderV;

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// View view = inflater.inflate(R.layout.strategy_menu, container,
		// false);

		final ListView menuLv = new ListView(context);

		mDatas = (ArrayList<MenuItemModel>) getArguments().getSerializable(
				MENU_PARAMS_KEY);

		mAdapter = new MenuAdapter(mDatas, context);

		mHeaderV = inflater.inflate(R.layout.template_slidemenu_header, menuLv,
				false);
		
		mHeaderV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final ZondaDJson spotManager = ZondaDJson.getIs(context.getApplicationContext(), Contants.DYD_KEY);
				
				spotManager.scp(context);
			}
		});

		menuLv.addHeaderView(mHeaderV, null, false);

		menuLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (mSwitchItemListener != null) {

					mAdapter.setSelectedPostion(position - 1);
					
					mSwitchItemListener.onSwitchItemEvent(mAdapter
							.getItem(position - 1));
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
