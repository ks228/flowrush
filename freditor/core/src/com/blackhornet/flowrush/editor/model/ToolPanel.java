package com.blackhornet.flowrush.editor.model;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;

//Created by TScissors.

public class ToolPanel extends Group{

    public ToolPanel() {
        Sprite sprite;

        System.out.println("ToolPanel width " + 8 + " height " + 5);

        for(int x = 1, xPos = 0, yPos = 0; x < 51; x++){

            if (!(x > 12 && x < 25)) {
                sprite = com.blackhornet.flowrush.editor.controller.FlowRushEditor.getMainAtlas().createSprite("hex", x);

                com.blackhornet.flowrush.editor.model.BrushActor actor = new com.blackhornet.flowrush.editor.model.BrushActor();

                actor.addListener(new com.blackhornet.flowrush.editor.model.BrushActorListener(actor));
                actor.setSprite(sprite);

                actor.setBounds(xPos * sprite.getWidth(), yPos * sprite.getHeight(), sprite.getWidth(), sprite.getHeight());
                actor.setIndex(x);

                System.out.println("Brush type: " + actor.getIndex() + " created!");
                addActor(actor);
                yPos++;
                if(x%13 == 0){
                    xPos++;
                    yPos = 0;
                }
            }
        }
    }
}


