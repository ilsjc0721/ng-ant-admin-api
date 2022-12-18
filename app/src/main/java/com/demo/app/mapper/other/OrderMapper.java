package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.*;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface OrderMapper extends BaseMapper<OrderEntity>{
    List<OrderEntity> selectOrder(SearchOrderDto searchOrderDto);

    Integer insertRevenue(List<InsertRevenue> insertRevenueList);

    Integer updateRoomStatus(UpdateRoom updateroom);

    Integer checkIn(CheckIn checkIn);

    List<ReportResponse> selectReport(SearchReportDto searchReportDto);

    List<OrderEntity> isOverlap(@Param("roomId") Integer roomId, @Param("inDate") Timestamp inDate, @Param("outDate") Timestamp outDate, @Param("id") Integer id);

    Integer cancelOrder(CancelOrder cancelOrder);
}
