package com.blackhornetworkshop.flowrush.model.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

//Created by TScissors. Основной класс маленьких кнопок

public class SmallButtonActor extends Actor{

    private Sprite sprite;

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

}
