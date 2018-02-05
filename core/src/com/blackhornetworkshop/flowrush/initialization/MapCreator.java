package com.blackhornetworkshop.flowrush.initialization;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.blackhornetworkshop.flowrush.FRAssetManager;
import com.blackhornetworkshop.flowrush.FRConstants;
import com.blackhornetworkshop.flowrush.FlowRush;
import com.blackhornetworkshop.flowrush.gameplay.TileActor;
import com.blackhornetworkshop.flowrush.gameplay.TileController;
import com.blackhornetworkshop.flowrush.listeners.HexActorListener;
import com.blackhornetworkshop.flowrush.screens.GameScreen;
import com.blackhornetworkshop.flowrush.ui.UIPool;

import java.util.ArrayList;

//Created by TScissors. Класс Group создает группу актеров HexActor на основании данных ActorInfo из массива ActorList

public class MapCreator {

    private static MapCreator instance;

    private Group mapGroup;

    private float xPos;
    private float yPos;

    private float minCoord = Gdx.graphics.getHeight();
    private float maxCoord;

    public static MapCreator getInstance(){
        if(instance == null){
            instance = new MapCreator();
            FlowRush.logDebug("MapCreator is initialized. Return new instance");
        }else{
            FlowRush.logDebug("MapCreator is already initialized. Return existing instance");
        }
        return instance;
    }

    private MapCreator(){}

    public Group getGroup(ArrayList<ArrayList<ActorInfo>> list) {
        minCoord = Gdx.graphics.getHeight();
        maxCoord = 0;

        mapGroup = new Group();

        mapGroup.addActor(UIPool.getTapOnTileActor());

        for (int x = 0; x < list.size(); x++) {
            for (int y = 0; y < list.get(0).size(); y++) {
                createActor(x, y, list, FRAssetManager.getAtlas());
            }
        }

        //блок нужен для вычисления размеров поля и правильной его верстки
        float coefficient = 0;
        boolean minus = true;
        for (int x = 0; x < list.size(); x++) {//проверяем нет ли в первом ряду пустышек, и если первый ряд полностью состоит из них - спускаемся на пол???? гекса.
            if (list.get(x).get(0).getIndex() != 0 && x % 2 != 0) {
                minus = false;
            }
        }

        mapGroup.setWidth(list.size() * FRConstants.HEX_WIDTH - (list.size() - 1) * (FRConstants.HEX_WIDTH / 4));
        mapGroup.setHeight(maxCoord - minCoord);

        float availHeight = (Gdx.graphics.getHeight() - (Gdx.graphics.getWidth() / 10) - UIPool.getLevelNumberActor().getHeight() * 1.1f - FRConstants.BUTTON_SIZE * 1.1f);
        float zoom = (Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 10)) / mapGroup.getWidth();

        if (mapGroup.getHeight() * zoom > availHeight && zoom > availHeight / mapGroup.getHeight()) {
            zoom = availHeight / mapGroup.getHeight();
        }

        if (minus) {
            coefficient = FRConstants.HEX_HEIGHT * 0.5f * zoom;
        }
        mapGroup.setPosition((Gdx.graphics.getWidth() - mapGroup.getWidth()) / 2, ((Gdx.graphics.getHeight() - (mapGroup.getHeight())) / 2) - coefficient);

        mapGroup.setOrigin(mapGroup.getWidth() / 2, mapGroup.getHeight() / 2);
        mapGroup.setScale(zoom);

        return mapGroup;
    }

    private void createActor(int x, int y, ArrayList<ArrayList<ActorInfo>> list, TextureAtlas atlas) {


        ActorInfo actorInfo = list.get(x).get(y);

        //Если индекс из actorInfo XY будет равен 0 актер не будет создан
        if (actorInfo.getIndex() != 0) {
            setPosXY(x, y);
            if (yPos < minCoord) {
                minCoord = yPos;
            }
            if (yPos + FRConstants.HEX_HEIGHT > maxCoord) {
                maxCoord = yPos + FRConstants.HEX_HEIGHT;
            }

            TileActor actor = new TileActor(actorInfo);
            TileController.setType(actor);

            actor.setSources(SourceInstaller.getSourceArray(actor));

            for (int a = 0; a < actor.getRotatePosition(); a++) { //смещаем sources на количество поворотов "position"
                TileController.moveSources(actor);
            }

            actor.addListener(new HexActorListener(actor));

            if (actor.getInclude() == 2) {
                GameScreen.numOfReceivers += 1;
            }

            actor.setRotation(actorInfo.getPosition() * (-60));
            actor.setxIndex(x);
            actor.setyIndex(y);

            //REFACTOR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            actor.setName(x + "" + y); //имя необходимо для точного вычисления "координат" сетки из шестиугольников, в SourceChecker // CREATE ANOTHER METHOD!
            //REFACTOR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            //REFACTOR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            actor.setBounds(xPos, yPos, FRConstants.HEX_WIDTH, FRConstants.HEX_HEIGHT); // ONLY POSITION!
            actor.setOriginX(FRConstants.HEX_WIDTH / 2); //DELETE, DO IT IN TILE ACTOR CLASS
            actor.setOriginY(FRConstants.HEX_HEIGHT / 2); //DELETE, DO IT IN TILE ACTOR CLASS
            //REFACTOR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            actor.setSprite(atlas.createSprite("hex", actor.getIndex())); //стартовая графика гекса

            TileController.setHexbackTouchOff(actor);

            if (actor.getInclude() == 1) {
                actor.setIcon(atlas.createSprite("iconMP"));
            } else if (actor.getInclude() == 2) {
                actor.setIcon(atlas.createSprite("iconE"));
            } else if (actor.getInclude() == 3) {
                actor.setIcon(atlas.createSprite("iconD"));
            }

            ScaleToAction scale = new ScaleToAction();
            scale.setScale(1, 1);
            scale.setDuration(0.25f);

            actor.setScale(0f, 0f);
            actor.addAction(scale); //добавляем стартовую анимацию

            mapGroup.addActor(actor);

            GameScreen.getInstance().groupArray.add(actor);

            if (actor.getInclude() != 0) {
                GameScreen.getInstance().specialActorsArray.add(actor);
            }
        }
    }

    private void setPosXY(int x, int y) {

        xPos = x * FRConstants.HEX_WIDTH - x * (FRConstants.HEX_WIDTH / 4);

        if (x % 2 == 0) {
            yPos = y * FRConstants.HEX_HEIGHT + FRConstants.HEX_HEIGHT / 2;
        } else {
            yPos = y * FRConstants.HEX_HEIGHT;
        }
    }


}
