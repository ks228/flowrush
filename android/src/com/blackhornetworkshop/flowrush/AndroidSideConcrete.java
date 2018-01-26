package com.blackhornetworkshop.flowrush;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AndroidSideConcrete implements AndroidSide {
    Context appContext;
    String fbUrl, twUrl, vkUrl, appPackageName;

    AndroidSideConcrete(Context appContext){
        this.appContext = appContext;
        fbUrl = "https://www.facebook.com/blackhornet.workshop/";
        twUrl = "https://twitter.com/blackhornet_w";
        vkUrl = "https://vk.com/blackhornet.workshop";
        appPackageName = appContext.getPackageName();
    }

    @Override
    public void sendMail(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:blackhornet.w@gmail.com?subject=FlowRushFeedBack&body=");
        intent.setData(data);
        appContext.startActivity(intent);
    }
    @Override
    public void openFacebook(){
        try {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + fbUrl)));
        } catch (Exception e) {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(fbUrl)));
        }
    }

    @Override
    public void openTwitter() {
        try {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=blackhornet_w")));
        }catch (Exception e) {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twUrl)));
        }
    }

    @Override
    public void openVK() {
        try {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vkontakte://profile/-112124312")));
        } catch (Exception e) {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(vkUrl)));
        }
    }

    @Override
    public void openPlaymarket() {
        try {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (Exception e) {
            appContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
