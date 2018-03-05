package com.blackhornet.flowrush.editor.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.blackhornet.flowrush.editor.view.EditorScreen;

//Created by TScissors.

public class MapActor extends Actor {

    private int index; // tile type
    private int position; // 0-5 sides
    private int include; //0 - nothing, 1 - source, 2 - destination point, 3 - dove
    private boolean rotable;

    private float angle;

    private Sprite sprite;
    private TextureAtlas atlas;

    private Sprite icon;

    private ActorInfo actorInfo;

    MapActor(ActorInfo actorInfo, TextureAtlas atlas){
        this.actorInfo = actorInfo;
        this.atlas = atlas;

        position = actorInfo.getPosition();
        index = actorInfo.getIndex();
        include = actorInfo.getInclude();
        rotable = false;

        angle = actorInfo.getPosition()*(-60);

        if(index == 0){
            setTempSprite();
        }else {
            setRotable();
            sprite = atlas.createSprite("hex",index);
            if(index>24&&index<38){
                setRotable();
                checkInclude();
            }else if(index>37&&index<51){
                setNotRotable();
                checkInclude();
            }
        }
    }

    public void setIndex(int index) {
        if(index == 0) {
            setTempSprite();
        }else {
            setRotable();
            this.index = index;
            System.out.println(index);
            this.sprite = atlas.createSprite("hex",index);
            actorInfo.setIndex(index);
        }
        if(index>24&&index<38){
            setRotable();
            include = 2;
            actorInfo.setInclude(include);
            checkInclude();
        }else if(index>37&&index<51){
            setNotRotable();
            include = 1;
            actorInfo.setInclude(include);
            checkInclude();
        }
    }

    void checkInclude(){
        if(include == 1) {
            icon = atlas.createSprite("iconMP");
        }else if(include == 2){
            icon = atlas.createSprite("iconE");
        }else if(include == 3){
            icon = atlas.createSprite("iconD");
        }
    }

    void setAngle(float angle){
        this.angle = angle;
        position+=1;
        if(position == 6){
            position = 0;
        }
        actorInfo.setPosition(position);
    }

    public void setInclude(int include){
        this.include = include;
        actorInfo.setInclude(include);
    }


    void setTempSprite(){
        sprite = EditorScreen.hexTemp;

        index = 0;
        actorInfo.setIndex(index);

        position = 0;
        actorInfo.setPosition(position);

        include = 0;
        actorInfo.setInclude(include);

        setNotRotable();

        angle = actorInfo.getPosition()*(-60);
    }

    void setRotable(){
        this.rotable = true;
    }
    void setNotRotable(){
        this.rotable = false;
    }
    public int getIndex(){return index;}
    float getAngle(){return angle;}
    public int getPosition(){return position;}
    public int getInclude(){return include;}
    boolean isRotable(){return rotable;}

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        if (include != 0) {
            batch.draw(icon, (getX() + (getWidth() - icon.getWidth()) / 2), (getY() + (getHeight() - icon.getHeight()) / 2), getOriginX(), getOriginY(), icon.getWidth(), icon.getHeight(), getScaleX(), getScaleY(), 0);
        }
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
