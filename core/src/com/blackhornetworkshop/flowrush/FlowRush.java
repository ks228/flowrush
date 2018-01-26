package com.blackhornetworkshop.flowrush;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.blackhornetworkshop.flowrush.gameplay.SourceChecker;
import com.blackhornetworkshop.flowrush.initialization.GamePreferences;
import com.blackhornetworkshop.flowrush.initialization.LevelLoader;
import com.blackhornetworkshop.flowrush.initialization.OneTouchProcessor;
import com.blackhornetworkshop.flowrush.initialization.SavedGame;
import com.blackhornetworkshop.flowrush.screens.GameScreen;
import com.blackhornetworkshop.flowrush.screens.LogoScreen;
import com.blackhornetworkshop.flowrush.screens.MainMenuScr;
import com.blackhornetworkshop.flowrush.ui.SmallButtonActor;
import com.blackhornetworkshop.flowrush.ui.TapOnTileActor;
import com.google.gson.Gson;


public class FlowRush extends Game {

    public ConstantBase.ScreenType screenType; // ++ADDED NEW

    //Data
    public GamePreferences prefs;
    public SavedGame save;

    //Screens
    private GameScreen gameScreen;
    private MainMenuScr mainMenuScr;

    //Actors
    public Group backGroup;
    public TapOnTileActor tapOnTileActor;
    public SmallButtonActor soundButton;
    public Label alphawhiteBack, levelNumberActor;

    //Graphics
    public SpriteBatch batch;
    public Skin skin;
    public TiledDrawable spriteBack;
    public TextureAtlas atlas;
    public Sprite qCircle;

    //Audio
    public Sound tapSound, lvlCompleteSound, packCompleteSound;
    public Music backgroundMusic;

    //Utils
    public Gson gson;
    private AssetManager manager;
    public final PlayServices playServices;
    public OneTouchProcessor oneTouchProcessor;
    public LevelLoader levelLoader;
    public SourceChecker checker;
    public AndroidSide androidSide;

    public AsyncExecutor executor;// ++ADDED NEW



