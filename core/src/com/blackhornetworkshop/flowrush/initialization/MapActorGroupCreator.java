package com.blackhornetworkshop.flowrush.initialization;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.blackhornetworkshop.flowrush.screens.GameScreen;

import java.util.ArrayList;

//Created by TScissors. Класс Group создает группу актеров HexActor на основании данных ActorInfo из массива ActorList

public class MapActorGroupCreator {

    private final GameScreen gameScreen;

    private Group mapGroup;

    private float xPos;
    private float yPos;

    private float minCoord = Gdx.graphics.getHeight();
    private float maxCoord = 0;

    public MapActorGroupCreator(GameScreen gameScreen, ArrayList<ArrayList<ActorInfo>> list, TextureAtlas atlas, float hexWidth, float hexHeight) {

        this.gameScreen = gameScreen;

        mapGroup = new Group();

        mapGroup.addActor(gameScreen.game.tapOnTileActor);

        for (int x = 0; x < list.size(); x++) {
            for (int y = 0; y < list.get(0).size(); y++) {
                createActor(x, y, hexWidth, hexHeight, list, atlas);
            }
        }

        //блок нужен для вычисления размеров поля и правильной его верстки
        float coefficient = 0;
        boolean minus = true;
        for (int x = 0; x < list.size(); x++) {//проверяем нет ли в первом ряду пустышек, и если первый ряд полностью состоит из них - спускаемся на пол???? гекса.
            if(list.get(x).get(0).getIndex()!=0&&x%2!=0){
                minus = false;
            }
        }

        mapGroup.setWidth(list.size() * hexWidth - (list.size() - 1) * (hexWidth / 4));
        mapGroup.setHeight(maxCoord - minCoord);

        float availHeight = (Gdx.graphics.getHeight()-(Gdx.graphics.getWidth()/10)-gameScreen.game.levelNumberActor.getHeight()*1.1f-gameScreen.game.cButtonSize*1.1f);
        float zoom  = (Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/10))/mapGroup.getWidth();

        if(mapGroup.getHeight()*zoom>availHeight&&zoom>availHeight/mapGroup.getHeight()){
            zoom=availHeight/mapGroup.getHeight();
        }

        if(minus) {
            coefficient = hexHeight*0.5f*zoom;
        }
        mapGroup.setPosition((Gdx.graphics.getWidth() - mapGroup.getWidth()) / 2, ((Gdx.graphics.getHeight() - (mapGroup.getHeight())) / 2)-coefficient);

        mapGroup.setOrigin(mapGroup.getWidth()/2, mapGroup.getHeight()/2);
        mapGroup.setScale(zoom);
        gameScreen.game.tapOnTileActor.setParameters(hexWidth, hexHeight);
    }

    private void createActor(int x, int y, float hexWidth, float hexHeight, ArrayList<ArrayList<ActorInfo>> list, TextureAtlas atlas){


        ActorInfo actorInfo = list.get(x).get(y);

        //Если индекс из actorInfo XY будет равен 0 актер не будет создан
        if(actorInfo.getIndex()!=0) {
            setPosXY(x, y, hexWidth, hexHeight);
            if (yPos < minCoord) {
            minCoord = yPos;
            }
            if (yPos+hexHeight > maxCoord) {
            maxCoord = yPos + hexHeight;
            }

            com.blackhornetworkshop.flowrush.gameplay.TileActor actor = new com.blackhornetworkshop.flowrush.gameplay.TileActor(actorInfo);
            com.blackhornetworkshop.flowrush.gameplay.TileController.setType(actor);

            actor.setSources(SourceInstaller.getSourceArray(actor));

            for(int a = 0; a < actor.getRotatePosition(); a++) { //смещаем sources на количество поворотов "position"
                com.blackhornetworkshop.flowrush.gameplay.TileController.moveSources(actor);
            }

            actor.addListener(new com.blackhornetworkshop.flowrush.listeners.HexActorListener(actor, gameScreen));

            if(actor.getInclude()==2){
                gameScreen.numOfReceivers += 1;
            }

            actor.setRotation(actorInfo.getPosition() * (-60));
            actor.setxIndex(x);
            actor.setyIndex(y);
            actor.setName(x + "" + y); //имя необходимо для точного вычисления "координат" сетки из шестиугольников, в SourceChecker
            actor.setBounds(xPos, yPos, hexWidth, hexHeight);

            actor.setOriginX(hexWidth / 2);
            actor.setOriginY(hexHeight / 2);



            actor.setSprite(atlas.createSprite("hex", actor.getIndex())); //стартовая графика гекса

            com.blackhornetworkshop.flowrush.gameplay.TileController.setHexbackTouchOff(actor, atlas);

            if(actor.getInclude() == 1) {
                actor.setIcon(atlas.createSprite("iconMP"));
            }else if(actor.getInclude() == 2){
                actor.setIcon(atlas.createSprite("iconE"));
            }else if(actor.getInclude() == 3){
                actor.setIcon(atlas.createSprite("iconD"));
            }

            ScaleToAction scale = new ScaleToAction();
            scale.setScale(1, 1);
            scale.setDuration(0.25f);

            actor.setScale(0f, 0f);
            actor.addAction(scale); //добавляем стартовую анимацию

            mapGroup.addActor(actor);

            gameScreen.groupArray.add(actor);

            if(actor.getInclude()!=0){
                gameScreen.specialActorsArray.add(actor);
            }
        }
    }

    private void setPosXY(int x,int y, float hexWidth, float hexHeight){

        xPos = (x * (hexWidth))-(x*(hexWidth/4));

        if(x%2==0){
            yPos = y * hexHeight+(hexHeight/2);
        } else {
            yPos = (y*hexHeight);
        }
    }

    public Group getGroup(){
        return mapGroup;
    }
}
