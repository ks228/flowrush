package com.blackhornetworkshop.flowrush.view.ui.background;

//Created by TScissors

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BottomBackgroundActor extends BackgroundActor{

    private float moveToYTop, posYDown;

    public BottomBackgroundActor(Sprite sprite, float duration){
        super(sprite, duration, 0);
        moveToYTop = Gdx.graphics.getHeight() + getHeight();
        posYDown = 0 - getHeight();
        restartPosition();
    }

    @Override
    public boolean isOutside() {
        return getY() >= Gdx.graphics.getHeight() + getHeight() / 2;
    }

    @Override
    public void restartPosition() {
        setPosition(randomX(), posYDown);
        setMovePosition(getX(), moveToYTop);
    }
}
