package com.imejadevs.diagnosis;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.imejadevs.diagnosis.Main.DrawerActivity;

public class SplashScrean extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make the window full screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screan);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        Thread background = new Thread(){
            public void run(){

                try {
                    sleep(5*100);

                    Intent intent = new Intent(getBaseContext(), DrawerActivity.class);
                    startActivity(intent);

                    finish();
                }
                catch (Exception e){

                }
            }
        };
        background.start();
    }
}
