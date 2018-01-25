package com.blackhornetworkshop.flowrush.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackhornetworkshop.flowrush.gameplay.TileActor;
import com.blackhornetworkshop.flowrush.gameplay.TileController;
import com.blackhornetworkshop.flowrush.screens.GameScreen;

//Created by TScissors. Класс слушатель классов HexActor, очищает, поворачивает, изменяет тип, запускает анимацию фона клетки

public class HexActorListener extends ClickListener {

    private TileActor actor;
    private float angle;
    private RotateToAction rotateToAction;
    private ScaleToAction scaleToAction;

    private RotateToAction rotateToAction1, rotateToAction2;
    private SequenceAction sequenceAction;

    final private GameScreen gameScreen;

    public HexActorListener(TileActor actor, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.actor = actor;

        scaleToAction = new ScaleToAction();
        scaleToAction.setDuration(0.05f);

        rotateToAction = new RotateToAction();
        rotateToAction.setDuration(0.13f);
        angle = actor.getAngle();

        rotateToAction1 = new RotateToAction();
        rotateToAction1.setDuration(0.05f);

        rotateToAction2 = new RotateToAction();
        rotateToAction2.setDuration(0.05f);

        rotateToAction1.setRotation(actor.getAngle() - 10);
        rotateToAction2.setRotation(actor.getAngle());

        sequenceAction = new SequenceAction(rotateToAction1, rotateToAction2);
    }


    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

        if(Gdx.input.isTouched()&&actor.isRotable()){

            scaleToAction.setScale(0.85f, 0.85f);

            if (actor.getActions().contains(scaleToAction, true)) {
                scaleToAction.restart();
            } else {
                scaleToAction.reset();
                actor.addAction(scaleToAction);
            }

        }
        TileController.setHexbackTouchOn(actor, gameScreen.game.atlas);
        return true;
    }
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
        TileController.setHexbackTouchOff(actor, gameScreen.game.atlas);

        if (actor.isRotable()) {
            scaleToAction.setScale(1f, 1f);

            if (actor.getActions().contains(scaleToAction, true)) {
                scaleToAction.restart();
            } else {
                scaleToAction.reset();
                actor.addAction(scaleToAction);
            }

            angle = actor.getAngle() - 60;

            TileController.setAngle(actor, angle);
            TileController.moveSources(actor);

            gameScreen.game.checker.checkAndSetActor();

            rotateToAction.setRotation(actor.getAngle());
            if (actor.getActions().contains(rotateToAction, true)) {
                rotateToAction.restart();
            } else {
                rotateToAction.reset();
                actor.addAction(rotateToAction);

            }

        }else{

            //sequenceAction = new SequenceAction(rotateToAction1, rotateToAction2); //пока так, приходиться создавать такой объект каждый раз, но по идее он сам по себе лишь оболочка

            /*if (actor.getActions().contains(sequenceAction, true)) { //вся фишка в том что нужено reset() для содержимого sequence а не для него самого
                sequenceAction.restart();
                System.out.println("contains");
            } else {*/
            //System.out.println(actor.getActions().size);
            sequenceAction.restart();
            //System.out.println("not contains");
            rotateToAction1.reset();
            rotateToAction2.reset();
            actor.addAction(sequenceAction);
            //}

        }

        gameScreen.game.tapOnTileActor.goAnim(actor.getX(), actor.getY());

       /*String sources = "";
        for (int n = 0; n < actor.getSourceArray().length; n++) {
            boolean source = actor.getSourceArray()[n];
            sources += "Source" + n + ": |" + source + "| ";
        }
        System.out.println("XYName: " + actor.getName() + " Include: "+actor.getInclude()+" Type: " + actor.getType() + " Index: " + actor.getIndex() + " Position: " + actor.getRotatePosition() + " " + sources + " PowerOn: " + actor.isPowerOn());*/
    }

}
