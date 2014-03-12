package com.zonda.template;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {

	private Context mContext;
	
	private List<MenuItemModel> mDatas;
	
	public MenuAdapter(List<MenuItemModel> datas, Context context){
		
		this.mDatas = datas;
		
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		
		return mDatas != null ? mDatas.size() : 0;
	}

	@Override
	public MenuItemModel getItem(int position) {

		return getCount() > position ? mDatas.get(position) : null;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		
		if(convertView == null){
			
			LayoutInflater inflater = LayoutInflater.from(mContext);
			
			convertView = inflater.inflate(R.layout.template_slidemenu, parent, false);

			holder = new ViewHolder();
			
			holder.tv = (TextView) convertView.findViewById(R.id.item_describe_tv);
			
			convertView.setTag(holder);
		}
		
		holder = (ViewHolder) convertView.getTag();
		
		holder.tv.setText(getItem(position).title);
		
		return convertView;
	}
	
	class ViewHolder{
		
		TextView tv;
	}

}


















