package com.demo.app.controller.other;

import com.demo.app.service.other.ClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;
import util.SearchFilter;

@RestController
@Validated
@Api(value = "ClassController",tags = "課程排定")
@RequestMapping("/api/class")
public class ClassController {
    @Autowired
    ClassService classService;

    @PostMapping("/list")
    @ApiOperation(value = "取得排課清單")
    //@PreAuthorize("@ss.hasPer('default:system:course')")
    public Result listClass(@RequestBody @Validated SearchFilter searchFilter) {
        return classService.list(searchFilter);
    }

    @PostMapping("/student")
    @ApiOperation(value = "取得學生清單")
    //@PreAuthorize("@ss.hasPer('default:system:course')")
    public Result listClassStudent(@RequestBody @Validated SearchFilter searchFilter) {
        return classService.listClassStudent(searchFilter);
    }
}
