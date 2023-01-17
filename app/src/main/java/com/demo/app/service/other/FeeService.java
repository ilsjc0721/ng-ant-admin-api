package com.demo.app.service.other;

import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.FeeMapper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import enums.MenuEnum;
import model.dto.other.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import result.Result;
import util.SearchFilter;
import util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
public class FeeService {

    @Autowired
    FeeMapper feeMapper;

    public Result getCoachFee(SearchFilter searchFilter) {
        SearchFeeReportDto searchFeeReportDto = new SearchFeeReportDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchFeeReportDto = getSearchFeeReportDto(searchFilter.getFilters());
        }

        if(searchFeeReportDto.getType().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("type"));
        }

        List<FeeReportEntity> feeList = feeMapper.getFee(searchFeeReportDto);

        for (FeeReportEntity fee : feeList) {
            List<FeeDetailReportEntity> feeDetailList = feeMapper.getFeeDetail(fee.getId());
            fee.setFeeDetailReport(feeDetailList);
        }

        PageInfo<FeeReportEntity> selectFeeReportPageInfo = new PageInfo<>(feeList);
        return Result.success(selectFeeReportPageInfo);
    }
    public Result getTuitionFee(SearchFilter searchFilter) {
        SearchFeeReportDto searchFeeReportDto = new SearchFeeReportDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchFeeReportDto = getSearchFeeReportDto(searchFilter.getFilters());
        }

        if(searchFeeReportDto.getType().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("type"));
        }

        List<FeeReportEntity> feeList = feeMapper.getFee(searchFeeReportDto);

        for (FeeReportEntity fee : feeList) {
            List<FeeDetailReportEntity> feeDetailList = feeMapper.getFeeDetail(fee.getId());
            fee.setFeeDetailReport(feeDetailList);
        }

        PageInfo<FeeReportEntity> selectFeeReportPageInfo = new PageInfo<>(feeList);
        return Result.success(selectFeeReportPageInfo);
    }

    private SearchFeeReportDto getSearchFeeReportDto(JSONObject jsonObject) {
        SearchFeeReportDto searchFeeReportDto = new SearchFeeReportDto();
        String period = jsonObject.getString("period");
        String type = jsonObject.getString("type");
        Integer userId = jsonObject.getInteger("userId");
        String status = jsonObject.getString("status");

        if (Objects.nonNull(period)) {
            searchFeeReportDto.setPeriod(period);
        }
        if (Objects.nonNull(type)) {
            searchFeeReportDto.setType(type);
        }
        if (Objects.nonNull(userId)) {
            searchFeeReportDto.setUserId(userId);
        }
        if (Objects.nonNull(status)) {
            searchFeeReportDto.setStatus(status);
        }

        return searchFeeReportDto;
    }
}
