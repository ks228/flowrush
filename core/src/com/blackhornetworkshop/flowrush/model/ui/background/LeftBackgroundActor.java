package com.blackhornetworkshop.flowrush.model.ui.background;

//Created by TScissors

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LeftBackgroundActor extends BackgroundActor{

    private float moveToXRight, posXLeft;

    public LeftBackgroundActor(Sprite sprite, float duration){
        super(sprite, duration, -90);
        moveToXRight = Gdx.graphics.getWidth() + getHeight();
        posXLeft = 0 - getHeight()/2;
        restartPosition();
    }

    @Override
    public boolean isOutside() {
        return getX() >= Gdx.graphics.getWidth() + getHeight() / 2;
    }

    @Override
    public void restartPosition() {
        setPosition(posXLeft, randomY());
        setMovePosition(moveToXRight, getY());
    }
}
