package com.yn.common.entity;

/**
 * @className: (静态常量)
 * @date 2018/09/12 17:58:36
 */
public class BetterStoreConstants {
    /**
     * 系统异常状态码
     */
    public static final String SYSTEM_ERROR_CODE = "000000";
    /**
     * 数据正常状态；公告未发布状态；
     */
    public static final int STATUS_YES = 0;
    /**
     * 失效状态；公告已发布状态
     */
    public static final int STATUS_NO = 1;
    /**
     * 数据操作失败
     */
    public static final String OPERATE_ERROR_MSG = "操作失败，数据错误";
    /**
     * 程序或者服务器异常
     */
    public static final String OPERATE_SERVER_ERROR_MSG = "操作失败，服务器异常";

    /**
     * 日期增加的天数1
     */
    public static final int RANGE_ONE_DAY = 1;

    /**
     * 用于群聊的group id
     */
    public static final String GROUP_ID = "showcase-websocket";

    public static final String WX_MINIPROGRAM_ERRCODE_KEY = "errcode";
    public static final String WX_MINIPROGRAM_ERRMSG_KEY = "errmsg";
    /**
     * 小程序http_api请求成功
     */
    public static final int WX_MINIPROGRAM_ERRCODE_SUCCESS = 0;
    /**
     * 小程序http_api请求失败
     */
    public static final int WX_MINIPROGRAM_ERRCODE_FAIL = -1;
    /**
     * jscode无效
     */
    public static final int WX_MINIPROGRAM_ERRCODE_JSCODE_INVALID = -1;
    /**
     * AppSecret 错误或者 AppSecret 不属于这个小程序，请开发者确认 AppSecret 的正确性
     */
    public static final int WX_MINIPROGRAM_ERRCODE_APPSECRET_INVALID = 40001;
    /**
     * 不合法的 AppID，请开发者检查 AppID 的正确性，避免异常字符，注意大小写
     */
    public static final int WX_MINIPROGRAM_ERRCODE_APPID_INVALID = 40013;

    public static final String WX_AUTH_LOGIN_DATA_ERROR_MSG = "不合法的数据，授权登录失败！";
    /**
     * elasticsearch的索引(类似于数据库)
     */
    public static final String ELASTICSEARCH_INDEX_BETTERSTORE = "betterstore";
    /**
     * elasticsearch的type(类似于数据库表)
     */
    public static final String ELASTICSEARCH_TYPE_PERSON_WX = "hkpersonwx";

}
