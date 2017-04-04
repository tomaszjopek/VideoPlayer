package com.example.tomek.videoplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //transparent navigation bars
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new MyAdapter(getImages(), this));

        gridView.setOnItemClickListener((parent, v, position, id) -> {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });
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
