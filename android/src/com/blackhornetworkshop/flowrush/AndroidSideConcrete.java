package com.blackhornetworkshop.flowrush;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class AndroidSideConcrete implements AndroidSide {

    private final AndroidLauncher androidLauncher;
    private final String fbUrl, twUrl, vkUrl, appPackageName;

    AndroidSideConcrete(AndroidLauncher androidLauncher){
        this.androidLauncher = androidLauncher;
        fbUrl = "https://www.facebook.com/blackhornet.workshop/";
        twUrl = "https://twitter.com/blackhornet_w";
        vkUrl = "https://vk.com/blackhornet.workshop";
        appPackageName = androidLauncher.getPackageName();
    }

    @Override
    public void sendMail(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:blackhornet.w@gmail.com?subject=FlowRushFeedBack&body=");
        intent.setData(data);
        androidLauncher.startActivity(intent);
    }
    @Override
    public void openFacebook(){
        try {
            androidLauncher.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + fbUrl)));
        } catch (Exception e) {
            androidLauncher.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fbUrl)));
        }
    }

    @Override
    public void openTwitter() {
        try {
            androidLauncher.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=blackhornet_w")));
        }catch (Exception e) {
            androidLauncher.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twUrl)));
        }
    }

    @Override
    public void openVK() {
        try {
            androidLauncher.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vkontakte://profile/-112124312")));
        } catch (Exception e) {
            androidLauncher.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(vkUrl)));
        }
    }

    @Override
    public void openPlaymarket() {
        try {
            androidLauncher.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (Exception e) {
            androidLauncher.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @Override
    public boolean isDebug() {return FRLogger.isDebug();}

    @Override
    public void logError(String msg, Throwable tr) {
        FRLogger.logError(msg, tr);
        showToast(msg);
    }

    @Override
    public void logDebug(String msg) {
        FRLogger.logDebug(msg);
    }

    @Deprecated
    @Override
    public void showToast(final String message) {
        androidLauncher.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(androidLauncher, message, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
