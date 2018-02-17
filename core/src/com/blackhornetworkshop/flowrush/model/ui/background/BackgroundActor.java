package com.blackhornetworkshop.flowrush.model.ui.background;

//Created by TScissors

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Align;

import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_HEIGHT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_WIDTH;

public abstract class BackgroundActor extends Actor{

    private Sprite sprite;
    private MoveToAction moveToAction;

    BackgroundActor(Sprite sprite, float duration, float rotation){
        this.sprite = sprite;
        moveToAction = new MoveToAction();
        moveToAction.setDuration(duration);
        addAction(moveToAction);
        setSize(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
        setOrigin(Align.center);
        setRotation(rotation);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (isOutside()) {
            restartPosition();
            resetAnimation();
        }
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    private void resetAnimation(){
        if (getActions().contains(moveToAction, true)) {
            moveToAction.restart();
        } else {
            moveToAction.reset();
            addAction(moveToAction);
        }
    }

    void setMovePosition(float x, float y){
        moveToAction.setPosition(x, y);
    }

    float randomX(){
        return MathUtils.random() * (SCREEN_WIDTH - SCREEN_WIDTH * 0.2f) + SCREEN_WIDTH * 0.1f;
    }

    float randomY(){
        return MathUtils.random() * (SCREEN_HEIGHT- SCREEN_HEIGHT * 0.2f) + SCREEN_HEIGHT * 0.1f;
    }

    abstract void restartPosition();
    abstract boolean isOutside();

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
