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

import com.blackhornetworkshop.flowrush.initialization.MapActorGroupCreator;
import com.blackhornetworkshop.flowrush.initialization.UiActorCreator;
import com.blackhornetworkshop.flowrush.listeners.LeftButtonListener;
import com.blackhornetworkshop.flowrush.listeners.RightButtonListener;
import com.blackhornetworkshop.flowrush.ui.PackCompleteActor;
import com.blackhornetworkshop.flowrush.ui.SmallButtonActor;

import java.util.ArrayList;

//Created by TScissors. Главный класс игрового экрана

public class GameScreen implements Screen {
    public final com.blackhornetworkshop.flowrush.FlowRush game;

    public final InputMultiplexer inputMultiplexer;

    private SpriteBatch batchForBack;
    public final Stage stage;
    private TiledDrawable background;
    private Stage hudStage;

    private Actor qCircle;

    private MoveToAction moveToActionPause;
    private SmallButtonActor pauseActor, nextButton, wellDonehex;
    private TextButton packComplMenuButton, packCompleteNextButton;
    public TextButton leftButton, rightButton;
    public Label dialogBack;
    private Label wellDone;
    private PackCompleteActor packCompleteActor;
    private com.blackhornetworkshop.flowrush.ui.PackBackActor packBackActor;

    private Group mapGroup;
    private Group movePauseGroup;

    public ArrayList<com.blackhornetworkshop.flowrush.gameplay.TileActor> groupArray;
    public ArrayList<com.blackhornetworkshop.flowrush.gameplay.TileActor> specialActorsArray;

    public static int numOfReceivers;

    public boolean iconWhite = false;

    public boolean firstTap;

    public GameScreen(com.blackhornetworkshop.flowrush.FlowRush gam) {

        game = gam;

        //Основная сцена для гексов и сцена для UI
        stage = new Stage(new ScreenViewport());
        hudStage = new Stage(new ScreenViewport());

        //множитель inputprocessor
        inputMultiplexer = new InputMultiplexer();
        //спрайт для отрисовки фона
        background = game.spriteBack;
        //Цвет заливки фона
        Gdx.gl.glClearColor(0.93f, 0.93f, 0.93f, 1);

        go();
    }

