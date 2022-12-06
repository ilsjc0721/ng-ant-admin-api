package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.InsertRevenue;
import model.dto.other.OrderEntity;
import model.dto.other.SearchOrderDto;
import model.dto.sys.user.InsertUserRoleDto;
import model.dto.sys.user.SearchUserDto;
import model.vo.sys.SelectUserVo;

import java.util.ArrayList;
import java.util.List;

public interface OrderMapper extends BaseMapper<OrderEntity>{
    List<OrderEntity> selectOrder(SearchOrderDto searchOrderDto);

    Integer insertRevenue(List<InsertRevenue> insertRevenueList);
}
