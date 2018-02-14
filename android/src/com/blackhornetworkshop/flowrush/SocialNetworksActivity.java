package com.blackhornetworkshop.flowrush;

//Created by TScissors.

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;


public class SocialNetworksActivity extends Activity {

    public final String facebookAppUrl = "fb://facewebmodal/f?href=https://www.facebook.com/blackhornet.workshop/";
    public final String facebookUrl = "https://www.facebook.com/blackhornet.workshop/";

    public final String twitterPackage = "com.twitter.android";
    public final String twitterAppUrl = "twitter://user?screen_name=blackhornet_w";
    public final String twitterUrl = "https://twitter.com/blackhornet_w";

    public final String vkAppUrl = "vkontakte://profile/-112124312";
    public final String vkUrl = "https://vk.com/blackhornet.workshop";

    public final String youtubePackage = "com.google.android.youtube";
    public final String youtubeUrl = "https://www.youtube.com/channel/UCSC5wwBU1s50mqMJWyd8aqQ";

    public final String instagramPackage = "com.instagram.android";
    public final String instagramAppUrl = "https://www.instagram.com/_u/blackhornetworkshop/";
    public final String instagramUrl = "https://www.instagram.com/blackhornetworkshop/";

    public final String googlePlusPackage = "com.google.android.apps.plus";
    public final String googlePlusUrl = "https://plus.google.com/114101982808881364546";

    private AlphaAnimation buttonClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FRAndroidHelper.getInstance().logDebug("SocialNetworksActivity onCreate()");
        super.onCreate(savedInstanceState);

        buttonClick = new AlphaAnimation(1f, 0.8f);
        buttonClick.setDuration(300);
        buttonClick.setRepeatMode(Animation.REVERSE);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.social_networks_layout);
    }

    public void openFacebook(View view) {
        FRAndroidHelper.getInstance().logDebug("AndroidHelper openFacebook() method called");
        open(view, facebookAppUrl, facebookUrl);
    }

    public void openTwitter(View view) {
        FRAndroidHelper.getInstance().logDebug("AndroidHelper openTwitter() method called");
        open(view, twitterPackage, twitterAppUrl, twitterUrl);
    }

    public void openVK(View view) {
        FRAndroidHelper.getInstance().logDebug("AndroidHelper openVK() method called");
        open(view, vkAppUrl, vkUrl);
    }

    public void openYoutube(View view) {
        FRAndroidHelper.getInstance().logDebug("AndroidHelper openYoutube() method called");
        open(view, youtubePackage, youtubeUrl, youtubeUrl);
    }

    public void openInstagram(View view) {
        FRAndroidHelper.getInstance().logDebug("AndroidHelper openInstagram() method called");
        open(view, instagramPackage, instagramAppUrl, instagramUrl);
    }

    public void openGooglePlus(View view) {
        FRAndroidHelper.getInstance().logDebug("AndroidHelper openGooglePlus() method called");
        open(view, googlePlusPackage, googlePlusUrl, googlePlusUrl);
    }

    public void open(View view, String pckge, String appUrl, String url) {
        view.startAnimation(buttonClick);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage(pckge);
            intent.setData(Uri.parse(appUrl));
            startActivity(intent);
        } catch (Exception ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    public void open(View view, String appUrl, String url){
        view.startAnimation(buttonClick);
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)));
        } catch (Exception ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

}
