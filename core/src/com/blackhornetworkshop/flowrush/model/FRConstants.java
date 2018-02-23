package com.blackhornetworkshop.flowrush.model;

import com.badlogic.gdx.Gdx;

public class FRConstants {

    public static final String TAG = "FlowRush";
    public static final boolean IS_DEBUG = false;

    public static final float SCREEN_HEIGHT = Gdx.graphics.getHeight();
    public static final float SCREEN_WIDTH = Gdx.graphics.getWidth();

    public static final float BUTTON_SIZE = SCREEN_HEIGHT / 12;
    public static final float HEX_HEIGHT = SCREEN_WIDTH / 5 * 0.86585367f;
    public static final float HEX_WIDTH = SCREEN_WIDTH / 5;

    static final float FONT_SIZE_X = BUTTON_SIZE * 0.43f;
    static final float FONT_SIZE_XL = BUTTON_SIZE * 0.5f;
    static final float FONT_SIZE_XXL = BUTTON_SIZE;

    public static final float MENU_BUTTON_WIDTH = SCREEN_WIDTH * 0.7f;
    public static final float MENU_BUTTON_X = (SCREEN_WIDTH - MENU_BUTTON_WIDTH) / 2;

    public static final float PLAY_BUTTON_WIDTH = SCREEN_WIDTH * 0.9f;
    public static final float PLAY_BUTTON_HEIGHT = BUTTON_SIZE * 1.3f;
    public static final float PLAY_BUTTON_X = (SCREEN_WIDTH - PLAY_BUTTON_WIDTH) / 2;
    public static final float PLAY_BUTTON_Y = (SCREEN_HEIGHT - BUTTON_SIZE * 4 - PLAY_BUTTON_HEIGHT);

    public static final float LEVELS_BUTTON_Y = (SCREEN_HEIGHT - BUTTON_SIZE * 4 - BUTTON_SIZE * 1.3f - BUTTON_SIZE * 1.1f);

    public final static float MENU_LABEL_Y = (SCREEN_HEIGHT * 0.98f - BUTTON_SIZE);

    public static final float MENU_PACK_COMPLETE_BUTTON_WIDTH = SCREEN_WIDTH * 0.4f;
    public static final float MENU_PACK_COMPLETE_BUTTON_HEIGHT = BUTTON_SIZE * 0.8f;
    public static final float MENU_PACK_COMPLETE_BUTTON_X = (SCREEN_WIDTH - MENU_PACK_COMPLETE_BUTTON_WIDTH) / 2;
    public static final float MENU_PACK_COMPLETE_BUTTON_Y = (SCREEN_HEIGHT - SCREEN_HEIGHT * 0.35f) / 4 * 3 - BUTTON_SIZE * 2.9f;

    public static final float RATE_DIALOG_BUTTON_WIDTH = SCREEN_WIDTH * 0.45f;
    public static final float RATE_DIALOG_BUTTON_HEIGHT = BUTTON_SIZE * 0.8f;
    public static final float RATE_US_DIALOG_BUTTON_RIGHT_X = (SCREEN_WIDTH - RATE_DIALOG_BUTTON_WIDTH * 2) / 3 * 2 + RATE_DIALOG_BUTTON_WIDTH;
    public static final float RATE_US_DIALOG_BUTTON_LEFT_X = (SCREEN_WIDTH - RATE_DIALOG_BUTTON_WIDTH * 2) / 3;
    public static final float RATE_US_DIALOG_BUTTON_Y = (BUTTON_SIZE * 1.45f - FONT_SIZE_X - BUTTON_SIZE * 0.8f) / 2;

    public static final float NEXT_PACK_BUTTON_WIDTH = SCREEN_WIDTH * 0.5f;
    public static final float NEXT_PACK_BUTTON_X = (SCREEN_WIDTH - NEXT_PACK_BUTTON_WIDTH) / 2;
    public static final float NEXT_PACK_BUTTON_Y = (SCREEN_HEIGHT - SCREEN_HEIGHT*0.35f) / 4 * 3 - BUTTON_SIZE* 2;

    public static final float EXIT_BUTTON_Y = (SCREEN_HEIGHT - BUTTON_SIZE * 4 - BUTTON_SIZE * 1.3f - BUTTON_SIZE * 2.2f);

