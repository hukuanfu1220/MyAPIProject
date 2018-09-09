package io.renren.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.app.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
}
