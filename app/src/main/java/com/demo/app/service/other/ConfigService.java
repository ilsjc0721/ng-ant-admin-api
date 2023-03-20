package com.demo.app.service.other;

import com.demo.app.mapper.other.ConfigMapper;
import enums.ErrorCodeEnum;
import model.dto.other.ConfigEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import result.CommonConstants;
import result.Result;

@Service
public class ConfigService {
    @Autowired
    ConfigMapper configMapper;

    public Result getValue(String name) {
        String value = configMapper.getValue(name);
        return Result.success(value);
    }

    public Result updateValue(ConfigEntity updateConfig) {
        ConfigEntity config = new ConfigEntity();
        BeanUtils.copyProperties(updateConfig, config);

        int res = configMapper.updateValue(config);

        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        } else {
            return Result.success();
        }
    }
}
