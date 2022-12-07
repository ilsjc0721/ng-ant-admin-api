package com.demo.app.service.other;

import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.OrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.del.BatchDeleteDto;
import model.dto.other.*;
import model.dto.sys.user.InsertUserRoleDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.CommonConstants;
import result.Result;
import util.SearchFilter;

import java.sql.Timestamp;
import java.util.ArrayList;
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

    @Transactional(rollbackFor = Exception.class)
    public Result insertOrder(InsertOrder insertOrder) {

        OrderEntity order = new OrderEntity();
        BeanUtils.copyProperties(insertOrder, order);
        int res = orderMapper.insert(order);

        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
        } else {
            ArrayList<InsertRevenue> InsertRevenueList = new ArrayList<>();
            if (order.getStatus().equals("入住")) {
                InsertRevenue revenue = new InsertRevenue();
                revenue.setOrderId(order.getId());
                revenue.setPayment(insertOrder.getPayment());
                revenue.setType("房費");
                revenue.setRevenue(order.getPrice());
                revenue.setRevenueUser(order.getUpdateUser());
                revenue.setRevenueDate(order.getUpdateTime());
                InsertRevenueList.add(revenue);
                //ToDo: update room.status
            } else {
                if (order.getDeposit().equals(0)) {
                    InsertRevenue revenue = new InsertRevenue();
                    revenue.setOrderId(order.getId());
                    revenue.setPayment(insertOrder.getPayment());
                    revenue.setType("未付款");
                    revenue.setRevenue(order.getPrice());
                    revenue.setRevenueUser(order.getUpdateUser());
                    revenue.setRevenueDate(order.getUpdateTime());
                    InsertRevenueList.add(revenue);
                } else {
                    InsertRevenue revenue = new InsertRevenue();
                    revenue.setOrderId(order.getId());
                    revenue.setPayment(insertOrder.getPayment());
                    revenue.setType("未付款");
                    revenue.setRevenue(order.getPrice() - order.getDeposit());
                    revenue.setRevenueUser(order.getUpdateUser());
                    revenue.setRevenueDate(order.getUpdateTime());
                    InsertRevenueList.add(revenue);

                    InsertRevenue revenue1 = new InsertRevenue();
                    revenue1.setOrderId(order.getId());
                    revenue1.setPayment(insertOrder.getPayment());
                    revenue1.setType("訂金");
                    revenue1.setRevenue(order.getDeposit());
                    revenue1.setRevenueUser(order.getUpdateUser());
                    revenue1.setRevenueDate(order.getUpdateTime());
                    InsertRevenueList.add(revenue1);
                }
            }

            int resRevenue = orderMapper.insertRevenue(InsertRevenueList);
            if (resRevenue == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
                ArrayList<Integer> delIds = new ArrayList<>();
                delIds.add(order.getId());
                orderMapper.deleteBatchIds(delIds);
                return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
            }
            UpdateRoom updateRoomStatus = new UpdateRoom();
            updateRoomStatus.setId(order.getRoomId());
            updateRoomStatus.setStatus(order.getType());
            int updateRoom = orderMapper.updateRoomStatus(updateRoomStatus);
            return Result.success();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updateOrder(UpdateOrder updateOrder) {
        OrderEntity order = new OrderEntity();
        BeanUtils.copyProperties(updateOrder, order);

        int res = orderMapper.updateById(order);

        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public Result checkIn(CheckIn checkIn) {
        int res = orderMapper.checkIn(checkIn);

        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        }
        return Result.success();
    }

    public Result delOrder(BatchDeleteDto batchDelete) {
        orderMapper.deleteBatchIds(batchDelete.getIds());
        return Result.success();
    }
    private SearchOrderDto getSearchOrderDto(JSONObject jsonObject) {
        SearchOrderDto searchOrderDto = new SearchOrderDto();
        Integer roomId = jsonObject.getInteger("roomId");
        Timestamp inDate = jsonObject.getTimestamp("inDate");
        Integer customerId = jsonObject.getInteger("customerId");

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
