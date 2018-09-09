package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.modules.app.dao.OrderDao;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

//    @Autowired
//    private OrderService orderService;
//    @Override
//    public List<OrderEntity> findListByUserIdAndStatus(int current, int size, long userId, int orderStatus) {
//        return orderService.findListByUserIdAndStatus((current-1)*size,size,userId,orderStatus);
//    }
}
