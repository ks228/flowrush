package com.blackhornetworkshop.flowrush;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.blackhornetworkshop.flowrush.model.AndroidHelper;

public class FRAndroidHelper implements AndroidHelper {

    private static final FRAndroidHelper instance = new FRAndroidHelper();

    private AndroidLauncher app;
    private final static String websiteUrl = "https://blackhor.net";

    static FRAndroidHelper getInstance() {
        return instance;
    }

    private FRAndroidHelper() {
    }

    void setup(AndroidLauncher app) {
        this.app = app;
    }

    @Override
    public void openWebsite() {
        logDebug("AndroidHelper openWebsite() method called");
        app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl)));
    }

    @Override
    public void sendMail() {
        logDebug("AndroidHelper sendMail() method called");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:blackhornet.w@gmail.com?subject=FlowRushFeedBack&body=");
        intent.setData(data);
        app.startActivity(intent);
    }


    @Override
    public void openPlayMarket() {
        logDebug("AndroidHelper openPlayMarket() method called");
        try {
            app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + app.getPackageName())));
        } catch (Exception e) {
            app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + app.getPackageName())));
        }
    }

    @Override
    public void showAd() {
        app.runOnUiThread(new Runnable() {
            public void run() {
                FRAndroidHelper.getInstance().logDebug("AndroidHelper showAd() method called");
                app.showAd();
            }
        });
    }

    @Override
    public void logError(String msg, Throwable tr) {
        Log.e(AndroidConstants.TAG, msg, tr);
        showToast(msg);
    }

    @Override
    public void logDebug(String msg) {
        if (AndroidConstants.IS_DEBUG) {
            Log.d(AndroidConstants.TAG, msg);
        }
    }

    @Override
    public void showToast(final String msg) {
        app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(app, msg, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void openSocialNetworksActivity() {
        Intent intent = new Intent(app, SocialNetworksActivity.class);
        app.startActivity(intent);
    }

    @Override
    public boolean isInternetConnected() {
        return app.isInternetConnected();
    }

    @Override
    public void initializeAds() {
        app.initializeAds();
        app.initializeBroadcastReceiver();
    }

    @Override
    public void startPurchase() {
        app.startPurchase();
    }

    @Override
    public boolean isRemoveAdsPurchased() {
        return app.isRemoveAdsPurchased();
    }
}
