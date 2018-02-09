package com.blackhornetworkshop.flowrush.model.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.blackhornetworkshop.flowrush.controller.LevelLoader;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;

//Created by TScissors

public class LevelNumberListener extends ButtonScaleListener {
    private int pack;
    private int level;
    private boolean isAvailable;

    public LevelNumberListener(boolean disableScaleAnimationBack, int level) {
        super(disableScaleAnimationBack);
        this.level = level;
    }

    @Override
    public void action(InputEvent event) {
        if(isAvailable){
            LevelLoader.getInstance().setLvl(pack, level);
            ScreenManager.setGameMainScreen();
        }
    }

    public void setPack(int pack) {this.pack = pack;}
    public void setAvailable(boolean isAvailable){
        if(isAvailable) disableAnimationBack();
        else enableAnimationBack();
        this.isAvailable = isAvailable;}
}
