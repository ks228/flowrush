package com.blackhornetworkshop.flowrush.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.blackhornetworkshop.flowrush.ConstantBase;
import com.blackhornetworkshop.flowrush.FlowRush;
import com.blackhornetworkshop.flowrush.gameplay.TileActor;
import com.blackhornetworkshop.flowrush.gameplay.TileController;
import com.blackhornetworkshop.flowrush.initialization.MapActorGroupCreator;
import com.blackhornetworkshop.flowrush.initialization.UiActorCreator;
import com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener;
import com.blackhornetworkshop.flowrush.listeners.LeftButtonListener;
import com.blackhornetworkshop.flowrush.listeners.RightButtonListener;
import com.blackhornetworkshop.flowrush.ui.PackBackActor;
import com.blackhornetworkshop.flowrush.ui.PackCompleteActor;
import com.blackhornetworkshop.flowrush.ui.SmallButtonActor;

import java.util.ArrayList;

//Created by TScissors. Главный класс игрового экрана

public class GameScreen implements Screen {

    private static GameScreen instance;

    public final Stage stage;
    private Stage hudStage;

    //Actors
    public Label dialogBack;
    public ArrayList<com.blackhornetworkshop.flowrush.gameplay.TileActor> groupArray;
    public TextButton leftButton, rightButton;
    private Group mapGroup;
    private Group movePauseGroup;
    private SmallButtonActor pauseActor, nextButton, wellDonehex, restartButton, mmenuButton, backButton;
    private PackBackActor packBackActor;
    private TextButton packComplMenuButton, packCompleteNextButton;
    private Actor qCircle;
    public ArrayList<TileActor> specialActorsArray;
    private Label wellDone;
    private PackCompleteActor packCompleteActor;

    //Graphics
    private TiledDrawable background;
    private SpriteBatch batchForBack;


    //Utils
    public final InputMultiplexer inputMultiplexer;
    public final MapActorGroupCreator mapActorGroupCreator;

    //Primitives
    public static int numOfReceivers;
    public boolean firstTap;
    public boolean iconWhite;

    //Other
    private MoveToAction moveToActionPause;

    public static GameScreen getInstance(){
        if(instance == null) {
            instance = new GameScreen();
            FlowRush.logDebug("GameScreen is initialized. Return new instance");
        }else {
            FlowRush.logDebug("GameScreen is already initialized. Return existing instance");
        }
        return instance;
    }

