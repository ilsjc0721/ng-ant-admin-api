package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.ClassRequest;
import model.dto.other.FeeDetailEntity;

public interface FeeDetailMapper extends BaseMapper<FeeDetailEntity> {
    Integer insertByEntity(FeeDetailEntity feeDetailEntity);
}
