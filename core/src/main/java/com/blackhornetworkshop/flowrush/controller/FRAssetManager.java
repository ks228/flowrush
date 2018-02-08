package com.blackhornetworkshop.flowrush.controller;

//Created by TScissors

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.ObjectMap;
import com.blackhornetworkshop.flowrush.model.FRConstants;
import com.blackhornetworkshop.flowrush.model.ex.FlowRushException;
import com.blackhornetworkshop.flowrush.view.FlowRush;

import java.util.ArrayList;

import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_HEIGHT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_WIDTH;

public class FRAssetManager {
    private static AssetManager manager;
    public static void dispose(){ manager.dispose();}

    private static Skin skin;
    private static TextureAtlas atlas;

    //Audio
    private static Sound tapSound, lvlCompleteSound, packCompleteSound;
    private static Music backgroundMusic;

    //Sprites
    private static TiledDrawable spriteBack;
    private static Sprite logoBHW;
    private static Sprite logoFR;
    private static Sprite quadrantSprite;
    private static Sprite stripe, soundOn, soundOff;
    private static Sprite hexBackgroundOffWithSource, hexBackgroundOn;
    private static Sprite hexBackgroundOff, hexBackgroundOnWithSource;
    private static Sprite iconSource;
    private static Sprite iconDoveOn, iconDoveOff;
    private static Sprite iconPointOn, iconPointOff, iconPointWhite;
    private static Sprite lockSprite;
    private static ArrayList<Sprite> hexes;
    private static Sprite backgroundWhite;
    private FRAssetManager(){}

    public static void loadAssets(){
        assetManagerLoad();

        skin = manager.get("ui/skin.json");
        atlas = skin.getAtlas();

        createSounds();
        createSprites();
    }

    private static void createSprites(){
        //Game
        hexBackgroundOff = atlas.createSprite("backhex");
        hexBackgroundOffWithSource = atlas.createSprite("backhexS");
        hexBackgroundOn = atlas.createSprite("backhex_touched");
        hexBackgroundOnWithSource = atlas.createSprite("backhex_touchedS");

        iconSource = atlas.createSprite("iconMP");
        iconDoveOff = atlas.createSprite("iconD");
        iconDoveOn = atlas.createSprite("iconDP");
        iconPointOff = atlas.createSprite("iconE");
        iconPointWhite = atlas.createSprite("iconEW");
        iconPointOn = atlas.createSprite("iconEP");
        hexes = new ArrayList<>();
        for(int x = 1; x < 51; x++){
            hexes.add(atlas.createSprite("hex", x));
        }

        //UI
        soundOff = atlas.createSprite("soundOff_icon");
        soundOn = atlas.createSprite("soundOn_icon");

        quadrantSprite = atlas.createSprite("q_circle");

        lockSprite = atlas.createSprite("lock");

        backgroundWhite = atlas.createSprite("back_white");

        if(Gdx.graphics.getWidth()<500) {
            spriteBack = new TiledDrawable(atlas.findRegion("point"));
            stripe = atlas.createSprite("animation");
        }else if(Gdx.graphics.getWidth()<900){
            spriteBack = new TiledDrawable(atlas.findRegion("point2"));
            stripe = atlas.createSprite("animation2");
        }else if(Gdx.graphics.getWidth()<1300){
            spriteBack = new TiledDrawable(atlas.findRegion("point3"));
            stripe = atlas.createSprite("animation3");
        }else{
            spriteBack = new TiledDrawable(atlas.findRegion("point4"));
            stripe = atlas.createSprite("animation4");
        }

        logoBHW = atlas.createSprite("h_logo");
        logoBHW.setSize(SCREEN_WIDTH * 0.6f, SCREEN_WIDTH * 0.6f*0.79f);
        logoBHW.setPosition((SCREEN_WIDTH - logoBHW.getWidth()) / 2, (SCREEN_HEIGHT - logoBHW.getHeight()) / 2);

        logoFR = atlas.createSprite("fr_logo");
        logoFR.setSize(SCREEN_WIDTH * 0.8f, SCREEN_WIDTH * 0.8f * 0.75f);
        logoFR.setPosition((SCREEN_WIDTH - logoFR.getWidth()) / 2, (SCREEN_HEIGHT - logoFR.getHeight()*0.9f)/2);
    }

    private static void createSounds(){
        tapSound = manager.get("sound/tap.ogg");
        packCompleteSound = manager.get("sound/packcomplete.ogg");
        lvlCompleteSound = manager.get("sound/lvlcomplete.ogg");
        backgroundMusic = manager.get("sound/background.ogg");

        backgroundMusic.setOnCompletionListener(new Music.OnCompletionListener(){
            @Override
            public void onCompletion(Music music) {
                music.play();
                music.setPosition(0.0f); // fix nu player issue
            }
        });
        backgroundMusic.setVolume(0.7f);
    }

