package com.blackhornetworkshop.flowrush;

import com.badlogic.gdx.*;

public class ConstantBase
{
    public static final float C_BUTTON_SIZE;
    public static final float HEX_HEIGHT;
    public static final float HEX_WIDTH;

    static {
        C_BUTTON_SIZE = Gdx.graphics.getHeight() / 12;
        HEX_WIDTH = Gdx.graphics.getWidth() / 5;
        HEX_HEIGHT = Gdx.graphics.getWidth() / 5 * 0.86585367f;
    }

    public enum ScreenType
    {
        GAME,
        GAME_LVL_COMPLETE,
        GAME_LVL_COMPLETE_PAUSE,
        GAME_PACK_COMPLETE,
        GAME_PAUSE,
        LOGO_BHW,
        LOGO_FR,
        MAIN_MENU_LVL_CHOISE,
        MAIN_MENU,
        MAIN_MENU_AUTHORS,
        MAIN_MENU_PACK_CHOISE,
        MAIN_MENU_SUPPORT_US,
        MAIN_MENU_GOOGLE_PLAY,
        MAIN_MENU_LOAD_SAVED_GAME
    }
}

