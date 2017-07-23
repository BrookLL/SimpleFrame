package com.riverlet.library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.reverlet.librarytest.R;
import com.riverlet.lib.util.LogUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtil.v(TAG, "onCreate");
        final TextView textview = (TextView) findViewById(R.id.textview);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.getLog(new LogUtil.OnReadLogListener() {
                    @Override
                    public void onReadSuccess(StringBuilder log) {
                        textview.setText(log);
                    }

                    @Override
                    public void onReadFailure() {
                        textview.setText("log获取失败");
                    }
                });
            }
        });
    }
}
