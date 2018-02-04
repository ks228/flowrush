package com.blackhornetworkshop.flowrush.ui;

//Created by TScissors. Актер фоновой анимации

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class BackAnimActor extends Actor {

    private Sprite sprite;
    private MoveToAction moveToAction;
    private int vectorType;

    public BackAnimActor(Sprite sprite, float timeToMove, int vectorType){
        this.vectorType = vectorType;
        this.sprite = sprite;

        moveToAction = new MoveToAction();
        moveToAction.setDuration(timeToMove);

        setSize(sprite.getWidth()*0.7f, sprite.getHeight()*0.7f);

        switch (this.vectorType) {
            case 1:
                setPosition(Gdx.graphics.getWidth(), MathUtils.random() * (Gdx.graphics.getHeight()-20)+10);
                moveToAction.setPosition(0-getHeight(), getY());
                setRotation(90);
                break;
            case 2:
                setPosition(MathUtils.random() * (Gdx.graphics.getWidth()-20)+10, 0-getHeight());
                moveToAction.setPosition(getX(), Gdx.graphics.getHeight()+getHeight());
                break;
            case 3:
                setPosition(0-getHeight(), MathUtils.random() * (Gdx.graphics.getHeight()-20)+10);
                moveToAction.setPosition(Gdx.graphics.getWidth(), getY());
                setRotation(-90);
                break;
            case 4:
                setPosition(MathUtils.random() * (Gdx.graphics.getWidth()-20)+10, Gdx.graphics.getHeight());
                moveToAction.setPosition(getX(), 0-getHeight());
                setRotation(180);
                break;
            default:
                break;
        }

        addAction(moveToAction);
    }

    private void setXY(){
        switch (vectorType){
            case 1:
                setPosition(Gdx.graphics.getWidth(), MathUtils.random() * (Gdx.graphics.getHeight()-20)+10);
                moveToAction.setPosition(0-getHeight(), getY());
                break;
            case 2:
                setPosition(MathUtils.random() * (Gdx.graphics.getWidth()-20)+10, 0-getHeight());
                moveToAction.setPosition(getX(), Gdx.graphics.getHeight()+getHeight());
                break;
            case 3:
                setPosition(0-getHeight(), MathUtils.random() * (Gdx.graphics.getHeight()-20)+10);
                moveToAction.setPosition(Gdx.graphics.getWidth(), getY());
                break;
            case 4:
                setPosition(MathUtils.random() * (Gdx.graphics.getWidth()-20)+10, Gdx.graphics.getHeight());
                moveToAction.setPosition(getX(), 0-getHeight());
                break;
            default:
                break;
        }
        if (getActions().contains(moveToAction, true)) {
            moveToAction.restart();
        } else {
            moveToAction.reset();
            addAction(moveToAction);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if(vectorType==1&&getX()<=-getHeight()){
            setXY();
        }else if(vectorType==2&&getY()>=Gdx.graphics.getHeight()){
            setXY();
        }else if(vectorType==3&&getX()>=Gdx.graphics.getWidth()){
            setXY();
        }else if(vectorType==4&&getY()<=-getHeight()){
            setXY();
        }

        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