    private static void assetManagerLoad(){
        manager = new AssetManager();
        InternalFileHandleResolver resolver = new InternalFileHandleResolver();

        FreeTypeFontGenerator.setMaxTextureSize(FreeTypeFontGenerator.NO_MAXIMUM);
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "ui/Iceberg-Regular.ttf";
        param.fontParameters.size = (int)(FRConstants.FONT_SIZE_XXL);
        manager.load("fontLarge.ttf", BitmapFont.class, param);

        FreetypeFontLoader.FreeTypeFontLoaderParameter param2 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param2.fontFileName = "ui/Iceberg-Regular.ttf";
        param2.fontParameters.size = (int)(FRConstants.FONT_SIZE_XL);
        manager.load("fontMid.ttf", BitmapFont.class, param2);

        FreetypeFontLoader.FreeTypeFontLoaderParameter param3 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param3.fontFileName = "ui/Iceberg-Regular.ttf";
        param3.fontParameters.size = (int)(FRConstants.FONT_SIZE_X);
        manager.load("fontSmall.ttf", BitmapFont.class, param3);

        manager.finishLoadingAsset("fontLarge.ttf");
        manager.finishLoadingAsset("fontMid.ttf");
        manager.finishLoadingAsset("fontSmall.ttf");
        BitmapFont fontLarge = manager.get("fontLarge.ttf");
        BitmapFont fontMid = manager.get("fontMid.ttf");
        BitmapFont fontSmall = manager.get("fontSmall.ttf");
        ObjectMap<String, Object> oMap = new ObjectMap<String, Object>();
        oMap.put("fontLarge", fontLarge);
        oMap.put("fontMid", fontMid);
        oMap.put("fontSmall", fontSmall);
        manager.load("ui/skin.json", Skin.class, new SkinLoader.SkinParameter("texture/atlas.atlas", oMap));      //Загружаем СКИН и с ним атлас интерфейса

        manager.load("sound/tap.ogg", Sound.class); //Загружаем звук тапа
        manager.load("sound/lvlcomplete.ogg", Sound.class); //Звук лвлкомплет
        manager.load("sound/background.ogg", Music.class); //Загружаем фоновую музыку
        manager.load("sound/packcomplete.ogg", Sound.class); //Звук паккомплит

        manager.finishLoading();
    }

    public static TextureAtlas getAtlas() {
        return atlas;
    }

    public static Skin getSkin() {
        return skin;
    }


    //Sounds

    public static Music getBackgroundMusic() {
        return backgroundMusic;
    }

    public static Sound getPackCompleteSound() {
        return packCompleteSound;
    }

    public static Sound getLvlCompleteSound() {
        return lvlCompleteSound;
    }

    public static Sound getTapSound() {
        return tapSound;
    }


    //Sprites

    public static Sprite getSoundOff() {
        return soundOff;
    }

    public static Sprite getSoundOn() {
        return soundOn;
    }

    public static TiledDrawable getSpriteBack() {
        return spriteBack;
    }

    public static Sprite getHexBackgroundOffWithSource() {
        return hexBackgroundOffWithSource;
    }

    public static Sprite getHexBackgroundOff() {
        return hexBackgroundOff;
    }
    public static Sprite getHexBackgroundOnWithSource() {
        return hexBackgroundOnWithSource;
    }

    public static Sprite getHexBackgroundOn() {
        return hexBackgroundOn;
    }

    public static Sprite getIconSource() {
        return iconSource;
    }

    public static Sprite getIconDoveOn() {
        return iconDoveOn;
    }

    public static Sprite getIconDoveOff() {
        return iconDoveOff;
    }

    public static Sprite getIconPointOn() {
        return iconPointOn;
    }

    public static Sprite getIconPointOff() {
        return iconPointOff;
    }

    public static Sprite getIconPointWhite() {
        return iconPointWhite;
    }

    public static Sprite getLockSprite() {
        return lockSprite;
    }

    public static Sprite getStripe() {
        return stripe;
    }

    public static Sprite getQuadrantSprite() {
        return quadrantSprite;
    }

    public static Sprite getLogoFR() {
        return logoFR;
    }

    public static Sprite getLogoBHW() {
        return logoBHW;
    }

    public static Sprite getHexSprite(int index) throws FlowRushException{
        if( index < 1 || index > 50) FlowRush.logError("Asset manager error",new FlowRushException("Wrong index!"));
        return hexes.get(index - 1);
    }

    public static Sprite getBackgroundWhite() {
        return backgroundWhite;
    }
}
