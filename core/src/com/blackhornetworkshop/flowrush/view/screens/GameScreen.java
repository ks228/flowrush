package com.blackhornetworkshop.flowrush.view.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.blackhornetworkshop.flowrush.controller.AdController;
import com.blackhornetworkshop.flowrush.controller.GameLogicController;
import com.blackhornetworkshop.flowrush.controller.RateDialogController;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.model.FRAssetManager;
import com.blackhornetworkshop.flowrush.controller.MapController;
import com.blackhornetworkshop.flowrush.model.FRFileHandler;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.model.HexActor;
import com.blackhornetworkshop.flowrush.controller.HexController;
import com.blackhornetworkshop.flowrush.controller.LevelController;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;

import static com.blackhornetworkshop.flowrush.model.FRConstants.BUTTON_SIZE;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_HEIGHT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_WIDTH;

//Created by TScissors.

public class GameScreen implements Screen, FRScreen {

    private static GameScreen instance;

    private Stage hexesStage;
    private Stage hudStage;
    private TiledDrawable dotBackground;

    private Group pauseGroup;
    private MoveToAction movePauseGroupAction;

    private static boolean isSpecialIconsAnimationWhite;
    private boolean isActive;
    private boolean showPackCompleteNextButton;

    private static InputMultiplexer inputMultiplexer;

    public static GameScreen getInstance() {
        if (instance == null) instance = new GameScreen();
        return instance;
    }

    private GameScreen() {
        inputMultiplexer = new InputMultiplexer();
    }

