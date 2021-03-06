package com.blackhornetworkshop.flowrush.model;

//Created by TScissors.

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.blackhornetworkshop.flowrush.model.FRConstants.HEX_HEIGHT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.HEX_WIDTH;

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
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.sourceArray = sourceArray;
        this.isRotable = include != 1;

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
            batch.draw(icon, (getX() + (getWidth() - HEX_WIDTH) / 2), (getY() + (getHeight() - HEX_HEIGHT) / 2), getOriginX(), getOriginY(), HEX_WIDTH, HEX_HEIGHT, getScaleX(), getScaleY(), 0);
        }
    }


    public void setAngle(float angle){this.angle = angle;}
    public float getAngle(){return angle;}

    public void setIcon(Sprite sprite){icon = sprite;}
    public void setBackground(Sprite sprite){
        background = sprite;}

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

    public void moveSources() {
        boolean val = sourceArray[0];
        sourceArray[0] = sourceArray[sourceArray.length - 1];
        for (int j = 0; j < sourceArray.length - 1; ++j) {
            boolean val2 = sourceArray[j + 1];
            sourceArray[j + 1] = val;
            val = val2;
        }
    }
}
