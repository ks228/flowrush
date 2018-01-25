package com.blackhornetworkshop.flowrush.initialization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

//Created by TScissors. Главный класс игрового экрана

public class UiActorCreator {

    public static TextButton getTextButton(int type, final com.blackhornetworkshop.flowrush.FlowRush game) { //актеры кнопки с текстом
        TextButton textButton;
        switch (type) {
            case 1: //Кнопка PLAY
                textButton = new TextButton("PLAY", game.skin, "playbutton");
                textButton.setSize(Gdx.graphics.getWidth() * 0.9f, game.cButtonSize * 1.3f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - game.cButtonSize * 4 - textButton.getHeight()));
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.getMainMenuScr().dispose();
                        game.setGameScreen();
                    }
                });
                break;
            case 2://Levels
                textButton = new TextButton("LEVELS", game.skin, "lightblue");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, game.cButtonSize);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - game.cButtonSize * 4 - game.cButtonSize * 1.3f - textButton.getHeight() * 1.1f));
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.getMainMenuScr().setPackChoiseScreen();
                    }
                });
                break;
            case 3: //Кнопка SEND FEEDBACK
                textButton = new TextButton("SEND FEEDBACK", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, game.cButtonSize);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) + game.cButtonSize / 2 + ((((Gdx.graphics.getHeight() * 0.98f - game.cButtonSize)) + (game.cButtonSize) / 2) - (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) - game.cButtonSize) * 0.05f / 2 + textButton.getHeight() * 0.3f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.androidSide.actionSend();
                    }
                });
                break;
            case 4: //Кнопка RATE
                textButton = new TextButton("RATE", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, game.cButtonSize);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) + game.cButtonSize / 2 + ((((Gdx.graphics.getHeight() * 0.98f - game.cButtonSize)) + (game.cButtonSize) / 2) - (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) - game.cButtonSize) * 0.05f / 2 + textButton.getHeight() * 1.4f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.androidSide.openPlaymarket();
                    }
                });
                break;
            case 5: //Кнопка SUPPORT US
                textButton = new TextButton("SUPPORT US", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, game.cButtonSize);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) + game.cButtonSize / 2 + ((((Gdx.graphics.getHeight() * 0.98f - game.cButtonSize)) + (game.cButtonSize) / 2) - (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) - game.cButtonSize) * 0.05f / 2 + textButton.getHeight() * 2.5f);
                textButton.setVisible(false);
                textButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.getMainMenuScr().setSupportUsScreen();
                    }
                });
                break;
            case 6: //Кнопка REMOVE ADS
                textButton = new TextButton("REMOVE ADS", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, game.cButtonSize);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) + game.cButtonSize / 2 + ((((Gdx.graphics.getHeight() * 0.98f - game.cButtonSize)) + (game.cButtonSize) / 2) - (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) - game.cButtonSize) * 0.05f / 2 + textButton.getHeight() * 1.9f);
                textButton.setVisible(false);
                break;
            case 7: //Надпись пустышка
                textButton = new TextButton("", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, game.cButtonSize);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() * 0.98f - textButton.getHeight()));
                textButton.setVisible(false);
                break;
            case 8: //Фон для кнопок соц сетей
                textButton = new TextButton("", game.skin, "white");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, game.cButtonSize * 1.5f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) + game.cButtonSize / 2 + ((((Gdx.graphics.getHeight() * 0.98f - game.cButtonSize)) + (game.cButtonSize) / 2) - (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) - game.cButtonSize) * 0.05f / 2 + game.cButtonSize * 0.3f);
                textButton.setVisible(false);
                break;
            case 9: //Кнопка MENU в PackComplete
                textButton = new TextButton("MENU", game.skin, "default");
                textButton.setSize(Gdx.graphics.getWidth() * 0.4f, game.cButtonSize * 0.8f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()*0.35f) / 4 * 3 - game.cButtonSize * 2.9f);
                textButton.setVisible(false);
                textButton.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(textButton, game) {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.getGameScreen().dispose();
                        game.setMainMenuScreen();
                    }
                });
                break;
            case 10: //Кнопка пустышка для диалога об оценке игры
                textButton = new TextButton("", game.skin);
                textButton.setSize(Gdx.graphics.getWidth() * 0.45f, game.cButtonSize * 0.8f);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth() * 2) / 3, (game.cButtonSize * 1.45f - game.skin.getFont("fontSmall").getLineHeight() - game.cButtonSize * 0.8f) / 2);
                textButton.setVisible(false);
                break;
            case 12: //Кнопка Next в PackComplete
                textButton = new TextButton("NEXT", game.skin, "darkblue");
                textButton.setSize(Gdx.graphics.getWidth() * 0.5f, game.cButtonSize);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()*0.35f) / 4 * 3 - game.cButtonSize * 2);
                textButton.setVisible(false);
                textButton.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(textButton, game) {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.getGameScreen().dispose();
                        game.setGameScreen();
                    }
                });
                break;
            case 13://Кнопка EXIT
                textButton = new TextButton("EXIT", game.skin, "lightblue");
                textButton.setSize(Gdx.graphics.getWidth() * 0.7f, game.cButtonSize);
                textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, (Gdx.graphics.getHeight() - game.cButtonSize * 4 - game.cButtonSize * 1.3f - textButton.getHeight() * 2.2f));
                textButton.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(textButton, game) {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.exit();
                    }
                });
                break;
            default:
                textButton = null;
                break;
        }
        textButton.setOrigin(textButton.getWidth() / 2, textButton.getHeight() / 2);
        if (type != 7 && type != 8) {
            textButton.setTransform(true);
        }
        return textButton;
    }

    public static TextButton getPackTextButton(int pack, final com.blackhornetworkshop.flowrush.FlowRush game) { //актеры отвечающие за переход на нужный пак
        TextButton textButton;

        if(game.levelLoader.getLevelPack(pack-1).available) {
            textButton = new TextButton("", game.skin, "darkblue");
        } else {
            textButton = new TextButton("", game.skin, "alphablackgrey");
        }

        String string;
        switch (pack) {
            case 1:
                string = "QUESTION";
                break;
            case 2:
                string = "IDEA";
                break;
            case 3:
                string = "MISSION";
                break;
            case 4:
                //string = "FLYING";
                string = "FLYING";
                break;
            case 5:
                //string = "SOURCE";
                string = "SOURCE\ncoming soon";
                break;
            default:
                string = "NONAME";
                break;
        }

        float up = (Gdx.graphics.getHeight() * 0.98f - game.cButtonSize); //верхний отступ
        float down = (game.cButtonSize) + Gdx.graphics.getHeight() * 0.02f; //нижний отступ
        float packButtonHeight = (up - down) / 6;

        textButton.setText(string);
        textButton.setSize(Gdx.graphics.getWidth() * 0.8f, packButtonHeight);
        textButton.setPosition((Gdx.graphics.getWidth() - textButton.getWidth()) / 2, up - ((up - down) - textButton.getHeight() * 5 - textButton.getHeight() * 0.1f * 4) / 2 - textButton.getHeight() * 0.1f * (pack - 1) - textButton.getHeight() * pack);
        textButton.setOrigin(textButton.getWidth() / 2, textButton.getHeight() / 2);
        textButton.setTransform(true);

        return textButton;
    }

    public static com.blackhornetworkshop.flowrush.ui.SmallButtonActor getSmallButtonActor(int type, final com.blackhornetworkshop.flowrush.FlowRush game) { //Маленькие кнопки актеры
        final com.blackhornetworkshop.flowrush.ui.SmallButtonActor smallButtonActor = new com.blackhornetworkshop.flowrush.ui.SmallButtonActor();

        smallButtonActor.setSize(game.cButtonSize, game.cButtonSize);
        if (type != 11) {
            smallButtonActor.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(smallButtonActor, game));
        }
        Sprite sprite;

        switch (type) {
            case 1://Иконка паузы
                sprite = game.atlas.createSprite("pause_icon");
                smallButtonActor.setSize(Gdx.graphics.getWidth() / 8, Gdx.graphics.getWidth() / 8);
                smallButtonActor.setPosition(0, 0);
                smallButtonActor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if (game.screenType != 33) {
                            game.alphawhiteBack.setVisible(true);
                        }
                        game.getGameScreen().pause();
                    }
                });
                break;
            case 2: //Иконка назад
                sprite = game.atlas.createSprite("back_icon");
                smallButtonActor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.getGameScreen().resume();
                    }
                });
                break;
            case 3://Иконка рестарта уровня
                sprite = game.atlas.createSprite("restart_icon");
                smallButtonActor.setPosition(game.cButtonSize + Gdx.graphics.getHeight() * 0.05f, 0);
                smallButtonActor.setVisible(true);
                smallButtonActor.addListener(new com.blackhornetworkshop.flowrush.listeners.ButtonScaleListener(smallButtonActor, game) {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if(game.screenType==35){
                            game.levelLoader.prevLvl();
                            game.screenType = 31;
                            game.getGameScreen().changeLvl();
                        }else {
                            game.levelLoader.reloadActorList();
                            game.getGameScreen().changeLvl();
                        }
                    }
                });
                break;
            case 4://Иконка перехода в главное меню из игры
                sprite = game.atlas.createSprite("mmenu_icon");
                smallButtonActor.setPosition(game.cButtonSize, game.cButtonSize);

                smallButtonActor.setVisible(true);

                smallButtonActor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.getGameScreen().dispose();
                        game.setMainMenuScreen();
                    }
                });
                break;
            case 5://Иконка контроля звука
                smallButtonActor.setVisible(true);

                if (game.prefs.isSoundIsOn()) {
                    sprite = game.atlas.createSprite("soundOn_icon");
                } else {
                    sprite = game.atlas.createSprite("soundOff_icon");
                }

                smallButtonActor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if (game.prefs.isSoundIsOn()) {
                            smallButtonActor.sprite = game.atlas.createSprite("soundOff_icon");
                            game.prefs.setSound(false);
                            game.backgroundMusic.pause();
                            game.savePrefsFile();
                            //System.out.println("sound on");
                        } else if (!game.prefs.isSoundIsOn()) {
                            smallButtonActor.sprite = game.atlas.createSprite("soundOn_icon");
                            game.prefs.setSound(true);
                            game.backgroundMusic.play();
                            game.savePrefsFile();
                            //System.out.println("sound off");
                        }
                    }
                });
                break;
            case 6://Иконка закрыть в главном меню
                sprite = game.atlas.createSprite("close_icon");
                smallButtonActor.setVisible(true);
                smallButtonActor.setPosition((Gdx.graphics.getWidth() - game.cButtonSize) / 2, Gdx.graphics.getHeight() * 0.02f);
                smallButtonActor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.getMainMenuScr().resume();
                    }
                });
                break;
            case 7://Иконка информации об авторах
                sprite = game.atlas.createSprite("authors_icon");
                smallButtonActor.setPosition(Gdx.graphics.getWidth() - game.cButtonSize - Gdx.graphics.getHeight() * 0.02f, Gdx.graphics.getHeight() * 0.02f);
                smallButtonActor.setVisible(true);
                smallButtonActor.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                        game.getMainMenuScr().setAuthorsScreen();
                    }
                });
                break;
            case 8://Кнопка Facebook
                sprite = game.atlas.createSprite("fb_icon");
                smallButtonActor.setSize(game.cButtonSize, game.cButtonSize);
                smallButtonActor.setPosition((Gdx.graphics.getWidth() - smallButtonActor.getWidth()) / 2 - game.cButtonSize * 1.25f, (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) + game.cButtonSize / 2 + ((((Gdx.graphics.getHeight() * 0.98f - game.cButtonSize)) + (game.cButtonSize) / 2) - (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) - game.cButtonSize) * 0.05f / 2 + game.cButtonSize * 0.3f + (game.cButtonSize * 1.5f - smallButtonActor.getHeight()) / 2);
                smallButtonActor.setVisible(false);
                smallButtonActor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.androidSide.openFacebook();
                    }
                });
                break;
            case 9://Кнопка Twitter
                sprite = game.atlas.createSprite("tw_icon");
                smallButtonActor.setSize(game.cButtonSize, game.cButtonSize);

                smallButtonActor.setPosition((Gdx.graphics.getWidth() - smallButtonActor.getWidth()) / 2, (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) + game.cButtonSize / 2 + ((((Gdx.graphics.getHeight() * 0.98f - game.cButtonSize)) + (game.cButtonSize) / 2) - (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) - game.cButtonSize) * 0.05f / 2 + game.cButtonSize * 0.3f + (game.cButtonSize * 1.5f - smallButtonActor.getHeight()) / 2);
                smallButtonActor.setVisible(false);
                smallButtonActor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.androidSide.openTwitter();
                    }
                });
                break;
            case 10://Кнопка Vk
                sprite = game.atlas.createSprite("vk_icon");
                smallButtonActor.setSize(game.cButtonSize, game.cButtonSize);
                smallButtonActor.setPosition((Gdx.graphics.getWidth() - smallButtonActor.getWidth()) / 2 + game.cButtonSize * 1.25f, (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) + game.cButtonSize / 2 + ((((Gdx.graphics.getHeight() * 0.98f - game.cButtonSize)) + (game.cButtonSize) / 2) - (game.cButtonSize / 2 + Gdx.graphics.getHeight() * 0.02f) - game.cButtonSize) * 0.05f / 2 + game.cButtonSize * 0.3f + (game.cButtonSize * 1.5f - smallButtonActor.getHeight()) / 2);
                smallButtonActor.setVisible(false);
                smallButtonActor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.androidSide.openVK();
                    }
                });
                break;
            case 11://Актер гекс в игре на надписи wellDone
                sprite = game.atlas.createSprite("bighex_light");
                //sprite = game.atlas.createSprite("lvlcomplete_XL");

                smallButtonActor.setSize(game.cButtonSize*0.9f, game.cButtonSize*0.9f*0.8947368421052632f);
                //smallButtonActor.setSize(game.cButtonSize*0.9f, game.cButtonSize*0.9f*0.866025f);
                smallButtonActor.setOrigin(smallButtonActor.getWidth()/2, smallButtonActor.getHeight()/2);
                smallButtonActor.setPosition((Gdx.graphics.getWidth()-Gdx.graphics.getWidth()*0.6f)/2-smallButtonActor.getWidth()/2, Gdx.graphics.getHeight()-smallButtonActor.getHeight()-(game.cButtonSize-smallButtonActor.getHeight())/2);
                smallButtonActor.setVisible(false);

                RotateByAction rotateToActionWellDone = new RotateByAction();
                rotateToActionWellDone.setDuration(2f);
                rotateToActionWellDone.setAmount(-360);

                RepeatAction repeatActionWellDone = new RepeatAction();
                repeatActionWellDone.setAction(rotateToActionWellDone);
                repeatActionWellDone.setCount(RepeatAction.FOREVER);

                smallButtonActor.addAction(repeatActionWellDone);
                break;
            case 12://Кнопка NEXT lvlcomplete
                sprite = game.atlas.createSprite("next_icon");
                smallButtonActor.setSize(Gdx.graphics.getWidth() / 8, Gdx.graphics.getWidth() / 8);
                smallButtonActor.setPosition(Gdx.graphics.getWidth() - smallButtonActor.getWidth(), 0);
                smallButtonActor.setVisible(false);
                smallButtonActor.setName("");
                smallButtonActor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.screenType = 31;
                        if(smallButtonActor.getName().equals("show pack")){
                            game.getGameScreen().showPackComplete();
                            smallButtonActor.setName("");
                        }else {
                            game.getGameScreen().changeLvl();
                        }
                    }
                });
                MoveToAction moveToAction1 = new MoveToAction();
                moveToAction1.setDuration(0.5f);
                moveToAction1.setPosition(Gdx.graphics.getWidth()-smallButtonActor.getWidth()*1.3f,0);

                MoveToAction moveToAction2 = new MoveToAction();
                moveToAction2.setDuration(0.5f);
                moveToAction2.setPosition(Gdx.graphics.getWidth()-smallButtonActor.getWidth(),0);

                SequenceAction seqActionNextButton = new SequenceAction(moveToAction1, moveToAction2);

                RepeatAction repeatActionNext = new RepeatAction();
                repeatActionNext.setAction(seqActionNextButton);
                repeatActionNext.setCount(RepeatAction.FOREVER);

                smallButtonActor.addAction(repeatActionNext);
                break;
            case 13://Кнопка SupportUs mainmenuscreen
                sprite = game.atlas.createSprite("ads_icon");
                smallButtonActor.setSize(game.cButtonSize, game.cButtonSize);
                smallButtonActor.setPosition((Gdx.graphics.getWidth() - smallButtonActor.getWidth()) / 2, Gdx.graphics.getHeight() * 0.02f);
                smallButtonActor.setVisible(true);
                smallButtonActor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        game.getMainMenuScr().setSupportUsScreen();
                    }
                });
                break;
            default:
                sprite = null;
                break;
        }
        smallButtonActor.setOrigin(smallButtonActor.getWidth() / 2, smallButtonActor.getHeight() / 2);
        smallButtonActor.setSprite(sprite);
        return smallButtonActor;
    }
}