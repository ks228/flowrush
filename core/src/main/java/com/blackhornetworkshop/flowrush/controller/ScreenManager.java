package com.blackhornetworkshop.flowrush.controller;

import com.badlogic.gdx.Screen;
import com.blackhornetworkshop.flowrush.model.FRConstants;
import com.blackhornetworkshop.flowrush.view.FlowRush;
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
    }

    public static void setLogoFRScreen() {
        checkScreen(LogoScreen.getInstance());
        currentScreen = LOGO_FR;
        LogoScreen.setFRLogo();
    }

    // MENU SCREEN

    public static void setMenuMainScreen() {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MAIN_MENU;
        MenuScreen.getInstance().setMainMenuScreen();
    }

    public static void setMenuAuthorsScreen() {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MAIN_MENU_AUTHORS;
        MenuScreen.getInstance().setAuthorsScreen();
    }

    public static void setMenuSupportScreen() {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MAIN_MENU_SUPPORT_US;
        MenuScreen.getInstance().setSupportUsScreen();
    }

    public static void setMenuGoogleSignInScreen() {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MAIN_MENU_GOOGLE_PLAY;
        MenuScreen.getInstance().setSignInScreen();
    }

    public static void setMenuGoogleSignedScreen() {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MAIN_MENU_GOOGLE_PLAY;
        MenuScreen.getInstance().setSignedScreen();
    }

    public static void setMenuPackChoiceScreen() {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MAIN_MENU_PACK_CHOICE;
        MenuScreen.getInstance().setPackChoiceScreen();
    }

    public static void setMenuLevelChoiceScreen(int pack) {
        checkScreen(MenuScreen.getInstance());
        currentScreen = MAIN_MENU_LVL_CHOICE;
        MenuScreen.getInstance().setLevelChoiceScreen(pack);
    }

    // GAME SCREEN

    public static void setGameMainScreen() {
        checkScreen(GameScreen.getInstance());
        if(currentScreen == GAME_PAUSE || currentScreen == GAME_LVL_COMPLETE_PAUSE){
            GameScreen.getInstance().removePause();
        }
        currentScreen = GAME;
        GameScreen.getInstance().setGameMainScreen();
    }

    public static void setGamePauseScreen() {
        checkScreen(GameScreen.getInstance());
        if(currentScreen != GAME_LVL_COMPLETE) {
            currentScreen = GAME_PAUSE;
        }else {
            currentScreen = GAME_LVL_COMPLETE_PAUSE;
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
    }

    public static void setGamePackCompleteScreen() {
        checkScreen(GameScreen.getInstance());
        currentScreen = GAME_PACK_COMPLETE;
        GameScreen.getInstance().setPackCompleteScreen();
    }

    private static void checkScreen(FRScreen screen) {
        if(!screen.isActive()) FlowRush.getInstance().setScreen((Screen)screen);
    }
}
