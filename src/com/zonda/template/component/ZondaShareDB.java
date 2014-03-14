package com.zonda.template.component;

import android.content.Context;
import android.content.SharedPreferences;

public class ZondaShareDB {

	private Context mContext;

	private SharedPreferences mSharedPreferences;

	private volatile static ZondaShareDB mShareDB;

	public static ZondaShareDB getInstance(Context context) {

		if (mShareDB == null) {

			synchronized (ZondaShareDB.class) {

				if (mShareDB == null) {

					mShareDB = new ZondaShareDB(context);
				}
			}
		}
		return mShareDB;
	}

	private ZondaShareDB(Context context) {

		mContext = context.getApplicationContext();

		mSharedPreferences = mContext.getSharedPreferences("zondaShareDB",
				Context.MODE_PRIVATE);
	}

	public int incrementCount() {

		int count = mSharedPreferences.getInt("startCount", 0);

		mSharedPreferences.edit().putInt("startCount", (count + 1)).commit();

		return count;
	}

}
