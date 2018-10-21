package com.tayyipgoren.yuru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.github.jorgecastillo.FillableLoader;

public class LogoActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        getSupportActionBar().hide();

        FillableLoader fillableLoader = findViewById(R.id.fillableLoader);
        fillableLoader.setSvgPath(SVG.YURU);
        fillableLoader.start();
        // fillableLoader.setPercentage(0);
    }
}
