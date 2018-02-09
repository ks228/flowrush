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

import com.blackhornetworkshop.flowrush.controller.RateDialogController;
import com.blackhornetworkshop.flowrush.model.FRAssetManager;
import com.blackhornetworkshop.flowrush.controller.MapController;
import com.blackhornetworkshop.flowrush.model.FRConstants;
import com.blackhornetworkshop.flowrush.model.FRFileHandler;
import com.blackhornetworkshop.flowrush.view.FlowRush;
import com.blackhornetworkshop.flowrush.controller.ScreenManager;
import com.blackhornetworkshop.flowrush.controller.SourceChecker;
import com.blackhornetworkshop.flowrush.model.HexActor;
import com.blackhornetworkshop.flowrush.controller.HexController;
import com.blackhornetworkshop.flowrush.controller.LevelController;
import com.blackhornetworkshop.flowrush.model.ui.UIPool;

//Created by TScissors.

public class GameScreen implements Screen, FRScreen {

    private static GameScreen instance;

    private Stage mainStage;
    private Stage hudStage;
    private TiledDrawable dotBackground;

    private Group pauseGroup;
    private MoveToAction movePauseGroupAction;

    private static boolean isSpecialIconsAnimationWhite;
    private boolean isActive;

    private static InputMultiplexer inputMultiplexer;

    public static GameScreen getInstance() {
        if (instance == null) instance = new GameScreen();
        return instance;
    }

    private GameScreen() {
    }

    @Override
    public void show() {
        FlowRush.logDebug("Game screen show() method called");
        isActive = true;
        isSpecialIconsAnimationWhite = false;

        mainStage = FlowRush.getInstance().getHexesStage();
        hudStage = FlowRush.getInstance().getHudStage();

        dotBackground = FRAssetManager.getBackgroundDot();

        UIPool.getSoundButton().setPosition(0, FRConstants.BUTTON_SIZE + Gdx.graphics.getHeight() * 0.05f);
        UIPool.getSoundButton().setVisible(true);

        pauseGroup = UIPool.getPauseGroup();
        pauseGroup.setPosition(-UIPool.getQuadrantPauseBackground().getWidth(), -UIPool.getQuadrantPauseBackground().getWidth());
        movePauseGroupAction = new MoveToAction();
        movePauseGroupAction.setDuration(0.2f);

        hudStage.addActor(UIPool.getPauseBackground());
        hudStage.addActor(UIPool.getWellDoneLabel());
        hudStage.addActor(UIPool.getWellDonehex());
        hudStage.addActor(UIPool.getNextLevelButton());
        hudStage.addActor(UIPool.getPauseButton());
        hudStage.addActor(UIPool.getLevelNumberActor());
        hudStage.addActor(pauseGroup);

        mainStage.addActor(UIPool.getBackgroundAnimation());

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

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(0, FlowRush.getOneTouchProcessor());
        inputMultiplexer.addProcessor(1, hudStage);
        inputMultiplexer.addProcessor(2, mainStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        Gdx.gl.glClearColor(0.93f, 0.93f, 0.93f, 1);

        startNewLevel();
    }

    @Override
    public void hide() {
        FlowRush.logDebug("Game screen hide() method called");

        mainStage.clear();
        hudStage.clear();
        pauseGroup.clear();

        isActive = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        FlowRush.getBatch().begin();
        dotBackground.draw(FlowRush.getBatch(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        FlowRush.getBatch().end();

        mainStage.act(Gdx.graphics.getDeltaTime());
        hudStage.act(Gdx.graphics.getDeltaTime());

        mainStage.draw();
        hudStage.draw();
    }

    public void setGameMainScreen() {
        if (!inputMultiplexer.getProcessors().contains(mainStage, true)) {
            inputMultiplexer.addProcessor(mainStage);
        }

        isSpecialIconsAnimationWhite = false;

        mainStage.getRoot().setVisible(true);
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
        mainStage.addActor(MapController.getMapGroup());

        SourceChecker.getInstance().initialization();
        SourceChecker.getInstance().update();

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

        mainStage.getRoot().setVisible(false);
        UIPool.getPauseButton().setVisible(false);
        UIPool.getLevelNumberActor().setVisible(false);

        if (FlowRush.getPreferences().isSoundOn()) {
            FRAssetManager.getPackCompleteSound().play();
        }

        if (UIPool.getPackCompleteNextPackButton().getName().equals("visible")) {
            UIPool.getPackCompleteNextPackButton().setVisible(true);
            UIPool.getPackCompleteNextPackButton().setName("");
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
        if (inputMultiplexer.getProcessors().contains(mainStage, true)) {
            inputMultiplexer.removeProcessor(mainStage);
        }
        UIPool.getPauseButton().setVisible(false);

        movePauseGroupUp();
    }

    public void removePause() {
        FlowRush.logDebug("GameScreen removePause() method called");

        if (!inputMultiplexer.getProcessors().contains(mainStage, true)) {
            inputMultiplexer.addProcessor(mainStage);
        }

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

        if (LevelController.nextLevelExist()) {
            LevelController.nextLvl();
            FlowRush.getSave().setCurrentLvl(LevelController.getCurrentLevel());
        } else {
            FlowRush.getSave().finishPack(LevelController.getCurrentPack() - 1);
            if (LevelController.nextPackExist() && LevelController.getLevelPack(LevelController.getCurrentPack()).available) {
                LevelController.nextPack();
                UIPool.getPackCompleteNextPackButton().setName("visible");
            }
        }

        FlowRush.checkAchievements();

        FRFileHandler.saveGame();

        if (FlowRush.isPlayServicesAvailable() && FlowRush.getPlayServices().isSignedIn()) {
            FlowRush.getPlayServices().saveGame();// Save in Google Play
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