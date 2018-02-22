package com.blackhornetworkshop.flowrush;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.blackhornetworkshop.flowrush.controller.AdController;
import com.blackhornetworkshop.flowrush.model.FRConstants;
import com.blackhornetworkshop.flowrush.model.FlowRush;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AndroidLauncher extends AndroidApplication {

    private ProgressDialog loadingDialog;
    private InterstitialAd interstitialAd;
    private ConnectivityManager cm;
    private BroadcastReceiver networkStateReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FRAndroidHelper.getInstance().logDebug("AndroidLauncher onCreate() method");

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

        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        networkStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                FRAndroidHelper.getInstance().logDebug("Connectivity changed");
                checkIsAdLoadAvailable();
            }
        };

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                FRAndroidHelper.getInstance().logDebug("Ad is loaded");
                AdController.setAdLoaded();
            }

            @Override
            public void onAdClosed() {
                checkIsAdLoadAvailable();
            }

        });
        initialize(FlowRush.getInstance(), getConfig());
    }

    public void checkIsAdLoadAvailable() {
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected() && !interstitialAd.isLoaded()) {
            FRAndroidHelper.getInstance().logDebug("Loading ad");
            interstitialAd.loadAd(new AdRequest.Builder().build());
        } else if (interstitialAd.isLoaded()) {
            FRAndroidHelper.getInstance().logDebug("Ad is already loaded");
        } else {
            FRAndroidHelper.getInstance().logDebug("Network is not available");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        FRAndroidHelper.getInstance().logDebug("Broadcast receiver registered");
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        FRAndroidHelper.getInstance().logDebug("Broadcast receiver unregistered");
        unregisterReceiver(networkStateReceiver);
    }

    public boolean isInternetConnected() {
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }


    private AndroidApplicationConfiguration getConfig() {
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        return config;
    }

    public void showAd() {
        if(isInternetConnected()) {
            FRAndroidHelper.getInstance().logDebug("Show ad");
            interstitialAd.show();
        }else{
            FRAndroidHelper.getInstance().logDebug("Don't show ad, because internet is off");
        }
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

    void showLoadingDialog() {
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

    boolean isPlayGamesPackageInstalled(){
        boolean app_installed;
        try {
            getPackageManager().getPackageInfo("com.google.android.play.games",PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        FRAndroidHelper.getInstance().logDebug("Play games package is installed: "+app_installed);
        return app_installed;
    }
}
