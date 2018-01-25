package com.blackhornetworkshop.flowrush.gameplay;

//Created by TScissors. Actor клетки

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TileActor extends Actor{
    private int index;
    private int position; // одна из 0-5 шести позиций вращения
    private float angle; //в массив не сохраняется, используется конкретно при ротации
    private int include; //наличие иконки 1. источник, всегда включен 2. приемник всегда выключен при старте 3. передатчик всегда выключен при старте
    private boolean rotable; // поворачиваемся?
    private int type; // один из 12-13 типов в зависимости от класса трубы?
    private int xIndex;
    private int yIndex;

    private com.blackhornetworkshop.flowrush.initialization.ActorInfo actorInfo;

    private boolean[] sourceArray;

    private boolean powerOn;

    //private TextureAtlas atlas;
    private Sprite hexback;
    private Sprite sprite;
    private Sprite icon;

    public TileActor(com.blackhornetworkshop.flowrush.initialization.ActorInfo actorInfo){ //конструктор класса
        this.actorInfo = actorInfo;

        //Инициализируем основные Fields класса
        position = actorInfo.getPosition();
        index = actorInfo.getIndex();
        include = actorInfo.getInclude();
        rotable = true;
        powerOn = false;

        angle = position*(-60);

        if(include == 1){
            powerOn = true;
            rotable = false;
        }
    }


    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(hexback, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
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
    public void setHexback(Sprite sprite){hexback = sprite;}
    public void setSprite(Sprite sprite){this.sprite = sprite;}

    public void setSources(boolean[] sourceArray){this.sourceArray = sourceArray;}
    public boolean[] getSourceArray(){return sourceArray;}

    public int getRotatePosition(){return position;}
    public void setRotatePosition(int position){this.position = position;}

    public void setPowerOn(){powerOn = true;}
    public boolean isPowerOn(){return powerOn;}
    public void setPowerOff(){powerOn = false;}

    public void setxIndex(int xIndex){this.xIndex = xIndex;}
    public int getxIndex(){return xIndex;}
    public void setyIndex(int yIndex){this.yIndex = yIndex;}
    public int getyIndex(){return yIndex;}

    public int getIndex(){return index;}
    public void setIndex(int index){this.index = index;}

    public int getType() {
        return type;
    }
    public void setType(int type){this.type = type;}

    public boolean isRotable() {
        return rotable;
    }

    public int getInclude(){return include;}

    public com.blackhornetworkshop.flowrush.initialization.ActorInfo getActorInfo(){return actorInfo;}
}