    public static final float MENU_BUTTON_Y_1 = (BUTTON_SIZE / 2 + SCREEN_HEIGHT * 0.02f) + BUTTON_SIZE / 2 + ((((SCREEN_HEIGHT * 0.98f - BUTTON_SIZE)) +
            (BUTTON_SIZE) / 2) - (BUTTON_SIZE / 2 + SCREEN_HEIGHT * 0.02f) - BUTTON_SIZE) * 0.05f / 2 + BUTTON_SIZE * 0.3f;

    public static final float MENU_BUTTON_Y_2 = (BUTTON_SIZE / 2 + SCREEN_HEIGHT * 0.02f) + BUTTON_SIZE / 2 + ((((SCREEN_HEIGHT * 0.98f - BUTTON_SIZE)) +
            (BUTTON_SIZE) / 2) - (BUTTON_SIZE / 2 + SCREEN_HEIGHT * 0.02f) - BUTTON_SIZE) * 0.05f / 2 + BUTTON_SIZE * 1.4f;

    public static final float MENU_BUTTON_Y_3 = (BUTTON_SIZE / 2 + SCREEN_HEIGHT * 0.02f) + BUTTON_SIZE / 2 + ((((SCREEN_HEIGHT * 0.98f - BUTTON_SIZE)) +
            (BUTTON_SIZE) / 2) - (BUTTON_SIZE / 2 + SCREEN_HEIGHT * 0.02f) - BUTTON_SIZE) * 0.05f / 2 + BUTTON_SIZE * 2.5f;

    public enum ScreenType {
        GAME_MAIN,
        GAME_LVL_COMPLETE,
        GAME_LVL_COMPLETE_PAUSE,
        GAME_PACK_COMPLETE,
        GAME_PAUSE,
        LOGO_BHW,
        LOGO_FR,
        MENU_LVL_CHOICE,
        MENU_MAIN,
        MENU_AUTHORS,
        MENU_PACK_CHOICE,
        MENU_SUPPORT_US,
        MENU_GOOGLE_PLAY
    }

    // Pack choice screen

    public final static float PACK_GROUP_TOP_MARGIN = SCREEN_HEIGHT * 0.98f - BUTTON_SIZE;
    public final static float PACK_GROUP_BOTTOM_MARGIN = BUTTON_SIZE + SCREEN_HEIGHT * 0.02f;
    public final static float PACK_BUTTON_HEIGHT = (PACK_GROUP_TOP_MARGIN - PACK_GROUP_BOTTOM_MARGIN) / 6;

    // Level choice screen

    public final static float LEVEL_NUMBER_GROUP_TOP_MARGIN = (SCREEN_HEIGHT*0.98f- BUTTON_SIZE);
    private final static float LEVEL_NUMBER_GROUP_BOTTOM_MARGIN = (BUTTON_SIZE)+SCREEN_HEIGHT*0.02f;
    public final static float LEVEL_NUMBER_SIZE = (LEVEL_NUMBER_GROUP_TOP_MARGIN - LEVEL_NUMBER_GROUP_BOTTOM_MARGIN)/12;

    private final static float LEVEL_NUMBER_Y_PADDING = ((LEVEL_NUMBER_GROUP_TOP_MARGIN - LEVEL_NUMBER_GROUP_BOTTOM_MARGIN)- LEVEL_NUMBER_SIZE *10)/11;
    private final static float LEVEL_NUMBER_X_PADDING = (SCREEN_WIDTH*0.9f- LEVEL_NUMBER_SIZE *5)/6;
    public final static float LEVEL_NUMBER_PADDING = LEVEL_NUMBER_X_PADDING > LEVEL_NUMBER_Y_PADDING ? LEVEL_NUMBER_Y_PADDING : LEVEL_NUMBER_X_PADDING;
    public final static float LEVEL_NUMBER_GROUP_MARGIN_LEFT = (SCREEN_WIDTH-SCREEN_WIDTH*0.9f)/2+(SCREEN_WIDTH*0.9f- LEVEL_NUMBER_PADDING *4- LEVEL_NUMBER_SIZE *5)/2;

    // Android

    public final static int RC_SIGN_IN = 9001;
    public final static int RC_LIST_SAVED_GAMES = 9002;
    public final static int RC_ACHIEVEMENT_UI = 9003;