    private void go() {
        //Количество получателей на каждой карте уникально
        numOfReceivers = 0;

        //Массивы необходимые для быстрой проверки всех актеров, параллельно с mapGroup !!!!!!!!!!!!!! ПОЧЕМУ НЕ ХВАТАЕТ MAPGROUP???
        groupArray = new ArrayList<com.blackhornetworkshop.flowrush.gameplay.TileActor>();
        specialActorsArray = new ArrayList<com.blackhornetworkshop.flowrush.gameplay.TileActor>();

        //Таймер сохранения и таймер анимации миганция иконок
        //timerSave = new Timer();
        Timer timerAnim = new Timer();

        //Батч для фона из точек
        batchForBack = new SpriteBatch();

        //номер уровня
        game.levelNumberActor.setText("" + game.levelLoader.getLvl());

        //Добавляем фоновую анимацию
        stage.addActor(game.backGroup);

        //Актеры паузы
        SmallButtonActor restartButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getSmallButtonActor(3, game);

        SmallButtonActor mmenuButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getSmallButtonActor(4, game);
        pauseActor = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getSmallButtonActor(1, game);
        SmallButtonActor backButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getSmallButtonActor(2, game);
        backButton.setPosition(0, 0);
        game.soundButton.setPosition(0, game.cButtonSize + Gdx.graphics.getHeight() * 0.05f);
        game.soundButton.setVisible(true);

        //Фон для актеров паузы
        qCircle = new Actor() {
            @Override
            public void draw(Batch batch, float alpha) {
                batch.draw(game.qCircle, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        //qCircle.setSize(Gdx.graphics.getWidth() * 0.45f, Gdx.graphics.getWidth() * 0.45f);
        qCircle.setSize(game.cButtonSize*2 + Gdx.graphics.getHeight() * 0.1f, game.cButtonSize*2 + Gdx.graphics.getHeight() * 0.1f);

        qCircle.setPosition(0, 0);

        wellDone = new Label("WELL DONE!", game.skin, "darkblue");
        wellDone.setSize(Gdx.graphics.getWidth() * 0.6f, game.cButtonSize * 0.7f);
        wellDone.setPosition((Gdx.graphics.getWidth() - wellDone.getWidth()) / 2, Gdx.graphics.getHeight() - game.cButtonSize * 0.85f);
        wellDone.setAlignment(Align.center);
        wellDone.setVisible(false);
        nextButton = UiActorCreator.getSmallButtonActor(12, game);
        wellDonehex = UiActorCreator.getSmallButtonActor(11, game);


        //Экшн для группы актеров паузы
        moveToActionPause = new MoveToAction();
        moveToActionPause.setDuration(0.2f);

        //Группа для актеров паузы
        movePauseGroup = new Group();
        movePauseGroup.addActor(qCircle);
        movePauseGroup.addActor(mmenuButton);
        movePauseGroup.addActor(game.soundButton);
        movePauseGroup.addActor(backButton);
        movePauseGroup.addActor(restartButton);
        movePauseGroup.setPosition(-Gdx.graphics.getWidth() * 0.4f, -Gdx.graphics.getWidth() * 0.4f);

        hudStage.addActor(game.alphawhiteBack);
        hudStage.addActor(wellDone);
        hudStage.addActor(wellDonehex);
        hudStage.addActor(nextButton);
        hudStage.addActor(pauseActor);
        hudStage.addActor(game.levelNumberActor);
        hudStage.addActor(movePauseGroup);

        //PackComplete создается только при условии что уровень последний
        if (!game.levelLoader.containsNext()) {
            createPackCompleteGroup();
        }

        mapGroup = new MapActorGroupCreator(this, game.levelLoader.getActorList(), game.atlas, game.hexWidth, game.hexHeight).getGroup();
        game.checker.initialization(this, mapGroup); //обязательно после создания mapgroup иначе special actors array будет пустым

        stage.addActor(mapGroup);

        inputMultiplexer.addProcessor(0, game.oneTouchProcessor);
        inputMultiplexer.addProcessor(1, hudStage);
        inputMultiplexer.addProcessor(2, stage);

        Gdx.input.setInputProcessor(inputMultiplexer);

        game.checker.checkAndSetActor();

        //таймер для анимации иконок
        timerAnim.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (int x = 0; x < specialActorsArray.size(); x++) {
                    if (specialActorsArray.get(x).getInclude() == 2 && !specialActorsArray.get(x).isPowerOn()) {
                        com.blackhornetworkshop.flowrush.gameplay.TileController.animIcon(specialActorsArray.get(x), iconWhite, game.atlas);
                    }
                }
                if (iconWhite) {
                    iconWhite = false;
                } else {
                    iconWhite = true;
                }
            }
        }, 0.6f, 0.6f);

        //Выставляем основные настройки отображения
        resume();
    }


    public void changeLvl() { //обязательно вместе с restart или nextlvl (в levelloader)
        //номер уровня
        game.levelNumberActor.setText("" + game.levelLoader.getLvl());

        //очистка
        numOfReceivers = 0;
        stage.clear();
        groupArray.clear();
        specialActorsArray.clear();
        mapGroup.clear();
        game.alphawhiteBack.setVisible(false);

        //Добавляем фоновую анимацию
        stage.addActor(game.backGroup);

        //загружаем группу актеров
        mapGroup = new MapActorGroupCreator(this, game.levelLoader.getActorList(), game.atlas, game.hexWidth, game.hexHeight).getGroup();
        stage.addActor(mapGroup);

        //обновляем чекер
        game.checker.initialization(this, mapGroup);
        game.checker.checkAndSetActor();

        //PackComplete создается только при условии что уровень последний
        if (!game.levelLoader.containsNext()) {
            createPackCompleteGroup();
        }
        resume();
    }

