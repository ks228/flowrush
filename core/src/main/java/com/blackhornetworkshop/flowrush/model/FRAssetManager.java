package com.blackhornetworkshop.flowrush.model;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.ObjectMap;
import com.blackhornetworkshop.flowrush.view.FlowRush;

import java.util.HashMap;

import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_HEIGHT;
import static com.blackhornetworkshop.flowrush.model.FRConstants.SCREEN_WIDTH;

public class FRAssetManager {

    private static AssetManager manager;
    private static Skin daySkin, nightSkin;
    private static TextureAtlas dayAtlas, nightAtlas;
    private static HashMap<String, Sprite> daySpriteHashMap, nightSpriteHashMap;
    private static HashMap<String, Label.LabelStyle> dayLabelStyleHashMap, nightLabelStyleHashMap;
    private static HashMap<String, TextButton.TextButtonStyle> dayTextbuttonStyleHashMap, nightTextbuttonStyleHashMap;

    public static void dispose() {
        FlowRush.logDebug("FRAssetManager dispose()");
        daySkin.remove("fontLarge", BitmapFont.class);// REMOVE IS IMPORTANT!
        daySkin.remove("fontMid", BitmapFont.class);
        daySkin.remove("fontSmall", BitmapFont.class);
        nightSkin.remove("fontLarge", BitmapFont.class);
        nightSkin.remove("fontMid", BitmapFont.class);
        nightSkin.remove("fontSmall", BitmapFont.class);
        manager.dispose();
    }

    private static TextureAtlas commonAtlas;

    //Audio
    private static Sound tapSound, lvlCompleteSound, packCompleteSound;
    private static Music backgroundMusic;

    //Sprites
    private static TiledDrawable dayBackgroundDot, nightBackgroundDot;
    private static Sprite dayQuadrantSprite, nightQuadrantSprite;
    private static Sprite fbLogo, twLogo, vkLogo;
    private static Sprite logoBHW, logoFR;
    private static Sprite dayBackgroundStripe, nightBackgroundStripe;
    private static Sprite dayBackgroundWhite, nightBackgroundWhite;
    private FRAssetManager() {

    }

    public static void loadAssets() {
        FlowRush.logDebug("FRAssetManager loadAssets() method called");

        daySpriteHashMap = new HashMap<>();
        dayLabelStyleHashMap = new HashMap<>();
        dayTextbuttonStyleHashMap = new HashMap<>();

        nightSpriteHashMap = new HashMap<>();
        nightLabelStyleHashMap = new HashMap<>();
        nightTextbuttonStyleHashMap = new HashMap<>();

        assetManagerLoad();

        nightSkin = manager.get("ui/night-skin.json");
        daySkin = manager.get("ui/day-skin.json");

        dayAtlas = daySkin.getAtlas();
        nightAtlas = nightSkin.getAtlas();
        commonAtlas = manager.get("texture/common-atlas.atlas");

        loadStyles();
        createSounds();
        createSprites();
        createLogos();
    }

    private static void assetManagerLoad() {
        manager = new AssetManager();
        InternalFileHandleResolver resolver = new InternalFileHandleResolver();

        FreeTypeFontGenerator.setMaxTextureSize(FreeTypeFontGenerator.NO_MAXIMUM);
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "ui/Iceberg-Regular.ttf";
        param.fontParameters.size = (int) (FRConstants.FONT_SIZE_XXL);
        manager.load("fontLarge.ttf", BitmapFont.class, param);

        FreetypeFontLoader.FreeTypeFontLoaderParameter param2 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param2.fontFileName = "ui/Iceberg-Regular.ttf";
        param2.fontParameters.size = (int) (FRConstants.FONT_SIZE_XL);
        manager.load("fontMid.ttf", BitmapFont.class, param2);

        FreetypeFontLoader.FreeTypeFontLoaderParameter param3 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param3.fontFileName = "ui/Iceberg-Regular.ttf";
        param3.fontParameters.size = (int) (FRConstants.FONT_SIZE_X);
        manager.load("fontSmall.ttf", BitmapFont.class, param3);

        manager.finishLoadingAsset("fontLarge.ttf");
        manager.finishLoadingAsset("fontMid.ttf");
        manager.finishLoadingAsset("fontSmall.ttf");
        BitmapFont fontLarge = manager.get("fontLarge.ttf");
        BitmapFont fontMid = manager.get("fontMid.ttf");
        BitmapFont fontSmall = manager.get("fontSmall.ttf");
        ObjectMap<String, Object> oMap = new ObjectMap<>();
        oMap.put("fontLarge", fontLarge);
        oMap.put("fontMid", fontMid);
        oMap.put("fontSmall", fontSmall);

        manager.load("ui/night-skin.json", Skin.class, new SkinLoader.SkinParameter("texture/night-atlas.atlas", oMap));
        manager.load("ui/day-skin.json", Skin.class, new SkinLoader.SkinParameter("texture/day-atlas.atlas", oMap));
        manager.load("texture/common-atlas.atlas", TextureAtlas.class);

        manager.load("sound/tap.ogg", Sound.class);
        manager.load("sound/lvlcomplete.ogg", Sound.class);
        manager.load("sound/background.ogg", Music.class);
        manager.load("sound/packcomplete.ogg", Sound.class);

        manager.finishLoading();
    }

