package com.blackhornetworkshop.flowrush.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.blackhornetworkshop.flowrush.FlowRush;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //new LwjglApplication(new FlowRush(), config);
        config.width = 450;
        config.height = 800;
        config.title = "Flow Rush";
    }
}
