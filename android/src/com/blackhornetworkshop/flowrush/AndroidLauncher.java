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
import com.badlogic.gdx.utils.async.AsyncExecutor;

public class AndroidLauncher extends AndroidApplication {

    boolean isPlayServicesAvailable;

    AndroidSide androidSide;
    FRPlayServices playServices;

    ProgressDialog loadingDialog;

    FlowRush flowRush;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        FRLogger.logDebug("AndroidLauncher onCreate() method");
        super.onCreate(savedInstanceState);

        isPlayServicesAvailable = FRPlayServices.isPlayServicesAvailable(this);

        loadingDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);

        //Prepare app
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        //---------------------------
        AsyncExecutor asyncExecutor = new AsyncExecutor(20);

        //FlowRush initialization
        androidSide = new AndroidSideConcrete(this);
        if(isPlayServicesAvailable) {
            FRLogger.logDebug("Play Services are available");
            FRPlayServices.initialize(this);
            playServices = FRPlayServices.getInstance();
            FlowRush.initialize(androidSide, playServices ,asyncExecutor);
        }else {
            FRLogger.logDebug("Play Services are not available");
            FlowRush.initialize(androidSide, asyncExecutor);
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

    void handleException(Exception exception, String details){
        androidSide.handleException(exception, details);
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

    void showLoadingDialog(final String msg){
        runOnUiThread(new Runnable() {
            public void run() {
                FRLogger.logDebug("Display loading dialog");
                loadingDialog.setMessage(msg);
                loadingDialog.show();
            }
        });
    }
    void hideLoadingDialog(){
        FRLogger.logDebug("Dismiss loading dialog");
        loadingDialog.dismiss();
    }

}