    public FlowRush(AndroidSide androidSide, PlayServices playServices){
        this.androidSide = androidSide;
        this.playServices = playServices;
        this.executor = new AsyncExecutor(20);// ++ADDED NEW
    }
    public void create() {
        this.batch = new SpriteBatch();// ++ADDED NEW

        //Отлавливаем кнопку BACK
        Gdx.input.setCatchBackKey(true);

        //Создаем процеесор ввода, необходим чтобы отключить мультитач и отлавливать кнопку BACK
        oneTouchProcessor = new OneTouchProcessor(this);

        //Загружаем все ресурсы в AssetManager
        manager = new AssetManager();
        InternalFileHandleResolver resolver = new InternalFileHandleResolver();

        FreeTypeFontGenerator.setMaxTextureSize(FreeTypeFontGenerator.NO_MAXIMUM);
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter param = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param.fontFileName = "ui/Iceberg-Regular.ttf";
        param.fontParameters.size = (int)(ConstantBase.C_BUTTON_SIZE);
        manager.load("fontLarge.ttf", BitmapFont.class, param);     //Загружаем шрифт первого размера

        FreetypeFontLoader.FreeTypeFontLoaderParameter param2 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param2.fontFileName = "ui/Iceberg-Regular.ttf";
        param2.fontParameters.size = (int)(ConstantBase.C_BUTTON_SIZE*0.5f);
        manager.load("fontMid.ttf", BitmapFont.class, param2);     //Загружаем шрифт второго размера

        FreetypeFontLoader.FreeTypeFontLoaderParameter param3 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        param3.fontFileName = "ui/Iceberg-Regular.ttf";
        param3.fontParameters.size = (int)(ConstantBase.C_BUTTON_SIZE*0.43);
        manager.load("fontSmall.ttf", BitmapFont.class, param3);     //Загружаем шрифт третьего размера

        manager.finishLoadingAsset("fontLarge.ttf");
        manager.finishLoadingAsset("fontMid.ttf");
        manager.finishLoadingAsset("fontSmall.ttf");
        BitmapFont fontLarge = manager.get("fontLarge.ttf");
        BitmapFont fontMid = manager.get("fontMid.ttf");
        BitmapFont fontSmall = manager.get("fontSmall.ttf");
        ObjectMap <String, Object> oMap = new ObjectMap<String, Object>();
        oMap.put("fontLarge", fontLarge);
        oMap.put("fontMid", fontMid);
        oMap.put("fontSmall", fontSmall);
        manager.load("ui/skin.json", Skin.class, new SkinLoader.SkinParameter("texture/atlas.atlas", oMap));      //Загружаем СКИН и с ним атлас интерфейса

        manager.load("sound/tap.ogg", Sound.class); //Загружаем звук тапа
        manager.load("sound/lvlcomplete.ogg", Sound.class); //Звук лвлкомплет
        manager.load("sound/background.ogg", Music.class); //Загружаем фоновую музыку
        manager.load("sound/packcomplete.ogg", Sound.class); //Звук паккомплит
        manager.finishLoading();

        //Привязываем к переменным ресурсы, для удобства использования
        skin = manager.get("ui/skin.json");
        atlas = skin.getAtlas();
        tapSound = manager.get("sound/tap.ogg");
        packCompleteSound = manager.get("sound/packcomplete.ogg");
        lvlCompleteSound = manager.get("sound/lvlcomplete.ogg");
        backgroundMusic = manager.get("sound/background.ogg");

        //классы-конструкторы для работы
        gson = new Gson();


        //Текстура фона для группы актров паузы
        qCircle = atlas.createSprite("q_circle");

        //Фон-точка
        Sprite sprite;
        if(Gdx.graphics.getWidth()<500) {
            spriteBack = new TiledDrawable(atlas.findRegion("point"));
            sprite = atlas.createSprite("animation");
        }else if(Gdx.graphics.getWidth()<900){
            spriteBack = new TiledDrawable(atlas.findRegion("point2"));
            sprite = atlas.createSprite("animation2");
        }else if(Gdx.graphics.getWidth()<1300){
            spriteBack = new TiledDrawable(atlas.findRegion("point3"));
            sprite = atlas.createSprite("animation3");
        }else{
            spriteBack = new TiledDrawable(atlas.findRegion("point4"));
            sprite = atlas.createSprite("animation4");
        }

        //анимация фона
        backGroup = new Group();
        for(int index = 1, type = 1; type<5; type++, index+=0.6f){ /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! FIX BUG INT---FLOAT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
            backGroup.addActor(new com.blackhornetworkshop.flowrush.ui.BackAnimActor(sprite,index ,type));
            backGroup.addActor(new com.blackhornetworkshop.flowrush.ui.BackAnimActor(sprite,index+0.3f,type));/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! FIX BUG INT---FLOAT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
        }

        //Файл настроек
        if(Gdx.files.local("prefs.json").exists()){
            System.out.println("load exist prefs");
            prefs = gson.fromJson(Gdx.files.local("prefs.json").reader(), GamePreferences.class);
        }else{
            prefs = new GamePreferences();
            System.out.println("create new prefs");
        }
        //Файл сохранения
        if(Gdx.files.local("save.json").exists()){
            System.out.println("load exist save");
            save = gson.fromJson(Gdx.files.local("save.json").reader(), SavedGame.class);
        }else{
            save = new SavedGame();
            save.setUniqSaveGameName();
            //System.out.println("create new save");
        }

        //Настраиваем фоновую музыку, этот код поможет избежать ошибок на некоторых моделях телефонов с паузой музыки после проигрывания (вроде андро 5 и какой то там эксперементальный nu player)
        backgroundMusic.setOnCompletionListener(new Music.OnCompletionListener(){
            @Override
            public void onCompletion(Music music) {
                music.play();
                music.setPosition(0.0f);
            }
        });
        backgroundMusic.setVolume(0.7f);


        //utils
        levelLoader = new com.blackhornetworkshop.flowrush.initialization.LevelLoader(this);
        checker = new SourceChecker();

        //Актер номера уровня
        levelNumberActor = new Label("", skin, "greyfont");
        levelNumberActor.setSize(ConstantBase.C_BUTTON_SIZE, ConstantBase.C_BUTTON_SIZE);
        levelNumberActor.setPosition(Gdx.graphics.getWidth() - levelNumberActor.getWidth(), Gdx.graphics.getHeight()-levelNumberActor.getHeight());
        levelNumberActor.setAlignment(Align.center);

        //Наложение белого цвета, для засветления во время паузы и прохождения уровня
        alphawhiteBack = new Label("", skin, "alphawhite");
        alphawhiteBack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        alphawhiteBack.setVisible(false);
        alphawhiteBack.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                if(screenType == ConstantBase.ScreenType.GAME_PAUSE){
                    getGameScreen().resume();
                }
            }
        });

        //Актер отображающий анимацию при касании тайла
        tapOnTileActor = new TapOnTileActor(atlas.createSprite("animbackhex"));

        setLogoScreen();
    }



    public void dispose() {
        /* ++++++++++++++ ADD this.batch.dispose();*/
        skin.remove("fontLarge", BitmapFont.class);// выгружаем шрифты из скина, так как manager делает их dispose() а затем повторяет эту же процедуру для скина. при второй попытке exception
        skin.remove("fontMid", BitmapFont.class);
        skin.remove("fontSmall", BitmapFont.class);
        manager.dispose();

        // CHANGE
        getScreen().dispose();
        playServices.disposeAsyncExecutor();

        /* WITH THAT
        this.getScreen().hide();
        this.executor.dispose();
         */
    }

    public void savePrefsFile(){
        String string = gson.toJson(prefs);
        FileHandle file = Gdx.files.local("prefs.json");
        file.writeString(string, false);
        //System.out.println("Saved prefs");
    }
    public void saveSaveFile(){
        String string = gson.toJson(save);
        FileHandle file = Gdx.files.local("save.json");
        file.writeString(string, false);
        System.out.println("Saved progress on local");
    }
    void setLogoScreen(){ setScreen(new LogoScreen(this)); }
    public void setMainMenuScreen(){
        mainMenuScr = new MainMenuScr(this);
        setScreen(mainMenuScr);

    }
    public void setGameScreen(){
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }
    public MainMenuScr getMainMenuScr(){return mainMenuScr;}
    public GameScreen getGameScreen(){return gameScreen;}
    public void pause(){
        backgroundMusic.pause();
    }
    public void resume(){ if(prefs.isSoundIsOn()) backgroundMusic.play(); }
    public void render() {
        super.render(); // important!
    }
}

