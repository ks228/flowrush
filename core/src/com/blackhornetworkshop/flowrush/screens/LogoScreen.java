package com.blackhornetworkshop.flowrush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.blackhornetworkshop.flowrush.ConstantBase;
import com.blackhornetworkshop.flowrush.FlowRush;

//Created by TScissors. Отдельный скрин для отображения лого компании и лого игры

public class LogoScreen implements Screen {
    private static LogoScreen instance;

    //Graphics
    private Sprite logo;
    private SpriteBatch batch;

    //Primitives
    private long startTime;
    private boolean isFRlogo;

    public static LogoScreen getInstance(){
        if(instance == null){
            instance = new LogoScreen();
            FlowRush.logDebug("LogoScreen is initialized. Return new instance");
        }else{
            FlowRush.logDebug("LogoScreen is already initialized. Return existing instance");
        }
        return instance;
    }

    private LogoScreen() {}

    @Override
    public void show() {
        this.batch = FlowRush.getInstance().batch;
        Gdx.input.setInputProcessor(FlowRush.getInstance().oneTouchProcessor);
        setBHWlogo();
    }

    private void setBHWlogo() {
        FlowRush.getInstance().screenType = ConstantBase.ScreenType.LOGO_BHW;
        //System.out.println("screen logo type 11");

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);

        logo = FlowRush.getInstance().atlas.createSprite("h_logo");
        logo.setSize(Gdx.graphics.getWidth() * 0.6f, Gdx.graphics.getWidth() * 0.6f*0.79f);
        logo.setPosition((Gdx.graphics.getWidth() - logo.getWidth()) / 2, (Gdx.graphics.getHeight() - logo.getHeight()) / 2);

        startTime = TimeUtils.nanoTime();
    }

    private void setFRlogo() {
        FlowRush.getInstance().screenType = ConstantBase.ScreenType.LOGO_FR;
        //System.out.println("screen logo type 12");

        Gdx.gl.glClearColor(0.26f, 0.64f, 0.87f, 1);

        //logo = new Sprite(new Texture("texture/fr_logo.png"));
        logo = FlowRush.getInstance().atlas.createSprite("fr_logo");
        logo.setSize(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getWidth() * 0.8f * 0.75f);

        logo.setPosition((Gdx.graphics.getWidth() - logo.getWidth()) / 2, (Gdx.graphics.getHeight() - logo.getHeight()*0.9f)/2);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        logo.draw(batch);
        batch.end();

        if (TimeUtils.nanoTime()-startTime>1500000000L&&!isFRlogo) {
            isFRlogo = true;
            setFRlogo();
        }
        if (TimeUtils.nanoTime()-startTime>3000000000L&&isFRlogo) {
            FlowRush.logDebug("Set main menu screen");
            FlowRush.getInstance().setMainMenuScreen();
            if(FlowRush.getInstance().prefs.isSoundOn()) {
                FlowRush.getInstance().backgroundMusic.play();
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

    }

    @Override
    public void dispose() {
        //System.out.println("dispose() был вызван в LogoScreen");
    }
}
