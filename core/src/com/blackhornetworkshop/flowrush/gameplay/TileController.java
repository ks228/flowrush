package com.blackhornetworkshop.flowrush.gameplay;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

//Created by TScissors. Класс с методами для управления тайлами

public class TileController {
    public static void setType(TileActor actor) {
        if (actor.getIndex() < 13) {
            actor.setType(actor.getIndex());
        } else if (actor.getIndex() >= 13 && actor.getIndex() < 25) {
            actor.setType(actor.getIndex() - 12);
        } else if (actor.getIndex() >= 25 && actor.getIndex() < 38) {
            actor.setType(actor.getIndex() - 24);
        } else if (actor.getIndex() >= 38 && actor.getIndex() < 51) {
            actor.setType(actor.getIndex() - 37);
        }
    }

    public static void moveSources(TileActor actor) {
        boolean[] sourceArray = actor.getSourceArray();
        boolean val = sourceArray[0];
        sourceArray[0] = sourceArray[sourceArray.length - 1];
        for (int j = 0; j < sourceArray.length - 1; ++j) {
            boolean val2 = sourceArray[j + 1];
            sourceArray[j + 1] = val;
            val = val2;
        }
        actor.setSources(sourceArray);
    }

    public static void setHexbackTouchOff(TileActor actor, TextureAtlas atlas) {
        if (actor.getIndex() < 25) {
            actor.setHexback(atlas.createSprite("backhex"));
        } else if (actor.getIndex() > 24) {
            actor.setHexback(atlas.createSprite("backhexS"));
        }
    }

    public static void setHexbackTouchOn(TileActor actor, TextureAtlas atlas) {
        if (actor.getIndex() < 25) {
            actor.setHexback(atlas.createSprite("backhex_touched"));
        } else if (actor.getIndex() > 24) {
            actor.setHexback(atlas.createSprite("backhex_touchedS"));
        }
    }

    static void setPowerOn(TileActor actor, TextureAtlas atlas) {
        actor.setPowerOn();
        if (actor.getIndex() < 13) {
            actor.setIndex(actor.getIndex() + 12);
        } else if (actor.getIndex() > 24 & actor.getIndex() < 38) {
            actor.setIndex(actor.getIndex() + 13);
        }
        actor.setSprite(atlas.createSprite("hex", actor.getIndex()));
        actor.getActorInfo().setIndex(actor.getIndex());
        if (actor.getInclude() == 1) {
            actor.setIcon(atlas.createSprite("iconMP"));
        } else if (actor.getInclude() == 2) {
            actor.setIcon(atlas.createSprite("iconEP"));
        } else if (actor.getInclude() == 3) {
            actor.setIcon(atlas.createSprite("iconDP"));
        }
    }

    static void setPowerOff(TileActor actor, boolean iconWhite, TextureAtlas atlas) {
        actor.setPowerOff();
        if (actor.getIndex() > 12 & actor.getIndex() < 25) {
            actor.setIndex(actor.getIndex() - 12);
        } else if (actor.getIndex() > 37 & actor.getIndex() < 51) {
            actor.setIndex(actor.getIndex() - 13);
        }
        actor.setSprite(atlas.createSprite("hex", actor.getIndex()));
        actor.getActorInfo().setIndex(actor.getIndex());
        if (actor.getInclude() == 1) {
            actor.setIcon(atlas.createSprite("iconMP"));
        } else if (actor.getInclude() == 2) {
            if (iconWhite) {
                actor.setIcon(atlas.createSprite("iconEW"));
            } else {
                actor.setIcon(atlas.createSprite("iconE"));
            }
        } else if (actor.getInclude() == 3) {
            actor.setIcon(atlas.createSprite("iconD"));
        }
    }

    public static void animIcon(TileActor actor, boolean iconWhite, TextureAtlas atlas) {
        if (iconWhite) {
            actor.setIcon(atlas.createSprite("iconE"));
        } else {
            actor.setIcon(atlas.createSprite("iconEW"));
        }
    }

    public static void setAngle(TileActor actor, float angle) {
        actor.setAngle(angle);
        actor.setRotatePosition(actor.getRotatePosition() + 1);
        if (actor.getRotatePosition() == 6) {
            actor.setRotatePosition(0);
        }
        actor.getActorInfo().setPosition(actor.getRotatePosition());
    }
}
