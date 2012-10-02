package br.com.globalcode.jhome.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {
	
	Class<?> activityClass;
	Class<?>[] paramTypes = { Integer.TYPE, Integer.TYPE };

	Method overrideAnimation = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        
        try {
			activityClass = Class.forName("android.app.Activity");
			overrideAnimation = activityClass.getDeclaredMethod("overridePendingTransition", 
					paramTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				Intent i = new Intent(SplashScreenActivity.this,
						DeviceControlsActvitity.class);
				startActivity(i);
				finish();
				if (overrideAnimation != null) {
					try {
						overrideAnimation.invoke(SplashScreenActivity.this, android.R.anim.fade_in,
								android.R.anim.fade_out);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}, 2000);
    }
}