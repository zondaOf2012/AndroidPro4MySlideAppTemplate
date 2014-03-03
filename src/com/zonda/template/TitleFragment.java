package com.zonda.template;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TitleFragment extends BaseFragment {

	private ListView mTitleLv;

	private TitleAdapter mTitleAdapter;
	
	private List<TitleModel> mDatas;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		configDatas();
	}
	
	private void configDatas() {

		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		view = inflater.inflate(R.layout.template_title_fragment, container, false);
		
		mTitleLv = (ListView) view.findViewById(R.id.title_fragment_contentv);
		
		mTitleAdapter = new TitleAdapter(mDatas, context);
		
		mTitleLv.setAdapter(mTitleAdapter);
		
		return view;
	}
}
