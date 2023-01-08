package com.demo.app.controller.other;

import com.demo.app.service.other.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.del.BatchDeleteDto;
import model.dto.other.CourseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
        return courseService.list(searchFilter);
    }

    @PostMapping
    @ApiOperation(value = "新增課程")
//    @PreAuthorize("@ss.hasPer('default:system:course:add')")
    public Result insertCourse(@RequestBody @Validated CourseEntity insertCourse) {
        return courseService.insert(insertCourse);
    }

    @PostMapping("/del")
    @ApiOperation(value = "刪除課程")
//    @PreAuthorize("@ss.hasPer('default:system:course:del')")
    public Result delOrder(@RequestBody @Validated BatchDeleteDto batchDelete) {
        return courseService.del(batchDelete);
    }

    @PutMapping
    @ApiOperation(value = "修改課程")
//    @PreAuthorize("@ss.hasPer('default:system:course:edit')")
    public Result updateCustomer(@RequestBody @Validated CourseEntity updateCourse) {
        return courseService.update(updateCourse);
    }
}
