package com.usen.myunitylib;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

public class MainActivity extends UnityPlayerActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getScanCode() == 401 && event.getAction() == KeyEvent.ACTION_DOWN) {
            // blue
//            Toast.makeText(UnityPlayer.currentActivity, "blue button:401", Toast.LENGTH_SHORT).show();
            UnityPlayer.UnitySendMessage("Main Camera", "OnAndroidKeyDown", "blue");
        }
        if (event.getScanCode() == 398 && event.getAction() == KeyEvent.ACTION_DOWN) {
            // red
//            Toast.makeText(UnityPlayer.currentActivity, "red button:398", Toast.LENGTH_SHORT).show();
            UnityPlayer.UnitySendMessage("Main Camera", "OnAndroidKeyDown", "red");
        }
        if (event.getScanCode() == 399 && event.getAction() == KeyEvent.ACTION_DOWN) {
            // green
//            Toast.makeText(UnityPlayer.currentActivity, "green button:399", Toast.LENGTH_SHORT).show();
            UnityPlayer.UnitySendMessage("Main Camera", "OnAndroidKeyDown", "green");
        }
        if (event.getScanCode() == 400 && event.getAction() == KeyEvent.ACTION_DOWN) {
            // yellow
//            Toast.makeText(UnityPlayer.currentActivity, "yellow button:400", Toast.LENGTH_SHORT).show();
            UnityPlayer.UnitySendMessage("Main Camera", "OnAndroidKeyDown", "yellow");
        }
        return super.dispatchKeyEvent(event);
    }
}
