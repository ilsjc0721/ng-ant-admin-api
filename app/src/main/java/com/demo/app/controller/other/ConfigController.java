package com.demo.app.controller.other;

import com.demo.app.service.other.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.other.ConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;
import util.SearchFilter;

@RestController
@Validated
@Api(value = "ConfigController",tags = "設定檔")
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    ConfigService configService;

    @PostMapping("/list")
    @ApiOperation(value = "取得設定")
    public Result list(@RequestBody @Validated SearchFilter searchFilter) {
        return configService.list(searchFilter);
    }

    @GetMapping("/{name}")
    @ApiOperation(value = "取得Value")
    public Result getValue(@PathVariable String name) {
        return configService.getValue(name);
    }

    @PutMapping
    @ApiOperation(value = "修改Value")
    public Result updateValue(@RequestBody @Validated ConfigEntity updateConfig) {
        return configService.updateValue(updateConfig);
    }

    @PostMapping
    @ApiOperation(value = "新增Value")
    public Result insertValue(@RequestBody @Validated ConfigEntity updateConfig) {
        return configService.insertValue(updateConfig);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "刪除Value")
    public Result deleteConfig(@PathVariable Integer id) {
        return configService.deleteConfig(id);
    }
}
