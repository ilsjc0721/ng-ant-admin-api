package com.demo.app.mapper.other;

import model.dto.other.FeeDetailReportEntity;
import model.dto.other.FeeReportEntity;
import model.dto.other.SearchFeeReportDto;

import java.util.List;

public interface FeeMapper {
    List<FeeReportEntity> getFee(SearchFeeReportDto searchFeeReportDto);
    List<FeeDetailReportEntity> getFeeDetail(Integer id);
}