    public static Sprite getSprite(String name){
        if(FlowRush.getPreferences().isNightMode()){
            return nightSpriteHashMap.get(name);
        }else{
            return daySpriteHashMap.get(name);
        }
    }

    private static void loadStyles(){
        dayLabelStyleHashMap.put("greyfont", daySkin.get("greyfont", Label.LabelStyle.class));
        dayLabelStyleHashMap.put("default", daySkin.get("default", Label.LabelStyle.class));
        dayLabelStyleHashMap.put("alphawhite", daySkin.get("alphawhite", Label.LabelStyle.class));
        dayLabelStyleHashMap.put("darkblue", daySkin.get("darkblue", Label.LabelStyle.class));
        dayLabelStyleHashMap.put("darkbluesmall", daySkin.get("darkbluesmall", Label.LabelStyle.class));
        dayLabelStyleHashMap.put("greyfont", daySkin.get("greyfont", Label.LabelStyle.class));


        nightLabelStyleHashMap.put("greyfont", nightSkin.get("greyfont", Label.LabelStyle.class));
        nightLabelStyleHashMap.put("default", nightSkin.get("default", Label.LabelStyle.class));
        nightLabelStyleHashMap.put("alphawhite", nightSkin.get("alphawhite", Label.LabelStyle.class));
        nightLabelStyleHashMap.put("darkblue", nightSkin.get("darkblue", Label.LabelStyle.class));
        nightLabelStyleHashMap.put("darkbluesmall", nightSkin.get("darkbluesmall", Label.LabelStyle.class));
        nightLabelStyleHashMap.put("greyfont", nightSkin.get("greyfont", Label.LabelStyle.class));



        dayTextbuttonStyleHashMap.put("darkblue", daySkin.get("darkblue", TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put("white", daySkin.get("white", TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put("playbutton", daySkin.get("playbutton", TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put("lightblue", daySkin.get("lightblue", TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put("default", daySkin.get("default", TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put("bordersmall", daySkin.get("bordersmall", TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put("whitesmall", daySkin.get("whitesmall", TextButton.TextButtonStyle.class));


        nightTextbuttonStyleHashMap.put("darkblue", nightSkin.get("darkblue", TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put("white", nightSkin.get("white", TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put("playbutton", nightSkin.get("playbutton", TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put("lightblue", nightSkin.get("lightblue", TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put("default", nightSkin.get("default", TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put("bordersmall", nightSkin.get("bordersmall", TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put("whitesmall", nightSkin.get("whitesmall", TextButton.TextButtonStyle.class));

    }

    private static void createSprites(){
        daySpriteHashMap.put("background_hex", dayAtlas.createSprite("backhex"));
        nightSpriteHashMap.put("background_hex", nightAtlas.createSprite("backhex"));

        daySpriteHashMap.put("background_hex_source", dayAtlas.createSprite("backhexS"));
        nightSpriteHashMap.put("background_hex_source", nightAtlas.createSprite("backhexS"));

        daySpriteHashMap.put("background_hex_touched", dayAtlas.createSprite("backhex_touched"));
        nightSpriteHashMap.put("background_hex_touched", dayAtlas.createSprite("backhex_touched"));

        daySpriteHashMap.put("background_hex_touched_source", nightAtlas.createSprite("backhex_touchedS"));
        nightSpriteHashMap.put("background_hex_touched_source", nightAtlas.createSprite("backhex_touchedS"));

        daySpriteHashMap.put("source_icon", dayAtlas.createSprite("iconMP"));
        daySpriteHashMap.put("dove_icon_off", dayAtlas.createSprite("iconD"));
        daySpriteHashMap.put("dove_icon_on", dayAtlas.createSprite("iconDP"));
        daySpriteHashMap.put("point_icon_off", dayAtlas.createSprite("iconE"));
        daySpriteHashMap.put("point_icon_white", dayAtlas.createSprite("iconEW"));
        daySpriteHashMap.put("point_icon_on", dayAtlas.createSprite("iconEP"));

        nightSpriteHashMap.put("source_icon", nightAtlas.createSprite("iconMP"));
        nightSpriteHashMap.put("dove_icon_off", nightAtlas.createSprite("iconD"));
        nightSpriteHashMap.put("dove_icon_on", nightAtlas.createSprite("iconDP"));
        nightSpriteHashMap.put("point_icon_off", nightAtlas.createSprite("iconE"));
        nightSpriteHashMap.put("point_icon_white", nightAtlas.createSprite("iconEW"));
        nightSpriteHashMap.put("point_icon_on", nightAtlas.createSprite("iconEP"));

        for (int x = 1; x < 51; x++) {
            daySpriteHashMap.put("hex"+x, dayAtlas.createSprite("hex", x));
        }

        for (int x = 1; x < 51; x++) {
            nightSpriteHashMap.put("hex"+x, nightAtlas.createSprite("hex", x));
        }

        daySpriteHashMap.put("close_icon", dayAtlas.createSprite("close_icon"));
        daySpriteHashMap.put("daynight", dayAtlas.createSprite("daynight"));
        daySpriteHashMap.put("soundOff", dayAtlas.createSprite("soundOff_icon"));
        daySpriteHashMap.put("soundOn", dayAtlas.createSprite("soundOn_icon"));
        daySpriteHashMap.put("q_circle", dayAtlas.createSprite("q_circle"));
        daySpriteHashMap.put("lock", dayAtlas.createSprite("lock"));
        daySpriteHashMap.put("back_white", dayAtlas.createSprite("back_white"));
        daySpriteHashMap.put("pause_icon", dayAtlas.createSprite("pause_icon"));
        daySpriteHashMap.put("back_icon", dayAtlas.createSprite("back_icon"));
        daySpriteHashMap.put("restart_icon", dayAtlas.createSprite("restart_icon"));
        daySpriteHashMap.put("mmenu_icon", dayAtlas.createSprite("mmenu_icon"));
        daySpriteHashMap.put("authors_icon", dayAtlas.createSprite("authors_icon"));
        daySpriteHashMap.put("bighex_light", dayAtlas.createSprite("bighex_light"));
        daySpriteHashMap.put("bighex_dark", dayAtlas.createSprite("bighex_dark"));
        daySpriteHashMap.put("next_icon", dayAtlas.createSprite("next_icon"));
        daySpriteHashMap.put("ads_icon", dayAtlas.createSprite("ads_icon"));
        daySpriteHashMap.put("controller", dayAtlas.createSprite("controller"));
        daySpriteHashMap.put("animbackhex", dayAtlas.createSprite("animbackhex"));

        nightSpriteHashMap.put("close_icon", nightAtlas.createSprite("close_icon"));
        nightSpriteHashMap.put("daynight", nightAtlas.createSprite("daynight"));
        nightSpriteHashMap.put("soundOff", nightAtlas.createSprite("soundOff_icon"));
        nightSpriteHashMap.put("soundOn", nightAtlas.createSprite("soundOn_icon"));
        nightSpriteHashMap.put("q_circle", nightAtlas.createSprite("q_circle"));
        nightSpriteHashMap.put("lock", nightAtlas.createSprite("lock"));
        nightSpriteHashMap.put("back_white", nightAtlas.createSprite("back_white"));
        nightSpriteHashMap.put("pause_icon", nightAtlas.createSprite("pause_icon"));
        nightSpriteHashMap.put("back_icon", nightAtlas.createSprite("back_icon"));
        nightSpriteHashMap.put("restart_icon", nightAtlas.createSprite("restart_icon"));
        nightSpriteHashMap.put("mmenu_icon", nightAtlas.createSprite("mmenu_icon"));
        nightSpriteHashMap.put("authors_icon", nightAtlas.createSprite("authors_icon"));
        nightSpriteHashMap.put("bighex_light", nightAtlas.createSprite("bighex_light"));
        nightSpriteHashMap.put("bighex_dark", nightAtlas.createSprite("bighex_dark"));
        nightSpriteHashMap.put("next_icon", nightAtlas.createSprite("next_icon"));
        nightSpriteHashMap.put("ads_icon", nightAtlas.createSprite("ads_icon"));
        nightSpriteHashMap.put("controller", nightAtlas.createSprite("controller"));
        nightSpriteHashMap.put("animbackhex", nightAtlas.createSprite("animbackhex"));


        if (Gdx.graphics.getWidth() < 500) {
            dayBackgroundDot = new TiledDrawable(dayAtlas.findRegion("point"));
            nightBackgroundDot = new TiledDrawable(nightAtlas.findRegion("point"));
            dayBackgroundStripe = dayAtlas.createSprite("animation");
            nightBackgroundStripe = nightAtlas.createSprite("animation");
        } else if (Gdx.graphics.getWidth() < 900) {
            dayBackgroundDot = new TiledDrawable(dayAtlas.findRegion("point2"));
            nightBackgroundDot = new TiledDrawable(nightAtlas.findRegion("point2"));
            dayBackgroundStripe = dayAtlas.createSprite("animation2");
            nightBackgroundStripe = nightAtlas.createSprite("animation2");
        } else if (Gdx.graphics.getWidth() < 1300) {
            dayBackgroundDot = new TiledDrawable(dayAtlas.findRegion("point3"));
            nightBackgroundDot = new TiledDrawable(nightAtlas.findRegion("point3"));
            dayBackgroundStripe = dayAtlas.createSprite("animation3");
            nightBackgroundStripe = nightAtlas.createSprite("animation3");
        } else {
            dayBackgroundDot = new TiledDrawable(dayAtlas.findRegion("point4"));
            nightBackgroundDot = new TiledDrawable(nightAtlas.findRegion("point4"));
            dayBackgroundStripe = dayAtlas.createSprite("animation4");
            nightBackgroundStripe = nightAtlas.createSprite("animation4");
        }

        dayQuadrantSprite = dayAtlas.createSprite("q_circle");
        nightQuadrantSprite = nightAtlas.createSprite("q_circle");

        dayBackgroundWhite = dayAtlas.createSprite("back_white");
        nightBackgroundWhite = nightAtlas.createSprite("back_white");

        fbLogo = commonAtlas.createSprite("fb_icon");
        twLogo = commonAtlas.createSprite("tw_icon");
        vkLogo = commonAtlas.createSprite("vk_icon");
    }

    private static void createLogos() {
        logoBHW = commonAtlas.createSprite("h_logo");
        logoBHW.setSize(SCREEN_WIDTH * 0.6f, SCREEN_WIDTH * 0.6f * 0.79f);
        logoBHW.setPosition((SCREEN_WIDTH - logoBHW.getWidth()) / 2, (SCREEN_HEIGHT - logoBHW.getHeight()) / 2);

        logoFR = commonAtlas.createSprite("fr_logo");
        logoFR.setSize(SCREEN_WIDTH * 0.8f, SCREEN_WIDTH * 0.8f * 0.75f);
        logoFR.setPosition((SCREEN_WIDTH - logoFR.getWidth()) / 2, (SCREEN_HEIGHT - logoFR.getHeight() * 0.9f) / 2);
    }

    private static void createSounds() {
        tapSound = manager.get("sound/tap.ogg");
        packCompleteSound = manager.get("sound/packcomplete.ogg");
        lvlCompleteSound = manager.get("sound/lvlcomplete.ogg");
        backgroundMusic = manager.get("sound/background.ogg");

        backgroundMusic.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                music.play();
                music.setPosition(0.0f); // fix nu player issue
            }
        });
        backgroundMusic.setVolume(0.7f);
    }

    public static TextButton.TextButtonStyle getTextButtonStyle(String style){
        if(FlowRush.getPreferences().isNightMode()){
            return nightTextbuttonStyleHashMap.get(style);
        }else{
            return dayTextbuttonStyleHashMap.get(style);
        }
    }

    public static Label.LabelStyle getLabelStyle(String style){
        if(FlowRush.getPreferences().isNightMode()){
            return nightLabelStyleHashMap.get(style);
        }else{
            return dayLabelStyleHashMap.get(style);
        }
    }

    public static BitmapFont getMidFont(){
        if(FlowRush.getPreferences().isNightMode()) {
            return nightSkin.getFont("fontMid");
        }else{
            return daySkin.getFont("fontMid");
        }
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

    public static TiledDrawable getBackgroundDot() {
        if(FlowRush.getPreferences().isNightMode()){
            return nightBackgroundDot;
        }else{
            return dayBackgroundDot;
        }
    }

    public static Sprite getBackgroundStripe() {
        if(FlowRush.getPreferences().isNightMode()){
            return nightBackgroundStripe;
        }else{
            return dayBackgroundStripe;
        }
    }

    public static Sprite getQuadrantSprite() {
        if(FlowRush.getPreferences().isNightMode()){
            return nightQuadrantSprite;
        }else{
            return dayQuadrantSprite;
        }
    }

    public static Sprite getLogoFR() {
        return logoFR;
    }

    public static Sprite getLogoBHW() {
        return logoBHW;
    }

    public static Sprite getFbLogo() {
        return fbLogo;
    }

    public static Sprite getTwLogo() {
        return twLogo;
    }

    public static Sprite getVkLogo() {
        return vkLogo;
    }

    public static Sprite getBackgroundWhite() {
        if(FlowRush.getPreferences().isNightMode()){
            return nightBackgroundWhite;
        }else{
            return dayBackgroundWhite;
        }
    }
}
