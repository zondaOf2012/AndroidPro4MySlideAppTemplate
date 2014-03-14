package com.zonda.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TitleFragment extends BaseFragment implements OnItemClickListener {

	public static final String TITLE_SLIDE_INDEX_KEY = "title_slide_index_key";

	private ListView mTitleLv;

	private TitleAdapter mTitleAdapter;

	private List<TitleModel> mDatas;

	private void configDatas() {

		mDatas = new ArrayList<TitleModel>();

		HashMap<String, String> listParams = getParams().params;

		TitleModel titleModel;

		for (Entry<String, String> titleEntry : listParams.entrySet()) {

			titleModel = new TitleModel();

			titleModel.titleText = titleEntry.getKey();

			titleModel.uri = titleEntry.getValue();

			mDatas.add(titleModel);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);

		view = inflater.inflate(R.layout.template_title_fragment, container,
				false);

		mTitleLv = (ListView) view.findViewById(R.id.title_fragment_contentv);

		configDatas();

		mTitleAdapter = new TitleAdapter(mDatas, context);

		mTitleLv.setOnItemClickListener(this);

		mTitleLv.setAdapter(mTitleAdapter);

		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		forwardContent(position);
	}

	void forwardContent(int position) {

		Intent intent = new Intent(context, WebViewFragmentActivity.class);
		
		getParams().web_uri = mDatas.get(position).uri;
		
		intent.putExtra(WebViewFragmentActivity.WEBVIEW_PARAMS_KEY,
				getParams());

		startActivity(intent);

		// WebViewFragment fragment = new WebViewFragment();
		//
		// getParams().web_uri = mDatas.get(position).uri;
		//
		// fragment.initInstance(getParams());
		//
		// getFragmentManager()
		// .beginTransaction()
		// // .setCustomAnimations(R.anim.a_to_b_of_in,
		// // R.anim.a_to_b_of_out,
		// // R.anim.push_right_in, R.anim.push_right_out)
		// .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
		// .replace(android.R.id.content, fragment).addToBackStack(null)
		// .commit();
	}
}
