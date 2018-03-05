package com.blackhornet.flowrush.editor.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.blackhornet.flowrush.editor.controller.FlowRushEditor;
import com.blackhornet.flowrush.editor.model.ActorInfo;
import com.blackhornet.flowrush.editor.model.Packer;

import java.util.ArrayList;

//Created by TScissors.

public class SettingsScreen implements Screen {

    private Stage stage;
    private TextButton createButton;
    private TextButton loadButton;

    private TextField widthField;
    private TextField heightField;
    private TextField createNameField;
    private TextField loadNameField;

    private Label messageLabel;

    public SettingsScreen() {
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        createNameField = new TextField("", FlowRushEditor.getSkin(), "default");
        createNameField.setMessageText("Map name");
        loadNameField = new TextField("", FlowRushEditor.getSkin(), "default");
        loadNameField.setMessageText("Map name");


        widthField = new TextField("", FlowRushEditor.getSkin(), "default");
        widthField.setMessageText("Width");
        heightField = new TextField("", FlowRushEditor.getSkin(), "default");
        heightField.setMessageText("Height");

        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());


        TextButton menuLoadButton = new TextButton("Load map", FlowRushEditor.getSkin(), "default");
        menuLoadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openLoadingInterface();
            }
        });

        TextButton menuCreateButton = new TextButton("New map", FlowRushEditor.getSkin(), "default");
        menuCreateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openCreatingInterface();
            }
        });

        createButton = new TextButton("Create", FlowRushEditor.getSkin(), "default");
        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {

                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    if (width > 6 || width < 2) throw new NumberFormatException();
                    if (height > 8 || height < 2) throw new NumberFormatException();

                    String name = createNameField.getText();

                    FlowRushEditor.getInstance().setScreen(new EditorScreen(width, height, name));

                } catch (NumberFormatException e) {
                    messageLabel.setText("Wrong values!");
                }
            }
        });

        loadButton = new TextButton("Load", FlowRushEditor.getSkin(), "default");
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ArrayList<ArrayList<ActorInfo>> actorListArray;
                Gson gson = new Gson();
                try {
                    String name = loadNameField.getText();
                    actorListArray = gson.fromJson(Gdx.files.internal(name + ".json").reader(), new TypeToken<ArrayList<ArrayList<ActorInfo>>>() {}.getType());
                    FlowRushEditor.getInstance().setScreen(new EditorScreen(actorListArray, name));
                    dispose();
                } catch (GdxRuntimeException ex) {
                    messageLabel.setText("Not found!");
                }
            }
        });

        TextButton createPackFilesButton = new TextButton("Create packs", FlowRushEditor.getSkin(), "default");
        createPackFilesButton.setSize(250, 60);
        createPackFilesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideAllFields();
                Packer packer = new Packer();
                packer.fillUpPacks();
                messageLabel.setText("Completed!");
            }
        });

        table.padTop(30);
        table.row().minHeight(60).minWidth(250).space(10);
        table.add(menuCreateButton);
        table.add(menuLoadButton);
        table.add(createPackFilesButton);
        table.row().minHeight(60).minWidth(250).space(10);
        table.add(widthField);
        table.add(loadNameField);
        table.row().minHeight(60).minWidth(250).space(10);
        table.add(heightField);
        table.add(loadButton);
        table.row().minHeight(60).minWidth(250).space(10);
        table.add(createNameField);
        table.row().minHeight(60).minWidth(250).space(10);
        table.add(createButton);

        hideAllFields();

        messageLabel = new Label("Let's begin!", FlowRushEditor.getSkin(), "default");
        messageLabel.setAlignment(Align.center);
        messageLabel.setSize(500, 100);
        messageLabel.setPosition((Gdx.graphics.getWidth()- messageLabel.getWidth())/2, Gdx.graphics.getHeight() - 400 - messageLabel.getHeight());

        String info = "Level input-output dir: current freditor.jar location\nPack input dir's: packs/pack1, ..., packs/pack5";
        Label infoLabel = new Label(info, FlowRushEditor.getSkin(), "default");
        infoLabel.setAlignment(Align.center);
        infoLabel.setWidth(700);
        infoLabel.setHeight(150);
        infoLabel.setPosition((Gdx.graphics.getWidth()-infoLabel.getWidth())/2, 30);
        stage.addActor(table);
        stage.addActor(messageLabel);
        stage.addActor(infoLabel);
    }

    private void openLoadingInterface() {
        messageLabel.setText("Enter name of the map");
        widthField.setVisible(false);
        heightField.setVisible(false);
        createNameField.setVisible(false);
        createButton.setVisible(false);

        messageLabel.clear();
        loadNameField.setVisible(true);
        loadButton.setVisible(true);
    }

    private void openCreatingInterface() {
        loadNameField.setVisible(false);
        loadButton.setVisible(false);

        widthField.setVisible(true);
        heightField.setVisible(true);
        createNameField.setVisible(true);
        createButton.setVisible(true);
        messageLabel.setText("Max size: 6x8\n Min size: 2x2");
    }

    private void hideAllFields() {
        widthField.setVisible(false);
        heightField.setVisible(false);
        createNameField.setVisible(false);
        loadNameField.setVisible(false);
        createButton.setVisible(false);
        loadButton.setVisible(false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.86f, 0.86f, 0.86f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("SettingsScreen dispose() called");
        stage.dispose();
    }
}