    @Override
    public void show() {
        FlowRush.logDebug("Game screen show() method called");
        isActive = true;
        isSpecialIconsAnimationWhite = false;

        hexesStage = FlowRush.getInstance().getHexesStage();
        hudStage = FlowRush.getInstance().getHudStage();

        dotBackground = FRAssetManager.getBackgroundDot();

        UIPool.getSoundButton().setPosition(0, BUTTON_SIZE + SCREEN_HEIGHT * 0.05f);
        UIPool.getSoundButton().setVisible(true);

        pauseGroup = UIPool.getPauseGroup();
        pauseGroup.setPosition(-UIPool.getQuadrantPauseBackground().getWidth(), -UIPool.getQuadrantPauseBackground().getWidth());
        pauseGroup.addActor(UIPool.getQuadrantPauseBackground());
        pauseGroup.addActor(UIPool.getRestartButton());
        pauseGroup.addActor(UIPool.getResumeButton());
        pauseGroup.addActor(UIPool.getMenuButton());
        pauseGroup.addActor(UIPool.getSoundButton());

        movePauseGroupAction = new MoveToAction();
        movePauseGroupAction.setDuration(0.2f);

        hudStage.addActor(UIPool.getPauseBackground());
        hudStage.addActor(UIPool.getWellDoneLabel());
        hudStage.addActor(UIPool.getWellDonehex());
        hudStage.addActor(UIPool.getNextLevelButton());
        hudStage.addActor(UIPool.getPauseButton());
        hudStage.addActor(UIPool.getLevelNumberActor());
        hudStage.addActor(pauseGroup);

        hexesStage.addActor(UIPool.getBackgroundAnimation());

        Timer.instance().clear();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (int x = 0; x < MapController.getSpecialActorsArraySize(); x++) {
                    HexActor specialActor = MapController.getSpecialActorsArrayChildren(x);
                    if (specialActor.getInclude() == 2 && !specialActor.isPowerOn()) {
                        HexController.animIcon(specialActor);
                    }
                }
                isSpecialIconsAnimationWhite = !isSpecialIconsAnimationWhite;
            }
        }, 0.6f, 0.6f);

        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(0, FlowRush.getOneTouchProcessor());
        inputMultiplexer.addProcessor(1, hudStage);
        inputMultiplexer.addProcessor(2, hexesStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        startNewLevel();
    }

    @Override
    public void hide() {
        FlowRush.logDebug("Game screen hide() method called");

        hexesStage.clear();
        hudStage.clear();
        pauseGroup.clear();

        isActive = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        FlowRush.getBatch().begin();
        dotBackground.draw(FlowRush.getBatch(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        FlowRush.getBatch().end();

        hexesStage.act(Gdx.graphics.getDeltaTime());
        hudStage.act(Gdx.graphics.getDeltaTime());

        hexesStage.draw();
        hudStage.draw();
    }

    public void setGameMainScreen() {
        if(FlowRush.getPreferences().isNightMode()){
            Gdx.gl.glClearColor(0.0745f, 0.0941f, 0.1059f, 1);
        }else{
            Gdx.gl.glClearColor(0.93f, 0.93f, 0.93f, 1);
        }

        if(LevelController.nextLevelExist() && ((LevelController.getCurrentLevel() > 15 && LevelController.getCurrentPack() == 1 && LevelController.getCurrentLevel() % 2 == 0) ||
                LevelController.getCurrentLevel() % 2 == 0 && LevelController.getCurrentPack() != 1)) {
            AdController.setShowAdOnNextScreen(true);
        }else{
            AdController.setShowAdOnNextScreen(false);
        }

        if (!inputMultiplexer.getProcessors().contains(hexesStage, true)) {
            inputMultiplexer.addProcessor(hexesStage);
        }

        isSpecialIconsAnimationWhite = false;

        hexesStage.getRoot().setVisible(true);
        UIPool.getPauseButton().setVisible(true);
        UIPool.getLevelNumberActor().setVisible(true);
        UIPool.getPauseBackground().setVisible(false);

        UIPool.getWellDoneLabel().setVisible(false);
        UIPool.getWellDonehex().setVisible(false);
        UIPool.getNextLevelButton().setVisible(false);
    }

    public void startNewLevel() {
        FlowRush.logDebug("Game screen startNewLevel() method called");

        UIPool.getLevelNumberActor().setText("" + LevelController.getCurrentLevel());

        MapController.createNewMapGroup(LevelController.getActorList());
        hexesStage.addActor(MapController.getMapGroup());

        GameLogicController.getInstance().initialization();
        GameLogicController.getInstance().update();

        if (!LevelController.nextLevelExist()) {
            enablePackCompleteGroup();
        }
    }


    private void enablePackCompleteGroup() {
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

    public static void hideRateDialog() {
        FlowRush.logDebug("GameScreen hideRateDialog() method called");
        UIPool.getDialogBackground().setVisible(false);
        UIPool.getLeftButton().setVisible(false);
        UIPool.getRightButton().setVisible(false);
    }

    public void setPackCompleteScreen() {
        UIPool.getNextLevelButton().setVisible(false);
        UIPool.getWellDoneLabel().setVisible(false);
        UIPool.getWellDonehex().setVisible(false);

        hexesStage.getRoot().setVisible(false);
        UIPool.getPauseButton().setVisible(false);
        UIPool.getLevelNumberActor().setVisible(false);

        if (FlowRush.getPreferences().isSoundOn()) {
            FRAssetManager.getPackCompleteSound().play();
        }

        if (showPackCompleteNextButton) {
            UIPool.getPackCompleteNextPackButton().setVisible(true);
        }

        UIPool.getPackCompleteLowerHex().setVisible(true);
        UIPool.getPackCompleteUpperHex().setVisible(true);
        UIPool.getPackCompleteMenuButton().setVisible(true);
        if (FlowRush.getPreferences().isShowRateDialog()) {
            RateDialogController.reset();
            UIPool.getDialogBackground().setVisible(true);
            UIPool.getLeftButton().setVisible(true);
            UIPool.getRightButton().setVisible(true);
        }
    }

    public void setPauseScreen() {
        if (inputMultiplexer.getProcessors().contains(hexesStage, true)) {
            inputMultiplexer.removeProcessor(hexesStage);
        }
        UIPool.getPauseButton().setVisible(false);

        movePauseGroupUp();
    }

    public void removePauseAndRestoreTouch() {
        FlowRush.logDebug("GameScreen removePauseAndRestoreTouch() method called");

        if (!inputMultiplexer.getProcessors().contains(hexesStage, true)) {
            inputMultiplexer.addProcessor(hexesStage);
        }

        removePause();
    }

    public void removePause() {
        FlowRush.logDebug("GameScreen removePause() method called");

        UIPool.getPauseButton().setVisible(true);
        UIPool.getPauseBackground().setVisible(false);
        movePauseGroupDown();
    }

    public void setGameLevelCompleteScreen() {
        UIPool.getNextLevelButton().setVisible(true);
        UIPool.getWellDoneLabel().setVisible(true);
        UIPool.getWellDonehex().setVisible(true);
    }

    public void levelComplete() {
        FlowRush.logDebug("GameScreen levelComplete() method called");

        showPackCompleteNextButton = false;

        if (LevelController.nextLevelExist()) {
            LevelController.nextLvl();
            FlowRush.getSave().setCurrentLvl(LevelController.getCurrentLevel());
        } else {
            FlowRush.getSave().finishPack(LevelController.getCurrentPack() - 1);
            if (LevelController.nextPackExist() && LevelController.getLevelPack(LevelController.getCurrentPack()).available) {
                LevelController.nextPack();
                showPackCompleteNextButton = true;
            }
        }

        FlowRush.checkAchievements();

        FRFileHandler.saveGame();

        if (FlowRush.isPlayServicesAvailable() && FlowRush.getPlayServices().isSignedIn()) {
            FlowRush.getPlayServices().saveGame();
        }

        if (FlowRush.getPreferences().isSoundOn()) {
            FRAssetManager.getLvlCompleteSound().play();
        }

        ScreenManager.setGameLevelCompleteScreen();
    }



    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        FlowRush.logDebug("GameScreen pause() method called");
    }

    @Override
    public void resume() {
        FlowRush.logDebug("GameScreen resume() method called");
    }

    @Override
    public void dispose() {
        FlowRush.logDebug("GameScreen dispose() called");
    }

    @Override
    public boolean isActive() {
        return isActive;
    }


    private void movePauseGroupUp() {
        movePauseGroupAction.setPosition(0, 0);
        if (pauseGroup.getActions().contains(movePauseGroupAction, true)) {
            movePauseGroupAction.restart();
        } else {
            movePauseGroupAction.reset();
            pauseGroup.addAction(movePauseGroupAction);
        }
    }

    private void movePauseGroupDown() {
        movePauseGroupAction.setPosition(-UIPool.getQuadrantPauseBackground().getWidth(), -UIPool.getQuadrantPauseBackground().getWidth());
        if (pauseGroup.getActions().contains(movePauseGroupAction, true)) {
            movePauseGroupAction.restart();
        } else {
            movePauseGroupAction.reset();
            pauseGroup.addAction(movePauseGroupAction);
        }
    }

    public static InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public static boolean isSpecialIconsAnimationWhite() {
        return isSpecialIconsAnimationWhite;
    }
}