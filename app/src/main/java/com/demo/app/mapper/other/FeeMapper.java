package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.*;

import java.util.List;

public interface FeeMapper extends BaseMapper<FeeEntity> {
    List<FeeReportEntity> getFee(SearchFeeReportDto searchFeeReportDto);
    List<FeeDetailReportEntity> getFeeDetail(Integer id);
}
