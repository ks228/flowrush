package com.blackhornetworkshop.flowrush.model;

import com.badlogic.gdx.*;

public class FRConstants {

    public static final String TAG = "FlowRush";
    public static final boolean IS_DEBUG = true;

    public static final float SCREEN_HEIGHT = Gdx.graphics.getHeight();
    public static final float SCREEN_WIDTH = Gdx.graphics.getWidth();

    public static final float BUTTON_SIZE = SCREEN_HEIGHT / 12;
    public static final float HEX_HEIGHT = SCREEN_WIDTH / 5 * 0.86585367f;
    public static final float HEX_WIDTH = SCREEN_WIDTH / 5;

    public static final float FONT_SIZE_X = BUTTON_SIZE * 0.43f;
    public static final float FONT_SIZE_XL = BUTTON_SIZE * 0.5f;
    public static final float FONT_SIZE_XXL = BUTTON_SIZE;

    public static final float MENU_BUTTON_WIDTH = SCREEN_WIDTH * 0.7f;
    public static final float MENU_BUTTON_X = (SCREEN_WIDTH - MENU_BUTTON_WIDTH) / 2;

    public static final float PLAY_BUTTON_WIDTH = SCREEN_WIDTH * 0.9f;
    public static final float PLAY_BUTTON_HEIGHT = BUTTON_SIZE * 1.3f;
    public static final float PLAY_BUTTON_X = (SCREEN_WIDTH - PLAY_BUTTON_WIDTH) / 2;
    public static final float PLAY_BUTTON_Y = (SCREEN_HEIGHT - BUTTON_SIZE * 4 - PLAY_BUTTON_HEIGHT);

    public static final float LEVELS_BUTTON_Y = (SCREEN_HEIGHT - BUTTON_SIZE * 4 - BUTTON_SIZE * 1.3f - BUTTON_SIZE * 1.1f);

    public final static float MENU_LABEL_Y = (SCREEN_HEIGHT * 0.98f - BUTTON_SIZE);

    public static final float SOCIAL_BACKGROUND_HEIGHT = BUTTON_SIZE * 1.5f;

    public static final float MENU_PACK_COMPLETE_BUTTON_WIDTH = SCREEN_WIDTH * 0.4f;
    public static final float MENU_PACK_COMPLETE_BUTTON_HEIGHT = BUTTON_SIZE * 0.8f;
    public static final float MENU_PACK_COMPLETE_BUTTON_X = (SCREEN_WIDTH - MENU_PACK_COMPLETE_BUTTON_WIDTH) / 2;
    public static final float MENU_PACK_COMPLETE_BUTTON_Y = (SCREEN_HEIGHT - SCREEN_HEIGHT * 0.35f) / 4 * 3 - BUTTON_SIZE * 2.9f;

    public static final float RATE_DIALOG_BUTTON_WIDTH = SCREEN_WIDTH * 0.45f;
    public static final float RATE_DIALOG_BUTTON_HEIGHT = BUTTON_SIZE * 0.8f;
    public static final float RATE_US_DIALOG_BUTTON_RIGHT_X = (Gdx.graphics.getWidth() - RATE_DIALOG_BUTTON_WIDTH * 2) / 3 * 2 + RATE_DIALOG_BUTTON_WIDTH;
    public static final float RATE_US_DIALOG_BUTTON_LEFT_X = (Gdx.graphics.getWidth() - RATE_DIALOG_BUTTON_WIDTH * 2) / 3;
    public static final float RATE_US_DIALOG_BUTTON_Y = (BUTTON_SIZE * 1.45f - FONT_SIZE_X - BUTTON_SIZE * 0.8f) / 2;

    public static final float NEXT_PACK_BUTTON_WIDTH = SCREEN_WIDTH * 0.5f;
    public static final float NEXT_PACK_BUTTON_X = (SCREEN_WIDTH - NEXT_PACK_BUTTON_WIDTH) / 2;
    public static final float NEXT_PACK_BUTTON_Y = (SCREEN_HEIGHT - SCREEN_HEIGHT*0.35f) / 4 * 3 - BUTTON_SIZE* 2;

    public static final float EXIT_BUTTON_Y = (SCREEN_HEIGHT - BUTTON_SIZE * 4 - BUTTON_SIZE * 1.3f - BUTTON_SIZE * 2.2f);

    public static final float SOCIAL_BUTTON_Y = BUTTON_SIZE / 2.0f + SCREEN_HEIGHT * 0.02f + BUTTON_SIZE / 2.0f + (SCREEN_HEIGHT * 0.98f - BUTTON_SIZE + BUTTON_SIZE / 2.0f -
            (BUTTON_SIZE / 2.0f + SCREEN_HEIGHT * 0.02f) - BUTTON_SIZE) * 0.05f / 2.0f + BUTTON_SIZE * 0.3f + (BUTTON_SIZE * 1.5f - BUTTON_SIZE) / 2.0f;

    public final static float REMOVE_ADS_BUTTON_Y = (BUTTON_SIZE / 2 + SCREEN_HEIGHT * 0.02f) + BUTTON_SIZE / 2 + ((((SCREEN_HEIGHT * 0.98f - BUTTON_SIZE)) +
            BUTTON_SIZE / 2) - (BUTTON_SIZE / 2 + SCREEN_HEIGHT * 0.02f) - BUTTON_SIZE) * 0.05f / 2 + BUTTON_SIZE * 1.9f;

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
    public final static int MAX_SNAPSHOT_RESOLVE_RETRIES = 20;
}

