package com.blackhornetworkshop.flowrush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

    private AndroidSide androidSide;
    private FRPlayServices playServices;

    private ProgressDialog loadingDialog;

    private FlowRush flowRush;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        FRLogger.logDebug("AndroidLauncher onCreate() method");
        super.onCreate(savedInstanceState);

        boolean isPlayServicesAvailable = FRPlayServices.isPlayServicesAvailable(this);

        loadingDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        loadingDialog.setMessage("Loading...");
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);

        //Prepare app
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        //FlowRush initialization
        androidSide = new AndroidSideConcrete(this);
        if(isPlayServicesAvailable) {
            FRLogger.logDebug("Play Services are available");
            FRPlayServices.initialize(this);
            playServices = FRPlayServices.getInstance();
            FlowRush.initialize(androidSide, playServices);
        }else {
            FRLogger.logDebug("Play Services are not available");
            FlowRush.initialize(androidSide);
        }
        flowRush = FlowRush.getInstance();

        //Layouts
        RelativeLayout mainLayout = (RelativeLayout)getLayoutInflater().inflate(R.layout.main_layout, null);
        View libGDXLayout = initializeForView(flowRush, getConfig());
        mainLayout.addView(libGDXLayout);

        setContentView(mainLayout);
    }

    private AndroidApplicationConfiguration getConfig() {
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        return config;
    }

    AndroidSide getAndroidSide(){
        return androidSide;
    }

    FlowRush getGame(){
        return flowRush;
    }

    @Override
    public void exit() {
        super.exit();
        FRLogger.logDebug("App exit");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == FRPlayServices.RC_SIGN_IN) {
            playServices.handleSignInResult(intent);
        }else if(requestCode == FRPlayServices.RC_LIST_SAVED_GAMES) {
            playServices.handleSnapshotSelectResult(intent);
        }
    }

    void showLoadingDialog(){
        runOnUiThread(new Runnable() {
            public void run() {
                FRLogger.logDebug("Display loading dialog");
                loadingDialog.show();
            }
        });
    }
    void hideLoadingDialog(){
        FRLogger.logDebug("Dismiss loading dialog");
        loadingDialog.dismiss();
    }

}
