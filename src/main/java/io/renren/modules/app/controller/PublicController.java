package io.renren.modules.app.controller;
import io.renren.common.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/*
import io.renren.common.utils.R;
import io.renren.common.utils.UUIDBuild;
import io.renren.modules.app.utils.JwtUtils;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
*/
@RequestMapping("/public")
@RestController
public class PublicController {


    @PostMapping("/upload")
    public R uploadFile(HttpServletRequest request, MultipartFile multipartFile, HttpServletResponse response) throws IOException {
        Map<String,Object> data = new HashMap<>();
        if (request.getContentLength()>0){

            InputStream inputStream = null;
            FileOutputStream outputStream = null;

            try {

                inputStream = request.getInputStream();
                long now = System.currentTimeMillis();
                String folderName = "/file/upload/image/";
                String string = request.getSession().getServletContext().getRealPath("/");
                String path = string + folderName + now;
                String fileName = multipartFile.getOriginalFilename();
                String newFileName =  now + fileName.substring(fileName.lastIndexOf("."));
                File targetFile = new File(path,newFileName);
                outputStream = new FileOutputStream(targetFile);
                byte temp[] = new byte[1024];
                int size = -1;
                while ((size = inputStream.read(temp)) != -1){
                    outputStream.write(temp,0,size);
                }

                multipartFile.transferTo(targetFile);
                String imageUrl = "/mimo" + folderName +now +"/"+newFileName;
                System.err.println("返回本地web路径："+ imageUrl);
                String localUrl = string + folderName+now+"/"+newFileName;
                System.err.println("本地硬盘tomcat物理路径："+localUrl);
                if (imageUrl != null){
                    Map<String,Object> map = new HashMap<>();
                    map.put("img",imageUrl);
                    data.put("data",map);

                }else {

                    return R.error(300,"上传失败");
                }

            }catch (IOException e){
                return R.error("未知异常");
            }finally {
                outputStream.close();
                inputStream.close();
//                return R.error();
            }

        }
        return R.ok(data);
    }




    /*
    private JwtUtils jwtUtils;

    @PostMapping("uploadFile")
    public R uploadFile(@RequestParam(value = "upfile",required = true) MultipartFile multipartFile, HttpServletRequest request){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        //生成token
        Long imageId = Long.parseLong(format.toString());
        String token = jwtUtils.generateToken(imageId);
        String time = format.format(date);
        String folderName = "/file/upload/image/";
        String string = request.getSession().getServletContext().getRealPath("/");
        String path = string + folderName + time;
        System.out.println(path);
        String fileName = multipartFile.getOriginalFilename();

        String newFileName = UUIDBuild.getUUID() + fileName.substring(fileName.lastIndexOf("."));
        File targetFile = new File(path,newFileName);

        if (!targetFile.exists()){
            targetFile.mkdirs();
        }

        try {
            multipartFile.transferTo(targetFile);
            String imageUrl = "/mimo" + folderName +time +"/"+newFileName;
            System.err.println("返回本地web路径："+ imageUrl);
            String localUrl = string + folderName+time+"/"+newFileName;
            System.err.println("本地硬盘tomcat物理路径："+localUrl);
            if (imageUrl != null){
                Map<String,Object>map = new HashMap<>();
                map.put("img",imageUrl);
                Map<String,Object> data = new HashMap<>();
                data.put("data",map);
                return R.ok(data);
            }else {
                return R.error(300,"上传失败");
            }


        }catch (Exception e) {
            e.printStackTrace();
            return R.error("未知错误");
        }

    }
    */

}
