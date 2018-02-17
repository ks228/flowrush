package com.blackhornetworkshop.flowrush.model.ui.background;

//Created by TScissors

import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_HEIGHT;

public class TopBackgroundActor extends BackgroundActor {

    private float moveToYDown, posYTop;

    public TopBackgroundActor(Sprite sprite, float duration) {
        super(sprite, duration, 180);
        moveToYDown = 0 - getHeight();
        posYTop = SCREEN_HEIGHT;
        restartPosition();
    }

    @Override
    public boolean isOutside() {
        return getY() <= 0 - getHeight() / 2;
    }

    @Override
    public void restartPosition() {
        setPosition(randomX(), posYTop);
        setMovePosition(getX(), moveToYDown);
    }
}
