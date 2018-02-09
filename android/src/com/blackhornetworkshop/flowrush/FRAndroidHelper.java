package com.blackhornetworkshop.flowrush;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.blackhornetworkshop.flowrush.model.AndroidHelper;
import com.blackhornetworkshop.flowrush.model.FRConstants;

public class FRAndroidHelper implements AndroidHelper {

    private static final FRAndroidHelper instance = new FRAndroidHelper();

    private AndroidLauncher app;
    private String fbUrl, twUrl, vkUrl, appPackageName;

    static FRAndroidHelper getInstance(){
        return instance;
    }

    private FRAndroidHelper(){}

    void setup(AndroidLauncher app){
        this.app = app;
        fbUrl = "https://www.facebook.com/blackhornet.workshop/";
        twUrl = "https://twitter.com/blackhornet_w";
        vkUrl = "https://vk.com/blackhornet.workshop";
        appPackageName = this.app.getPackageName();
    }

    @Override
    public void sendMail(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:blackhornet.w@gmail.com?subject=FlowRushFeedBack&body=");
        intent.setData(data);
        app.startActivity(intent);
    }
    @Override
    public void openFacebook(){
        try {
            app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + fbUrl)));
        } catch (Exception e) {
            app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fbUrl)));
        }
    }

    @Override
    public void openTwitter() {
        try {
            app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=blackhornet_w")));
        }catch (Exception e) {
            app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twUrl)));
        }
    }

    @Override
    public void openVK() {
        try {
            app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vkontakte://profile/-112124312")));
        } catch (Exception e) {
            app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(vkUrl)));
        }
    }

    @Override
    public void openPlaymarket() {
        try {
            app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (Exception e) {
            app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @Override
    public void logError(String msg, Throwable tr) {
        Log.e(FRConstants.TAG, msg, tr);
        showToast(msg);
    }

    @Override
    public void logDebug(String msg) {
        if(FRConstants.IS_DEBUG) {
            Log.d(FRConstants.TAG, msg);
        }
    }

    private void showToast(final String msg) {
        app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(app, msg, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
