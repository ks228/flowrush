package com.blackhornetworkshop.flowrush.model;

//Created by TScissors. Actor клетки

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HexActor extends Actor{
    private int index;
    private int rotatePosition; // 0-5 rotation range
    private float angle; // not loading from file
    private int include; // 1 - source. 2 - receiver. 3 - dove
    private boolean isRotable;
    private int xIndex;
    private int yIndex;

    private boolean[] sourceArray;

    private boolean isPowerOn;

    private Sprite background, spriteOff, spriteOn, icon;

    public HexActor(int index, int include, Sprite spriteOff, Sprite spriteOn, int rotatePosition, int xIndex, int yIndex, boolean[] sourceArray){
        this.index = index;
        this.include = include;
        this.spriteOn = spriteOn;
        this.spriteOff = spriteOff;
        this.rotatePosition = rotatePosition;
        this.isRotable = include != 1;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.sourceArray = sourceArray;

        isPowerOn = !isRotable;

        angle = this.rotatePosition *(-60);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(background, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        if(isPowerOn){
            batch.draw(spriteOn, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }else{
            batch.draw(spriteOff, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        if (include != 0) {
            batch.draw(icon, (getX() + (getWidth() - Gdx.graphics.getWidth() / 5) / 2), (getY() + (getHeight() - Gdx.graphics.getWidth() / 5 * 0.8658536585365854f) / 2), getOriginX(), getOriginY(), Gdx.graphics.getWidth() / 5, Gdx.graphics.getWidth() / 5 * 0.8658536585365854f, getScaleX(), getScaleY(), 0);
        }
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void setAngle(float angle){this.angle = angle;}
    public float getAngle(){return angle;}

    public void setIcon(Sprite sprite){icon = sprite;}
    public void setBackground(Sprite sprite){
        background = sprite;}

    public void setSources(boolean[] sourceArray){this.sourceArray = sourceArray;}
    public boolean[] getSourceArray(){return sourceArray;}

    public int getRotatePosition(){return rotatePosition;}
    public void setRotatePosition(int position){this.rotatePosition = position;}

    public void setPowerOn(){ isPowerOn = true; }
    public boolean isPowerOn(){ return isPowerOn; }
    public void setPowerOff(){ isPowerOn = false; }

    public int getXIndex(){return xIndex;}
    public int getYIndex(){return yIndex;}

    public int getIndex(){return index;}

    public boolean isRotable() {
        return isRotable;
    }

    public int getInclude(){return include;}

    public void setInclude(int include) {
        this.include = include;
    }
}
