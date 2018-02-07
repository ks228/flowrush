package com.blackhornetworkshop.flowrush.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.blackhornetworkshop.flowrush.controller.FRAssetManager;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;

//Created by TScissors. Отдельный скрин для отображения лого компании и лого игры

public class LogoScreen implements Screen, FRScreen {

    private static LogoScreen instance;

    //Graphics
    private Sprite logoBHW;
    private Sprite logoFR;
    private SpriteBatch batch;

    //Primitives
    private static long startTime;
    private static boolean isFRlogo;
    private boolean isActive;

    public static LogoScreen getInstance(){
        if(instance == null) instance = new LogoScreen();
        return instance;
    }

    private LogoScreen() {}

    @Override
    public void show() {
        batch = FlowRush.getInstance().getBatch();
        logoBHW = FRAssetManager.getLogoBHW();
        logoFR = FRAssetManager.getLogoFR();
        Gdx.input.setInputProcessor(FlowRush.getOneTouchProcessor());
        isActive = true;
    }

    public static void setBHWLogo(){
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
        isFRlogo = false;
        startTime = TimeUtils.nanoTime();
    }

    public static void setFRLogo(){
        Gdx.gl.glClearColor(0.26f, 0.64f, 0.87f, 1);
        isFRlogo = true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if(isFRlogo) logoFR.draw(batch);
        else logoBHW.draw(batch);
        batch.end();

        if (TimeUtils.nanoTime()-startTime > 1500000000L && !isFRlogo) {
            ScreenManager.setLogoFRScreen();
        }else if (TimeUtils.nanoTime()-startTime>3000000000L && isFRlogo) {
            ScreenManager.setMenuMainScreen();
            if(FlowRush.getPreferences().isSoundOn()) {
                FRAssetManager.getBackgroundMusic().play();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        isActive = false;
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isActive() {
        return isActive;
    }
}
