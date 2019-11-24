package com.tonyelkharrat.ui.managers;


public class UrlManager {

    //Retrun a correct path of a picture
    public static String getUrlPictureFromPath(String path){
         String prefix = "http://image.tmdb.org/t/p/w500";
         return prefix + path;
    }
}

