package com.zonda.template;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TitleAdapter extends BaseAdapter {

	private List<TitleModel> mDatas;

	private Context mContext;

	public TitleAdapter(List<TitleModel> datas, Context context) {

		mDatas = datas;

		mContext = context;
	}

	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
	}

	@Override
	public TitleModel getItem(int position) {
		return getCount() > position ? mDatas.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TitleViewHolder holder;

		if (convertView == null || convertView.getTag() == null) {

			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.template_title_item, parent, false);

			holder = new TitleViewHolder();

			holder.titleTv = (TextView) convertView
					.findViewById(R.id.column_title);

			convertView.setTag(holder);
		} else {

			holder = (TitleViewHolder) convertView.getTag();
		}

		holder.titleTv.setText(getItem(position).titleText);

		return convertView;
	}

	class TitleViewHolder {

		public TextView titleTv;
	}

}
