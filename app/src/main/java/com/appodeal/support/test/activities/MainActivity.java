package com.appodeal.support.test.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.NativeCallbacks;
import com.appodeal.support.test.R;
import com.appodeal.support.test.constants.Constants;
import com.appodeal.support.test.patterns.SingletonBanner;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, Constants {

    private Button mBtnGoToListview;
    private TextView mTvCounter;
    private CountDownTimer mCountDownTimer;

    private boolean mOnButtonPress;
    private boolean mButtonStateRotatedScreen;
    private long mCounerTime = MAX_TIMER_VALUE_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvCounter = (TextView) findViewById(R.id.time_counter);
        mBtnGoToListview = (Button) findViewById(R.id.button_go_to_listview);

        mButtonStateRotatedScreen = false;
        mBtnGoToListview.setOnClickListener(this);
        mBtnGoToListview.setEnabled(mButtonStateRotatedScreen);

        mOnButtonPress = false;

        Appodeal.disableLocationPermissionCheck();
        Appodeal.initialize(this, APP_KEY, Appodeal.INTERSTITIAL | Appodeal.BANNER |
                Appodeal.NATIVE);
        Appodeal.setTesting(true);

        setBannerCallback();
        onButtonEnabled();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);

        if (!mOnButtonPress)
            onStartTimeCount();

        mBtnGoToListview.setEnabled(mButtonStateRotatedScreen);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mCountDownTimer != null)
        mCountDownTimer.cancel();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ARGS_KEY_PRESENT_TIMER_VALUE, mCounerTime);
        outState.putBoolean(ARGS_KEY_AVAILABILITY_BUTTON_STATE, mButtonStateRotatedScreen);
        outState.putBoolean(ARGS_KEY_PRESS_BUTTON_STATE, mOnButtonPress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCounerTime = savedInstanceState.getLong(ARGS_KEY_PRESENT_TIMER_VALUE);
        mButtonStateRotatedScreen = savedInstanceState.getBoolean(ARGS_KEY_AVAILABILITY_BUTTON_STATE);
        mOnButtonPress = savedInstanceState.getBoolean(ARGS_KEY_PRESS_BUTTON_STATE);
    }

    @Override
    public void onClick(View view) {
        mOnButtonPress = true;

        if (mCountDownTimer != null)
        mCountDownTimer.cancel();

        mTvCounter.setVisibility(View.GONE);

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    private void onStartStaticInterstitial() {
        Appodeal.show(this, Appodeal.INTERSTITIAL);
    }

    private void onStartTimeCount() {
        mCountDownTimer = new CountDownTimer(mCounerTime, COUNT_DOWN_INTERVAL_MILLIS) {
            @Override
            public void onTick(long l) {
                mTvCounter.setText("" + (int) (l / COUNT_DOWN_INTERVAL_MILLIS));
                mCounerTime = l;
            }

            @Override
            public void onFinish() {
                onStartStaticInterstitial();
                mCounerTime = MAX_TIMER_VALUE_MILLIS;
            }
        }.start();
    }

    private void setBannerCallback() {
        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int height, boolean isPrecache) {
                SingletonBanner.getInstance(MainActivity.this);
            }

            @Override
            public void onBannerFailedToLoad() {
            }

            @Override
            public void onBannerShown() {
                new CountDownTimer(BANNER_DISPLAY_TIME_MILLIS, COUNT_DOWN_INTERVAL_MILLIS) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {
                        Appodeal.hide(MainActivity.this, Appodeal.BANNER);
                    }
                }.start();
            }

            @Override
            public void onBannerClicked() {
            }
        });
    }

    private void onButtonEnabled() {
        Appodeal.setNativeCallbacks(new NativeCallbacks() {
            @Override
            public void onNativeLoaded() {
                mButtonStateRotatedScreen = true;
                mBtnGoToListview.setEnabled(mButtonStateRotatedScreen);
            }

            @Override
            public void onNativeFailedToLoad() {
            }

            @Override
            public void onNativeShown(NativeAd nativeAd) {
            }

            @Override
            public void onNativeClicked(NativeAd nativeAd) {
            }
        });
    }
}
