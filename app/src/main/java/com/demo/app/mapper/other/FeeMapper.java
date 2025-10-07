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

    Integer getFeeCount(Integer classId);

    List<DepositEntity> getDeposit(SearchDepositDto searchDepositDto);

    Integer getSummaryDeposit(Integer userId);

    Integer insertDeposit(DepositEntity deposit);
    Integer updateDeposit(DepositEntity deposit);
    void delDeposit(Integer id);
    List<RevenueEntity> getRevenue(SearchFeeReportDto searchFeeReportDto);

    Integer insertClassroomFee(@Param("ym") String ym, @Param("id") Integer id);
    Integer delClassroomFee(@Param("id") Integer id);

    List<ClassroomFeeEntity> getClassroomFee (SearchClassroomFeeDto searchClassroomFeeDto);
}
