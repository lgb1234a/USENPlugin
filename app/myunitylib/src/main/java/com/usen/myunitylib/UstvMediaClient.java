package com.usen.myunitylib;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.usen.ustv.service.IUstvMediaService;

public class UstvMediaClient  {

    public interface ServiceListener {
        void onServiceConnected(  );
    }

    private static final String TAG = UstvMediaClient.class.getSimpleName();

    private static UstvMediaClient mUstvMediaClient;
    private Context mContext;
    private boolean mConnected = false;
    private boolean mBinded = false;
    private ServiceListener mServiceListener;
    public static IUstvMediaService mService;

    public static UstvMediaClient getInstance(Context context) {
        if (mUstvMediaClient == null) {
            synchronized (UstvMediaClient.class) {
                if (mUstvMediaClient == null) {
                    mUstvMediaClient = new UstvMediaClient(context);
                }
            }
        }
        return mUstvMediaClient;
    }

    public UstvMediaClient(Context mContext) {
        this.mContext = mContext;
    }

    private void doConnectMediaService() {

        if( mBinded ) {
            mContext.unbindService( mConn );
            mBinded = false;
        }

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.usen.ustv",
                "com.usen.ustv.service.UstvMediaService"));
        mBinded = mContext.bindService(intent, mConn, Service.BIND_AUTO_CREATE );
        Log.d(TAG,"bindService:" + mBinded);
    }

    public final ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d(TAG,"onServiceConnected");
            mService = IUstvMediaService.Stub.asInterface(binder);
            mConnected = true;
            doServiceConnected();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mConnected = false;
            Log.d(TAG,"ServiceDisconnected");
        }
    };

    public IUstvMediaService getUstvMediaService(){
        if(mConnected){
            return mService;
        }
        return null;
    }

    public boolean connectUstvMediaService( ){

        if(!mConnected){
            doConnectMediaService();
        } else {
            doServiceConnected();
        }
        Log.d(TAG,"MediaService connect:" + mConnected);
        return mConnected;
    }

    public void disConnectUstvMediaService(){

        if( mConnected  && mConn != null ){
            mContext.unbindService( mConn );
            mBinded = false;
            mConnected = false;
        }
    }

    public void setServiceListener( ServiceListener serviceListener) {
        this.mServiceListener = serviceListener;
    }

    private void doServiceConnected() {
        if( mServiceListener != null) {
            mServiceListener.onServiceConnected();
        }
    }

    public void preparePlaying( boolean pauseUsenMusic ) {

        IUstvMediaService mediaService = getUstvMediaService();
        if( mediaService == null ) return;

        try {
            mediaService.preparePlaying( pauseUsenMusic );
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public void registerProcessDeath(IBinder cb, String packageName) {

        IUstvMediaService mediaService = getUstvMediaService();
        if( mediaService == null ) return;

        try {
            mediaService.registerProcessDeath( cb, packageName );
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public void completePlaying( ) {

        IUstvMediaService mediaService = getUstvMediaService();
        if( mediaService == null ) return;

        try {
            mediaService.completePlaying( );
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
