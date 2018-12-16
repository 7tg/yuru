package com.tayyipgoren.yuru;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tayyipgoren.yuru.marvellous.Helpers;

import com.github.jorgecastillo.FillableLoader;
import com.tayyipgoren.yuru.yuru.SVG;

public class LogoActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);


        //Create svg logo
        FillableLoader fillableLoader = findViewById(R.id.fillableLoader);
        fillableLoader.setSvgPath(SVG.YURU);

        //Check if device has internet access
        if(Helpers.haveInternet(getApplicationContext()))
        {
            fillableLoader.start();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);

                }
            }, 5000);
        }else
        {
            this.checkInternet();
        }

        // fillableLoader.setPercentage(0);
    }


    private void checkInternet() {
        if (Helpers.haveInternet(getApplicationContext())) {
            return;
        } else {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LogoActivity.this);
            alertDialogBuilder.setMessage(getString(R.string.error_no_connection_avaible));
            alertDialogBuilder.setPositiveButton(getString(R.string.go_to_settings),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            startActivityForResult(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS), 0);
                        }
                    });

            alertDialogBuilder.setNegativeButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
