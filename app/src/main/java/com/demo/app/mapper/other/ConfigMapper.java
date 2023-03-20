package com.demo.app.mapper.other;

import model.dto.other.ConfigEntity;

import java.util.List;

public interface ConfigMapper {
    String getValue(String name);
    Integer updateValue(ConfigEntity config);
}
