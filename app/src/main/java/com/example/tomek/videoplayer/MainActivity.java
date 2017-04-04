package com.example.tomek.videoplayer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
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
        gridView.setAdapter(new MyAdapter(getImages(), this));
        gridView.setLongClickable(true);
        gridView.setClickable(true);


        gridView.setOnItemClickListener((parent, v, position, id) -> {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });

        MyOnLongClikckListener myOnLongClikckListener = new MyOnLongClikckListener(videos,thread,handler,this, gridView);
        gridView.setOnItemLongClickListener(myOnLongClikckListener);
        gridView.setOnTouchListener(myOnLongClikckListener.getReleaseListener());

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
