package com.blackhornetworkshop.flowrush.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.blackhornetworkshop.flowrush.ConstantBase;
import com.blackhornetworkshop.flowrush.FlowRush;
import com.blackhornetworkshop.flowrush.initialization.LevelGroupCreator;
import com.blackhornetworkshop.flowrush.initialization.UiActorCreator;
import com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener;
import com.blackhornetworkshop.flowrush.ui.SmallButtonActor;

//Created by TScissors. Экран меню игры

public class MainMenuScr implements Screen {
    private final FlowRush game;
    private Stage stage;
    private LevelGroupCreator levelGroupCreator;

    //Actors
    private Group levelGroup, packGroup;
    private SmallButtonActor twitterButton, facebookButton, vkButton, authorsButton, closeButton, supportUsSmallButton, googlePlayButton;
    private TextButton playButton, lvlButton, supportUsButton, rateUsButton, feedButton, /**removeAds,*/ socialBack, menuLabel, signInButton, signOutButton, showSnapshotsButton, showAchievementsButton;
    public  TextButton exitButton;
    private Label messageBack, innerScreenBack;

    public MainMenuScr(final com.blackhornetworkshop.flowrush.FlowRush gam) {
        game = gam;

        //Начальный уровень берем из файла сохранения
        game.levelLoader.setLvl(game.save.getCurrentPack(), game.save.getCurrentLvl());

        //Кнопка звука одна для всех экранов
        game.soundButton = UiActorCreator.getSmallButtonActor(5, game); // MAYBE IT SHOULD BE IN FLOWRUSH.JAVA !!!!!!!!!!!!!!!!!!!!!!!!!!???????????????????????????????????????????????????

        //Заливаем фон
        Gdx.gl.glClearColor(0.26f, 0.64f, 0.87f, 1);

        //Создаем сцену
        this.stage = new Stage(new ScreenViewport(), game.batch);

        //MainMenu top label
        menuLabel = UiActorCreator.getTextButton(7, game);
        menuLabel.setText("SUPPORT US");

        //Кнопка играть!
        playButton = UiActorCreator.getTextButton(1, game);
        playButton.addListener(new ButtonScaleListener(playButton, game)); // DELETED IN 1.04 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        //Создаем группу актеров
        levelGroupCreator = new LevelGroupCreator(game);
        levelGroup = new Group(); //класс пустышка, так оптимальней чтобы сразу он там был в случае нескольких кликов по пакам оправдано, сразу есть что удалять в слушателе ниже
        levelGroup.setVisible(false);
        levelGroup.setName("levelgroup"); //имя для поиска

        //Группа актеров-паков
        packGroup = new Group();
        for(int x = 1; x<6; x++){
            final int p = x;
            TextButton packButton = UiActorCreator.getPackTextButton(p, game);
            packButton.addListener(new ButtonScaleListener(packButton, game));
            if(game.levelLoader.getLevelPack(p-1).available) {
                packButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        selectLevelScreen(p);
                    }
                });
            }
            packGroup.addActor(packButton);
        }
        packGroup.setVisible(false);

        //Кнопка выбора паков
        lvlButton = UiActorCreator.getTextButton(2, game);
        lvlButton.addListener(new ButtonScaleListener(lvlButton, game));

        exitButton = UiActorCreator.getTextButton(13, game);
        exitButton.setVisible(false);

        //Основное окно для информации
        innerScreenBack = new Label("", game.skin, "default");
        innerScreenBack.setSize(Gdx.graphics.getWidth()*0.9f, (((Gdx.graphics.getHeight()*0.98f- ConstantBase.C_BUTTON_SIZE))+(ConstantBase.C_BUTTON_SIZE)/2)-(ConstantBase.C_BUTTON_SIZE/2+Gdx.graphics.getHeight()*0.02f)); // размеры up и down иннерскрин? высчитываем через высоту textButton любого выше высчитываем через высоту кнопки circle button back
        innerScreenBack.setPosition((Gdx.graphics.getWidth()-innerScreenBack.getWidth())/2, ConstantBase.C_BUTTON_SIZE/2+Gdx.graphics.getHeight()*0.02f);
        innerScreenBack.setVisible(false);

        //!!!!!!!!!!! НЕ УДАЛИТЬ СЛУЧАЙНО
        /**removeAds = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getTextButton(6, game);
        removeAds.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(removeAds, game));*/

        //Фон и кнопки социальных сетей
        socialBack = UiActorCreator.getTextButton(8, game);
        twitterButton = UiActorCreator.getSmallButtonActor(9, game);
        facebookButton = UiActorCreator.getSmallButtonActor(8, game);
        vkButton = UiActorCreator.getSmallButtonActor(10, game);

        //Кнопка открывающая варианты поддержки
        supportUsButton = UiActorCreator.getTextButton(5, game);
        supportUsButton.addListener(new ButtonScaleListener(supportUsButton, game));

        rateUsButton= UiActorCreator.getTextButton(4, game);
        rateUsButton.addListener(new ButtonScaleListener(rateUsButton, game));

        //Кнопка перехода на скрин отправки отзыва
        feedButton = UiActorCreator.getTextButton(3, game);
        feedButton.addListener(new ButtonScaleListener(feedButton, game));

        //Фон для отображения информации
        messageBack = new Label("development\nTIMUR SCISSORS\n\ndesign\nSONYA KOVALSKI\n\nmusic\nERIC HOPTON", game.skin, "default");
        messageBack.setVisible(false);
        messageBack.setAlignment(Align.top);
        messageBack.setWrap(true);
        Container<Label> labelContainer = new Container<Label>(messageBack); //Контейнер нужен для того чтобы сделать перенос строки
        labelContainer.fill();
        labelContainer.setSize(innerScreenBack.getWidth()*0.9f, (innerScreenBack.getHeight()-ConstantBase.C_BUTTON_SIZE)*0.95f);
        labelContainer.setPosition((Gdx.graphics.getWidth()-innerScreenBack.getWidth()*0.9f)/2, innerScreenBack.getY()+ConstantBase.C_BUTTON_SIZE/2+(innerScreenBack.getHeight()-ConstantBase.C_BUTTON_SIZE)*0.05f/2);

        //Кнопка закрытия окна с информацией или выбором уровней
        closeButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getSmallButtonActor(6, game);

        //Кнопка открытия окна с информацией об авторах
        authorsButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getSmallButtonActor(7, game);

        supportUsSmallButton = com.blackhornetworkshop.flowrush.initialization.UiActorCreator.getSmallButtonActor(13, game);

        //Актер фон треугольник из треугольников
        Actor whiteBackActor = new Image(){
            public void draw(Batch batch, float alpha) {
                batch.draw(game.atlas.createSprite("back_white"), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        whiteBackActor.setSize(Gdx.graphics.getWidth()*0.65f, Gdx.graphics.getWidth()*0.65f*1.71358024691358f);
        whiteBackActor.setPosition(Gdx.graphics.getWidth()-whiteBackActor.getWidth(), 0);

        //Актер фон треугольник из треугольников
        Actor whiteBackActorUp = new Actor(){
            public void draw(Batch batch, float alpha) {
                batch.draw(game.atlas.createSprite("back_white"), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };
        whiteBackActorUp.setSize(Gdx.graphics.getWidth()*0.65f, Gdx.graphics.getWidth()*0.65f*1.71358024691358f);
        whiteBackActorUp.setOrigin(whiteBackActorUp.getWidth()/2, whiteBackActorUp.getHeight()/2);
        whiteBackActorUp.setRotation(180);
        whiteBackActorUp.setPosition(0, Gdx.graphics.getHeight()-whiteBackActorUp.getHeight());

        //Настраиваем общую актера кнопку звука
        game.soundButton.setPosition(Gdx.graphics.getHeight()*0.02f, Gdx.graphics.getHeight()*0.02f);
        game.soundButton.setVisible(true);



        //Добавляем всех актеров на сцену
        stage.addActor(game.backGroup);
        stage.addActor(whiteBackActor);
        stage.addActor(whiteBackActorUp);
        stage.addActor(playButton);
        stage.addActor(lvlButton);
        stage.addActor(exitButton);
        stage.addActor(game.soundButton);
        stage.addActor(authorsButton);
        stage.addActor(supportUsSmallButton);
        stage.addActor(innerScreenBack);
        stage.addActor(labelContainer);
        stage.addActor(packGroup);
        stage.addActor(supportUsButton);
        stage.addActor(rateUsButton);
        stage.addActor(feedButton);
        /**stage.addActor(removeAds);*/
        stage.addActor(socialBack);
        stage.addActor(twitterButton);
        stage.addActor(facebookButton);
        stage.addActor(vkButton);
        stage.addActor(levelGroup);
        stage.addActor(closeButton);
        stage.addActor(menuLabel);

        //Google Play
        if(FlowRush.isPlayServicesAvailable) {
            googlePlayButton = UiActorCreator.getSmallButtonActor(14, game);
            signInButton = UiActorCreator.getTextButton(14, game);
            signInButton.addListener(new ButtonScaleListener(signInButton, game));
            showSnapshotsButton = UiActorCreator.getTextButton(15, game);
            showSnapshotsButton.addListener(new ButtonScaleListener(showSnapshotsButton, game));
            showAchievementsButton = UiActorCreator.getTextButton(16, game);
            showAchievementsButton.addListener(new ButtonScaleListener(showAchievementsButton, game));
            signOutButton = UiActorCreator.getTextButton(17, game);
            signOutButton.addListener(new ButtonScaleListener(signOutButton, game));

            stage.addActor(googlePlayButton);
            stage.addActor(signInButton);
            stage.addActor(signOutButton);
            stage.addActor(showSnapshotsButton);
            stage.addActor(showAchievementsButton);
        }

        //Процессоры ввода
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(0, game.oneTouchProcessor);
        inputMultiplexer.addProcessor(1, stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        resume();
    }

    public void selectLevelScreen(int pack){
        game.screenType = ConstantBase.ScreenType.MAIN_MENU_LVL_CHOISE;

        setOnVisibleForInnerScreen();
        menuLabel.setText("SELECT LEVEL");
        stage.getRoot().findActor("levelgroup").remove(); //удаляем старую группу она улетает к сборщику мусора так как кроме stage она нигде не задействована
        levelGroup = levelGroupCreator.getLevelGroup(pack); //создаем новую группу
        levelGroup.setName("levelgroup"); //имя для поиска и удаления
        stage.addActor(levelGroup);
        levelGroup.setVisible(true);
    }

    public void setPackChoiseScreen(){
        game.screenType = ConstantBase.ScreenType.MAIN_MENU_PACK_CHOISE;
        //System.out.println("screen main menu choose pack screen type 24");
        menuLabel.setText("SELECT PACK");

        setOnVisibleForInnerScreen();
        packGroup.setVisible(true);
    }

    public void setSupportUsScreen(){
        game.screenType = ConstantBase.ScreenType.MAIN_MENU_SUPPORT_US;
       // System.out.println("screen main menu support us type 23");

        setOnVisibleForInnerScreen();
        supportUsButton.setVisible(false);
        rateUsButton.setVisible(false);
        feedButton.setVisible(false);

        /**messageBack.setText("\nPlease support our indie development team. Remove ads or share our game with your friends .\n\nYou help us to grow.\nThank you!");*/
        messageBack.setText("Please support our indie development team. Share our game with your friends.\n\nYou help us to grow.\nThank you!");
        menuLabel.setText("SUPPORT US");

        messageBack.setVisible(true);
        //supportUsLabel.setVisible(true);
        socialBack.setVisible(true);
        /**removeAds.setVisible(true);*/
        twitterButton.setVisible(true);
        facebookButton.setVisible(true);
        vkButton.setVisible(true);
    }

    public void setAuthorsScreen(){
        game.screenType = ConstantBase.ScreenType.MAIN_MENU_AUTHORS;
        //System.out.println("screen main menu authors type 22");

        messageBack.setText("development\nTIMUR SCISSORS\n\ndesign\nSONYA KOVALSKI\n\nmusic\nERIC HOPTON");
        menuLabel.setText("AUTHORS");
        setOnVisibleForInnerScreen();

        messageBack.setVisible(true);
        supportUsButton.setVisible(true);
        rateUsButton.setVisible(true);
        feedButton.setVisible(true);
    }

    public void setSignInScreen(){
        game.screenType = ConstantBase.ScreenType.MAIN_MENU_GOOGLE_PLAY;

        messageBack.setText("By signing in your game progress will be saved online " +
                "with Google Play. Your Google Play Games Achievements will be associated with your" +
                "Google+ indentity. These will be viewable from some Google products");
        menuLabel.setText("GOOGLE PLAY");

        setOnVisibleForInnerScreen();
        messageBack.setVisible(true);
        googlePlayButton.setVisible(false);
        signInButton.setVisible(true);
    }

    public void setSignedScreen(){
        game.screenType = ConstantBase.ScreenType.MAIN_MENU_LOAD_SAVED_GAME;

        messageBack.setText("Welcome!\nHere you can see your Flow Rush achievements and manage your saved games");
        menuLabel.setText("GOOGLE PLAY");

        setOnVisibleForInnerScreen();
        messageBack.setVisible(true);
        googlePlayButton.setVisible(false);
        showSnapshotsButton.setVisible(true);
        showAchievementsButton.setVisible(true);
        signOutButton.setVisible(true);
    }

    private void setOnVisibleForInnerScreen(){
        innerScreenBack.setVisible(true);
        closeButton.setVisible(true);
        menuLabel.setVisible(true);

        if(FlowRush.isPlayServicesAvailable) {
            googlePlayButton.setVisible(false);
        }
        authorsButton.setVisible(false);
        supportUsSmallButton.setVisible(false);
        playButton.setVisible(false);
        lvlButton.setVisible(false);
        exitButton.setVisible(false);
        Timer.instance().clear();
        game.soundButton.setVisible(false);
        packGroup.setVisible(false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        game.screenType = ConstantBase.ScreenType.MAIN_MENU;
        //System.out.println("screen main menu type 21");

        menuLabel.setVisible(false);
        levelGroup.setVisible(false);
        innerScreenBack.setVisible(false);
        closeButton.setVisible(false);
        messageBack.setVisible(false);
        packGroup.setVisible(false);
        supportUsButton.setVisible(false);
        rateUsButton.setVisible(false);
        feedButton.setVisible(false);
        socialBack.setVisible(false);
        /**removeAds.setVisible(false);*/
        twitterButton.setVisible(false);
        facebookButton.setVisible(false);
        vkButton.setVisible(false);

        if(FlowRush.isPlayServicesAvailable) {
            googlePlayButton.setVisible(true);
            signInButton.setVisible(false);
            showSnapshotsButton.setVisible(false);
            showAchievementsButton.setVisible(false);
            signOutButton.setVisible(false);
        }

        playButton.setVisible(true);
        lvlButton.setVisible(true);
        game.soundButton.setVisible(true);
        authorsButton.setVisible(true);
        supportUsSmallButton.setVisible(true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}