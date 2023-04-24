package com.demo.app.controller.other;

import com.demo.app.service.other.ApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.del.BatchDeleteDto;
import model.dto.other.ApplyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;
import util.SearchFilter;

@RestController
@Validated
@Api(value = "ApplyController",tags = "課程報名")
@RequestMapping("/api/apply")
public class ApplyController {

    @Autowired
    ApplyService applyService;

    @PostMapping("/list")
    @ApiOperation(value = "取得報名資料")
    //@PreAuthorize("@ss.hasPer('default:system:apply')")
    public Result listApply(@RequestBody @Validated SearchFilter searchFilter) {
        return applyService.list(searchFilter);
    }

    @PostMapping
    @ApiOperation(value = "新增報名")
//    @PreAuthorize("@ss.hasPer('default:system:apply:add')")
    public Result insertApply(@RequestBody @Validated ApplyEntity insertApply) {
        return applyService.insert(insertApply);
    }

    @PostMapping("/del")
    @ApiOperation(value = "刪除報名")
//    @PreAuthorize("@ss.hasPer('default:system:apply:del')")
    public Result delApply(@RequestBody @Validated BatchDeleteDto batchDelete) {
        return applyService.del(batchDelete);
    }

//    @PutMapping
//    @ApiOperation(value = "修改報名資料")
////    @PreAuthorize("@ss.hasPer('default:system:apply:edit')")
//    public Result updateApply(@RequestBody @Validated ApplyEntity updateApply) {
//        return applyService.update(updateApply);
//    }
}
