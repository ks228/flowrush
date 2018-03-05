package com.blackhornet.flowrush.editor.model;

//Created by TScissors.

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;


class BrushActor extends Actor {

    private Sprite sprite;
    private int index;

    void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

    Sprite getSprite(){
        return sprite;}

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }

}
