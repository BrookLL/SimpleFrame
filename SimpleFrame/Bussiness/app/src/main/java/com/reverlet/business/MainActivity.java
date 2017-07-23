package com.reverlet.business;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.reverlet.uikitlib.widget.RingView;
import com.riverlet.lib.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RingView ringView = (RingView) findViewById(R.id.ring);
        final TextView textView = (TextView) findViewById(R.id.text);
        float[] datas = new float[]{250,200,150,100,50};
        ringView.setData(datas);
        ringView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ringView.animStart();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.getLog(new LogUtil.OnReadLogListener() {
                    @Override
                    public void onReadSuccess(StringBuilder log) {
                        textView.setText(log);
                    }

                    @Override
                    public void onReadFailure() {
                        textView.setText("log获取失败");
                    }
                });
            }
        });
    }
}
