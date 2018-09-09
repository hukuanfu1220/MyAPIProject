package io.renren.common.wxpay.controller;

import io.renren.common.wxpay.util.Constant;
import io.renren.common.wxpay.util.WXUtil;
import io.renren.common.wxpay.vo.payVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by QX on 2017/9/28.
 */
@RestController
@RequestMapping("wx")
public class wxpayController {



    /**
     *
     * @param payVo
     * @return 微信预支付接口
     * @throws Exception
     */
        @PostMapping("wxpay")
        public Map<Object, Object> doWeinXinRequest(@RequestBody payVo payVo) throws Exception {

            Map<Object,Object> resInfo = new HashMap<Object, Object>();

            Map<String, Object> beforPayMap = new HashMap<String, Object>();
        //    String body =tOrderTService.queryStrByNum(payVo.getOuttradeno()).get("str").toString();
            String price = (int)(Double.parseDouble(payVo.getPrice()) * 100) + "";

            Map<String, String> configMap = getConfigMap();
            //---------------生成订单号 结束------------------------
            String noncestr = WXUtil.getNonceStr(); // 随机字符串
            String timestamp = WXUtil.getTimeStamp();
            SortedMap<String, String>  prePayParamMap = new TreeMap<String, String>();
//			// 设置获取预支付id的参数
            prePayParamMap.put("appid", configMap.get("appid"));//银行渠道
      //      prePayParamMap.put("body",body); //商品描述
            prePayParamMap.put("mch_id", configMap.get("mch_id")); //商户号
            prePayParamMap.put("nonce_str", noncestr); //随机数
            prePayParamMap.put("notify_url", configMap.get("notify_url")); //接收财付通通知的URL
            prePayParamMap.put("out_trade_no", payVo.getOuttradeno()); //商家订单号
            prePayParamMap.put("spbill_create_ip","192.168.0.1"); //订单生成的机器IP，指用户浏览器端IP
            prePayParamMap.put("total_fee", price); //订单总金额,单位分
            prePayParamMap.put("trade_type", "APP"); //支付类型
            prePayParamMap.put("prePayGate", configMap.get("prePayGate")); //字符编码
            prePayParamMap.put("partnerKey", configMap.get("partnerKey")); //字符编码
            //获取prepayId|
            System.err.println("prePayParamMap:"+prePayParamMap);

            String prepayid = WXUtil.getPrePayId(prePayParamMap);

            System.err.println("prepayid:"+prepayid);
            //吐回给客户端的参数
            Map<String, Object> object = new HashMap<>();

            if (null != prepayid && !"".equals(prepayid)) {
                SortedMap<String, String>  startPayParam = new TreeMap<String, String>();
                startPayParam.put("appid", configMap.get("appid"));
                startPayParam.put("noncestr", noncestr);
                startPayParam.put("package", "Sign=WXPay");
                startPayParam.put("partnerid", configMap.get("mch_id"));
                startPayParam.put("prepayid", prepayid);
                startPayParam.put("timestamp", timestamp);
                startPayParam.put("partnerKey", configMap.get("partnerKey"));
                String sign = WXUtil.getPaySign(startPayParam);
                //生成签名
                object.put("sign", sign);
                object.put("timestamp", timestamp);
                object.put("prepayid", prepayid);
                object.put("partnerid", Constant.MCH_ID);
                object.put("package", "Sign=WXPay");
                object.put("noncestr", noncestr);
                object.put("appid", Constant.APP_ID);

            }
            System.err.println("object"+object);
            resInfo.put("entity", object);
            resInfo.put("status", "00000");
            resInfo.put("display", "生成成功");
            return resInfo;
        }

    /**
     * 从配置文件获取微信支付相关数据
     * @return
     */
        private static Map<String, String> getConfigMap() {

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("notify_url", Constant.NOTIFY_URL);
            paramMap.put("mch_id", Constant.MCH_ID);
            paramMap.put("appid", Constant.APP_ID);
            paramMap.put("prePayGate", Constant.GATEURL);
            paramMap.put("partnerKey", Constant.PARTNER_KEY);
            return paramMap;
        }
}
