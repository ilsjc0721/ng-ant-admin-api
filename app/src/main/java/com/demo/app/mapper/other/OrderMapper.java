package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.*;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderEntity>{
    List<OrderEntity> selectOrder(SearchOrderDto searchOrderDto);

    Integer insertRevenue(List<InsertRevenue> insertRevenueList);

    Integer updateRoomStatus(UpdateRoom updateroom);

    Integer checkIn(CheckIn checkIn);

    List<ReportResponse> selectReport(SearchReportDto searchReportDto);
}
