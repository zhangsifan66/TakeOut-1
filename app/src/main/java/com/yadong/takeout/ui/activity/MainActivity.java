package com.yadong.takeout.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yadong.takeout.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.activity_main)
    public void onClick() {

        Toast.makeText(this,"dasdadadad",Toast.LENGTH_LONG).show();
    }
}
