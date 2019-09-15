package com.identita.audioapp;

public class AudioApp {
    public native int StartRecord(int samplerate, int numseconds, String host, String wavfolder);
}
