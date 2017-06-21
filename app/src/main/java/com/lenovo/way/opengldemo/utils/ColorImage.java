package com.lenovo.way.opengldemo.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lenovo.way.opengldemo.R;

/**
 * @author way
 * @data 2017/5/26
 * @description color image.
 */

public class ColorImage {

    public static Bitmap mBitmap1;
    public static Bitmap mBitmap2;
    public static Bitmap mBitmap3;
    public static Bitmap mBitmap4;
    public static Bitmap mBitmap5;
    public static Bitmap mBitmap6;

    public static void load(Resources resources) {
        mBitmap1 = BitmapFactory.decodeResource(resources, R.drawable.icon1);
        mBitmap2 = BitmapFactory.decodeResource(resources, R.drawable.icon2);
        mBitmap3 = BitmapFactory.decodeResource(resources, R.drawable.icon3);
        mBitmap4 = BitmapFactory.decodeResource(resources, R.drawable.icon4);
        mBitmap5 = BitmapFactory.decodeResource(resources, R.drawable.icon5);
        mBitmap6 = BitmapFactory.decodeResource(resources, R.drawable.icon6);

    }
}
