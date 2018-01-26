package com.blackhornetworkshop.flowrush.ui;

//Created by TScissors. Актер фоновой анимации клетки

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.blackhornetworkshop.flowrush.ConstantBase;


public class TapOnTileActor extends Actor {

    private Sprite tapEffect;
    private ScaleToAction scale1;
    private ScaleToAction scale2;
    private SequenceAction sequenceAction;

    public TapOnTileActor(Sprite sprite){
        setPosition(-1000, -1000);
        setSize(ConstantBase.HEX_WIDTH, ConstantBase.HEX_HEIGHT);
        setOrigin(getWidth()/2, getHeight()/2);

        tapEffect = sprite;

        scale1 = new ScaleToAction();
        scale1.setScale(1.3f, 1.3f);
        scale1.setDuration(0.2f);

        scale2 = new ScaleToAction();
        scale2.setScale(0f, 0f);
        scale2.setDuration(0.0f);

        sequenceAction = new SequenceAction(scale1, scale2);
    }

    //DELETED FOR 1.05 setParameters

    public void goAnim(float xPos, float yPos){

        //removeAction(sequenceAction);
        //setScale(0, 0);


        setX(xPos);
        setY(yPos);

        //setX(xPos + (width / 2));
        //setY(yPos + (height / 2));

        //sequenceAction = new SequenceAction(scale1, scale2); //пока так, приходиться создавать такой объект каждый раз, но по идее он сам по себе лишь оболочка
        sequenceAction.restart();

        scale1.reset();
        scale2.reset();
        addAction(sequenceAction);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(tapEffect, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