    private void checkAchievements() {
        if (game.levelLoader.getLvl() == 1 && !game.save.getAchievements()[0]) {//прошел первый уровень
            if (game.playServices.isSignedIn()) {
                game.playServices.unlockAchievement(1);
            }
            game.save.getAchievements()[0] = true;
        }
        if (game.levelLoader.getLvl() == 1 && game.levelLoader.getPack() == 2 && !game.save.getAchievements()[1]) {//прошел первый уровень с голубем !!!!!!!!!!!!! ПОМЕнЯТь КОГДА ГОЛУБ ОКАЖЕТСЯ НА СВОЕМ МЕСТе
            if (game.playServices.isSignedIn()) {
                game.playServices.unlockAchievement(2);
            }
            game.save.getAchievements()[1] = true;
        }
        if (game.levelLoader.getLvl() == 10 && game.levelLoader.getPack() == 1 && !game.save.getAchievements()[1]) {//прошел первый уровень с голубем !!!!!!!!!!!!! ПОМЕнЯТь КОГДА ГОЛУБ ОКАЖЕТСЯ НА СВОЕМ МЕСТе
            if (game.playServices.isSignedIn()) {
                game.playServices.unlockAchievement(2);
            }
            game.save.getAchievements()[1] = true;
        }

        //!!!!!!!!!!!!!!!СДЕЛАТЬ АЧИВКУ ДЛЯ ТРЕТЬЕГО И ЧЕТВЕРТОГО (5) ПАКОВ!!!!!!!
        if (game.levelLoader.getLvl() == 50 && game.levelLoader.getPack() == 1 && !game.save.getAchievements()[2]) { //прошел первый пак
            if (game.playServices.isSignedIn()) {
                game.playServices.unlockAchievement(3);
            }
            game.save.getAchievements()[2] = true;
        }
        if (game.levelLoader.getLvl() == 50 && game.levelLoader.getPack() == 2 && !game.save.getAchievements()[3]) { //прошел второй пак
            if (game.playServices.isSignedIn()) {
                game.playServices.unlockAchievement(4);
            }
            game.save.getAchievements()[3] = true;
        }
        if (game.levelLoader.getLvl() == 50 && game.levelLoader.getPack() == 3 && !game.save.getAchievements()[4]) { //прошел третий пак
            if (game.playServices.isSignedIn()) {
                game.playServices.unlockAchievement(5);
            }
            game.save.getAchievements()[4] = true;
        }
        if (game.levelLoader.getLvl() == 50 && game.levelLoader.getPack() == 4 && !game.save.getAchievements()[5]) { //прошел четвертый пак
            if (game.playServices.isSignedIn()) {
                game.playServices.unlockAchievement(6);
            }
            game.save.getAchievements()[5] = true;
        }
        if (game.levelLoader.getLvl() == 50 && game.levelLoader.getPack() == 5 && !game.save.getAchievements()[6]) { //прошел пятый пак
            if (game.playServices.isSignedIn()) {
                game.playServices.unlockAchievement(7);
            }
            game.save.getAchievements()[6] = true;
        }
        if (game.save.getLevelsProgress()[0] == 50 && game.save.getLevelsProgress()[1] == 50 && game.save.getLevelsProgress()[2] == 50 && game.save.getLevelsProgress()[3] == 50 && game.save.getLevelsProgress()[4] == 50 && !game.save.getAchievements()[7]) { // прошел все уровни
            if (game.playServices.isSignedIn()) {
                game.playServices.unlockAchievement(8);
            }
            game.save.getAchievements()[7] = true;
        }
        //тут же проверяем был ли это первый пак с ураганом
    }
    public void levelComplete() {

        checkAchievements();

        //level complete отображается в любом случае
        game.screenType = 33;

        if (game.prefs.isSoundIsOn()) {
            game.lvlCompleteSound.play();
        }

        nextButton.setVisible(true);
        wellDone.setVisible(true);
        wellDonehex.setVisible(true);

        if (game.levelLoader.containsNext()) { //если следующий уровень существует в паке то переключаем его тут
            game.levelLoader.nextLvl();
            game.save.setCurrentLvl(game.levelLoader.getLvl());
            game.saveSaveFile();
        } else { //если уровня нет, проверяем доступность следующего пака и тогда переключаем его
            nextButton.setName("show pack");
            if (game.levelLoader.getLevelPack(game.levelLoader.getPack()).available) {//next pack available
                game.levelLoader.nextPack();
                game.saveSaveFile();
                packCompleteNextButton.setName("visible");
            }
        }

        game.playServices.checkAndSave(false); //сохраняем результат
    }
    public void showPackComplete(){
        nextButton.setVisible(false);
        wellDone.setVisible(false);
        wellDonehex.setVisible(false);

        game.screenType = 34;

        //System.out.println("screen game packcomplete type 34");

        stage.getRoot().setVisible(false);
        pauseActor.setVisible(false);
        game.levelNumberActor.setVisible(false);

        if (game.prefs.isSoundIsOn()) {
            game.packCompleteSound.play();
        }
        if (packCompleteNextButton.getName().equals("visible")) {
            packCompleteNextButton.setVisible(true);
            packCompleteNextButton.setName("");
        }

        packBackActor.setVisible(true);
        packCompleteActor.setVisible(true);
        packComplMenuButton.setVisible(true);
        if (game.prefs.isShowRateDialog()) {
            dialogBack.setVisible(true);
            leftButton.setVisible(true);
            rightButton.setVisible(true);
        }
    }