    // Texts
    public final static String AUTHORS_MSG= "development\nTIMUR SCISSORS\n\ndesign\nANN KOVALSKI\n\nmusic\nERIC HOPTON";
    //public final static String SUPPORT_MSG= "Please support our indie development team. Join us on social media and share our game with your friends.\n\nAlso you can remove ads and in this way support our projects.";
    public final static String SUPPORT_MSG= "Please support our indie development team. Join us on social media and share our game with your friends.";
    public final static String SIGN_IN_MSG= "By signing in your Google Play Games account, you can: save and load your in-game progress, earn in-game achievements, get experience for your Google Play Games account";
    public final static String SIGNED_MSG= "Welcome!\nHere you can see your\nFlow Rush\n achievements and manage your saved games";
    public final static String QUESTION_1 = "ENJOYING  FLOW RUSH?";
    public final static String QUESTION_2 = "RATE THE GAME, PLEASE";
    public final static String QUESTION_3 = "SEND US FEEDBACK, PLEASE";
    public final static String ANSWER_YES_1 = "YES!";
    public final static String ANSWER_YES_2 = "YES, SURE";
    public final static String ANSWER_YES_3 = "OK";
    public final static String ANSWER_NO_1 = "NOT SURE";
    public final static String ANSWER_NO_2 = "NO, THANKS";

    // Sprites

    public final static String BACKGROUND_HEX = "backhex";
    public final static String BACKGROUND_HEX_WITH_SOURCE = "backhexS";
    public final static String BACKGROUND_HEX_TOUCHED = "backhex_touched";
    public final static String BACKGROUND_HEX_TOUCHED_WITH_SOURCE = "backhex_touchedS";

    public final static String SOURCE_ICON = "iconMP";
    public final static String DOVE_OFF_ICON = "iconD";
    public final static String DOVE_ON_ICON = "iconDP";
    public final static String POINT_OFF_ICON = "iconE";
    public final static String POINT_ON_ICON = "iconEP";
    public final static String POINT_WHITE_ICON = "iconEW";

    public final static String HEX = "hex";

    public final static String CLOSE_ICON = "close_icon";
    public final static String DAY_NIGHT_ICON = "daynight";
    public final static String SOUND_OFF_ICON = "soundOff_icon";
    public final static String SOUND_ON_ICON = "soundOn_icon";
    public final static String LOCK_ICON = "lock";
    public final static String PAUSE_ICON = "pause_icon";
    public final static String BACK_BUTTON_ICON = "back_icon";
    public final static String RESTART_ICON = "restart_icon";
    public final static String MENU_ICON = "mmenu_icon";
    public final static String AUTHORS_ICON = "authors_icon";
    public final static String NEXT_ICON = "next_icon";
    public final static String ADS_ICON = "ads_icon";
    public final static String CONTROLLER_ICON = "controller";

    public final static String BIG_HEX_LIGHT = "bighex_light";
    public final static String BIG_HEX_DARK = "bighex_dark";
    public final static String BACKGROUND_ANIMATION = "animbackhex";

    final static String TRIANGLE_BACKGROUND = "back_white";
    final static String QUADRANT_BACKGROUND = "q_circle";

    public final static String LABEL_STYLE_DEFAULT = "default";
    public final static String LABEL_STYLE_GREYFONT = "greyfont";
    public final static String LABEL_STYLE_ALPHAWHITE = "alphawhite";
    public final static String LABEL_STYLE_DARKBLUE = "darkblue";
    public final static String LABEL_STYLE_DARKBLUESMALL = "darkbluesmall";

    public final static String TEXT_BUTTON_STYLE_DEFAULT = "default";
    public final static String TEXT_BUTTON_STYLE_LIGHTBLUE = "lightblue";
    public final static String TEXT_BUTTON_STYLE_DARKBLUE = "darkblue";
    public final static String TEXT_BUTTON_STYLE_WHITE = "white";
    public final static String TEXT_BUTTON_STYLE_PLAYBUTTON = "playbutton";
    public final static String TEXT_BUTTON_STYLE_BORDERSMALL = "bordersmall";
    public final static String TEXT_BUTTON_STYLE_WHITESMALL = "whitesmall";
}

