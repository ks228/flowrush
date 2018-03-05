package com.blackhornet.flowrush.editor.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.Gson;
import com.blackhornet.flowrush.editor.controller.FlowRushEditor;
import com.blackhornet.flowrush.editor.model.MapActorGroup;
import com.blackhornet.flowrush.editor.model.LabelSelectedBrushActor;
import com.blackhornet.flowrush.editor.model.ActorInfo;
import com.blackhornet.flowrush.editor.model.ToolPanel;
import com.blackhornet.flowrush.editor.model.ActorListCreator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//Created by TScissors.

public class EditorScreen implements Screen {

    private static ActorListCreator actorList;
    private Stage stage;
    public static Sprite hexTemp;
    public static int indexForBrush;
    public static LabelSelectedBrushActor selectedBrush;
    private int mapWidth, mapHeight;
    private static String mapName;
    private Label messageLabel;

    EditorScreen(int mapWidth, int mapHeight, String mapName) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        EditorScreen.mapName = mapName;

        actorList = new ActorListCreator(mapWidth, mapHeight);
    }

    EditorScreen(ArrayList<ArrayList<ActorInfo>> actorListArray, String mapName) {

        actorList = new ActorListCreator(actorListArray.size(), actorListArray.get(0).size());
        actorList.setActorList(actorListArray);

        mapWidth = actorList.getWidth();
        mapHeight = actorList.getHeight();
        EditorScreen.mapName = mapName;
    }

    @Override
    public void show(){
        hexTemp = new Sprite(FlowRushEditor.getMainAtlas().createSprite("backhex"));

        stage = new Stage(new ScreenViewport());

        System.out.println("Map size: " + mapWidth + "x" + mapHeight);

        MapActorGroup mapActorGroup = new MapActorGroup(actorList, FlowRushEditor.getMainAtlas());
        float mapGroupHeight = mapHeight*FlowRushEditor.HEX_HEIGHT*0.5f;
        mapActorGroup.setScale(0.5f, 0.5f);
        mapActorGroup.setPosition(220, (Gdx.graphics.getHeight()-mapGroupHeight)/2);
        selectedBrush = new LabelSelectedBrushActor();

        selectedBrush.setBounds(Gdx.graphics.getWidth()-250f, 200, 100, 88);

        ToolPanel toolPanel = new ToolPanel();
        toolPanel.setPosition(10, 10);
        toolPanel.setScale(0.3f, 0.3f);

        TextButton saveButton = new TextButton("Save", FlowRushEditor.getSkin(), "default");
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gson gson = new Gson();
                String string = gson.toJson(EditorScreen.actorList.getActorList());

                try{
                    FileWriter fileWriter = new FileWriter(EditorScreen.mapName+ ".json");
                    fileWriter.write(string);
                    fileWriter.flush();
                    fileWriter.close();
                    messageLabel.setText("Map saved");
                } catch (IOException ex) {
                    messageLabel.setText("Error!");
                    ex.getStackTrace();
                }
            }
        });

        TextButton menuButton = new TextButton("Back to menu", FlowRushEditor.getSkin(), "default");
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FlowRushEditor.getInstance().setScreen(new SettingsScreen());
            }
        });

        Label infoLabel = new Label("Shift+click: place tile\nCtrl+click: remove tile\nD+click: dove tile\nR+click: switch rotate", FlowRushEditor.getSkin(), "default");
        messageLabel = new Label("Choose a tile", FlowRushEditor.getSkin(), "default");

        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.right | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        table.row().minHeight(50).minWidth(300);
        table.add(saveButton);
        table.row().minHeight(50).minWidth(300);
        table.add(menuButton);
        table.row().minHeight(50).minWidth(300).spaceTop(10);
        table.add(messageLabel);
        table.row().minHeight(200).minWidth(300).spaceTop(10);
        table.add(infoLabel);

        stage.addActor(table);

        stage.addActor(mapActorGroup);
        stage.addActor(toolPanel);
        stage.addActor(selectedBrush);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.86f,0.86f,0.86f, 1);
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

    }

    @Override
    public void dispose() {

    }
}
