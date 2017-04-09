package com.example.tomek.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.VideoView;

import java.util.List;

/**
 * Created by Tomek on 2017-04-04.
 */

public class MyOnLongClikckListener /*implements AdapterView.OnItemLongClickListener*/ {
    private List<String> videos;
    private Runnable thread;
    private int tmp;
    private Handler handler;
    private boolean mEnabled;
    private Context mContext;
    private int position;
    private VideoView holder;
    private GridView gridView;
    private GestureDetector mTapDetector;
    private View.OnTouchListener mReleaseListener = new OnReleaseListener();

    class GestureTap extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            holder.start();
            thread = new Runnable() {
                @Override
                public void run() {
                        holder.seekTo(20000);

                    handler.postDelayed(this, 10000);
                }
            };
            handler.postDelayed(thread, 10000);
            mEnabled = true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            int position = gridView.pointToPosition((int) e.getX(), (int)e.getY());
            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra("position", position);
            mContext.startActivity(intent);
            return true;
        }
    }

    public MyOnLongClikckListener(List<String> videos, Runnable thread, Handler handler, Context mContext, GridView gridView) {
        this.videos = videos;
        this.thread = thread;
        this.handler = handler;
        this.mContext = mContext;
        this.gridView = gridView;
        this.mEnabled = false;
        mTapDetector = new GestureDetector(mContext, new GestureTap());
    }

    public View.OnTouchListener getReleaseListener() {
        return mReleaseListener;
    }

    private class OnReleaseListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            position = gridView.pointToPosition((int) motionEvent.getX(), (int)motionEvent.getY());
            holder =(VideoView)view.findViewById(20000+ position);

            if(motionEvent.getAction() == android.view.MotionEvent.ACTION_UP) {
                if(mEnabled) {

                    handler.removeCallbacks(thread);
                    mEnabled = false;
                    holder.pause();
                    holder.setVideoPath(videos.get(position));
                    holder.seekTo(20000);
                    return true;
                }

                int first = gridView.getFirstVisiblePosition();
             //   gridView.smoothScrollToPosition(first);
                gridView.smoothScrollToPositionFromTop(first, 0, 200);

            }
            else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN);{
               mTapDetector.onTouchEvent(motionEvent);
            }
            return false;
        }
    }
}
