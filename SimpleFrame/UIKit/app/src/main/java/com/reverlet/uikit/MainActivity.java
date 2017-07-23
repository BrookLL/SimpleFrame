package com.reverlet.uikit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.reverlet.uikitlib.widget.RingView;

import reverlettest.com.uikit.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RingView ringview = (RingView) findViewById(R.id.ringview);
        float[] datas = new float[]{250,200,150,100,50};
        ringview.setData(datas);
        ringview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ringview.animStart();
            }
        });
    }
}
