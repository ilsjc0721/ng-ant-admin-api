package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeeMapper extends BaseMapper<FeeEntity> {
    List<FeeReportEntity> getFee(SearchFeeReportDto searchFeeReportDto);
    List<FeeDetailReportEntity> getFeeDetail(Integer id);

    Integer calculateFeeById(@Param("id") Integer id,@Param("updateUser") Integer updateUser);

    Integer deleteFeeDetailByClassId(Integer classId);

    List<Integer> getFeeIdByClassId(Integer classId);

}
