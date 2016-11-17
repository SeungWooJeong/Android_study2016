package kr.ac.kookmin.everydaylifelogger;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by user on 2016-11-16.
 */

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);
    }
}
