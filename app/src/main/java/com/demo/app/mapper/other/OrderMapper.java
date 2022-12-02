package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.OrderEntity;
import model.dto.other.SearchOrderDto;
import java.util.List;

public interface OrderMapper extends BaseMapper<OrderEntity>{
    List<OrderEntity> selectOrder(SearchOrderDto searchOrderDto);
}
