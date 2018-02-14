package com.blackhornetworkshop.flowrush.model.ui.background;

//Created by TScissors

import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_WIDTH;

public class LeftBackgroundActor extends BackgroundActor{

    private float moveToXRight, posXLeft;

    public LeftBackgroundActor(Sprite sprite, float duration){
        super(sprite, duration, -90);
        moveToXRight = SCREEN_WIDTH + getHeight();
        posXLeft = 0 - getHeight()/2;
        restartPosition();
    }

    @Override
    public boolean isOutside() {
        return getX() >= SCREEN_WIDTH + getHeight() / 2;
    }

    @Override
    public void restartPosition() {
        setPosition(posXLeft, randomY());
        setMovePosition(moveToXRight, getY());
    }
}
