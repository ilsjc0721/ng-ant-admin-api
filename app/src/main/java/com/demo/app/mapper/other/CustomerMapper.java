package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.CustomerEntity;
import model.dto.other.CustomerResponse;
import model.dto.other.SearchCustomerDto;

import java.util.List;

public interface CustomerMapper extends BaseMapper<CustomerEntity> {
    List<CustomerResponse> selectCustomer(SearchCustomerDto searchCustomerDto);
}
