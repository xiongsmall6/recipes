package com.xiong.recipes.constant;

import android.Manifest;

/**
 * @function: app常量
 */
public class Constant {
    private static final String ROOT_URL = "http://192.168.3.96:8081";
    public static String DAY_FOOD = ROOT_URL + "/food/day";
    public static String FOOD_DETAIL = ROOT_URL + "/food/detail";
    public static String FOOD_SEARCH = ROOT_URL + "/food/list";
    public static String USER_REGISTER = ROOT_URL + "/food/register";
    public static String USER_LOGIN = ROOT_URL + "/food/login";
    public static String USER_UPDATER = ROOT_URL + "/food/update";
    public static String IMAGE_UPDATE = ROOT_URL + "/food/upload/image";
    public static String FOOD_ADD = ROOT_URL + "/food/add";


    public static String USER = "user";
    public static String USER_NAME = "user_name";
    public static String IS_LOGIN= "is_login";
}
