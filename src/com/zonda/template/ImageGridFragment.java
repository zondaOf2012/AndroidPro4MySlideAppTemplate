package com.zonda.template;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageGridFragment extends BaseFragment implements
		AdapterView.OnItemClickListener {

	public static final int holoRedLight = 0xffff4444;

	public static final int holoGreenLight = 0xff99cc00;

	public static final int holoBlueLight = 0xff33b5e5;

	private final static String TAG = "ImageGridFragment";

	public final static String GRID_PARAMS_KEY = "Grid_Type";

	private int mImageThumbSize;

	private int mImageThumbSpacing;

	private ImageAdapter mAdapter;

	private ArrayList<String> images = new ArrayList<String>();

	public ImageGridFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mImageThumbSize = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_size);

		mImageThumbSpacing = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_spacing);

		mAdapter = new ImageAdapter(getActivity());

		initImgs();
	}

	private void initImgs() {

		MenuItemModel itemModel = getParams();

		int first = itemModel.grid_first_index;

		int count = itemModel.grid_count;

		String assertPrefix = itemModel.grid_item_prefix;

		String assertPostFix = itemModel.grid_item_postfix;

		StringBuffer imgAssertPath = null;

		for (int i = first; i <= (count - first); i++) {

			imgAssertPath = new StringBuffer();

			imgAssertPath.append(assertPrefix).append(i).append(assertPostFix);

			images.add(imgAssertPath.toString());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView images size: " + images.size());

		final View v = inflater.inflate(R.layout.image_grid_fragment,
				container, false);

		String titleText = getTitleText();

		if (TextUtils.isEmpty(titleText)) {

			titleText = getResources().getString(R.string.app_name);
		}

		final GridView mGridView = (GridView) v.findViewById(R.id.gridView);

		// mGridView.setBackgroundColor(holoGreenLight);

		mGridView.setAdapter(mAdapter);

		mGridView.setOnItemClickListener(this);

		mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						if (mAdapter.getNumColumns() == 0) {
							final int numColumns = (int) Math.floor(mGridView
									.getWidth()
									/ (mImageThumbSize + mImageThumbSpacing));
							if (numColumns > 0) {
								final int columnWidth = (mGridView.getWidth() / numColumns)
										- mImageThumbSpacing;
								mAdapter.setNumColumns(numColumns);
								mAdapter.setItemHeight(columnWidth);
								if (BuildConfig.DEBUG) {
									Log.d(TAG,
											"onCreateView - numColumns set to "
													+ numColumns);
								}
							}
						}
					}
				});

		return v;
	}

	private class ImageAdapter extends BaseAdapter {

		private final Context mContext;
		private int mItemHeight = 0;
		private int mNumColumns = 0;
		private GridView.LayoutParams mImageViewLayoutParams;

		public ImageAdapter(Context context) {
			super();
			mContext = context;
			mImageViewLayoutParams = new GridView.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		}

		@Override
		public int getCount() {

			return images.size();
		}

		@Override
		public Object getItem(int position) {
			return images.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView imageView = null;

			if (convertView != null) {

				imageView = (ImageView) convertView;

				imageView.setImageBitmap(null);
			} else {

				imageView = new ImageView(mContext);
			}

			if (position % 2 == 0) {

				imageView.setBackgroundColor(holoRedLight);
			} else {

				imageView.setBackgroundColor(holoBlueLight);
			}

			try {

				Log.d(TAG, "getView fileDir: " + images.get(position));

				InputStream ins = mContext.getAssets().open(
						images.get(position));

				Bitmap bitmap = BitmapFactory.decodeStream(ins);

				imageView.setScaleType(ScaleType.CENTER_INSIDE);

				imageView.setLayoutParams(mImageViewLayoutParams);

				imageView.setImageBitmap(bitmap);

				Log.d(TAG, "getView setImageBitmap: " + position);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return imageView;
		}

		public void setItemHeight(int height) {
			if (height == mItemHeight) {
				return;
			}
			mItemHeight = height;
			mImageViewLayoutParams = new GridView.LayoutParams(
					LayoutParams.MATCH_PARENT, mItemHeight);
			notifyDataSetChanged();
		}

		public void setNumColumns(int numColumns) {
			mNumColumns = numColumns;
		}

		public int getNumColumns() {
			return mNumColumns;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(context, WebViewFragmentActivity.class);

		MenuItemModel itemModel = getParams();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(itemModel.grid_detail_prefix)
		  .append(itemModel.grid_first_index + position)
		  .append(itemModel.grid_detail_postfix);
		
		itemModel.web_uri = sb.toString();
		
		intent.putExtra(WebViewFragmentActivity.WEBVIEW_PARAMS_KEY,
				getParams());

		startActivity(intent);
	}
}
