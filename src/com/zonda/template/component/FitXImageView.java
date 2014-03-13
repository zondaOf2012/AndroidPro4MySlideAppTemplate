package com.zonda.template.component;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FitXImageView extends ImageView {

	public FitXImageView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		
		setScaleType(ScaleType.MATRIX);
	}

	@Override
	protected boolean setFrame(int l, int t, int r, int b) {

		Matrix matrix = getImageMatrix();
		
		float scaleFactor = getWidth() / (float) getDrawable().getIntrinsicWidth();
		
		matrix.setScale(scaleFactor, scaleFactor, 0, 0);
		
		setImageMatrix(matrix);
		
		return super.setFrame(l, t, r, b);
	}
}
