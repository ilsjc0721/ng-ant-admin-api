package com.demo.app.controller.other;

import com.demo.app.service.other.FeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.other.ClassRequest;
import model.dto.other.SearchFeeReportDto;
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
@Api(value = "ApplyController",tags = "費用")
@RequestMapping("/api/fee")
public class FeeController {

    @Autowired
    FeeService feeService;

    @PostMapping
    @ApiOperation(value = "費用")
    @PreAuthorize("@ss.hasPer('default:system:fee')")
    public Result getFee(@RequestBody @Validated SearchFeeReportDto searchFeeReportDto) {
        return feeService.getFee(searchFeeReportDto);
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
}
