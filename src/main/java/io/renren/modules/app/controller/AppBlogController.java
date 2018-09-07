package io.renren.modules.app.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.entity.BlogEntity;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.service.BlogService;
import io.renren.modules.app.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
@Api("APP文章接口")
public class AppBlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;


    @GetMapping("/list")
    public R getArticleList(@RequestParam Integer userId){
        List<BlogEntity> user = blogService.selectList(new EntityWrapper<BlogEntity>().eq("user_id",userId));

        UserEntity userEntity = userService.selectById(userId);
        Map<String, Object> map = new HashMap<>();

        Map<String,Object> data = new HashMap<>();
        data.put("list",user);
        data.put("userId",userId);
        data.put("username",userEntity.getUsername());
        map.put("data",data);

        return R.ok(map);

    }

    @PostMapping("/addlist")
    public R addArticleList(@RequestBody Map map){
        String title = (String)map.get("article_title");
        String content = (String) map.get("article_content");
        Integer userId = (Integer) map.get("userId");

        UserEntity user = userService.selectById(userId);
        if (user == null){
            return R.error(300,"没有该用户");
        }else {

        }


        BlogEntity blogEntity = new BlogEntity();

        blogEntity.setArticleContent(content);
        blogEntity.setArticleTitle(title);
        blogEntity.setUserId(userId);
        blogEntity.setSubmissionDate(new Date());
        blogEntity.setArticleAuthor(user.getUsername());


        boolean status = blogService.insertAllColumn(blogEntity);

        if (status){

            return R.ok();
        }else {
            return R.error(300,"error");
        }


    }

    @GetMapping("/articleDetail")
    public R selectOneArticle(@RequestParam Integer articleId){
        BlogEntity blogEntity = blogService.selectOne(new EntityWrapper<BlogEntity>().eq("article_id",articleId));

        if (blogEntity == null){
            return R.error(300,"没有该文章");
        }else {
            Map<String,Object> map = new HashMap<>();
            map.put("content",blogEntity.getArticleContent());
            map.put("title",blogEntity.getArticleTitle());
            map.put("author",blogEntity.getArticleAuthor());
            map.put("time",blogEntity.getSubmissionDate());
            map.put("userId",blogEntity.getUserId());
            map.put("articleId",blogEntity.getArticleId());

            Map<String,Object> data = new HashMap<>();
            data.put("data",map);
            return R.ok(data);
        }
    }

    @PostMapping("/deleteArticle")
    public R deleteArticle(@RequestBody Map map){
        Integer userId = (Integer)map.get("userId");
        Integer articleId = (Integer)map.get("articleId");

        UserEntity userEntity = userService.selectById(userId);
        BlogEntity blogEntity = blogService.selectById(articleId);
        BlogEntity entity = blogService.selectOne(new EntityWrapper<BlogEntity>().eq("user_id",userId).eq("article_id",articleId));

        if (userEntity == null ){
            return R.error(300,"没有该用户");
        }else {
            if (blogEntity == null){
                return R.error(300,"没有该文章");
            }else {

                if (entity == null){
                    return R.error(300,"该用户没有该文章");
                }else {

                    boolean trueOrfalse = blogService.deleteById(articleId);

                    if (trueOrfalse){
                        return R.ok();
                    }else {
                        return R.error(300,"该文章已被删除");
                    }
                }



            }
        }

    }

    @PostMapping("/updateArticle")
    public R updateArticle(@RequestBody Map map){
        String content = (String)map.get("articleContent");
        Integer articleId = (Integer)map.get("articleId");
        Integer userId = (Integer)map.get("userId");

        BlogEntity blogEntity = blogService.selectOne(new EntityWrapper<BlogEntity>().eq("user_id",userId).eq("article_id",articleId));

        if (blogEntity == null){
            return R.error(300,"该用户没有该文章");
        }else {
            BlogEntity entity = new BlogEntity();
            entity.setArticleContent(content);
            entity.setSubmissionDate(new Date());
            entity.setArticleId(articleId);
            entity.setUserId(userId);
            boolean status = blogService.updateById(entity);
            if (status){
                return R.ok();
            }else {
                return R.error(300,"更新失败");
            }
        }




    }

}
