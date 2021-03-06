package com.blackhornetworkshop.flowrush.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.model.FRAssetManager;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;
import com.blackhornetworkshop.flowrush.controller.FlowRush;

import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_HEIGHT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_WIDTH;

//Created by TScissors.

public class LogoScreen implements Screen, FRScreen {

    private static LogoScreen instance;

    private float elapsedTimeFromStart;
    private static boolean isFRlogo;
    private boolean isActive;

    private Animation<TextureRegion> animation;
    private float elapsedTimeAnimation;
    private float animationWidth;
    private float animationHeight;


    public static LogoScreen getInstance() {
        if (instance == null){
            FlowRush.logDebug("LogoScreen was created");
            instance = new LogoScreen();
        }
        return instance;
    }

    private LogoScreen() {}

    @Override
    public void show() {
        FlowRush.logDebug("LogoScreen show() method called");
        isActive = true;
        elapsedTimeAnimation = 0f;
        elapsedTimeFromStart = 0f;

        Array<TextureAtlas.AtlasRegion> regions = FRAssetManager.getLogoAtlas().findRegions("bhw");
        animationWidth = SCREEN_WIDTH/2 > 550 ? 550 : SCREEN_WIDTH/2;
        animationHeight = animationWidth * regions.get(0).getRegionHeight() / regions.get(0).getRegionWidth();

        animation = new Animation<>(0.10f, regions);

        FlowRush.logDebug("Screen width:" + SCREEN_WIDTH);
        FlowRush.logDebug("Logo BHW width:" + animationWidth);
        FlowRush.logDebug("Logo BHW height:" + animationHeight);

        Gdx.input.setInputProcessor(FlowRush.getOneTouchProcessor());
    }

    public static void setBHWLogo() {
        Gdx.gl.glClearColor(0.1373f, 0.1725f, 0.1961f, 1);
        isFRlogo = false;
    }

    public static void setFRLogo() {
        Gdx.gl.glClearColor(0.26f, 0.64f, 0.87f, 1);
        isFRlogo = true;
        FlowRush.getAndroidHelper().initializeInAppBillingService();
        FRAssetManager.loadAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        elapsedTimeFromStart += Gdx.graphics.getDeltaTime();

        if (FRAssetManager.update() && elapsedTimeFromStart > 8f && isFRlogo) {
            FlowRush.logDebug("LogoScreen method render() call after loading assets");
            setMainMenu();
        } else {
            drawLogo();
        }
    }

    private void drawLogo(){
        FlowRush.getBatch().begin();
        if (!isFRlogo & elapsedTimeFromStart > 2f) {
            if (elapsedTimeFromStart > 5f) {
                ScreenManager.setLogoFRScreen();
            }
            elapsedTimeAnimation += Gdx.graphics.getDeltaTime();
            FlowRush.getBatch().draw(animation.getKeyFrame(elapsedTimeAnimation, false), SCREEN_WIDTH / 2 - animationWidth / 2, SCREEN_HEIGHT / 2 - animationHeight / 2, animationWidth, animationHeight);
        } else if (isFRlogo) {
            FRAssetManager.getLogoFR().draw(FlowRush.getBatch());
        }
        FlowRush.getBatch().end();
    }

    private void setMainMenu(){
        FRAssetManager.createAssets();
        UIPool.initialize();
        ScreenManager.setMenuMainScreen();
        if (FlowRush.getPreferences().isSoundOn()) {
            FRAssetManager.getBackgroundMusic().play();
        }
        FRAssetManager.unloadLogos();
        animation = null;
        dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        FlowRush.logDebug("LogoScreen pause() method called");
    }

    @Override
    public void resume() {
        FlowRush.logDebug("LogoScreen resume() method called");
    }

    @Override
    public void hide() {
        FlowRush.logDebug("LogoScreen hide() method called");
        isActive = false;
    }

    @Override
    public void dispose() {
        instance = null;
        FlowRush.logDebug("LogoScreen dispose() method called");
    }

    @Override
    public boolean isActive() {
        return isActive;
    }
}
