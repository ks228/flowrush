package com.blackhornet.flowrush.editor.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.blackhornet.flowrush.editor.view.EditorScreen;

//Created by TScissors.

public class LabelSelectedBrushActor extends Actor{
    private Sprite labelSprite;
    public LabelSelectedBrushActor(){
        labelSprite = EditorScreen.hexTemp;
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(labelSprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    void setLabelSprite(Sprite labelSprite){
        this.labelSprite = labelSprite;
    }
}

