package com.blackhornetworkshop.flowrush.model.ui;

//Created by TScissors.

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.blackhornetworkshop.flowrush.model.FRAssetManager;
import com.blackhornetworkshop.flowrush.view.FlowRush;

public class PackCompleteTopHex extends Actor {

    private Sprite sprite;
    private Container<Label> labelContainer;


    public PackCompleteTopHex(String string){
        setSize(Gdx.graphics.getHeight()*0.35f*1.117647058823529f, Gdx.graphics.getHeight()*0.35f);
        setOrigin(getWidth()/2, getHeight()/2);
        setPosition((Gdx.graphics.getWidth()-getWidth())/2, (Gdx.graphics.getHeight()-getHeight())/4*3);

        sprite = FRAssetManager.getSprite("bighex_dark");

        Color color;
        if(FlowRush.getPreferences().isNightMode()){
            color = new Color(0.4078f, 0.4118f, 0.4118f, 1);
        }else{
            color = new Color(1f, 1f, 1f, 1);
        }

        Label.LabelStyle labelStyle = new Label.LabelStyle(FRAssetManager.getMidFont(), color);
        Label label = new Label(string+"\nPACK\nIS DONE!",labelStyle);
        label.setAlignment(Align.center);

        labelContainer = new Container<Label>(label);
        labelContainer.setPosition(getX()+getWidth()/2, getY()+getHeight()/2);
        labelContainer.setTransform(true);
        setVisible(false);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        labelContainer.setScale(getScaleX(), getScaleY());
        labelContainer.setPosition(getX()+getWidth()/2, getY()+getHeight()/2);
        labelContainer.draw(batch, 1);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
