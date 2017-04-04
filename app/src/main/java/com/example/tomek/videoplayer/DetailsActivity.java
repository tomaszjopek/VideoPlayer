package com.example.tomek.videoplayer;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private VideoView currentPlaying;
    private ViewPager viewPager;
    private SeekBar seekBar;
    /*private MediaController mediaController;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        /*mediaController = new MediaController(this);*/

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdapter(getPathsToVideos(),this));
        viewPager.setCurrentItem(getIntent().getExtras().getInt("position"));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                VideoView videoView = (VideoView) findViewById(10000 + viewPager.getCurrentItem());
                videoView.seekTo(0);
                videoView.pause();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    VideoView videoView = (VideoView) findViewById(10000 + viewPager.getCurrentItem());
                    videoView.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private List<String> getPathsToVideos() {
        List<String> list = new ArrayList<>();

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MojeVideo/");

        if(dir.isDirectory()) {
            File[] files = dir.listFiles();
            for(File file : files) {
                list.add(file.getAbsolutePath());
            }
        }

        return list;
    }

    public void playMe(View view) {
        VideoView videoView = (VideoView) findViewById(10000 + viewPager.getCurrentItem());
        currentPlaying = videoView;
        seekBar.setMax(videoView.getDuration());
        seekBar.postDelayed(onEverySecond, 1000);
        videoView.start();
    }

    public void pauseMe(View view) {
        VideoView videoView = (VideoView) findViewById(10000 + viewPager.getCurrentItem());
        videoView.pause();
    }

    private Runnable onEverySecond = new Runnable() {
        @Override
        public void run() {
            if(seekBar != null) {
                seekBar.setProgress(currentPlaying.getCurrentPosition());
            }

            if(currentPlaying.isPlaying()) {
                seekBar.postDelayed(onEverySecond, 1000);
            }
        }
    };

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
        startActivity(new Intent(this, MainActivity.class));
    }
}
