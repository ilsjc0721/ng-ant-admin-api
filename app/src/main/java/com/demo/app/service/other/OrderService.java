package com.demo.app.service.other;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.OrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.del.BatchDeleteDto;
import model.dto.other.*;
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

    private boolean isOverlap(Integer roomId, Timestamp inDate, Timestamp outDate, Integer id) {
        List<OrderEntity> orderList = orderMapper.isOverlap(roomId, inDate, outDate, id);
        return CollectionUtil.isNotEmpty(orderList);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insertOrder(InsertOrder insertOrder) {
        // check overlap
        if (isOverlap(insertOrder.getRoomId(), insertOrder.getInDate(), insertOrder.getOutDate(), 0)) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_ADD_ORDER_DUPLICATE);
        }

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
    public Result cancelOrder(CancelOrder cancelOrder) {

        int res = orderMapper.cancelOrder(cancelOrder);

//        if (res == CommonConstants.DataBaseOperationStatus.IS_FAILURE) {
//            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
//        }
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

    public Result report(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());

        SearchReportDto searchReportDto = new SearchReportDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchReportDto = getReportDto(searchFilter.getFilters());
        }

        List<ReportResponse> reportList = orderMapper.selectReport(searchReportDto);

        PageInfo<ReportResponse> selectReportPageInfo = new PageInfo<>(reportList);
        return Result.success(selectReportPageInfo);
    }
    private SearchOrderDto getSearchOrderDto(JSONObject jsonObject) {
        SearchOrderDto searchOrderDto = new SearchOrderDto();
        Integer roomId = jsonObject.getInteger("roomId");
        Timestamp inDate = jsonObject.getTimestamp("inDate");
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

    private SearchReportDto getReportDto(JSONObject jsonObject) {
        SearchReportDto searchReportDto = new SearchReportDto();

        Timestamp startDate = jsonObject.getTimestamp("startDate");
        Timestamp endDate = jsonObject.getTimestamp("endDate");
        String orderType = jsonObject.getString("orderType");
        String payment = jsonObject.getString("payment");
        String revenueType = jsonObject.getString("revenueType");
        Integer revenueUser = jsonObject.getInteger("revenueUser");

        if (Objects.nonNull(startDate)) {
            searchReportDto.setStartDate(startDate);
        }
        if (Objects.nonNull(endDate)) {
            searchReportDto.setEndDate(endDate);
        }
        if (Objects.nonNull(orderType)) {
            searchReportDto.setOrderType(orderType);
        }

        if (Objects.nonNull(payment)) {
            searchReportDto.setPayment(payment);
        }

        if (Objects.nonNull(revenueType)) {
            searchReportDto.setRevenueType(revenueType);
        }

        if (Objects.nonNull(revenueUser)) {
            searchReportDto.setRevenueUser(revenueUser);
        }

        return searchReportDto;
    }
}
