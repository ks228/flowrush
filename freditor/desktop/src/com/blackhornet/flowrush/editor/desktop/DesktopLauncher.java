package com.blackhornet.flowrush.editor.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blackhornet.flowrush.editor.controller.FlowRushEditor;

public class DesktopLauncher {
    public static void main (String[] arg) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "FlowRushEditor";
        config.height = 800;
        config.width = 1000;
        config.addIcon("icon.png", Files.FileType.Internal);

        new LwjglApplication(FlowRushEditor.getInstance(), config);
    }
}

