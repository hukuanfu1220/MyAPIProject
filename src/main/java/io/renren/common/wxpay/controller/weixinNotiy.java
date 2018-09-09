package io.renren.common.wxpay.controller;

import io.renren.common.wxpay.util.XMLUtil;
import org.jdom.JDOMException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController()
@RequestMapping("wx")
public class weixinNotiy {

	@PostMapping("notify")
	public String weixinnotiy(HttpServletRequest request){
		String inputLine;
		String notityXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map m = null;
		try {
			m = XMLUtil.doXMLParse(notityXml);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	//	System.err.println("fee" + m.get("return_code").toString());
		if("SUCCESS".equals(m.get("return_code").toString())){
			Double fee = Double.parseDouble(m.get("total_fee").toString())*0.01;
		//	System.err.println("fee" + fee);
		//	tOrderTService.updateByOrderNum( m.get("out_trade_no").toString(), m.get("transaction_id").toString(),"微信", fee);
			return "success";
		}else {
			return "fail";
		}
	}
}
