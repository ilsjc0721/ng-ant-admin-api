package com.demo.app.mapper.other;

import model.dto.other.ConfigEntity;
import model.dto.other.SearchConfigDto;

import java.util.List;

public interface ConfigMapper {
    String getValue(String name);
    Integer updateValue(ConfigEntity config);
    Integer insertValue(ConfigEntity config);
    List<ConfigEntity> selectConfig(SearchConfigDto searchConfigDto);
    Integer deleteConfig(Integer Id);
}
