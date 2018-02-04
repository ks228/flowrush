package com.blackhornetworkshop.flowrush.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.blackhornetworkshop.flowrush.FRAssetManager;
import com.blackhornetworkshop.flowrush.FRConstants;
import com.blackhornetworkshop.flowrush.FlowRush;
import com.blackhornetworkshop.flowrush.gameplay.SourceChecker;
import com.blackhornetworkshop.flowrush.gameplay.TileActor;
import com.blackhornetworkshop.flowrush.gameplay.TileController;
import com.blackhornetworkshop.flowrush.initialization.LevelLoader;
import com.blackhornetworkshop.flowrush.initialization.MapCreator;
import com.blackhornetworkshop.flowrush.ui.UIPool;

import java.util.ArrayList;

//Created by TScissors. Главный класс игрового экрана

public class GameScreen implements Screen {

    private static GameScreen instance;

    public Stage mainStage;
    private Stage hudStage;

    //Actors
    public ArrayList<TileActor> groupArray;
    private Group mapGroup;
    private Group movePauseGroup;
    public ArrayList<TileActor> specialActorsArray;

    //Graphics
    private TiledDrawable background;
    private SpriteBatch batchForBack;


    //Primitives
    public static int numOfReceivers;
    public boolean firstTap;
    public boolean iconWhite;

    //Other
    private MoveToAction moveToActionPause;

    public InputMultiplexer inputMultiplexer;

    public static GameScreen getInstance(){
        if(instance == null) {
            instance = new GameScreen();
            FlowRush.logDebug("GameScreen is initialized. Return new instance");
        }else {
            FlowRush.logDebug("GameScreen is already initialized. Return existing instance");
        }
        return instance;
    }

    private GameScreen() {}

