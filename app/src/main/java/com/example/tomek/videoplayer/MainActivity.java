package com.example.tomek.videoplayer;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private List<String> videos;
    private int tmp;
    private final static int REQUEST_CODE = 200;
    final Handler handler = new Handler();
    private Runnable thread;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE); // without sdk version check

        //transparent navigation bars
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        videos = getPathsToVideos();

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new MyAdapter(videos, this));
        gridView.setLongClickable(true);
        gridView.setClickable(true);

        MyOnLongClikckListener myOnLongClikckListener = new MyOnLongClikckListener(videos,thread,handler,this, gridView);
        gridView.setOnTouchListener(myOnLongClikckListener.getReleaseListener());
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // save file
            } else {
                Toast.makeText(getApplicationContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
