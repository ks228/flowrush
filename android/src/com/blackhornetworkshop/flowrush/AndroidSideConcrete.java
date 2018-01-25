package com.blackhornetworkshop.flowrush;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class AndroidSideConcrete implements AndroidSide {
    Context appContext;

    AndroidSideConcrete(Context appContext){
        this.appContext=appContext;}



    @Override
    public void actionSend(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:blackhornet.w@gmail.com?subject=" + "FlowRushFeedBack" + "&body=" + "");
        intent.setData(data);
        appContext.startActivity(intent);
    }
    @Override
    public void openFacebook(){
        try {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/blackhornet.workshop/")));
        } catch (Exception e) {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/blackhornet.workshop/")));
        }
    }

    @Override
    public void openTwitter() {
        try {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=blackhornet_w")));
        }catch (Exception e) {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/blackhornet_w")));
        }
    }

    @Override
    public void openVK() {
        try {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vkontakte://profile/-112124312")));
        } catch (Exception e) {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/blackhornet.workshop")));
        }
    }

    @Override
    public void openPlaymarket() {
        final String appPackageName = appContext.getPackageName(); // getPackageName() from Context or Activity object
        try {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (Exception e) {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
