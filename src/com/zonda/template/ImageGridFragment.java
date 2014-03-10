package com.zonda.template;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class ImageGridFragment extends Fragment implements
		AdapterView.OnItemClickListener {

	public static final int holoRedLight = 0xffff4444;

	public static final int holoGreenLight = 0xff99cc00;

	public static final int holoBlueLight = 0xff33b5e5;

	private final static String TAG = "ImageGridFragment";

	private final static String GRID_TYPE = "Grid_Type";
	
	public final static String EXTRA_TITLE_ARG = "EXTRA_TITLE_ARG";

	public final static int GRID_PETS_TYPE = 1;

	public final static int GRID_ZOMBIES_TYPE = 2;
	
	private int mImageThumbSize;

	private int mImageThumbSpacing;

	private ImageAdapter mAdapter;

	private ArrayList<String> images = new ArrayList<String>();

	public ImageGridFragment() {

	}

	public static ImageGridFragment getInstance(int grid_type, String titleName) {

		ImageGridFragment fragment = new ImageGridFragment();

		Bundle args = new Bundle();

		args.putInt(GRID_TYPE, grid_type);
		
		args.putString(EXTRA_TITLE_ARG, titleName);

		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mImageThumbSize = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_size);

		mImageThumbSpacing = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_spacing);

		mAdapter = new ImageAdapter(getActivity());

		int types = getArguments().getInt(GRID_TYPE, GRID_PETS_TYPE);

		StringBuffer sb = null;

		switch (types) {
		case GRID_PETS_TYPE:

			for (int i = 1; i <= 45; i++) {

				sb = new StringBuffer();
				sb.append("pet").append(File.separator).append("pet_")
						.append(i).append(".jpg");
				images.add(sb.toString());
			}
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView images size: " + images.size());

		final View v = inflater.inflate(R.layout.image_grid_fragment,
				container, false);
		
		//TODO
		String titleText = getArguments().getString(EXTRA_TITLE_ARG);
		
		if(titleText == null){
			
			titleText = getResources().getString(R.string.app_name);
		}
		

		final GridView mGridView = (GridView) v.findViewById(R.id.gridView);

//		mGridView.setBackgroundColor(holoGreenLight);
		
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
			
			if(position % 2 == 0){
				
				imageView.setBackgroundColor(holoRedLight);
			}else{
				
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

//		Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
//
//		int types = -1;
//		
//		switch (getArguments().getInt(GRID_TYPE, GRID_PLANTS_TYPE)) {
//		case GRID_PLANTS_TYPE:
//			
//			types = ImageDetailActivity.EXTRA_PLANTS_TYPE;
//			break;
//		case GRID_ZOMBIES_TYPE:
//			
//			types = ImageDetailActivity.EXTRA_ZOMBIES_TYPE;
//			break;
//		default:
//			break;
//		}
//		
//		intent.putExtra(ImageDetailActivity.EXTRA_DETAIL_TYPE, types);
//		
//		intent.putExtra(ImageDetailActivity.EXTRA_IMAGE, position);
//		
//		startActivity(intent);
	}
}
