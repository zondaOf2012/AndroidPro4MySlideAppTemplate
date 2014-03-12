package com.zonda.template;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

	private final static String PARAMS_KEY = "params_key";
	
	protected Context context;

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		context = activity;
	}
	
	public void initInstance(MenuItemModel menuItemModel){
		
		Bundle args = new Bundle();
		
		args.putSerializable(PARAMS_KEY, menuItemModel);
		
		setArguments(args);
	}
	
	protected MenuItemModel getParams() {
		
		MenuItemModel itemModel = null;
		
		if(getArguments() != null){
			
			itemModel = (MenuItemModel) getArguments().getSerializable(PARAMS_KEY);
		}
		
		return itemModel;
	}
	
	protected String getTitleText(){
		
		MenuItemModel itemModel = getParams();
		
		return itemModel != null ? itemModel.title : "";
	}
}
