package io.renren.modules.app.controller;


import io.renren.common.annotation.AuthIgnore;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/app")
@Api("APP登录接口")
public class AppLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        long userId = userService.login(form);

        UserEntity userEntity = userService.selectById(userId);

        //生成token
        String token = jwtUtils.generateToken(userId);

        Map<String, Object> map = new HashMap<>();

        Map<String,Object> data = new HashMap<>();
        data.put("userId",userId);
        data.put("username",userEntity.getUsername());
        data.put("mobile",userEntity.getMobile());
        data.put("password",userEntity.getPassword());
        data.put("token", token);
        map.put("data",data);

        return R.ok(map);
    }

    @GetMapping("selectUserInfo")
    public R selectUserInfo(@RequestParam Integer userId){
        UserEntity userEntity = userService.selectById(userId);
        if (userEntity == null){
            return R.error(300,"没有该用户的信息");
        }else {
            Map<String, Object> map = new HashMap<>();
            Map<String,Object> data = new HashMap<>();
            data.put("userId",userId);
            data.put("username",userEntity.getUsername());
            data.put("mobile",userEntity.getMobile());
            data.put("password",userEntity.getPassword());
            data.put("sex", userEntity.getSex());
            data.put("address",userEntity.getAddress());
            data.put("headerUrl",userEntity.getHeaderUrl());
            map.put("data",data);

            return R.ok(map);
        }
    }

    @PostMapping("modifyUserInfo")
    public R modifyUserInfo(@RequestBody Map map){
        Long userId = Long.parseLong(map.get("userId").toString());//(long)map.get("userId");
        String username = (String)map.get("username");
        String sex = (String)map.get("sex");
        String address = (String)map.get("address");

        UserEntity userEntity = userService.selectById(userId);
        if (userEntity == null){
            return R.error(300,"不存在该用户");
        }else {
            UserEntity entity = new UserEntity();
            entity.setUserId(userId);
            if (username != null){
                entity.setUsername(username);
            }
            if (sex != null){
                entity.setSex(sex);
            }
            if (address != null){
                entity.setAddress(address);
            }

            boolean status = userService.updateById(entity);
            if (status){
                return R.ok();
            }else {
                return R.error(300,"修改失败");
            }
        }


    }







}
