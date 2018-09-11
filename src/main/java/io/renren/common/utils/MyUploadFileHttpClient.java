package io.renren.common.utils;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MyUploadFileHttpClient {

    public String client(String url, HttpMethod method, MultiValueMap<String,String> params){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Bmob-Application-Id", "623709487b89f25654b46947258f71f6");
        headers.add("X-Bmob-REST-API-Key", "06cb7471cde91c49a31912a9fe726c3c");
        headers.add("Content-Type", "image/jpeg");

        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return response.getBody();

    }

}
