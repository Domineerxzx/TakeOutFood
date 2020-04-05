package com.triplebro.domineer.takeoutfood.properties;

public class ProjectProperties {

    public static final String ADDRESS_GET_REQUEST_CODE = "http://120.25.96.141:8080/login/request";

    public static final String ADDRESS_REGISTER = "http://120.25.96.141:8080/login/registernumber";

    public static final String ADDRESS_UPDATE_REGISTER_INFO = "http://120.25.96.141:8080/user/update_info";

    public static final String ADDRESS_LOGIN = "http://120.25.96.141:8080/login/login";

    public static final int LOGIN_SUCCESS = 0;

    public static final int LOGIN_ADMIN_SUCCESS = 2;

    public static final int LOGIN_ADMIN_FAILED = 3;

    public static final int LOGIN_FAILED = 1;

    public static final int REGISTER_SUCCESS = 0;

    public static final int REGISTER_FAILED = 1;

    public static final int GET_REQUEST_CODE_SUCCESS = 2;

    public static final int GET_REQUEST_CODE_FAILED = 3;

    public static final int FROM_GALLERY = 1;

    public static final int FROM_CAMERA = 2;

    public static final int ADMIN = 1;

    public static final int USER = 2;

    public static final int REGISTER_ADMIN_SUCCESS = 4;

    public static final int DATA_INSERT_SUCCESS = 1;

    public static final int DATA_INSERT_FAILED = 2;

    //获取token令牌的地址
    public static final String TOKEN_ADDRESS = "http://thethreestooges.cn/index.php";
    //oss服务器地址
    public static final String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
    //oss服务器上的存储空间名
    public static final String BUCKET_NAME = "thethreestooges";
    //massage的4种情况
    public static final int WHAT_SUCCESS_DOWNLOAD = 3;

    public static final int WHAT_SUCCESS_UPLOAD = 1;

    public static final int WHAT_FAILED_UPLOAD = 2;

    public static final int WHAT_FAILED_DOWNLOAD = 4;

    public static final int ORDER_STATE_WAIT_PAY = 0;

    public static final int ORDER_STATE_WAIT_SEND = 1;

    public static final int ORDER_STATE_WAIT_GET = 2;

    public static final int ORDER_STATE_WAIT_EVALYATE = 3;

    public static final int ORDER_STATE_CANCEL = 4;

    public static final int ORDER_STATE_DONE = 5;
}
