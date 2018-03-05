package com.blackhornetworkshop.flowrush.controller;

import com.badlogic.gdx.Screen;
import com.blackhornetworkshop.flowrush.model.FRConstants;
import com.blackhornetworkshop.flowrush.model.FlowRush;
import com.blackhornetworkshop.flowrush.view.screens.FRScreen;
import com.blackhornetworkshop.flowrush.view.screens.GameScreen;
import com.blackhornetworkshop.flowrush.view.screens.LogoScreen;
import com.blackhornetworkshop.flowrush.view.screens.MenuScreen;

import static com.blackhornetworkshop.flowrush.model.FRConstants.ScreenType.*;

public class ScreenManager {

    private static FRConstants.ScreenType currentScreen;

    public static FRConstants.ScreenType getCurrentScreen() {
        return currentScreen;
    }

    // LOGO SCREEN

    public static void setLogoBHWScreen() {
        checkScreen(LogoScreen.getInstance());
        currentScreen = LOGO_BHW;
        LogoScreen.setBHWLogo();
        FlowRush.logDebug("BHW LOGO screen");
    }

    public static void setLogoFRScreen() {
        checkScreen(LogoScreen.getInstance());
        currentScreen = LOGO_FR;
        LogoScreen.setFRLogo();
        FlowRush.logDebug("FR LOGO screen");
    }

    // MENU SCREEN

    public static void setMenuMainScreen() {
        checkScreen(MenuScreen.getInstance());
        if(isShowAd(currentScreen)) {
            AdController.showAd();
        }
        currentScreen = MENU_MAIN;
        MenuScreen.getInstance().setMainMenuScreen();
        FlowRush.logDebug("MAIN MENU screen");
    }

    public static void setMenuAuthorsScreen() {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MENU_AUTHORS;
        MenuScreen.getInstance().setAuthorsScreen();
        FlowRush.logDebug("MENU AUTHORS screen");
    }

    public static void setMenuSupportScreen() {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MENU_SUPPORT_US;
        MenuScreen.getInstance().setSupportUsScreen();
        FlowRush.logDebug("MENU SUPPORT US screen");
    }

    public static void setMenuGoogleSignInScreen() {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MENU_GOOGLE_PLAY;
        MenuScreen.getInstance().setSignInScreen();
        FlowRush.logDebug("MENU GOOGLE PLAY screen (sign in)");
    }

    public static void setMenuGoogleSignedScreen() {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MENU_GOOGLE_PLAY;
        MenuScreen.getInstance().setSignedScreen();
        FlowRush.logDebug("MENU GOOGLE PLAY screen (signed)");
    }

    public static void setMenuPackChoiceScreen() {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MENU_PACK_CHOICE;
        MenuScreen.getInstance().setPackChoiceScreen();
        FlowRush.logDebug("MENU PACK CHOICE screen");
    }

    public static void setMenuLevelChoiceScreen(int pack) {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MENU_LVL_CHOICE;
        MenuScreen.getInstance().setLevelChoiceScreen(pack);
        FlowRush.logDebug("MENU LVL CHOICE screen");
    }

    // GAME_MAIN SCREEN

    public static void setGameMainScreen() {
        checkScreen(GameScreen.getInstance());
        if(isShowAd(currentScreen)){
            AdController.showAd();
        }
        if(currentScreen == GAME_PAUSE || currentScreen == GAME_LVL_COMPLETE_PAUSE){
            GameScreen.getInstance().removePauseAndRestoreTouch();
        }else if(currentScreen == GAME_PACK_COMPLETE){
            GameScreen.hidePackCompleteGroup();
        }
        currentScreen = GAME_MAIN;
        GameScreen.getInstance().setGameMainScreen();
        FlowRush.logDebug("GAME MAIN screen");
    }

    public static void setGamePauseScreen() {
        checkScreen(GameScreen.getInstance());
        if(currentScreen != GAME_LVL_COMPLETE) {
            currentScreen = GAME_PAUSE;
            FlowRush.logDebug("GAME PAUSE screen");
        }else {
            currentScreen = GAME_LVL_COMPLETE_PAUSE;
            FlowRush.logDebug("GAME LVL COMPLETE PAUSE screen");
        }
        GameScreen.getInstance().setPauseScreen();
    }

    public static void setGameLevelCompleteScreen() {
        checkScreen(GameScreen.getInstance());
        if(currentScreen == GAME_LVL_COMPLETE_PAUSE){
            GameScreen.getInstance().removePause();
        }
        currentScreen = GAME_LVL_COMPLETE;
        GameScreen.getInstance().setGameLevelCompleteScreen();
        FlowRush.logDebug("GAME LVL COMPLETE screen");
    }

    public static void setGamePackCompleteScreen() {
        checkScreen(GameScreen.getInstance());
        currentScreen = GAME_PACK_COMPLETE;
        GameScreen.getInstance().setPackCompleteScreen();
        FlowRush.logDebug("GAME PACK COMPLETE screen");
    }

    private static void checkScreen(FRScreen screen) {
        if(!screen.isActive()) FlowRush.getInstance().setScreen((Screen)screen);
    }

    private static boolean isShowAd(FRConstants.ScreenType screenType){
        if(!FlowRush.getPreferences().isAdsRemoved()) {
            boolean showAd = (screenType == GAME_LVL_COMPLETE || screenType == GAME_LVL_COMPLETE_PAUSE) &&
                    AdController.isShowAdOnNextScreen() && AdController.isAdLoaded();
            if(showAd){
                FlowRush.logDebug("Screen manager check isShowAd() = true");
            }else{
                FlowRush.logDebug("Screen manager check isShowAd() = false");
                FlowRush.logDebug("ScreenType: "+screenType+" Is ad loaded: "+AdController.isAdLoaded()
                        +" Is show ad on next screen: "+AdController.isShowAdOnNextScreen());
            }
            return showAd;
        }else{
            FlowRush.logDebug("Don't show ad, because ads removed");
            return false;
        }
    }
}
