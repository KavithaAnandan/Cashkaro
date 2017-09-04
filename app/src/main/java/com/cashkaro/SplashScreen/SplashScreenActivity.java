package com.cashkaro.SplashScreen;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.cashkaro.CommonModules.TinyDB;
import com.cashkaro.MainHome.MenuActivity;
import com.cashkaro.R;
import com.cashkaro.WelcomeScreen.WelcomeScreenActvity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by SelvaGaneshM on 02-09-2017.
 */

public class SplashScreenActivity extends AppCompatActivity {


    private ImageView splash_logo_img;
    private Animation animation;
    private TinyDB tinyDB;

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);


        setInitViews();


        try {
            PackageInfo info = info = getPackageManager().getPackageInfo("com.cashkaro",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("com.cashkaro:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }




        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tinyDB.getString("first_time") != null && tinyDB.getString("first_time").equalsIgnoreCase("first_time")) {
                   startActivity(new Intent(SplashScreenActivity.this, MenuActivity.class));
                    finish();
                } else {
                    tinyDB.putString("first_time","first_time");
                    startActivity(new Intent(SplashScreenActivity.this, WelcomeScreenActvity.class));
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);


    }

    private void setInitViews() {


        splash_logo_img = (ImageView) findViewById(R.id.splash_logo_img);


        animation = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        ((ImageView) findViewById(R.id.splash_logo_img)).startAnimation(animation);

        tinyDB = new TinyDB(SplashScreenActivity.this);


    }
}
