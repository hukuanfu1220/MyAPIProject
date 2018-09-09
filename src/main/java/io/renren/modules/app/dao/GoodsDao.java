package io.renren.modules.app.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.app.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsDao extends BaseMapper<GoodsEntity> {
}
