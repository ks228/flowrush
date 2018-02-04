package com.blackhornetworkshop.flowrush.ui.background;

//Created by TScissors

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RightBackgroundActor extends BackgroundActor{

    private float moveToXLeft, posXRight;

    public RightBackgroundActor(Sprite sprite, float duration){
        super(sprite, duration, 90);
        moveToXLeft = 0 - getHeight();
        posXRight = Gdx.graphics.getWidth()+getHeight()/2;
        restartPosition();
    }

    @Override
    public boolean isOutside() {
        return getX() <= 0 - getHeight() / 2;
    }

    @Override
    public void restartPosition() {
        setPosition(posXRight, randomY());
        setMovePosition(moveToXLeft, getY());
    }
}
