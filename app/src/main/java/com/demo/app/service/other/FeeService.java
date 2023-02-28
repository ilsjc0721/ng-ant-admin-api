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

    public Result updateFeeStatus(UpdateFeeStatusDto updateFeeStatusDto) {
        if(updateFeeStatusDto.getUserId().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("userId"));
        }

        if(updateFeeStatusDto.getId().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("id"));
        }

        if(updateFeeStatusDto.getStatus().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("status"));
        }

        if (feeMapper.updateFeeStatus(
                updateFeeStatusDto.getId(),
                updateFeeStatusDto.getStatus(),
                updateFeeStatusDto.getUserId(),
                updateFeeStatusDto.getMemo()) > 0) {
            return Result.success();
        } else {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        }
    }
    public Result getFee(SearchFeeReportDto searchFeeReportDto) {
        searchFeeReportDto.setUserId(null);
        List<FeeReportEntity> feeList = feeMapper.getFee(searchFeeReportDto);

        for (FeeReportEntity fee : feeList) {
            List<FeeDetailReportEntity> feeDetailList = feeMapper.getFeeDetail(fee.getId());
            fee.setFeeDetailReport(feeDetailList);
        }

        return Result.success(feeList);
    }
    public Result getCoachFee(SearchFeeReportDto searchFeeReportDto) {
        if(searchFeeReportDto.getType().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("type"));
        }

        List<FeeReportEntity> feeList = feeMapper.getFee(searchFeeReportDto);

        for (FeeReportEntity fee : feeList) {
            List<FeeDetailReportEntity> feeDetailList = feeMapper.getFeeDetail(fee.getId());
            fee.setFeeDetailReport(feeDetailList);
        }

        return Result.success(feeList);
    }
    public Result getTuitionFee(SearchFeeReportDto searchFeeReportDto) {
        if(searchFeeReportDto.getType().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("type"));
        }

        List<FeeReportEntity> feeList = feeMapper.getFee(searchFeeReportDto);

        for (FeeReportEntity fee : feeList) {
            List<FeeDetailReportEntity> feeDetailList = feeMapper.getFeeDetail(fee.getId());
            fee.setFeeDetailReport(feeDetailList);
        }

        return Result.success(feeList);
    }

}
