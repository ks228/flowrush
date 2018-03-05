package com.blackhornet.flowrush.editor.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;

//Created by TScissors.

public class MapActorGroup extends Group {

    public MapActorGroup (ActorListCreator list, TextureAtlas atlas) {

        float xPos;
        float yPos;

        for (int x = 0; x < list.getWidth(); x++) {

            for (int y = 0; y < list.getHeight(); y++) {

                ActorInfo actorInfo = list.getActorInfo(x, y);

                if(x == 0){
                    xPos = x* com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_WIDTH;
                    yPos = (y* com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_HEIGHT)+(com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_HEIGHT/2);
                }else if(x%2==0){
                    xPos = (x * com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_WIDTH)-(x*(com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_WIDTH/4));
                    yPos = y * com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_HEIGHT+(com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_HEIGHT/2);
                } else {
                    xPos = (x * com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_WIDTH)-(x*(com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_WIDTH/4));
                    yPos = (y* com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_HEIGHT);
                }

                MapActor actor = new MapActor(actorInfo, atlas);
                actor.addListener(new MapActorListener(actor));

                actor.setRotation(actorInfo.getPosition()*(-60));

                actor.setBounds(xPos, yPos, com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_WIDTH, com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_HEIGHT);

                actor.setOriginX(com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_WIDTH / 2);
                actor.setOriginY(com.blackhornet.flowrush.editor.controller.FlowRushEditor.HEX_HEIGHT / 2);

                addActor(actor);
            }
        }
    }
}




