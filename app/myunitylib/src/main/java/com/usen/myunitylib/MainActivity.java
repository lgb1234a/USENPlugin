package com.usen.myunitylib;

import android.content.Context;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

public class MainActivity extends UnityPlayerActivity implements UstvMediaClient.ServiceListener {

    private UstvMediaClient mMediaClient;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mMediaClient = UstvMediaClient.getInstance(getApplicationContext());
        mMediaClient.setServiceListener(this);
        mMediaClient.connectUstvMediaService();
    }

    @Override
    public void onServiceConnected() {
        mMediaClient.registerProcessDeath(new Binder(), this.getPackageName());
        // 连接成功,默认停止那边的音乐
        mMediaClient.preparePlaying(true);
    }

    @Override public void onUnityPlayerUnloaded()
    {
        Log.d("My bingo unity app", "onUnityPlayerUnloaded  ---> completePlaying()");
        mMediaClient.completePlaying();
        super.onUnityPlayerUnloaded();
    }

    // When Unity player quited kill process
    @Override public void onUnityPlayerQuitted()
    {
        Log.d("My bingo unity app", "onUnityPlayerQuitted  ---> completePlaying()");
        mMediaClient.completePlaying();
        super.onUnityPlayerQuitted();
    }

    @Override protected void onDestroy ()
    {
        mMediaClient.preparePlaying(false);
        mMediaClient.disConnectUstvMediaService();
        super.onDestroy();
    }

    // Pause Unity
    @Override protected void onPause()
    {
        super.onPause();
        Log.d("My bingo unity app", "onPause  ---> completePlaying()");
        mMediaClient.completePlaying();
    }

    // Resume Unity
    @Override protected void onResume()
    {
        super.onResume();
        Log.d("My bingo unity app", "onResume  ---> preparePlaying(true)");
        mMediaClient.preparePlaying(true);
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
