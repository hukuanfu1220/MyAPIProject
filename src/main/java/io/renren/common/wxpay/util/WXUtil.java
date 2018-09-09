package io.renren.common.wxpay.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;
import org.json.JSONException;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;


/**
 * Created by ASUS on 2017/9/28.
 */
public class WXUtil {

    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), Constant.ENCODING);
    }

    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
    public static String getPrePayId(SortedMap<String, String> paramMap) throws Exception{
        String prePayId = null;
        String sign = sendPrepay(paramMap); //
        System.err.println("sign:"+sign);
        String prePayGate = paramMap.get("prePayGate"); // 网关
        String xml = "<xml>" +
                "<appid>" + paramMap.get("appid") + "</appid>" +
                "<body>" + paramMap.get("body") + "</body>" +
                "<mch_id>" + paramMap.get("mch_id") + "</mch_id>" +
                "<nonce_str>" + paramMap.get("nonce_str") + "</nonce_str>" +
                "<notify_url>" + paramMap.get("notify_url") + "</notify_url>" +
                "<out_trade_no>" + paramMap.get("out_trade_no") + "</out_trade_no>" +
                "<spbill_create_ip>" + paramMap.get("spbill_create_ip") + "</spbill_create_ip>" +
                "<total_fee>" + paramMap.get("total_fee") + "</total_fee>" +
                "<trade_type>" + paramMap.get("trade_type") + "</trade_type>" +
                "<sign><![CDATA[" + sign + "]]></sign>" +
                "</xml>";


        System.err.println("prePayGate:"+prePayGate);
        System.err.println("xml:"+xml);

        Map resMap = sendParam2Weixin(prePayGate, xml);
        System.err.println("resMap:"+resMap);
        if(null != resMap && !resMap.isEmpty() && resMap.get("result_code").equals("SUCCESS")) {
            prePayId = resMap.get("prepay_id") + "";
        }
        return prePayId;
    }
    // 获取预支付id
    public static String sendPrepay(SortedMap<String, String> paramMap) throws JSONException {
        StringBuffer sb = new StringBuffer();
        Set<Entry<String, String>> es = paramMap.entrySet();
        Iterator<Entry<String, String>> it = es.iterator();
        while(it.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k) && !"prePayGate".equals(k)  && !"partnerKey".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + paramMap.get("partnerKey"));
        String enc = Constant.ENCODING;
        System.out.println(enc);
        String sign = MD5Util.MD5Encode(sb.toString(), enc).toUpperCase();
        System.out.println(sign);
        return sign;
    }
    /**
     * @author lcb
     * @Title: title
     * @Description: desc
     * @param @param params
     * @return String JSON字符串
     */
    public static String getPaySign(SortedMap<String, String> startPayParam) throws Exception{
        String sign = sendPrepay(startPayParam);
        return sign;
    }
    /**
     * @author lcb
     * @Title: title
     * @Description: 发送数据给微信
     * @param @param params
     * @return String JSON字符串
     */
    public static Map sendParam2Weixin(String url, String data) throws IOException, JDOMException {

        System.err.println("data:"+data);
        System.err.println("url:"+url);
        Map resMap = new HashMap<>();
        System.err.println("--------------------------");
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            System.err.println("httpost:"+httpost);

            httpost.setEntity(new StringEntity(data, Constant.ENCODING));


            HttpResponse execute = httpClient.execute(httpost);
            HttpEntity entity = execute.getEntity();
            Integer code = execute.getStatusLine().getStatusCode();

            System.err.println("code:"+code);
            if(null != code && "200".equals(code+"")) {
                String xmlRes = EntityUtils.toString(entity, Constant.ENCODING);
                resMap = XMLUtil.doXMLParse(xmlRes);
            }

        return resMap;
    }
}
