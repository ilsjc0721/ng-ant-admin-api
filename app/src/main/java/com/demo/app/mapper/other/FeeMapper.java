package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeeMapper extends BaseMapper<FeeEntity> {

    Integer updateFeeStatus(@Param("id") Integer id, @Param("status") String status, @Param("userId") Integer userId, @Param("memo") String memo);
    List<FeeReportEntity> getFee(SearchFeeReportDto searchFeeReportDto);
    List<FeeDetailReportEntity> getFeeDetail(Integer id);

    Integer calculateFeeById(@Param("id") Integer id,@Param("updateUser") Integer updateUser);

    Integer deleteFeeDetailByClassId(Integer classId);

    List<Integer> getFeeIdByClassId(Integer classId);

    MailSettingEntity getMailSetting();

}
