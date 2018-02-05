package com.blackhornetworkshop.flowrush;

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

public class FRAssetManager {
    private static AssetManager manager;
    static void dispose(){ manager.dispose();}

    //Graphics
    private static Skin skin;
    private static TiledDrawable spriteBack;
    private static Sprite qCircle;
    private static Sprite stripe, soundOn, soundOff;
    private static TextureAtlas atlas;

    //Audio
    private static Sound tapSound, lvlCompleteSound, packCompleteSound;
    private static Music backgroundMusic;

    private FRAssetManager(){}

    static void loadAssets(){
        assetManagerLoad();

        //Привязываем к переменным ресурсы, для удобства использования
        skin = manager.get("ui/skin.json");
        atlas = skin.getAtlas();
        tapSound = manager.get("sound/tap.ogg");
        packCompleteSound = manager.get("sound/packcomplete.ogg");
        lvlCompleteSound = manager.get("sound/lvlcomplete.ogg");
        backgroundMusic = manager.get("sound/background.ogg");

        //Текстура фона для группы актров паузы
        qCircle = atlas.createSprite("q_circle");

        //Фон-точка
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

        //Background music
        backgroundMusic.setOnCompletionListener(new Music.OnCompletionListener(){
            @Override
            public void onCompletion(Music music) {
                music.play();
                music.setPosition(0.0f); // fix nu player issue
            }
        });
        backgroundMusic.setVolume(0.7f);

        soundOff = atlas.createSprite("soundOff_icon");
        soundOn = atlas.createSprite("soundOn_icon");
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

    public static Sprite getSoundOff() {
        return soundOff;
    }

    public static Sprite getSoundOn() {

        return soundOn;
    }

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

    public static TextureAtlas getAtlas() {
        return atlas;
    }

    public static Sprite getStripe() {
        return stripe;
    }

    public static Sprite getqCircle() {
        return qCircle;
    }

    public static TiledDrawable getSpriteBack() {
        return spriteBack;
    }

    public static Skin getSkin() {
        return skin;
    }

    public static AssetManager getManager() {
        return manager;
    }
}
