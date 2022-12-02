package com.demo.app.service.other;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.app.mapper.other.OrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.del.BatchDeleteDto;
import model.dto.other.*;
import model.entity.sys.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.CommonConstants;
import result.Result;
import util.SearchFilter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    public Result listOrder(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());

        SearchOrderDto searchOrderDto = new SearchOrderDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchOrderDto = getSearchOrderDto(searchFilter.getFilters());
        }

        List<OrderEntity> orderList = orderMapper.selectOrder(searchOrderDto);

        PageInfo<OrderEntity> selectOrderPageInfo = new PageInfo<>(orderList);
        return Result.success(selectOrderPageInfo);
    }

    private SearchOrderDto getSearchOrderDto(JSONObject jsonObject) {
        SearchOrderDto searchOrderDto = new SearchOrderDto();
        Integer roomId = jsonObject.getInteger("roomId");
        String inDate = jsonObject.getString("inDate");
        Integer customerId = jsonObject.getInteger("customerId");

        if (Objects.nonNull(roomId)) {
            searchOrderDto.setRoomId(roomId);
        }
        if (Objects.nonNull(inDate)) {
            searchOrderDto.setInDate(inDate);
        }
        if (Objects.nonNull(customerId)) {
            searchOrderDto.setCustomerId(customerId);
        }

        return searchOrderDto;
    }
}
