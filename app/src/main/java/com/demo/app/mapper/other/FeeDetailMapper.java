package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.ClassRequest;
import model.dto.other.FeeDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

public interface FeeDetailMapper extends BaseMapper<FeeDetailEntity> {
    Integer insertByEntity(FeeDetailEntity feeDetailEntity);
    Integer insertDeposit(
            @Param("user_id") Integer user_id,
            @Param("deposit_date") Timestamp deposit_date,
            @Param("type") String type,
            @Param("deposit") Integer deposit,
            @Param("fee_id") Integer fee_id,
            @Param("class_id") Integer class_id,
            @Param("memo") String memo,
            @Param("update_time") Timestamp update_time
    );
}
