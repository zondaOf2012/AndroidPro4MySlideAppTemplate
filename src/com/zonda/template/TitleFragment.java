package com.zonda.template;

import java.util.ArrayList;
import java.util.List;

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

		CharSequence[] titleArray = getResources().getTextArray(
				R.array.slide_title_items);

		TitleModel titleModel;

		for (CharSequence titleText : titleArray) {

			titleModel = new TitleModel();

			titleModel.titleText = titleText.toString();

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

	String getItemUri(int position) {

		int slideIndex = 0;

		if (getArguments() != null) {

			slideIndex = getArguments().getInt(TITLE_SLIDE_INDEX_KEY, 0);
		}
		return "file:///android_asset/" + slideIndex + "_" + (position + 1)
				+ ".html";
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		forwardContent(position);
	}

	void forwardContent(int position) {

		WebViewFragment fragment = new WebViewFragment();

		Bundle args = new Bundle();

		args.putString(WebViewFragment.WEB_URI_KEY, getItemUri(position));

		fragment.setArguments(args);

		getFragmentManager()
				.beginTransaction()
				// .setCustomAnimations(R.anim.a_to_b_of_in,
				// R.anim.a_to_b_of_out,
				// R.anim.push_right_in, R.anim.push_right_out)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.replace(android.R.id.content, fragment).addToBackStack(null)
				.commit();
	}
}
