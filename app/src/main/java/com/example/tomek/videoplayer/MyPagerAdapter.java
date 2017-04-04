package com.example.tomek.videoplayer;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Tomek on 2017-04-03.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<String> mDataSet;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MyPagerAdapter(List<String> mDataSet, Context mContext) {
        this.mDataSet = mDataSet;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item_view, container, false);

        VideoView videoView = (VideoView) itemView.findViewById(R.id.myvideoview);
        videoView.setVideoPath(mDataSet.get(position));
        videoView.setId(10000 + position);
        videoView.seekTo(100);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}
