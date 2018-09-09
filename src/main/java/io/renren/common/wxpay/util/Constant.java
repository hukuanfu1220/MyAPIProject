package io.renren.common.wxpay.util;

import io.renren.config.OrtherConfig;

/**
 * Created by ASUS on 2017/9/28.
 */
public class Constant {
    // 新版微信接口
    public static String GATEURL = OrtherConfig.getConfig().getString("GATEURL"); //预支付 接口地址
    public static String APP_ID =  OrtherConfig.getConfig().getString("APP_ID");// appId
    public static String MCH_ID =  OrtherConfig.getConfig().getString("MCH_ID");//商户id
    public static String ENCODING =  OrtherConfig.getConfig().getString("ENCODING"); // 编码格式
    public static String PARTNER_KEY =  OrtherConfig.getConfig().getString("PARTNER_KEY");// key
    public static String APP_SECRET = OrtherConfig.getConfig().getString("APP_SECRET");//appSecret
    public static String NOTIFY_URL= OrtherConfig.getConfig().getString("NOTIFY_URL");//回调
}
