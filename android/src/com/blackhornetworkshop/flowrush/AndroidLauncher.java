package com.blackhornetworkshop.flowrush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.blackhornetworkshop.flowrush.controller.AdController;
import com.blackhornetworkshop.flowrush.model.FRConstants;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AndroidLauncher extends AndroidApplication {

    private ProgressDialog loadingDialog;
    private InterstitialAd interstitialAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        FRAndroidHelper.getInstance().logDebug("AndroidLauncher onCreate() method");
        super.onCreate(savedInstanceState);

        loadingDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        loadingDialog.setMessage("Loading...");
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        boolean isPlayServicesAvailable = FRPlayServices.isPlayServicesAvailable(this);

        FRAndroidHelper.getInstance().setup(this);

        if (isPlayServicesAvailable) {
            FRAndroidHelper.getInstance().logDebug("Play Services are available");
            FRPlayServices.getInstance().setup(this);
        } else {
            FRAndroidHelper.getInstance().logDebug("Play Services are not available");
        }

        if (isPlayServicesAvailable)
            FlowRush.getInstance().setup(FRAndroidHelper.getInstance(), FRPlayServices.getInstance());
        else FlowRush.getInstance().setup(FRAndroidHelper.getInstance());
        FRAndroidHelper.getInstance().logDebug("FlowRush is initialized");

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                AdController.setAdLoaded();
            }
            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        interstitialAd.loadAd(new AdRequest.Builder().build());

        initialize(FlowRush.getInstance(), getConfig());
    }

    private AndroidApplicationConfiguration getConfig() {
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        return config;
    }

    public void showAd() {
        interstitialAd.show();
    }

    @Override
    public void exit() {
        super.exit();
        FRAndroidHelper.getInstance().logDebug("App exit");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == FRConstants.RC_SIGN_IN) {
            FRPlayServices.getInstance().handleSignInResult(intent);
        } else if (requestCode == FRConstants.RC_LIST_SAVED_GAMES) {
            FRPlayServices.getInstance().handleSnapshotSelectResult(intent);
        }
    }

    void showLoadingLabel() {
        runOnUiThread(new Runnable() {
            public void run() {
                FRAndroidHelper.getInstance().logDebug("Display loading dialog");
                loadingDialog.show();
            }
        });
    }

    void hideLoadingDialog() {
        FRAndroidHelper.getInstance().logDebug("Dismiss loading dialog");
        loadingDialog.dismiss();
    }

}
