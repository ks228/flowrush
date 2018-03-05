package com.blackhornet.flowrush.editor.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blackhornet.flowrush.editor.view.SettingsScreen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//Created by TScissors.

public class FlowRushEditor extends Game {

    private static final FlowRushEditor instance = new FlowRushEditor();

    private static final Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
    }
    private static TextureAtlas atlasUI;
    private static TextureAtlas mainAtlas;
    private static Skin skin;

    public final static float HEX_WIDTH = 186;
    public final static float HEX_HEIGHT = 161;

    public static FlowRushEditor getInstance(){
        return instance;
    }

    private FlowRushEditor(){
    }

    @Override
    public void create() {
        atlasUI = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlasUI);
        mainAtlas = new TextureAtlas(Gdx.files.internal("texture/hexAtlas.txt"));

        setScreen(new SettingsScreen());
    }

    public static TextureAtlas getMainAtlas(){
        return mainAtlas;
    }

    public static Skin getSkin(){
        return skin;
    }

    public static Gson getGson(){
        return gson;
    }

    @Override
    public void dispose() {
        System.out.println("FlowRushEditor dispose() called");

        getScreen().dispose();

        mainAtlas.dispose();
        atlasUI.dispose();
        skin.dispose();
    }

}