    @Override
    public void show() {
        //Основная сцена для гексов и сцена для UI
        mainStage = FlowRush.getInstance().getMainStage();
        hudStage = FlowRush.getInstance().getHudStage();

        //Батч для фона из точек
        batchForBack = FlowRush.getInstance().getBatch();

        //Массивы необходимые для быстрой проверки всех актеров, параллельно с mapGroup !!!!!!!!!!!!!! ПОЧЕМУ НЕ ХВАТАЕТ MAPGROUP???
        groupArray = new ArrayList<TileActor>();
        specialActorsArray = new ArrayList<TileActor>();

        //Экшн для группы актеров паузы
        moveToActionPause = new MoveToAction();
        moveToActionPause.setDuration(0.2f);

        //спрайт для отрисовки фона
        background = FRAssetManager.getSpriteBack();

        FlowRush.logDebug("Game screen show() method called");

        UIPool.getSoundButton().setPosition(0, FRConstants.C_BUTTON_SIZE+ Gdx.graphics.getHeight() * 0.05f);
        UIPool.getSoundButton().setVisible(true);

        //Группа для актеров паузы
        movePauseGroup = new Group();
        movePauseGroup.addActor(UIPool.getQuadrant());
        movePauseGroup.addActor(UIPool.getMainMenuButton());
        movePauseGroup.addActor(UIPool.getBackButton());
        movePauseGroup.addActor(UIPool.getRestartButton());
        movePauseGroup.addActor(UIPool.getSoundButton());

        hudStage.addActor(UIPool.getPauseBackground());
        hudStage.addActor(UIPool.getWellDoneLabel());
        hudStage.addActor(UIPool.getWellDonehex());
        hudStage.addActor(UIPool.getNextLevelButton());
        hudStage.addActor(UIPool.getPauseButton());
        hudStage.addActor(UIPool.getLevelNumberActor());
        hudStage.addActor(movePauseGroup);

        mainStage.addActor(UIPool.getBackgroundAnimation());

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(0, FlowRush.getInstance().oneTouchProcessor);
        inputMultiplexer.addProcessor(1, hudStage);
        inputMultiplexer.addProcessor(2, mainStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //game.checker.checkAndSetActor();

        //таймер для анимации иконок
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (int x = 0; x < specialActorsArray.size(); x++) {
                    if (specialActorsArray.get(x).getInclude() == 2 && !specialActorsArray.get(x).isPowerOn()) {
                        TileController.animIcon(specialActorsArray.get(x), iconWhite);
                    }
                }
                iconWhite = !iconWhite;
            }
        }, 0.6f, 0.6f);

        //Выставляем основные настройки отображения
        //resume();

        //Цвет заливки фона
        Gdx.gl.glClearColor(0.93f, 0.93f, 0.93f, 1);

        startNewLevel();
    }

    @Override
    public void hide() {
        FlowRush.logDebug("Game screen hide() method called");

        mainStage.clear();
        hudStage.clear();
        movePauseGroup.clear();
        mapGroup.clear();
    }

    public void startNewLevel() { //обязательно вместе с startNewLevel или nextlvl (в levelloader)
        //номер уровня
        UIPool.getLevelNumberActor().setText("" + LevelLoader.getInstance().getLvl());

        //очистка
        numOfReceivers = 0;
        //mainStage.clear(); // NEED HERE SOME REFACTORING !!!!!!!!!!!!!!!!!!!!! CLEAR AND AGAIN ADD ? CHANGE IT!!!!!!!!!!!!!!
        groupArray.clear();
        specialActorsArray.clear();
        UIPool.getPauseBackground().setVisible(false);

        if(mapGroup != null) {
            mapGroup.clear();
        }

        //загружаем группу актеров // DELETED HEX WIDTH & HEIGHT !!!!!!
        mapGroup = MapCreator.getInstance().getGroup(LevelLoader.getInstance().getActorList());
        mainStage.addActor(mapGroup);

        //обновляем чекер
        SourceChecker.getInstance().initialization(mapGroup);
        SourceChecker.getInstance().checkAndSetActor();

        //PackComplete создается только при условии что уровень последний
        if (!LevelLoader.getInstance().containsNext()) {
            createPackCompleteGroup();
        }
        resume();
    }

    private void checkAchievements() {
        int currentLevel = LevelLoader.getInstance().getLvl();
        int currentPack = LevelLoader.getInstance().getPack();

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! REFACTOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (currentLevel == 1 && !FlowRush.getInstance().save.getAchievements()[0]) {//прошел первый уровень
            FlowRush.getInstance().unlockAchievement(1);
        }

        // FIRST LEVEL WITH A DOVE
        if (((currentLevel == 10 && currentPack == 1)
                || (currentLevel == 1 && (currentPack == 2 || currentPack == 3
                || currentPack == 4 || currentPack == 5))) && !FlowRush.getInstance().save.getAchievements()[1]) {
            FlowRush.getInstance().unlockAchievement(2);
        }

        if (currentLevel == 50
                && currentPack == 1
                && !FlowRush.getInstance().save.getAchievements()[2]) { //прошел первый пак
            FlowRush.getInstance().unlockAchievement(3);
        }
        if (currentLevel == 50
                && currentPack == 2
                && !FlowRush.getInstance().save.getAchievements()[3]) { //прошел второй пак
            FlowRush.getInstance().unlockAchievement(4);
        }
        if (currentLevel == 50
                && currentPack == 3
                && !FlowRush.getInstance().save.getAchievements()[4]) { //прошел третий пак
            FlowRush.getInstance().unlockAchievement(5);
        }
        if (currentLevel == 50
                && currentPack == 4
                && !FlowRush.getInstance().save.getAchievements()[5]) { //прошел четвертый пак
            FlowRush.getInstance().unlockAchievement(6);
        }
        if (currentLevel == 50
                && currentPack == 5
                && !FlowRush.getInstance().save.getAchievements()[6]) { //прошел пятый пак
            FlowRush.getInstance().unlockAchievement(7);
        }

        if (FlowRush.getInstance().save.getLevelsProgress()[0] == 50
                && FlowRush.getInstance().save.getLevelsProgress()[1] == 50
                && FlowRush.getInstance().save.getLevelsProgress()[2] == 50
                && FlowRush.getInstance().save.getLevelsProgress()[3] == 50
                && FlowRush.getInstance().save.getLevelsProgress()[4] == 50
                && !FlowRush.getInstance().save.getAchievements()[7]) { // прошел все уровни
            FlowRush.getInstance().unlockAchievement(8);
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! REFACTOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }
    public void levelComplete() {
        checkAchievements();

        //level complete отображается в любом случае
        FlowRush.getInstance().screenType = FRConstants.ScreenType.GAME_LVL_COMPLETE;

        if (FlowRush.getInstance().prefs.isSoundOn()) {
            FRAssetManager.getLvlCompleteSound().play();
        }

        UIPool.getNextLevelButton().setVisible(true);
        UIPool.getWellDoneLabel().setVisible(true);
        UIPool.getWellDonehex().setVisible(true);


        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! REFACTOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (LevelLoader.getInstance().containsNext()) { //если следующий уровень существует в паке то переключаем его тут
            LevelLoader.getInstance().nextLvl();
            FlowRush.getInstance().save.setCurrentLvl(LevelLoader.getInstance().getLvl());
            FlowRush.getInstance().saveSaveFile();
        } else { //если уровня нет, проверяем доступность следующего пака и тогда переключаем его
            UIPool.getNextLevelButton().setName("show pack");
            if (LevelLoader.getInstance().getLevelPack(LevelLoader.getInstance().getPack()).available) {//next pack available
                LevelLoader.getInstance().nextPack();
                FlowRush.getInstance().saveSaveFile();
                UIPool.getPackCompleteNextPackButton().setName("visible");
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! REFACTOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        if(FlowRush.isPlayServicesAvailable && FlowRush.getPlayServices().isSignedIn()) {
            FlowRush.getPlayServices().saveGame();// Save in Google Play
        }
    }
    public void showPackComplete(){
        UIPool.getNextLevelButton().setVisible(false);
        UIPool.getWellDoneLabel().setVisible(false);
        UIPool.getWellDonehex().setVisible(false);

        FlowRush.getInstance().screenType = FRConstants.ScreenType.GAME_PACK_COMPLETE;

        //System.out.println("screen game packcomplete type 34");

        mainStage.getRoot().setVisible(false);
        UIPool.getPauseButton().setVisible(false);
        UIPool.getLevelNumberActor().setVisible(false);

        if (FlowRush.getInstance().prefs.isSoundOn()) {
            FRAssetManager.getPackCompleteSound().play();
        }
        if (UIPool.getPackCompleteNextPackButton().getName().equals("visible")) { //!!!!!!!!!!!!!!!!!!!!!!! VISIBLE ???????????????
            UIPool.getPackCompleteNextPackButton().setVisible(true);
            UIPool.getPackCompleteNextPackButton().setName("");
        }

        UIPool.getPackCompleteLowerHex().setVisible(true);
        UIPool.getPackCompleteUpperHex().setVisible(true);
        UIPool.getPackCompleteMenuButton().setVisible(true);
        if (FlowRush.getInstance().prefs.isShowRateDialog()) {
            UIPool.getDialogBackground().setVisible(true);
            UIPool.getLeftButton().setVisible(true);
            UIPool.getRightButton().setVisible(true);
        }
    }

    private void createPackCompleteGroup() { //Для создания группы актеров PackComplete
        UIPool.getPackCompleteNextPackButton().setVisible(false);
        UIPool.getPackCompleteLowerHex().setVisible(false);
        UIPool.getPackCompleteUpperHex().setVisible(false);
        UIPool.getPackCompleteMenuButton().setVisible(false);
        UIPool.getDialogBackground().setVisible(false);
        UIPool.getLeftButton().setVisible(false);
        UIPool.getRightButton().setVisible(false);

        hudStage.addActor(UIPool.getPackCompleteNextPackButton());
        hudStage.addActor(UIPool.getPackCompleteLowerHex());
        hudStage.addActor(UIPool.getPackCompleteUpperHex());
        hudStage.addActor(UIPool.getPackCompleteMenuButton());
        hudStage.addActor(UIPool.getDialogBackground());
        hudStage.addActor(UIPool.getLeftButton());
        hudStage.addActor(UIPool.getRightButton());
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batchForBack.begin();
        background.draw(batchForBack, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batchForBack.end();

        mainStage.act(Gdx.graphics.getDeltaTime());
        hudStage.act(Gdx.graphics.getDeltaTime());

        mainStage.draw();
        hudStage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        if (FlowRush.getInstance().screenType == FRConstants.ScreenType.GAME_LVL_COMPLETE) {
            FlowRush.getInstance().screenType = FRConstants.ScreenType.GAME_LVL_COMPLETE_PAUSE; //lvlcomplete+pause
            //System.out.println("screen game pause on type 35");
        }else{
            FlowRush.getInstance().screenType = FRConstants.ScreenType.GAME_PAUSE;
            //System.out.println("screen game pause on type 32");
        }

        inputMultiplexer.removeProcessor(mainStage); //!!!!!!!!!!  в случае с 33 уже убран
        UIPool.getPauseButton().setVisible(false);
        movePauseGroupUp();
    }

    private void movePauseGroupUp() {
        moveToActionPause.setPosition(0, 0);
        if (movePauseGroup.getActions().contains(moveToActionPause, true)) {
            moveToActionPause.restart();
        } else {
            moveToActionPause.reset();
            movePauseGroup.addAction(moveToActionPause);
        }
    }

    private void movePauseGroupDown() {
        moveToActionPause.setPosition(-UIPool.getQuadrant().getWidth(), -UIPool.getQuadrant().getWidth());
        if (movePauseGroup.getActions().contains(moveToActionPause, true)) {
            moveToActionPause.restart();
        } else {
            moveToActionPause.reset();
            movePauseGroup.addAction(moveToActionPause);
        }
    }

    @Override
    public void resume() {
        if (FlowRush.getInstance().screenType == FRConstants.ScreenType.GAME_PAUSE) {//Вариант когда нажата пауза
            FlowRush.getInstance().screenType = FRConstants.ScreenType.GAME;
            if (inputMultiplexer.getProcessors().size < 3 && FlowRush.getInstance().screenType != FRConstants.ScreenType.GAME_LVL_COMPLETE) {
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! inputMultiplexer.getProcessors().contains(mainStage, true);
                inputMultiplexer.addProcessor(mainStage);
            }
            UIPool.getPauseButton().setVisible(true);
            UIPool.getPauseBackground().setVisible(false);
            movePauseGroupDown();
        } else if (FlowRush.getInstance().screenType != FRConstants.ScreenType.GAME_LVL_COMPLETE && FlowRush.getInstance().screenType != FRConstants.ScreenType.GAME_LVL_COMPLETE_PAUSE) {
            FlowRush.getInstance().screenType = FRConstants.ScreenType.GAME;

            if (inputMultiplexer.getProcessors().size < 3) {
                inputMultiplexer.addProcessor(mainStage);
            }
            mainStage.getRoot().setVisible(true);
            movePauseGroupDown();
            UIPool.getPauseButton().setVisible(true);
            UIPool.getLevelNumberActor().setVisible(true);
            UIPool.getPauseBackground().setVisible(false);

            UIPool.getWellDoneLabel().setVisible(false);
            UIPool.getWellDonehex().setVisible(false);
            UIPool.getNextLevelButton().setVisible(false);
        } else if (FlowRush.getInstance().screenType == FRConstants.ScreenType.GAME_LVL_COMPLETE_PAUSE) {
            UIPool.getPauseButton().setVisible(true);
            movePauseGroupDown();
            FlowRush.getInstance().screenType = FRConstants.ScreenType.GAME_LVL_COMPLETE;
        }
    }

    @Override
    public void dispose() {
        FlowRush.logDebug("GameScreen dispose() called");
    }
}