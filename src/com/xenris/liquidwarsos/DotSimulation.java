//    This file is part of Liquid Wars.
//
//    Copyright (C) 2013-2014 Henry Shepperd (hshepperd@gmail.com)
//
//    Liquid Wars is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Liquid Wars is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Liquid Wars.  If not, see <http://www.gnu.org/licenses/>.

package com.xenris.liquidwarsos;

import android.content.res.AssetManager;

public class DotSimulation {
    private long gNativePointer;
    private boolean gIsValid = false;
    private long gStepNumber = 0;
    private Map gMap;

    private static native long newNative(long seed, int numberOfPlayers, int[] colors, int teamSize, int width, int height, boolean[] wallMap);
    private static native void deleteNative(long pointer);
    private static native void drawNative(long pointer);
    private static native void stepNative(long pointer);
    private static native void setPlayerPositionNative(long pointer, int playerId, float x, float y);

    static {
        System.loadLibrary("nativeinterface");
    }

    public DotSimulation(long seed, int numberOfPlayers, int[] colors, int teamSize, Map map) {
        gMap = map;

        // TODO Use map.width() and map.height() instead of constants.
        boolean[] wallMap = gMap.createWallMap();
        gNativePointer = newNative(seed, numberOfPlayers, colors, teamSize, Constants.WIDTH, Constants.HEIGHT, wallMap);

        if(gNativePointer != 0) {
            gIsValid = true;
        }
    }

    public void delete() {
        if(gIsValid) {
            gIsValid = false;

            synchronized(this) {
                deleteNative(gNativePointer);
            }
        }
    }

    public void draw() {
        if(gIsValid) {
            synchronized(this) {
                drawNative(gNativePointer);
            }
        }
    }

    public void step() {
        gStepNumber++;

        if(gIsValid) {
            synchronized(this) {
                stepNative(gNativePointer);
            }
        }
    }

    public boolean isValid() {
        return gIsValid;
    }

    public long getStepNumber() {
        return gStepNumber;
    }

    // TODO Currently playerId is an index, but it possibly should be an id.
    public void setPlayerPosition(int playerId, float x, float y) {
        if(gIsValid) {
            synchronized(this) {
                setPlayerPositionNative(gNativePointer, playerId, x, y);
            }
        }
    }

    public Map getMap() {
        return gMap;
    }

//    public static native void onSurfaceCreated();
//    public static native void onDrawFrame();
//    public static native void onSurfaceChanged(int width, int height);
//    public static native void init(AssetManager assetManager);
//    public static native void uninit();
//    public static native void stepDots();
//    public static native void createGame(int team, int map, int seed, int dotsPerTeam);
//    public static native void destroyGame();
//    public static native void setPlayerPosition(int team, short[] xs, short[] ys);
//    public static native int getNearestDot(int p, short px, short py);
//    public static native int teamScore(int p);
//    public static native void setTimeSidebar(float t);
}
