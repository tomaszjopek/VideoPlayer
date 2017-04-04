package com.example.tomek.videoplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomek on 2017-04-04.
 */

public class MyOnLongClikckListener implements AdapterView.OnItemLongClickListener {
    private List<String> videos;
    private Runnable thread;
    private int tmp;
    private Handler handler;
    private boolean mEnabled;
    private Context mContext;
    private int position;
    private ImageView holder;
    private View.OnTouchListener mReleaseListener = new OnReleaseListener();

    public MyOnLongClikckListener(List<String> videos, Runnable thread, Handler handler, Context mContext) {
        this.videos = videos;
        this.thread = thread;
        this.handler = handler;
        this.mContext = mContext;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView image = (ImageView) view.findViewById(R.id.gridViewItem);
        this.position = position;
        this.holder = image;

        MediaMetadataRetriever mediaMetadataRetriever= new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(videos.get(position));

        Bitmap[] frames = new Bitmap[5];
        int counter = 0;
        for(long i = 0; i < 60000000; i+=12000000) {
            frames[counter++] = mediaMetadataRetriever.getFrameAtTime(i);
        }

        thread = new Runnable() {
            @Override
            public void run() {
                if(tmp > 4)
                    tmp = 0;

                image.setImageBitmap(frames[tmp++]);
                handler.postDelayed(this, 500);
            }
        };

        handler.postDelayed(thread, 1000);
        mEnabled = true;

        return true;
    }

    public View.OnTouchListener getReleaseListener() {
        return mReleaseListener;
    }

    private class OnReleaseListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(motionEvent.getAction() == android.view.MotionEvent.ACTION_UP) {
                if(mEnabled) {
                    handler.removeCallbacks(thread);
                    mEnabled = false;
                    Drawable drawable = ContextCompat.getDrawable(mContext, getImages().get(position));
                    holder.setImageDrawable(drawable);
                    return true;
                }
            }
            return false;
        }
    }

    private ArrayList<Integer> getImages() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.one);
        list.add(R.drawable.two);
        list.add(R.drawable.three);
        list.add(R.drawable.four);
        list.add(R.drawable.five);
        list.add(R.drawable.six);
        list.add(R.drawable.seven);
        list.add(R.drawable.eight);
        list.add(R.drawable.nine);
        list.add(R.drawable.ten);
        list.add(R.drawable.eleven);
        list.add(R.drawable.twelve);

        return list;
    }
}
