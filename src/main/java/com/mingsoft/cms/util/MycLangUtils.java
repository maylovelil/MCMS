package com.mingsoft.cms.util;

/**
 * @Description:
 * @author: :MaYong
 * @Date: 2018/6/1 17:56
 */
public class MycLangUtils {

    public static String GLOB_EN = "en";

    public static String GLOB_ZH = "zh";
    /**
     * 设置全局中文
     */
    public static void setZh(){
        SessionUtils.setRequestAttribute(SessionUtils.GLOB_LANGUAGE_IN18,GLOB_ZH);
    }
    /**
     * 设置全局英文
     */
    public  static void setEn(){
        SessionUtils.setRequestAttribute(SessionUtils.GLOB_LANGUAGE_IN18,GLOB_EN);
    }

    public static  boolean isZh(){
        if(GLOB_ZH.equals(SessionUtils.getRequestAttribute(SessionUtils.GLOB_LANGUAGE_IN18))){
            return true;
        }else{
            return false;
        }
    }
}
