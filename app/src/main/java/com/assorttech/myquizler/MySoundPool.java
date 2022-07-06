package com.assorttech.myquizler;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class MySoundPool {

    private static SoundPool mSoundPool;
    private static int correct;
    private static int wrong;
    private static int buttonClick;
    public MySoundPool(Context context){
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        correct= mSoundPool.load(context,R.raw.correct,1);
        wrong= mSoundPool.load(context,R.raw.wrong,1);
        buttonClick= mSoundPool.load(context,R.raw.multimedia_button_click,1);

    }

    public void CorrectSound(){
        mSoundPool.play(correct,1.0f,1.0f,1,0,1.0f);
    }
    public void WrongSound(){
        mSoundPool.play(wrong,1.0f,1.0f,1,0,1.0f);
    }
    public void ClickSound() {
        mSoundPool.play(buttonClick,1.0f,1.0f,1,0,1.0f);
    }
}
