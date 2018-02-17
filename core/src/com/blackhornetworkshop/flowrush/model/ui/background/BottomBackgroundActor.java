package com.blackhornetworkshop.flowrush.model.ui.background;

//Created by TScissors

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_HEIGHT;

public class BottomBackgroundActor extends BackgroundActor {

    private float moveToYTop, posYDown;

    public BottomBackgroundActor(Sprite sprite, float duration) {
        super(sprite, duration, 0);
        moveToYTop = SCREEN_HEIGHT + getHeight();
        posYDown = 0 - getHeight();
        restartPosition();
    }

    @Override
    public boolean isOutside() {
        return getY() >= SCREEN_HEIGHT + getHeight() / 2;
    }

    @Override
    public void restartPosition() {
        setPosition(randomX(), posYDown);
        setMovePosition(getX(), moveToYTop);
    }
}
