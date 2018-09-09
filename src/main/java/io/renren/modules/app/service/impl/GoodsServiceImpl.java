package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.app.dao.GoodsDao;
import io.renren.modules.app.entity.GoodsEntity;
import io.renren.modules.app.service.GoodsService;
import org.springframework.stereotype.Service;

@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {
}
