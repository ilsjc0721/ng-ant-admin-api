package com.demo.app.service.other;

import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.OrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import model.dto.other.CustomerEntity;
import model.dto.other.OrderEntity;
import model.dto.other.SearchCustomerDto;
import model.dto.other.SearchOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import result.Result;
import util.SearchFilter;

import java.sql.Timestamp;
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

    public Result orderDetail(Integer id) {
        SearchOrderDto searchOrderDto = new SearchOrderDto();
        searchOrderDto.setId(id);
        List<OrderEntity> orderList = orderMapper.selectOrder(searchOrderDto);
        Optional<OrderEntity> order = orderList.stream().findFirst();
        if (order.isPresent()){
            return Result.success(order.get());
        } else {
            return Result.success(new CustomerEntity());
        }
    }

    private SearchOrderDto getSearchOrderDto(JSONObject jsonObject) {
        SearchOrderDto searchOrderDto = new SearchOrderDto();
        Integer roomId = jsonObject.getInteger("roomId");
        Timestamp inDate = jsonObject.getTimestamp("inDate");
        Integer customerId = jsonObject.getInteger("customerId");
        System.out.println("-----------");

        if (Objects.nonNull(roomId)) {
            System.out.println(roomId);
            searchOrderDto.setRoomId(roomId);
        }
        if (Objects.nonNull(inDate)) {
            System.out.println(inDate);
            searchOrderDto.setInDate(inDate);
        }
        if (Objects.nonNull(customerId)) {
            System.out.println(customerId);
            searchOrderDto.setCustomerId(customerId);
        }

        return searchOrderDto;
    }
}
