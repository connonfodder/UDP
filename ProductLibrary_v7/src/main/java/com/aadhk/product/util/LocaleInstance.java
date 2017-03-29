package com.aadhk.product.util;

import java.util.Locale;

/**
 * Created by jack on 11/10/2016.
 */

public class LocaleInstance {

    private static LocaleInstance instance;
    private static Locale locale = Locale.US;

    private LocaleInstance(){
        instance = this;
    }

    public static void setLocale(Locale l){
        if(instance == null) getInstance();
        locale = l;
    }

    public static Locale getLocale(){
        if(instance == null) getInstance();
        return locale;
    }

    private static LocaleInstance getInstance(){
        if(instance==null) instance = new LocaleInstance();
        return instance;
    }

}
