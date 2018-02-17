package com.blackhornetworkshop.flowrush.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.utils.Align;
import com.blackhornetworkshop.flowrush.model.FRAssetManager;

import static com.blackhornetworkshop.flowrush.model.FRConstants.BIG_HEX_LIGHT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_HEIGHT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_WIDTH;

//Created by TScissors.

public class PackCompleteLowerHex extends Actor {
    private Sprite sprite;

    public PackCompleteLowerHex(){
        setSize(SCREEN_HEIGHT*0.35f*1.117647058823529f, SCREEN_HEIGHT*0.35f);
        setPosition((SCREEN_WIDTH-getWidth())/2, (SCREEN_HEIGHT-getHeight())/4*3);
        setOrigin(Align.center);

        sprite = FRAssetManager.getSprite(BIG_HEX_LIGHT);

        RotateByAction rotateToActionPackBack = new RotateByAction();
        rotateToActionPackBack.setDuration(3f);
        rotateToActionPackBack.setAmount(-360);

        RepeatAction repeatActionPackBack = new RepeatAction();
        repeatActionPackBack.setAction(rotateToActionPackBack);
        repeatActionPackBack.setCount(RepeatAction.FOREVER);

        addAction(repeatActionPackBack);

        setVisible(false);
    }
    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }


    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
