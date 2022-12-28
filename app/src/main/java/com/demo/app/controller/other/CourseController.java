package com.demo.app.controller.other;

import com.demo.app.service.other.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.Result;
import util.SearchFilter;

@RestController
@Validated
@Api(value = "CourseController",tags = "課程管理")
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping("/list")
    @ApiOperation(value = "取得課程")
    //@PreAuthorize("@ss.hasPer('default:system:course')")
    public Result listCourse(@RequestBody @Validated SearchFilter searchFilter) {
        return courseService.listCourse(searchFilter);
    }
}
