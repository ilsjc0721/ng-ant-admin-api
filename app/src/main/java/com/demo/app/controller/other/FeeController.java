package com.demo.app.controller.other;

import com.demo.app.service.other.FeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.del.BatchDeleteDto;
import model.dto.other.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;
import util.SearchFilter;

@RestController
@Validated
@Api(value = "ApplyController",tags = "費用")
@RequestMapping("/api/fee")
public class FeeController {

    @Autowired
    FeeService feeService;

    @PostMapping
    @ApiOperation(value = "費用總表")
    @PreAuthorize("@ss.hasPer('default:system:fee')")
    public Result getFee(@RequestBody @Validated SearchFeeReportDto searchFeeReportDto) {
        return feeService.getFee(searchFeeReportDto);
    }

    @PutMapping
    @ApiOperation(value = "更新費用總表狀態")
    @PreAuthorize("@ss.hasPer('default:system:fee')")
    public Result updateFeeStatus(@RequestBody @Validated UpdateFeeStatusDto updateFeeStatusDto) {
        return feeService.updateFeeStatus(updateFeeStatusDto);
    }

    @PostMapping("/coach")
    @ApiOperation(value = "教練費")
    //@PreAuthorize("@ss.hasPer('default:system:fee-coach')")
    public Result getCoachFee(@RequestBody @Validated SearchFeeReportDto searchFeeReportDto) {
        return feeService.getCoachFee(searchFeeReportDto);
    }

    @PostMapping("/tuition")
    @ApiOperation(value = "學費")
    //@PreAuthorize("@ss.hasPer('default:system:fee-tuition')")
    public Result getTuitionFee(@RequestBody @Validated SearchFeeReportDto searchFeeReportDto) {
        return feeService.getTuitionFee(searchFeeReportDto);
    }

    @PostMapping("/mail")
    @ApiOperation(value = "費用郵件通知")
    //@PreAuthorize("@ss.hasPer('default:system:fee-tuition')")
    public Result sendFeeMail(@RequestBody @Validated FeeMailRequest feeMailRequest) {
        return feeService.sendFeeMail(feeMailRequest);
    }

    @PostMapping("/deposit/list")
    @ApiOperation(value = "取得點數歷程")
    //@PreAuthorize("@ss.hasPer('default:system:apply')")
    public Result getDeposit(@RequestBody @Validated SearchFilter searchFilter) {
        return feeService.getDeposit(searchFilter);
    }

    @GetMapping("/deposit/{id}")
    @ApiOperation(value = "取得點數")
//    @PreAuthorize("@ss.hasPer('default:system:account')")
    public Result getSummaryDeposit(@PathVariable Integer id) {
        return feeService.getSummaryDeposit(id);
    }

    @PostMapping("/deposit")
    @ApiOperation(value = "新增點數")
    @PreAuthorize("@ss.hasPer('default:system:deposit')")
    public Result insertDeposit(@RequestBody @Validated DepositEntity insertDeposit) {
        return feeService.insertDeposit(insertDeposit);
    }

    @PostMapping("/deposit/del")
    @ApiOperation(value = "刪除點數")
    @PreAuthorize("@ss.hasPer('default:system:deposit')")
    public Result delDeposit(@RequestBody @Validated DepositEntity deleteDeposit) {
        return feeService.delDeposit(deleteDeposit);
    }

    @PutMapping("/deposit")
    @ApiOperation(value = "修改點數")
    @PreAuthorize("@ss.hasPer('default:system:deposit')")
    public Result updateDeposit(@RequestBody @Validated DepositEntity updateDeposit) {
        return feeService.updateDeposit(updateDeposit);
    }

    @PostMapping("/revenue")
    @ApiOperation(value = "場地營業額")
    @PreAuthorize("@ss.hasPer('default:system:fee')")
    public Result getRevenue(@RequestBody @Validated SearchFeeReportDto searchFeeReportDto) {
        return feeService.getRevenue(searchFeeReportDto);
    }

}
