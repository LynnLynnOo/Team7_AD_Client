package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.SessionManager;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import java.text.SimpleDateFormat;
import java.util.Locale;
/**
 * Created by Kay Thi Swe Tun
 **/
public class SplashActivity extends AwesomeSplash {


    public static int clicked = 0;
    TextView please_wait;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH);
    private boolean state;
    private SessionManager session;

    @Override
    public void initSplash (ConfigSplash configSplash){


        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(2000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_TOP);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_RIGHT); //or Flags.REVEAL_TOP


        //Customize Logo
        configSplash.setLogoSplash(R.drawable.splash); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //Customize Title
        configSplash.setTitleSplash("Stationery Request Management");
        configSplash.setTitleTextColor(android.R.color.white);
        configSplash.setTitleTextSize(20f); //float value

    }

    @Override
    public void animationsFinished () {
        //verifyStoragePermissions(this);

        session = SessionManager.getInstance();
        state = session.isLoggedIn();

        new CountDownTimer(1800, 900) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (state) {

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    TextView txt = findViewById(R.id.wttv);

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();

    }
}
