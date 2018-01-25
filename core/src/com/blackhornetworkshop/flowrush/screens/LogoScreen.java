package com.blackhornetworkshop.flowrush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.blackhornetworkshop.flowrush.FlowRush;

//Created by TScissors. Отдельный скрин для отображения лого компании и лого игры

public class LogoScreen implements Screen {
    private Sprite logo;
    private SpriteBatch batch;

    final private FlowRush game;

    private long startTime;

    private boolean isFRlogo;

    public LogoScreen(final FlowRush game) {
        Gdx.input.setInputProcessor(game.oneTouchProcessor);
        this.game = game;
        setBHWlogo();
        batch = new SpriteBatch();
    }

    private void setBHWlogo() {
        game.screenType = 11;
        //System.out.println("screen logo type 11");

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);

        logo = game.atlas.createSprite("h_logo");
        logo.setSize(Gdx.graphics.getWidth() * 0.6f, Gdx.graphics.getWidth() * 0.6f*0.79f);
        logo.setPosition((Gdx.graphics.getWidth() - logo.getWidth()) / 2, (Gdx.graphics.getHeight() - logo.getHeight()) / 2);

        startTime = TimeUtils.nanoTime();
    }

    private void setFRlogo() {
        game.screenType = 12;
        //System.out.println("screen logo type 12");

        Gdx.gl.glClearColor(0.26f, 0.64f, 0.87f, 1);

        //logo = new Sprite(new Texture("texture/fr_logo.png"));
        logo = game.atlas.createSprite("fr_logo");
        logo.setSize(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getWidth() * 0.8f * 0.75f);

        logo.setPosition((Gdx.graphics.getWidth() - logo.getWidth()) / 2, (Gdx.graphics.getHeight() - logo.getHeight()*0.9f)/2);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        logo.draw(batch);
        batch.end();

        if (TimeUtils.nanoTime()-startTime>1500000000l&&!isFRlogo) {
            isFRlogo = true;
            setFRlogo();
        }
        if (TimeUtils.nanoTime()-startTime>3000000000l&&isFRlogo) {

            game.setMainMenuScreen();
            if(game.prefs.isSoundIsOn()) {
                game.backgroundMusic.play();
            }
            if(!game.playServices.isSignedIn()) {
                game.playServices.signIn();
            }
            dispose();
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
        batch.dispose();
        //System.out.println("dispose() был вызван в LogoScreen");
    }
}