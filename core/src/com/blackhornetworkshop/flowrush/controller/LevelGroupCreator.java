package com.blackhornetworkshop.flowrush.controller;

//Created by TScissors. Класс создатель актеров кнопок для перехода на привязанный уровень

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackhornetworkshop.flowrush.model.FRConstants;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.controller.listeners.ButtonScaleListener;

public class LevelGroupCreator {

    private static LevelGroupCreator instance;

    private float lvlNumSize;

    private float xPos;
    private float yPos;

    private float up;
    private float down;

    private int lvlAvailable;
    private int pack;

    public static LevelGroupCreator getInstance(){
        if(instance == null){
            instance = new LevelGroupCreator();
            FlowRush.logDebug("LevelGroupCreator is initialized. Return new instance");
        }else{
            FlowRush.logDebug("LevelGroupCreator is already initialized. Return existing instance");
        }
        return instance;
    }

    private LevelGroupCreator(){}

    public Group setupLevelGroup(Group levelGroup, int pack){
        levelGroup.clear();
        this.pack = pack;
        lvlAvailable = FlowRush.getInstance().save.getLevelsProgress()[pack-1];
        //System.out.println(lvlAvailable+" available level");

        up = (Gdx.graphics.getHeight()*0.98f- FRConstants.BUTTON_SIZE); //верхний отступ
        down = (FRConstants.BUTTON_SIZE)+Gdx.graphics.getHeight()*0.02f; //нижний отступ

        lvlNumSize = (up-down)/12;

        createGroup(levelGroup);

        return levelGroup;
    }

    private void createGroup(Group levelGroup){
        final Sprite greyback = FRAssetManager.getAtlas().createSprite("lock");
        for (int x = 1; x < 51; x++) {
            final int i = x;
            setPos(x);
            if(x<=lvlAvailable) {
                TextButton levelNumberButton = new TextButton(""+x, FRAssetManager.getSkin(), "lightblue");
                levelNumberButton.setSize(lvlNumSize, lvlNumSize);
                levelNumberButton.setPosition(xPos, yPos);
                levelNumberButton.setOrigin(lvlNumSize/2, lvlNumSize/2);
                levelNumberButton.setTransform(true);

                levelNumberButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        LevelLoader.getInstance().setLvl(pack, i);
                        ScreenManager.setGameMainScreen();
                    }
                });
                levelNumberButton.addListener(new ButtonScaleListener());
                levelGroup.addActor(levelNumberButton);
            }else{
                Actor levelLock = new Actor(){
                    @Override
                    public void draw(Batch batch, float alpha) {
                            batch.draw(greyback, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
                    }
                };
                levelLock.setSize(lvlNumSize, lvlNumSize);
                levelLock.setPosition(xPos, yPos);
                levelLock.setOrigin(lvlNumSize/2, lvlNumSize/2);
                levelLock.addListener(new ButtonScaleListener());
                levelGroup.addActor(levelLock);
            }
        }
    }
    private void setPos(int number){

        float y = ((up-down)-lvlNumSize*10)/11; //отступ по игрикам
        float x = (Gdx.graphics.getWidth()*0.9f-lvlNumSize*5)/6; //отступ по иксам
        float pad;
        if(x>y){
            pad = y;
        }else{
            pad = x;
        }
        float a = (Gdx.graphics.getWidth()-Gdx.graphics.getWidth()*0.9f)/2+(Gdx.graphics.getWidth()*0.9f-pad*4-lvlNumSize*5)/2; //отступ слева первый, up это отступ сверху первый
        if(number<6){
            xPos = (a+pad*(number-1)+lvlNumSize*(number-1));
            yPos = up -pad*1-lvlNumSize*1;

        }else if(number<11){
            xPos = (a+pad*(number-5-1)+lvlNumSize*((number-5)-1));
            yPos = up-pad*2-lvlNumSize*2;
        }else if(number<16){
            xPos = (a+pad*(number-10-1)+lvlNumSize*((number-10)-1));
            yPos = up-pad*3-lvlNumSize*3;
        }else if(number<21){
            xPos = (a+pad*(number-15-1)+lvlNumSize*((number-15)-1));
            yPos = up-pad*4-lvlNumSize*4;
        }else if(number<26){
            xPos = (a+pad*(number-20-1)+lvlNumSize*((number-20)-1));
            yPos = up-pad*5-lvlNumSize*5;
        }else if(number<31){
            xPos = (a+pad*(number-25-1)+lvlNumSize*((number-25)-1));
            yPos = up-pad*6-lvlNumSize*6;
        }else if(number<36){
            xPos = (a+pad*(number-30-1)+lvlNumSize*((number-30)-1));
            yPos = up-pad*7-lvlNumSize*7;
        }else if(number<41){
            xPos = (a+pad*(number-35-1)+lvlNumSize*((number-35)-1));
            yPos = up-pad*8-lvlNumSize*8;
        }else if(number<46){
            xPos = (a+pad*(number-40-1)+lvlNumSize*((number-40)-1));
            yPos = up-pad*9-lvlNumSize*9;
        }else if(number<51){
            xPos = (a+pad*(number-45-1)+lvlNumSize*((number-45)-1));
            yPos = up-pad*10-lvlNumSize*10;
        }
    }
}
