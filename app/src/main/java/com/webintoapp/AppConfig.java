package com.webintoapp;

public class AppConfig {

    // ==========================================
    // 1. RESOURCE CONFIGURATION
    // ==========================================
    // Uncomment ONLY ONE of the options below.

    // Option A: REMOTE RESOURCE (Web URL)
    public static final String URL_TO_LOAD = "http://toffeex.wuaze.com/";

    // Option B: LOCAL RESOURCE (Local HTML file from assets folder)
    // public static final String URL_TO_LOAD = "file:///android_asset/index.html";


    // ==========================================
    // 2. UI & DISPLAY SETTINGS
    // ==========================================
    public static final boolean SHOW_STATUS_BAR = true; 
    public static final boolean SHOW_SPLASH_SCREEN = true; 
    public static final int SPLASH_SCREEN_DURATION = 3000; // Time in milliseconds (3000 = 3 sec)


    // ==========================================
    // 3. WEBVIEW ADVANCED SETTINGS
    // ==========================================
    public static final boolean ENABLE_JAVASCRIPT = true;
    public static final boolean ENABLE_ZOOM_CONTROLS = false;
    public static final boolean ENABLE_DOM_STORAGE = true;


    // ==========================================
    // 4. CACHE MANAGEMENT
    // ==========================================
    // 0 = Default, 1 = Cache Only, 2 = No Cache (Saves Storage)
    public static final int CACHE_MODE = 2; 
}
