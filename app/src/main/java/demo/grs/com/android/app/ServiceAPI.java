package demo.grs.com.android.app;

import demo.grs.com.android.utils.common.AppUtils;

/**
 * Created by gaoruishan on 15/12/23.
 */
public class ServiceAPI {
    // 测试服务器
    private static final String SERVER_BASE_TEST = "http://tapi.selftravel.com.cn/api";
    // 正式服务器
    private static final String SERVER_BASE_PRODUCT = AppUtils.getURL();
    private static String mServerBase = SERVER_BASE_PRODUCT;
    //常用
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_ID = "id";
    public static final String UTF_8 = "utf-8";
    public static final String PARAM_LIMIT = "limit";
    public static final String PARAM_OFFSET = "offset";
    public static final String PARAM_TOTAL = "total";
    public static final String PARAM_LATITUDE = "latitude";
    public static final String PARAM_LONGITUDE = "longitude";
    public static final String PARAM_CITY = "city";
    public static final String PARAM_DIVIDE = "divice";

    public static void switchServer(boolean debug) {
        mServerBase = debug ? SERVER_BASE_TEST : SERVER_BASE_PRODUCT;
    }
    public static class Token{
        public static final String AUTHE_TOKEN = "api-token-verify/";
        public static String buildAuthToken() {
            String url = mServerBase +"api/" + AUTHE_TOKEN;
            return url;
        }
    }
    public static class getUserDetailList{
        public static final String BUILD_STRING = "getUsersDetail.do";
        public static String buildString(String id) {
            String url = mServerBase +"friends/" + BUILD_STRING +"?user_id="+ id;
            return url;
        }
    }
}
