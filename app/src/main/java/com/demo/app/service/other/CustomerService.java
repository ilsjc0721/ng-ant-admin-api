package com.demo.app.service.other;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.app.mapper.other.CustomerMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.del.BatchDeleteDto;
import model.dto.other.CustomerEntity;
import model.dto.other.InsertCustomer;
import model.dto.other.SearchCustomerDto;
import model.dto.other.UpdateCustomer;
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
public class CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    public Result listCustomer(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());

        SearchCustomerDto searchCustomerDto = new SearchCustomerDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchCustomerDto = getSearchCustomerDto(searchFilter.getFilters());
        }

        List<CustomerEntity> customerList = customerMapper.selectCustomer(searchCustomerDto);

        PageInfo<CustomerEntity> selectCustomerPageInfo = new PageInfo<>(customerList);
        return Result.success(selectCustomerPageInfo);
    }

    public Result customerDetail(Integer id) {
        SearchCustomerDto searchCustomerDto = new SearchCustomerDto();
        searchCustomerDto.setId(id);
        List<CustomerEntity> customerList = customerMapper.selectCustomer(searchCustomerDto);
        Optional<CustomerEntity> customer = customerList.stream().findFirst();
        if (customer.isPresent()){
            return Result.success(customer.get());
        } else {
            return Result.success(new CustomerEntity());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insertCustomer(InsertCustomer insertCustomer) {
        // 驗證身分證號是否重複
        if (isUniqueIdNumber(insertCustomer.getIdNumber())) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_ADD_CUSTOMER_DUPLICATE);
        }

        CustomerEntity customer = new CustomerEntity();
        BeanUtils.copyProperties(insertCustomer, customer);
        int res = customerMapper.insert(customer);

        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updateCustomer(UpdateCustomer updateCustomer) {
        CustomerEntity customer = new CustomerEntity();
        BeanUtils.copyProperties(updateCustomer, customer);

        int res = customerMapper.updateById(customer);

        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        }
        return Result.success();
    }

    public Result delCustomer(BatchDeleteDto batchDeleteDto) {
        customerMapper.deleteBatchIds(batchDeleteDto.getIds());
        return Result.success();
    }

    private boolean isUniqueIdNumber(String idNumber) {
        QueryWrapper<CustomerEntity> idNumberQuery = new QueryWrapper<CustomerEntity>().eq("id_number", idNumber);
        List<CustomerEntity> CustomerEntitys = customerMapper.selectList(idNumberQuery);
        return CollectionUtil.isNotEmpty(CustomerEntitys);
    }

    private SearchCustomerDto getSearchCustomerDto(JSONObject jsonObject) {
        SearchCustomerDto searchCustomerDto = new SearchCustomerDto();
        String name = jsonObject.getString("name");
        String idNumber = jsonObject.getString("idNumber");
        String phone = jsonObject.getString("phone");
        Integer id = jsonObject.getInteger("id");

        if (Objects.nonNull(name)) {
            searchCustomerDto.setName(name);
        }
        if (Objects.nonNull(idNumber)) {
            searchCustomerDto.setIdNumber(idNumber);
        }
        if (Objects.nonNull(phone)) {
            searchCustomerDto.setPhone(phone);
        }
        if (Objects.nonNull(id)) {
            searchCustomerDto.setId(id);
        }

        return searchCustomerDto;
    }
}
