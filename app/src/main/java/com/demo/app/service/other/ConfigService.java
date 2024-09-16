package com.demo.app.service.other;

import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.ConfigMapper;
import enums.ErrorCodeEnum;
import model.dto.other.ConfigEntity;
import model.dto.other.SearchConfigDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import result.CommonConstants;
import result.Result;
import util.SearchFilter;

import java.util.List;
import java.util.Objects;

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

    public Result insertValue(ConfigEntity updateConfig) {
        ConfigEntity config = new ConfigEntity();
        BeanUtils.copyProperties(updateConfig, config);

        int res = configMapper.insertValue(config);

        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        } else {
            return Result.success();
        }
    }

    public Result deleteConfig(Integer id) {
        Integer value = configMapper.deleteConfig(id);
        return Result.success(value);
    }

    public Result list(SearchFilter searchFilter) {
        SearchConfigDto searchConfigDto = new SearchConfigDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchConfigDto = getSearchConfigDto(searchFilter.getFilters());
        }

        List<ConfigEntity> configList = configMapper.selectConfig(searchConfigDto);

        return Result.success(configList);
    }

    private SearchConfigDto getSearchConfigDto(JSONObject jsonObject) {
        SearchConfigDto searchConfigDto = new SearchConfigDto();
        Integer id = jsonObject.getInteger("id");
        String name = jsonObject.getString("name");

        if (Objects.nonNull(id)) {
            searchConfigDto.setId(id);
        }
        if (Objects.nonNull(name)) {
            searchConfigDto.setName(name);
        }

        return searchConfigDto;
    }
}
