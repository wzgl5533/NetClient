package com.qlh.net;

/**
 * 作者：dell on 2018/5/8 16:12
 * 描述：
 */

public class QLHUrls {

    //基础地址
    public static final String BASE_URL = "http://www.shangyuekeji.com/player/api/";

    //发送验证码
    public static final String SEND_CODE = "http://www.shangyuekeji.com:8080/";

    public static String HTTP_ROOT = "";//播放基础地址

    //--------------相对地址--------------------------------------------------------
    public static final String PLAYER_ROOT = "v1/player/getHttpRoot";//获取播放主地址
    public static final String THEME_LIST = "v1/player/getThemeList";//场景列表
    public static final String MENU_LIST = "v1/player/getMenuList";//对应场景的菜单列表
    public static final String SONG_LIST = "v1/player/getSongList";//歌曲相对地址列表（播放规则：root+theme+menu+song）
    //登录
    public static final String HAS_PHONE_NUMBER ="V1/UserManager/hasIdentifier";
    public static final String CREATE_USER = "V1/UserManager/createWholeByIdentifier";
    public static final String SMS_GET ="V1/SMS/SendCode";
    public static final String LOGIN = "V1/UserManager/loginWithSMS";

}