    private void createPackCompleteGroup() { //Для создания группы актеров PackComplete
        packCompleteActor = new PackCompleteActor(game.atlas, game.skin.getFont("fontMid"), game.save.getPackName());
        packBackActor = new com.blackhornetworkshop.flowrush.ui.PackBackActor(game.atlas);
        packComplMenuButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getTextButton(9, game);
        packCompleteNextButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getTextButton(12, game);
        packCompleteNextButton.setName("");

        //части диалога об оценке/отзыве игры
        dialogBack = new Label("ENJOYING  FLOW RUSH?", game.skin, "darkbluesmall");
        dialogBack.setSize(Gdx.graphics.getWidth(), game.cButtonSize * 1.45f);
        dialogBack.setPosition(0, 0);
        dialogBack.setAlignment(Align.top);

        leftButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getTextButton(10, game);
        leftButton.setStyle(game.skin.get("bordersmall", TextButton.TextButtonStyle.class));
        leftButton.setText("NOT SURE");
        leftButton.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(leftButton, game));
        LeftButtonListener leftButtonListener = new LeftButtonListener(this);
        leftButton.addListener(leftButtonListener);

        rightButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getTextButton(10, game);
        rightButton.setStyle(game.skin.get("whitesmall", TextButton.TextButtonStyle.class));
        rightButton.setText("YES!");
        rightButton.setX((Gdx.graphics.getWidth() - rightButton.getWidth() * 2) / 3 * 2 + rightButton.getWidth());
        rightButton.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(rightButton, game));
        RightButtonListener rightButtonListener = new RightButtonListener(this, leftButtonListener);
        rightButton.addListener(rightButtonListener);


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
    public void show() {
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
        if (game.screenType == 33) {
            game.screenType = 35; //lvlcomplete+pause
            //System.out.println("screen game pause on type 35");
        }else{
            game.screenType = 32;
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
        if (game.screenType == 32) {//Вариант когда нажата пауза
            game.screenType = 31;
            if (inputMultiplexer.getProcessors().size < 3 && game.screenType != 33) {
                inputMultiplexer.addProcessor(stage);
            }
            pauseActor.setVisible(true);
            game.alphawhiteBack.setVisible(false);
            movePauseGroupDown();
        } else if (game.screenType != 33&&game.screenType != 35) {
            game.screenType = 31;

            if (inputMultiplexer.getProcessors().size < 3) {
                inputMultiplexer.addProcessor(stage);
            }
            stage.getRoot().setVisible(true);
            movePauseGroupDown();
            pauseActor.setVisible(true);
            game.levelNumberActor.setVisible(true);
            game.alphawhiteBack.setVisible(false);

            wellDone.setVisible(false);
            wellDonehex.setVisible(false);
            nextButton.setVisible(false);
        } else if (game.screenType == 35) {
            pauseActor.setVisible(true);
            movePauseGroupDown();
            game.screenType = 33;
        }
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        hudStage.dispose();
        batchForBack.dispose();

        //System.out.println("dispose() был вызван в GameScreen");
    }
}