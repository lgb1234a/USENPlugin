package com.usen.ustv.service;

interface IUstvMediaService {

    void registerProcessDeath(IBinder cb, String packageName);
    void preparePlaying( boolean pauseUsenMusic );
    void completePlaying();

}

