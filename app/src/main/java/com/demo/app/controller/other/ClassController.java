package com.demo.app.controller.other;

import com.demo.app.service.other.ClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.del.BatchDeleteDto;
import model.dto.other.ClassConfirmRequest;
import model.dto.other.ClassRequest;
import model.dto.sys.user.InsertUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping
    @ApiOperation(value = "新增排課")
//    @PreAuthorize("@ss.hasPer('default:system:account:add')")
    public Result insertClass(@RequestBody @Validated ClassRequest classRequest) {
        return classService.insertClass(classRequest);
    }

    @PutMapping
    @ApiOperation(value = "修改排課")
//    @PreAuthorize("@ss.hasPer('default:system:account:add')")
    public Result updateClass(@RequestBody @Validated ClassRequest classRequest) {
        return classService.updateClass(classRequest);
    }
    @PostMapping("/del")
    @ApiOperation(value = "删除排課")
//    @PreAuthorize("@ss.hasPer('default:system:account:del')")
    public Result delClass(@RequestBody @Validated BatchDeleteDto batchDeleteDto) {
        return classService.delClass(batchDeleteDto);
    }

    @PostMapping("/student")
    @ApiOperation(value = "取得學生清單")
    //@PreAuthorize("@ss.hasPer('default:system:course')")
    public Result listClassStudent(@RequestBody @Validated SearchFilter searchFilter) {
        return classService.listClassStudent(searchFilter);
    }

    @GetMapping("/student/{id}")
    @ApiOperation(value = "取得學生明細")
//    @PreAuthorize("@ss.hasPer('default:system:account')")
    public Result getClassStudentDetail(@PathVariable Integer id) {
        return classService.getClassStudentDetail(id);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "取得排課明細")
//    @PreAuthorize("@ss.hasPer('default:system:account')")
    public Result getClassFee(@PathVariable Integer id) {
        return classService.getClassFee(id);
    }

    @GetMapping("/calendar")
    @ApiOperation(value = "取得月曆")
//    @PreAuthorize("@ss.hasPer('default:system:account')")
    public Result getClassCalendar(@RequestParam Integer id, @RequestParam String classMonth, @RequestParam String classType) {
        return classService.getClassCalendar(id, classMonth, classType);
    }

    @PostMapping("/confirm")
    @ApiOperation(value = "到課確認")
    public Result confirmClass(@RequestBody @Validated ClassConfirmRequest classConfirmRequest) {
        return classService.confirmClass(classConfirmRequest);
    }

    @PostMapping("/rollback-confirm")
    @ApiOperation(value = "到課取消")
    public Result rollbackConfirmClass(@RequestBody @Validated ClassConfirmRequest classConfirmRequest) {
        return classService.rollbackConfirmClass(classConfirmRequest);
    }
}
