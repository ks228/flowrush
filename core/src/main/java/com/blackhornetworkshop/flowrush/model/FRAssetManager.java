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

import static com.blackhornetworkshop.flowrush.model.FRConstants.*;

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
        dayLabelStyleHashMap.put(LABEL_STYLE_GREYFONT, daySkin.get(LABEL_STYLE_GREYFONT, Label.LabelStyle.class));
        dayLabelStyleHashMap.put(LABEL_STYLE_DEFAULT, daySkin.get(LABEL_STYLE_DEFAULT, Label.LabelStyle.class));
        dayLabelStyleHashMap.put(LABEL_STYLE_ALPHAWHITE, daySkin.get(LABEL_STYLE_ALPHAWHITE, Label.LabelStyle.class));
        dayLabelStyleHashMap.put(LABEL_STYLE_DARKBLUE, daySkin.get(LABEL_STYLE_DARKBLUE, Label.LabelStyle.class));
        dayLabelStyleHashMap.put(LABEL_STYLE_DARKBLUESMALL, daySkin.get(LABEL_STYLE_DARKBLUESMALL, Label.LabelStyle.class));

        nightLabelStyleHashMap.put(LABEL_STYLE_GREYFONT, nightSkin.get(LABEL_STYLE_GREYFONT, Label.LabelStyle.class));
        nightLabelStyleHashMap.put(LABEL_STYLE_DEFAULT, nightSkin.get(LABEL_STYLE_DEFAULT, Label.LabelStyle.class));
        nightLabelStyleHashMap.put(LABEL_STYLE_ALPHAWHITE, nightSkin.get(LABEL_STYLE_ALPHAWHITE, Label.LabelStyle.class));
        nightLabelStyleHashMap.put(LABEL_STYLE_DARKBLUE, nightSkin.get(LABEL_STYLE_DARKBLUE, Label.LabelStyle.class));
        nightLabelStyleHashMap.put(LABEL_STYLE_DARKBLUESMALL, nightSkin.get(LABEL_STYLE_DARKBLUESMALL, Label.LabelStyle.class));


        dayTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_DARKBLUE, daySkin.get(TEXT_BUTTON_STYLE_DARKBLUE, TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_WHITE, daySkin.get(TEXT_BUTTON_STYLE_WHITE, TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_PLAYBUTTON, daySkin.get(TEXT_BUTTON_STYLE_PLAYBUTTON, TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_LIGHTBLUE, daySkin.get(TEXT_BUTTON_STYLE_LIGHTBLUE, TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_DEFAULT, daySkin.get(TEXT_BUTTON_STYLE_DEFAULT, TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_BORDERSMALL, daySkin.get(TEXT_BUTTON_STYLE_BORDERSMALL, TextButton.TextButtonStyle.class));
        dayTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_WHITESMALL, daySkin.get(TEXT_BUTTON_STYLE_WHITESMALL, TextButton.TextButtonStyle.class));

        nightTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_DARKBLUE, nightSkin.get(TEXT_BUTTON_STYLE_DARKBLUE, TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_WHITE, nightSkin.get(TEXT_BUTTON_STYLE_WHITE, TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_PLAYBUTTON, nightSkin.get(TEXT_BUTTON_STYLE_PLAYBUTTON, TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_LIGHTBLUE, nightSkin.get(TEXT_BUTTON_STYLE_LIGHTBLUE, TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_DEFAULT, nightSkin.get(TEXT_BUTTON_STYLE_DEFAULT, TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_BORDERSMALL, nightSkin.get(TEXT_BUTTON_STYLE_BORDERSMALL, TextButton.TextButtonStyle.class));
        nightTextbuttonStyleHashMap.put(TEXT_BUTTON_STYLE_WHITESMALL, nightSkin.get(TEXT_BUTTON_STYLE_WHITESMALL, TextButton.TextButtonStyle.class));

    }

    private static void createSprites(){
        daySpriteHashMap.put(BACKGROUND_HEX, dayAtlas.createSprite(BACKGROUND_HEX));
        nightSpriteHashMap.put(BACKGROUND_HEX, nightAtlas.createSprite(BACKGROUND_HEX));

        daySpriteHashMap.put(BACKGROUND_HEX_WITH_SOURCE, dayAtlas.createSprite(BACKGROUND_HEX_WITH_SOURCE));
        nightSpriteHashMap.put(BACKGROUND_HEX_WITH_SOURCE, nightAtlas.createSprite(BACKGROUND_HEX_WITH_SOURCE));

        daySpriteHashMap.put(BACKGROUND_HEX_TOUCHED, dayAtlas.createSprite(BACKGROUND_HEX_TOUCHED));
        nightSpriteHashMap.put(BACKGROUND_HEX_TOUCHED, dayAtlas.createSprite(BACKGROUND_HEX_TOUCHED));

        daySpriteHashMap.put(BACKGROUND_HEX_TOUCHED_WITH_SOURCE, nightAtlas.createSprite(BACKGROUND_HEX_TOUCHED_WITH_SOURCE));
        nightSpriteHashMap.put(BACKGROUND_HEX_TOUCHED_WITH_SOURCE, nightAtlas.createSprite(BACKGROUND_HEX_TOUCHED_WITH_SOURCE));

        daySpriteHashMap.put(SOURCE_ICON, dayAtlas.createSprite(SOURCE_ICON));
        daySpriteHashMap.put(DOVE_OFF_ICON, dayAtlas.createSprite(DOVE_OFF_ICON));
        daySpriteHashMap.put(DOVE_ON_ICON, dayAtlas.createSprite(DOVE_ON_ICON));
        daySpriteHashMap.put(POINT_OFF_ICON, dayAtlas.createSprite(POINT_OFF_ICON));
        daySpriteHashMap.put(POINT_WHITE_ICON, dayAtlas.createSprite(POINT_WHITE_ICON));
        daySpriteHashMap.put(POINT_ON_ICON, dayAtlas.createSprite(POINT_ON_ICON));

        nightSpriteHashMap.put(SOURCE_ICON, nightAtlas.createSprite(SOURCE_ICON));
        nightSpriteHashMap.put(DOVE_OFF_ICON, nightAtlas.createSprite(DOVE_OFF_ICON));
        nightSpriteHashMap.put(DOVE_ON_ICON, nightAtlas.createSprite(DOVE_ON_ICON));
        nightSpriteHashMap.put(POINT_OFF_ICON, nightAtlas.createSprite(POINT_OFF_ICON));
        nightSpriteHashMap.put(POINT_WHITE_ICON, nightAtlas.createSprite(POINT_WHITE_ICON));
        nightSpriteHashMap.put(POINT_ON_ICON, nightAtlas.createSprite(POINT_ON_ICON));

        for (int x = 1; x < 51; x++) {
            daySpriteHashMap.put(HEX+x, dayAtlas.createSprite(HEX, x));
        }

        for (int x = 1; x < 51; x++) {
            nightSpriteHashMap.put(HEX+x, nightAtlas.createSprite(HEX, x));
        }

        daySpriteHashMap.put(CLOSE_ICON, dayAtlas.createSprite(CLOSE_ICON));
        daySpriteHashMap.put(DAY_NIGHT_ICON, dayAtlas.createSprite(DAY_NIGHT_ICON));
        daySpriteHashMap.put(SOUND_OFF_ICON, dayAtlas.createSprite(SOUND_OFF_ICON));
        daySpriteHashMap.put(SOUND_ON_ICON, dayAtlas.createSprite(SOUND_ON_ICON));
        daySpriteHashMap.put(LOCK_ICON, dayAtlas.createSprite(LOCK_ICON));
        daySpriteHashMap.put(PAUSE_ICON, dayAtlas.createSprite(PAUSE_ICON));
        daySpriteHashMap.put(BACK_BUTTON_ICON, dayAtlas.createSprite(BACK_BUTTON_ICON));
        daySpriteHashMap.put(RESTART_ICON, dayAtlas.createSprite(RESTART_ICON));
        daySpriteHashMap.put(MENU_ICON, dayAtlas.createSprite(MENU_ICON));
        daySpriteHashMap.put(AUTHORS_ICON, dayAtlas.createSprite(AUTHORS_ICON));
        daySpriteHashMap.put(NEXT_ICON, dayAtlas.createSprite(NEXT_ICON));
        daySpriteHashMap.put(ADS_ICON, dayAtlas.createSprite(ADS_ICON));
        daySpriteHashMap.put(CONTROLLER_ICON, dayAtlas.createSprite(CONTROLLER_ICON));

        nightSpriteHashMap.put(CLOSE_ICON, nightAtlas.createSprite(CLOSE_ICON));
        nightSpriteHashMap.put(DAY_NIGHT_ICON, nightAtlas.createSprite(DAY_NIGHT_ICON));
        nightSpriteHashMap.put(SOUND_OFF_ICON, nightAtlas.createSprite(SOUND_OFF_ICON));
        nightSpriteHashMap.put(SOUND_ON_ICON, nightAtlas.createSprite(SOUND_ON_ICON));
        nightSpriteHashMap.put(LOCK_ICON, nightAtlas.createSprite(LOCK_ICON));
        nightSpriteHashMap.put(PAUSE_ICON, nightAtlas.createSprite(PAUSE_ICON));
        nightSpriteHashMap.put(BACK_BUTTON_ICON, nightAtlas.createSprite(BACK_BUTTON_ICON));
        nightSpriteHashMap.put(RESTART_ICON, nightAtlas.createSprite(RESTART_ICON));
        nightSpriteHashMap.put(MENU_ICON, nightAtlas.createSprite(MENU_ICON));
        nightSpriteHashMap.put(AUTHORS_ICON, nightAtlas.createSprite(AUTHORS_ICON));
        nightSpriteHashMap.put(NEXT_ICON, nightAtlas.createSprite(NEXT_ICON));
        nightSpriteHashMap.put(ADS_ICON, nightAtlas.createSprite(ADS_ICON));
        nightSpriteHashMap.put(CONTROLLER_ICON, nightAtlas.createSprite(CONTROLLER_ICON));

        daySpriteHashMap.put(BIG_HEX_LIGHT, dayAtlas.createSprite(BIG_HEX_LIGHT));
        daySpriteHashMap.put(BIG_HEX_DARK, dayAtlas.createSprite(BIG_HEX_DARK));
        daySpriteHashMap.put(BACKGROUND_ANIMATION, dayAtlas.createSprite(BACKGROUND_ANIMATION));

        nightSpriteHashMap.put(BIG_HEX_LIGHT, nightAtlas.createSprite(BIG_HEX_LIGHT));
        nightSpriteHashMap.put(BIG_HEX_DARK, nightAtlas.createSprite(BIG_HEX_DARK));
        nightSpriteHashMap.put(BACKGROUND_ANIMATION, nightAtlas.createSprite(BACKGROUND_ANIMATION));


        if (SCREEN_WIDTH < 500) {
            dayBackgroundDot = new TiledDrawable(dayAtlas.findRegion("point"));
            nightBackgroundDot = new TiledDrawable(nightAtlas.findRegion("point"));
            dayBackgroundStripe = dayAtlas.createSprite("animation");
            nightBackgroundStripe = nightAtlas.createSprite("animation");
        } else if (SCREEN_WIDTH < 900) {
            dayBackgroundDot = new TiledDrawable(dayAtlas.findRegion("point2"));
            nightBackgroundDot = new TiledDrawable(nightAtlas.findRegion("point2"));
            dayBackgroundStripe = dayAtlas.createSprite("animation2");
            nightBackgroundStripe = nightAtlas.createSprite("animation2");
        } else if (SCREEN_WIDTH < 1300) {
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

        dayQuadrantSprite = dayAtlas.createSprite(QUADRANT_BACKGROUND);
        nightQuadrantSprite = nightAtlas.createSprite(QUADRANT_BACKGROUND);

        dayBackgroundWhite = dayAtlas.createSprite(TRIANGLE_BACKGROUND);
        nightBackgroundWhite = nightAtlas.createSprite(TRIANGLE_BACKGROUND);

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
