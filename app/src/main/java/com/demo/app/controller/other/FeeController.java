package com.demo.app.controller.other;

import com.demo.app.service.other.FeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.other.ClassRequest;
import model.dto.other.FeeMailRequest;
import model.dto.other.SearchFeeReportDto;
import model.dto.other.UpdateFeeStatusDto;
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
    @ApiOperation(value = "費用郵件")
    //@PreAuthorize("@ss.hasPer('default:system:fee-tuition')")
    public Result sendFeeMail(@RequestBody @Validated FeeMailRequest feeMailRequest) {
        return feeService.sendFeeMail(feeMailRequest);
    }

}
