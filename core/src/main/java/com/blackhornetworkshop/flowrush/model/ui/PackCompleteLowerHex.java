package com.blackhornetworkshop.flowrush.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

//Created by TScissors. Актер отображающий нижний гекс и pack complete

public class PackCompleteLowerHex extends Actor {
    private Sprite spriteBack;

    public PackCompleteLowerHex(TextureAtlas atlas){
        setSize(Gdx.graphics.getHeight()*0.35f*1.117647058823529f, Gdx.graphics.getHeight()*0.35f);
        setOrigin(getWidth()/2, getHeight()/2);
        setPosition((Gdx.graphics.getWidth()-getWidth())/2, (Gdx.graphics.getHeight()-getHeight())/4*3);

        spriteBack = atlas.createSprite("bighex_light");

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
        batch.draw(spriteBack, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

}
