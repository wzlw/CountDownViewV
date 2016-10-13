package com.zl.countdownview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CountDownView mCountDownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCountDownView = (CountDownView) findViewById(R.id.cdv);
        mCountDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownView.start(5000);
            }
        });
        mCountDownView.setOnCountDownListener(new CountDownView.OnCountDownListener() {
            @Override
            public void start() {
                Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void finish() {
                Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
