package com.zonda.template;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends Fragment{

	private OnItemClickListener onItemClickListener;
	
	private int textArrayResId;
	
	public static MenuFragment getInstance(OnItemClickListener onItemClickListener, int menuArrayResId){
		
		MenuFragment fragment = new MenuFragment();
		
		fragment.onItemClickListener = onItemClickListener;
		
		fragment.textArrayResId = menuArrayResId;
		
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

//		View view = inflater.inflate(R.layout.strategy_menu, container, false);
		
		final Context context = inflater.getContext();

		final ListView menuLv = new ListView(context);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
				textArrayResId,
				android.R.layout.simple_list_item_1);

		menuLv.setOnItemClickListener(onItemClickListener);
		
		menuLv.setAdapter(adapter);

		return menuLv;
	}



}
