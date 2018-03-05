package com.blackhornetworkshop.flowrush;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager;

import com.android.vending.billing.IInAppBillingService;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.blackhornetworkshop.flowrush.controller.AdController;
import com.blackhornetworkshop.flowrush.model.FlowRush;
import com.blackhornetworkshop.flowrush.model.ex.FlowRushException;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication {

    private ProgressDialog loadingDialog;
    private InterstitialAd interstitialAd;
    private ConnectivityManager cm;
    private BroadcastReceiver networkStateReceiver;

    private boolean isBroadcastReceiverInitialized;

    private IInAppBillingService inAppBillingService;
    private ServiceConnection inAppServiceConnection;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FRAndroidHelper.getInstance().logDebug("AndroidLauncher onCreate() method");

        isBroadcastReceiverInitialized = false;

        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

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
            FlowRush.getInstance().setup(FRAndroidHelper.getInstance(), FRPlayServices.getInstance());
        } else {
            FRAndroidHelper.getInstance().logDebug("Play Services are not available");
            FlowRush.getInstance().setup(FRAndroidHelper.getInstance());
        }

        initialize(FlowRush.getInstance(), getConfig());
    }

    public void startPurchase(){
        try {
            FRAndroidHelper.getInstance().logDebug("Purchase flow started");
            Bundle buyIntentBundle = inAppBillingService.getBuyIntent(3, getPackageName(), getString(R.string.flowrush_remove_ads), "inapp", "");
            PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

            startIntentSenderForResult(pendingIntent.getIntentSender(), AndroidConstants.RC_PURCHASE, new Intent(), 0, 0, 0);
        }catch (Exception ex){
            FRAndroidHelper.getInstance().logError("In-app purchase error", ex);
        }
    }

    public void initializeInAppBillingService(){
        inAppServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                inAppBillingService = null;
                FRAndroidHelper.getInstance().logDebug("In-app billing service = null");
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                inAppBillingService = IInAppBillingService.Stub.asInterface(service);
                if(!isRemoveAdsPurchased()) {
                    initializeAds();
                    initializeBroadcastReceiver();
                }else{
                    FlowRush.getPreferences().setAdsIsRemoved(true);
                    FlowRush.logDebug("Skip ads initialization, because ads removed");
                }
            }
        };

        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, inAppServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (inAppServiceConnection != null) {
            unbindService(inAppServiceConnection);
            FRAndroidHelper.getInstance().logDebug("Unbind in-app billing service connection");
        }
    }

    public void initializeBroadcastReceiver(){
        FRAndroidHelper.getInstance().logDebug("BroadcastReceiver initialization");
        networkStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                FRAndroidHelper.getInstance().logDebug("Connectivity changed");
                if (!FlowRush.getPreferences().isAdsRemoved()) {
                    loadAdIfNotLoaded();
                } else {
                    FRAndroidHelper.getInstance().logDebug("Ads removed, nothing to load");
                }
            }
        };
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        isBroadcastReceiverInitialized = true;
    }

    public void initializeAds(){
        FRAndroidHelper.getInstance().logDebug("Ads initialization");
        MobileAds.initialize(this, getString(R.string.app_ads_key));

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.ads_key));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                FRAndroidHelper.getInstance().logDebug("Ad is loaded");
                AdController.setIsAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                FRAndroidHelper.getInstance().logDebug("Failed to load ad");
            }

            @Override
            public void onAdClosed() {
                FRAndroidHelper.getInstance().logDebug("Ad is closed");
                loadAdIfNotLoaded();
            }
        });
    }

    public void loadAdIfNotLoaded() {
        if (!interstitialAd.isLoading()) {
            if (isInternetConnected()) {
                FRAndroidHelper.getInstance().logDebug("Network is available");
                boolean isAdLoaded = interstitialAd.isLoaded();
                if (!isAdLoaded) {
                    FRAndroidHelper.getInstance().logDebug("Loading ad");
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                } else {
                    FRAndroidHelper.getInstance().logDebug("Ad is already loaded");
                }
            } else {
                FRAndroidHelper.getInstance().logDebug("Network is not available");
            }
        } else {
            FlowRush.logDebug("Can't start ad loading, because ad loading in already progress");
        }
    }

    public boolean isAdLoaded(){
        return interstitialAd.isLoaded();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isBroadcastReceiverInitialized) {
            FRAndroidHelper.getInstance().logDebug("Broadcast receiver registered");
            registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isBroadcastReceiverInitialized) {
            FRAndroidHelper.getInstance().logDebug("Broadcast receiver unregistered");
            unregisterReceiver(networkStateReceiver);
        }
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

        if (requestCode == AndroidConstants.RC_SIGN_IN) {
            FRPlayServices.getInstance().handleSignInResult(intent);
        } else if (requestCode == AndroidConstants.RC_LIST_SAVED_GAMES) {
            FRPlayServices.getInstance().handleSnapshotSelectResult(intent);
        } else  if (requestCode == AndroidConstants.RC_PURCHASE) {
            String purchaseData = intent.getStringExtra("INAPP_PURCHASE_DATA");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    if(sku.equals(getString(R.string.flowrush_remove_ads))){
                        FRAndroidHelper.getInstance().logDebug("Remove ads purchase was bought");
                        FlowRush.getPreferences().setAdsIsRemoved(true);
                        UIPool.getRemoveAdsButton().setVisible(false);
                        FlowRush.getAndroidHelper().showToast("Thank you! Ads are removed");
                    }
                }
                catch (JSONException e) {
                    FRAndroidHelper.getInstance().logDebug("Failed to parse purchase data.");
                    FlowRush.getAndroidHelper().showToast("Purchase failure");
                    e.printStackTrace();
                }
            }else{
                FlowRush.getAndroidHelper().showToast("Purchase failure");
            }
        }
    }
    public boolean isRemoveAdsPurchased(){
        try {
            Bundle ownedItems = inAppBillingService.getPurchases(3, getPackageName(), "inapp", null);
            int response = ownedItems.getInt("RESPONSE_CODE");
            if (response == AndroidConstants.BILLING_RESPONSE_RESULT_OK) {
                ArrayList<String> ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");

                boolean removeAdsPurchased = false;

                for (int i = 0; i < ownedSkus.size(); ++i) {
                    String sku = ownedSkus.get(i);
                    if(sku.equals(getString(R.string.flowrush_remove_ads))) removeAdsPurchased = true;
                }

                return removeAdsPurchased;
            }else{
                FRAndroidHelper.getInstance().logError("Error getting info about purchases", new FlowRushException("Response code: "+response));
                return false;
            }
        }catch (Exception ex){
            FRAndroidHelper.getInstance().logError("Error getting info about purchases", ex);
            return false;
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

    boolean isPlayGamesPackageInstalled() {
        boolean app_installed;
        try {
            getPackageManager().getPackageInfo("com.google.android.play.games", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        FRAndroidHelper.getInstance().logDebug("Play games package is installed: " + app_installed);
        return app_installed;
    }
}
