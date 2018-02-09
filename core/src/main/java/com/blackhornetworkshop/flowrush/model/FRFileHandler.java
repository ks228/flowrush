package com.blackhornetworkshop.flowrush.model;

//Created by TScissors

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.blackhornetworkshop.flowrush.model.ex.FlowRushException;
import com.blackhornetworkshop.flowrush.view.FlowRush;

public class FRFileHandler {

    public static Packs loadPacks() {
        try {
            return FlowRush.getGson().fromJson(Gdx.files.internal("lvls/levels.json").reader(), Packs.class);
        } catch (Exception ex) {
            throw new FlowRushException("Error loading file levels.json", ex);
        }
    }

    public static GamePreferences loadPreferences() {
        try {
            return FlowRush.getGson().fromJson(Gdx.files.local("preferences.json").reader(), GamePreferences.class);
        } catch (Exception ex) {
            throw new FlowRushException("Error loading file preferences.json", ex);
        }
    }

    public static SavedGame loadSavedGame() {
        try {
            return FlowRush.getGson().fromJson(Gdx.files.local("save.json").reader(), SavedGame.class);
        } catch (Exception ex) {
            throw new FlowRushException("Error loading file save.json", ex);
        }
    }

    static void savePreferences() {
        try {
            String string = FlowRush.getGson().toJson(FlowRush.getPreferences());
            FileHandle file = Gdx.files.local("preferences.json");
            file.writeString(string, false);
            FlowRush.logDebug("File preferences.json is saved");
        } catch (Exception ex) {
            throw new FlowRushException("Error saving file preferences.json", ex);
        }
    }

    public static void saveGame() {
        try {
            String string = FlowRush.getGson().toJson(FlowRush.getSave());
            FileHandle file = Gdx.files.local("save.json");
            file.writeString(string, false);
            FlowRush.logDebug("File save.json is saved");
        } catch (Exception ex) {
            throw new FlowRushException("Error saving file save.json", ex);
        }
    }
}
