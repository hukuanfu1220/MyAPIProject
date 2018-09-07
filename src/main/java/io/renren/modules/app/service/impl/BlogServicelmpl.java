package io.renren.modules.app.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.app.dao.BlogDao;
import io.renren.modules.app.entity.BlogEntity;
import io.renren.modules.app.service.BlogService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("blogService")
public class BlogServicelmpl extends ServiceImpl<BlogDao, BlogEntity> implements BlogService {

}
