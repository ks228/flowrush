package com.blackhornetworkshop.flowrush.model.ui;

//Created by TScissors.

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.blackhornetworkshop.flowrush.model.FRAssetManager;
import com.blackhornetworkshop.flowrush.controller.FlowRush;

import static com.blackhornetworkshop.flowrush.model.FRConstants.BIG_HEX_DARK;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_HEIGHT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_WIDTH;

public class PackCompleteTopHex extends Actor {

    private Sprite sprite;
    private Container<Label> labelContainer;
    private Label label;
    private Label.LabelStyle daylabelStyle, nightLabelStyle;

    public PackCompleteTopHex(String string){
        setSize(SCREEN_HEIGHT*0.35f*1.117647058823529f, SCREEN_HEIGHT*0.35f);
        setOrigin(Align.center);
        setPosition((SCREEN_WIDTH-getWidth())/2, (SCREEN_HEIGHT-getHeight())/4*3);

        sprite = FRAssetManager.getSprite(BIG_HEX_DARK);

        Color dayColor = new Color(1f, 1f, 1f, 1);
        Color nightColor = new Color(0.4078f, 0.4118f, 0.4118f, 1);

        nightLabelStyle = new Label.LabelStyle(FRAssetManager.getMidFont(), nightColor);
        daylabelStyle = new Label.LabelStyle(FRAssetManager.getMidFont(), dayColor);


        if(FlowRush.getPreferences().isNightMode()) {
            label = new Label(string + "\nPACK\nIS DONE!", nightLabelStyle);
        }else{
            label = new Label(string + "\nPACK\nIS DONE!", daylabelStyle);
        }
        label.setAlignment(Align.center);

        labelContainer = new Container<>(label);
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

    public void reload(Sprite sprite){
        this.sprite = sprite;
        if(FlowRush.getPreferences().isNightMode()){
            label.setStyle(nightLabelStyle);
        }else{
            label.setStyle(daylabelStyle);
        }
    }
}
