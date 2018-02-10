package com.blackhornetworkshop.flowrush.model;

public class GamePreferences{

    private boolean soundIsOn = true;
    private boolean showRateDialog = true;
    private boolean adsIsRemoved = false;
    private boolean isNightMode = false;

    public boolean isNightMode(){ return isNightMode;}
    public void setNightMode(boolean isNightMode){
        this.isNightMode = isNightMode;
    }
    public boolean isAdsIsRemoved(){return adsIsRemoved;}
    public void setAdsIsRemoved(boolean value){adsIsRemoved = value;}

    public boolean isSoundOn(){return soundIsOn;}
    public void setSound(boolean value){
        soundIsOn = value;
    }

    public boolean isShowRateDialog(){return showRateDialog;}

    public void setShowRateDialog(boolean value){
        showRateDialog = value;
    }
}
