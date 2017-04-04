package com.example.tomek.videoplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomek on 2017-03-19.
 */

public class MyAdapter extends BaseAdapter {
    private List<Integer>  mData;
    private Context mContext;
    private LayoutInflater inflater;

    public MyAdapter(ArrayList<Integer> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.gridview_item, null);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.gridViewItem);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
        int actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data,  metrics);

        int displayHeigth = metrics.heightPixels;
        int imageHeight = displayHeigth/4;

        Drawable drawable = ContextCompat.getDrawable(mContext, mData.get(position));
        viewHolder.image.setImageDrawable(drawable);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.rowLayout);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams((int)metrics.widthPixels/2, imageHeight));

        return convertView;
    }

    private class ViewHolder {
        ImageView image;
    }


}
