package com.blackhornet.flowrush.editor.model;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackhornet.flowrush.editor.view.EditorScreen;

//Created by TScissors.

class BrushActorListener extends ClickListener {

    private com.blackhornet.flowrush.editor.model.BrushActor actor;

    BrushActorListener(com.blackhornet.flowrush.editor.model.BrushActor actor) {
        this.actor = actor;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        System.out.println(" Index: " +actor.getIndex());
        EditorScreen.indexForBrush = actor.getIndex();

        EditorScreen.selectedBrush.setLabelSprite(actor.getSprite());

        return true;
    }
}
