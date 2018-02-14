package com.blackhornetworkshop.flowrush.model.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.blackhornetworkshop.flowrush.model.FRAssetManager;
import com.blackhornetworkshop.flowrush.model.listeners.LevelNumberListener;

//Created by TScissors

public class LevelNumberButton extends TextButton {

    private int level;
    private boolean isAvailable;
    private Sprite lock;
    private LevelNumberListener levelNumberListener;
    public LevelNumberButton(String text, TextButtonStyle textButtonStyle, Sprite lock, int level) {
        super(text, textButtonStyle);
        this.level = level;
        this.lock = lock;
        levelNumberListener = new LevelNumberListener(false, level);
        addListener(levelNumberListener);
    }

    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
        levelNumberListener.setAvailable(isAvailable);
    }

    public void setPack(int pack) {
        levelNumberListener.setPack(pack);
    }

    public int getLevel(){return level;}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(isAvailable) {
            super.draw(batch, parentAlpha);
        }else{
            batch.draw(lock, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    public void setLockSprite(Sprite lock){
        this.lock = lock;
    }
}