    private GameScreen() {
        //Основная сцена для гексов и сцена для UI
        stage = FlowRush.getInstance().getMainStage();
        hudStage = FlowRush.getInstance().getHudStage();

        //множитель inputprocessor
        inputMultiplexer = new InputMultiplexer();

        //спрайт для отрисовки фона
        background = FlowRush.getInstance().spriteBack;

        //Массивы необходимые для быстрой проверки всех актеров, параллельно с mapGroup !!!!!!!!!!!!!! ПОЧЕМУ НЕ ХВАТАЕТ MAPGROUP???
        groupArray = new ArrayList<TileActor>();
        specialActorsArray = new ArrayList<TileActor>();

        //Батч для фона из точек
        batchForBack = FlowRush.getInstance().batch;

        //Map creator
        mapActorGroupCreator = new MapActorGroupCreator();

        //Актеры паузы
        restartButton = UiActorCreator.getSmallButtonActor(3);
        mmenuButton = UiActorCreator.getSmallButtonActor(4);
        pauseActor = UiActorCreator.getSmallButtonActor(1);
        backButton = UiActorCreator.getSmallButtonActor(2);
        //backButton.setPosition(0, 0); /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! NOT EXIST IN 1.04 */

        //Фон для актеров паузы
        qCircle = new Actor() {
            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(FlowRush.getInstance().qCircle, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };

        qCircle.setSize(ConstantBase.C_BUTTON_SIZE*2 + Gdx.graphics.getHeight() * 0.1f, ConstantBase.C_BUTTON_SIZE*2 + Gdx.graphics.getHeight() * 0.1f);

        qCircle.setPosition(0, 0);

        wellDone = new Label("WELL DONE!", FlowRush.getInstance().skin, "darkblue");
        wellDone.setSize(Gdx.graphics.getWidth() * 0.6f, ConstantBase.C_BUTTON_SIZE * 0.7f);
        wellDone.setPosition((Gdx.graphics.getWidth() - wellDone.getWidth()) / 2, Gdx.graphics.getHeight() - ConstantBase.C_BUTTON_SIZE * 0.85f);
        wellDone.setAlignment(Align.center);
        wellDone.setVisible(false);
        nextButton = UiActorCreator.getSmallButtonActor(12);
        wellDonehex = UiActorCreator.getSmallButtonActor(11);


        //Экшн для группы актеров паузы
        moveToActionPause = new MoveToAction();
        moveToActionPause.setDuration(0.2f);
    }

    @Override
    public void show() {
        FlowRush.logDebug("Game screen show() method called");

        FlowRush.getInstance().soundButton.setPosition(0, ConstantBase.C_BUTTON_SIZE+ Gdx.graphics.getHeight() * 0.05f);
        FlowRush.getInstance().soundButton.setVisible(true);

        //Группа для актеров паузы
        movePauseGroup = new Group();
        movePauseGroup.addActor(qCircle);
        movePauseGroup.addActor(mmenuButton);
        movePauseGroup.addActor(backButton);
        movePauseGroup.addActor(restartButton);
        movePauseGroup.addActor(FlowRush.getInstance().soundButton);

        hudStage.addActor(FlowRush.getInstance().alphawhiteBack);
        hudStage.addActor(wellDone);
        hudStage.addActor(wellDonehex);
        hudStage.addActor(nextButton);
        hudStage.addActor(pauseActor);
        hudStage.addActor(FlowRush.getInstance().levelNumberActor);
        hudStage.addActor(movePauseGroup);

        stage.addActor(FlowRush.getInstance().backGroup);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        inputMultiplexer.addProcessor(0, FlowRush.getInstance().oneTouchProcessor);
        inputMultiplexer.addProcessor(1, hudStage);
        inputMultiplexer.addProcessor(2, stage);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        Gdx.input.setInputProcessor(inputMultiplexer);

        //game.checker.checkAndSetActor();

        //таймер для анимации иконок
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (int x = 0; x < specialActorsArray.size(); x++) {
                    if (specialActorsArray.get(x).getInclude() == 2 && !specialActorsArray.get(x).isPowerOn()) {
                        TileController.animIcon(specialActorsArray.get(x), iconWhite, FlowRush.getInstance().atlas);
                    }
                }
                iconWhite = !iconWhite;
            }
        }, 0.6f, 0.6f);

        //Выставляем основные настройки отображения
        //resume();

        //Цвет заливки фона
        Gdx.gl.glClearColor(0.93f, 0.93f, 0.93f, 1);

        restart();
    }

    @Override
    public void hide() {
        FlowRush.logDebug("Game screen hide() method called");

        //inputMultiplexer.clear();
        stage.clear();
        hudStage.clear();
        movePauseGroup.clear();
        mapGroup.clear();
    }

    public void restart() { //обязательно вместе с restart или nextlvl (в levelloader)
        //номер уровня
        FlowRush.getInstance().levelNumberActor.setText("" + FlowRush.getInstance().levelLoader.getLvl());

        //очистка
        numOfReceivers = 0;
        //stage.clear(); // NEED HERE SOME REFACTORING !!!!!!!!!!!!!!!!!!!!! CLEAR AND AGAIN ADD ? CHANGE IT!!!!!!!!!!!!!!
        groupArray.clear();
        specialActorsArray.clear();
        FlowRush.getInstance().alphawhiteBack.setVisible(false);

        if(mapGroup != null) {
            mapGroup.clear();
        }

        //загружаем группу актеров // DELETED HEX WIDTH & HEIGHT !!!!!!
        mapGroup = mapActorGroupCreator.getGroup(FlowRush.getInstance().levelLoader.getActorList(), FlowRush.getInstance().atlas);
        stage.addActor(mapGroup);

        //обновляем чекер
        FlowRush.getInstance().checker.initialization(this, mapGroup);
        FlowRush.getInstance().checker.checkAndSetActor();

        //PackComplete создается только при условии что уровень последний
        if (!FlowRush.getInstance().levelLoader.containsNext()) {
            createPackCompleteGroup();
        }
        resume();
    }

    private void checkAchievements() {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! REFACTOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (FlowRush.getInstance().levelLoader.getLvl() == 1 && !FlowRush.getInstance().save.getAchievements()[0]) {//прошел первый уровень
            FlowRush.getInstance().unlockAchievement(1);
        }

        // FIRST LEVEL WITH A DOVE
        if (((FlowRush.getInstance().levelLoader.getLvl() == 10 && FlowRush.getInstance().levelLoader.getPack() == 1)
                || (FlowRush.getInstance().levelLoader.getLvl() == 1 && (FlowRush.getInstance().levelLoader.getPack() == 2 || FlowRush.getInstance().levelLoader.getPack() == 3
                || FlowRush.getInstance().levelLoader.getPack() == 4 || FlowRush.getInstance().levelLoader.getPack() == 5))) && !FlowRush.getInstance().save.getAchievements()[1]) {
            FlowRush.getInstance().unlockAchievement(2);
        }

        if (FlowRush.getInstance().levelLoader.getLvl() == 50
                && FlowRush.getInstance().levelLoader.getPack() == 1
                && !FlowRush.getInstance().save.getAchievements()[2]) { //прошел первый пак
            FlowRush.getInstance().unlockAchievement(3);
        }
        if (FlowRush.getInstance().levelLoader.getLvl() == 50
                && FlowRush.getInstance().levelLoader.getPack() == 2
                && !FlowRush.getInstance().save.getAchievements()[3]) { //прошел второй пак
            FlowRush.getInstance().unlockAchievement(4);
        }
        if (FlowRush.getInstance().levelLoader.getLvl() == 50
                && FlowRush.getInstance().levelLoader.getPack() == 3
                && !FlowRush.getInstance().save.getAchievements()[4]) { //прошел третий пак
            FlowRush.getInstance().unlockAchievement(5);
        }
        if (FlowRush.getInstance().levelLoader.getLvl() == 50
                && FlowRush.getInstance().levelLoader.getPack() == 4
                && !FlowRush.getInstance().save.getAchievements()[5]) { //прошел четвертый пак
            FlowRush.getInstance().unlockAchievement(6);
        }
        if (FlowRush.getInstance().levelLoader.getLvl() == 50
                && FlowRush.getInstance().levelLoader.getPack() == 5
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
        FlowRush.getInstance().screenType = ConstantBase.ScreenType.GAME_LVL_COMPLETE;

        if (FlowRush.getInstance().prefs.isSoundOn()) {
            FlowRush.getInstance().lvlCompleteSound.play();
        }

        nextButton.setVisible(true);
        wellDone.setVisible(true);
        wellDonehex.setVisible(true);


        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! REFACTOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (FlowRush.getInstance().levelLoader.containsNext()) { //если следующий уровень существует в паке то переключаем его тут
            FlowRush.getInstance().levelLoader.nextLvl();
            FlowRush.getInstance().save.setCurrentLvl(FlowRush.getInstance().levelLoader.getLvl());
            FlowRush.getInstance().saveSaveFile();
        } else { //если уровня нет, проверяем доступность следующего пака и тогда переключаем его
            nextButton.setName("show pack");
            if (FlowRush.getInstance().levelLoader.getLevelPack(FlowRush.getInstance().levelLoader.getPack()).available) {//next pack available
                FlowRush.getInstance().levelLoader.nextPack();
                FlowRush.getInstance().saveSaveFile();
                packCompleteNextButton.setName("visible");
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! REFACTOR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        if(FlowRush.isPlayServicesAvailable && FlowRush.getInstance().getPlayServices().isSignedIn()) {
            FlowRush.getInstance().getPlayServices().saveGame();// Save in Google Play
        }
    }
    public void showPackComplete(){
        nextButton.setVisible(false);
        wellDone.setVisible(false);
        wellDonehex.setVisible(false);

        FlowRush.getInstance().screenType = ConstantBase.ScreenType.GAME_PACK_COMPLETE;

        //System.out.println("screen game packcomplete type 34");

        stage.getRoot().setVisible(false);
        pauseActor.setVisible(false);
        FlowRush.getInstance().levelNumberActor.setVisible(false);

        if (FlowRush.getInstance().prefs.isSoundOn()) {
            FlowRush.getInstance().packCompleteSound.play();
        }
        if (packCompleteNextButton.getName().equals("visible")) { //!!!!!!!!!!!!!!!!!!!!!!! VISIBLE ???????????????
            packCompleteNextButton.setVisible(true);
            packCompleteNextButton.setName("");
        }

        packBackActor.setVisible(true);
        packCompleteActor.setVisible(true);
        packComplMenuButton.setVisible(true);
        if (FlowRush.getInstance().prefs.isShowRateDialog()) {
            dialogBack.setVisible(true);
            leftButton.setVisible(true);
            rightButton.setVisible(true);
        }
    }

    private void createPackCompleteGroup() { //Для создания группы актеров PackComplete
        packCompleteActor = new PackCompleteActor(FlowRush.getInstance().atlas, FlowRush.getInstance().skin.getFont("fontMid"), FlowRush.getInstance().save.getPackName());
        packBackActor = new PackBackActor(FlowRush.getInstance().atlas);
        packComplMenuButton = UiActorCreator.getTextButton(9);
        packCompleteNextButton = UiActorCreator.getTextButton(12);
        packCompleteNextButton.setName("");

        //части диалога об оценке/отзыве игры
        dialogBack = new Label("ENJOYING  FLOW RUSH?", FlowRush.getInstance().skin, "darkbluesmall");
        dialogBack.setSize(Gdx.graphics.getWidth(), ConstantBase.C_BUTTON_SIZE * 1.45f);
        dialogBack.setPosition(0, 0);
        dialogBack.setAlignment(Align.top);

        leftButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getTextButton(10);
        leftButton.setStyle(FlowRush.getInstance().skin.get("bordersmall", TextButton.TextButtonStyle.class));
        leftButton.setText("NOT SURE");
        leftButton.addListener(new ButtonScaleListener(leftButton));
        LeftButtonListener leftButtonListener = new LeftButtonListener(this);
        leftButton.addListener(leftButtonListener);

        rightButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getTextButton(10);
        rightButton.setStyle(FlowRush.getInstance().skin.get("whitesmall", TextButton.TextButtonStyle.class));
        rightButton.setText("YES!");
        rightButton.setX((Gdx.graphics.getWidth() - rightButton.getWidth() * 2) / 3 * 2 + rightButton.getWidth());
        rightButton.addListener(new ButtonScaleListener(rightButton));
        rightButton.addListener(new RightButtonListener(this, leftButtonListener));


        packCompleteNextButton.setVisible(false);
        packBackActor.setVisible(false);
        packCompleteActor.setVisible(false);
        packComplMenuButton.setVisible(false);
        dialogBack.setVisible(false);
        leftButton.setVisible(false);
        rightButton.setVisible(false);

        hudStage.addActor(packCompleteNextButton);
        hudStage.addActor(packBackActor);
        hudStage.addActor(packCompleteActor);
        hudStage.addActor(packComplMenuButton);
        hudStage.addActor(dialogBack);
        hudStage.addActor(leftButton);
        hudStage.addActor(rightButton);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batchForBack.begin();
        background.draw(batchForBack, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batchForBack.end();

        stage.act(Gdx.graphics.getDeltaTime());
        hudStage.act(Gdx.graphics.getDeltaTime());

        stage.draw();
        hudStage.draw();
        /*long usedBytes = (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1048576;
        System.out.println(usedBytes+" mb");*/
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        if (FlowRush.getInstance().screenType == ConstantBase.ScreenType.GAME_LVL_COMPLETE) {
            FlowRush.getInstance().screenType = ConstantBase.ScreenType.GAME_LVL_COMPLETE_PAUSE; //lvlcomplete+pause
            //System.out.println("screen game pause on type 35");
        }else{
            FlowRush.getInstance().screenType = ConstantBase.ScreenType.GAME_PAUSE;
            //System.out.println("screen game pause on type 32");
        }

        inputMultiplexer.removeProcessor(stage); //!!!!!!!!!!  в случае с 33 уже убран
        pauseActor.setVisible(false);
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
        moveToActionPause.setPosition(-qCircle.getWidth(), -qCircle.getWidth());
        if (movePauseGroup.getActions().contains(moveToActionPause, true)) {
            moveToActionPause.restart();
        } else {
            moveToActionPause.reset();
            movePauseGroup.addAction(moveToActionPause);
        }
    }

    @Override
    public void resume() {
        if (FlowRush.getInstance().screenType == ConstantBase.ScreenType.GAME_PAUSE) {//Вариант когда нажата пауза
            FlowRush.getInstance().screenType = ConstantBase.ScreenType.GAME;
            if (inputMultiplexer.getProcessors().size < 3 && FlowRush.getInstance().screenType != ConstantBase.ScreenType.GAME_LVL_COMPLETE) {
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! inputMultiplexer.getProcessors().contains(stage, true);
                inputMultiplexer.addProcessor(stage);
            }
            pauseActor.setVisible(true);
            FlowRush.getInstance().alphawhiteBack.setVisible(false);
            movePauseGroupDown();
        } else if (FlowRush.getInstance().screenType != ConstantBase.ScreenType.GAME_LVL_COMPLETE && FlowRush.getInstance().screenType != ConstantBase.ScreenType.GAME_LVL_COMPLETE_PAUSE) {
            FlowRush.getInstance().screenType = ConstantBase.ScreenType.GAME;

            if (inputMultiplexer.getProcessors().size < 3) {
                inputMultiplexer.addProcessor(stage);
            }
            stage.getRoot().setVisible(true);
            movePauseGroupDown();
            pauseActor.setVisible(true);
            FlowRush.getInstance().levelNumberActor.setVisible(true);
            FlowRush.getInstance().alphawhiteBack.setVisible(false);

            wellDone.setVisible(false);
            wellDonehex.setVisible(false);
            nextButton.setVisible(false);
        } else if (FlowRush.getInstance().screenType == ConstantBase.ScreenType.GAME_LVL_COMPLETE_PAUSE) {
            pauseActor.setVisible(true);
            movePauseGroupDown();
            FlowRush.getInstance().screenType = ConstantBase.ScreenType.GAME_LVL_COMPLETE;
        }
    }

    @Override
    public void dispose() {
        /*stage.dispose();
        hudStage.dispose();*/
        FlowRush.logDebug("GameScreen dispose() called");
    }
}