package io.renren.modules.app.controller;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import io.renren.common.utils.MyUploadFileHttpClient;
import io.renren.common.utils.R;
import io.renren.common.utils.UUIDBuild;
import io.swagger.annotations.ApiOperation;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@RequestMapping("public")
@RestController
public class PublicController {



    /**
     * 在配置文件中配置的文件保存路径
     */
    private String location = "/Users/hukuanfu/Desktop/renren-fast/imagedata";

    private String [] types = {".jpg",".bmg",".jpeg",".png"};
    @RequestMapping(value = "/uploadimage",method = RequestMethod.POST)
    public R upload(@RequestParam(value = "file",required = false) MultipartFile file) throws Exception{





        String fileName = "";
        if (!file.isEmpty()){
            fileName = file.getOriginalFilename();

            AVFile file1 = new AVFile("image.png",file.getBytes());
            file1.save();

            try {
                file1.save();
            }catch (AVException e){

            }

// 文件保存到云端后，获取其 URL 和文件大小
            String imgUrl = file1.getUrl();
            file.getSize();

            //存在web本地服务器
            String type = fileName.substring(fileName.lastIndexOf("."));
            if (Arrays.asList(types).contains(type)){
                BufferedOutputStream outputStream = null;
                File fileSourcePath = new File(location);
                if (!fileSourcePath.exists()){
                    fileSourcePath.mkdirs();
                }

                fileName = file.getOriginalFilename();
                outputStream = new BufferedOutputStream(new FileOutputStream(new File(fileSourcePath,fileName)));
                outputStream.write(file.getBytes());
                outputStream.flush();

                return R.ok().put("img",imgUrl);
            }
            return R.error("此格式不支持！");
        }
        return R.error("文件不能为空！");
    }


    @RequestMapping(value = "/uploadvideo",method = RequestMethod.POST)
    public R uploadVideo(@RequestParam(value = "file",required = false) MultipartFile file) throws Exception{


        String fileName = "";
        if (!file.isEmpty()){
            fileName = file.getOriginalFilename();

            AVFile file1 = new AVFile("video.mp4",file.getBytes());
            file1.save();

            try {
                file1.save();
            }catch (AVException e){

            }

// 文件保存到云端后，获取其 URL 和文件大小
            String videoUrl = file1.getUrl();
            file.getSize();


            return R.ok().put("videourl",videoUrl);
        }
        return R.error("文件不能为空！");
    }


    @PostMapping("uploadfile")
    @ApiOperation("上传")
    public R uploadFile(@RequestParam(value = "upfile",required = false) MultipartFile multipartFile, HttpServletRequest request){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
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

            return R.ok().put("img",imageUrl);


        }catch (Exception e) {
            e.printStackTrace();
            return R.error("未知错误");
        }

    }

    /**
     * 将文件保存到字节数组中
     * This class implements an output stream in which the data is written into a byte array.
     * @author chunlynn
     */
    public byte[] inputStreamToByte(InputStream in) throws Exception {
        // This class implements an output stream in which the data is written into a byte array.
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); // 输出流对象，用来接收文件流，然后写入一个字节数组中
        int len;
        byte[] buffer = new byte[1024]; //缓存1KB
        while ((len = in.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        byte[] data = bos.toByteArray(); // 字节数组，输出流中的文件保存到字节数组
        bos.close();
        return data;
    }




}
