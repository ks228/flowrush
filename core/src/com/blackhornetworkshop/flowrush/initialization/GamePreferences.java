package com.blackhornetworkshop.flowrush.initialization;

public class GamePreferences{
    private boolean soundIsOn = true;
    private boolean showRateDialog = true; //показывать диалог каждый раз при выполнении пака пока пройдя по схеме человек не отключит это (нажмет не нравится или поставит рейтинг)
    private boolean adsIsRemoved = false;

    public boolean isAdsIsRemoved(){return adsIsRemoved;}
    public void setAdsIsRemoved(boolean value){adsIsRemoved = value;}

    public boolean isSoundOn(){return soundIsOn;}
    void setSound(boolean value){
        soundIsOn = value;
    }

    public boolean isShowRateDialog(){return showRateDialog;}

    public void setShowRateDialog(boolean value){
        //System.out.println("show rate dialog "+value);
        showRateDialog = value;
    }
}
