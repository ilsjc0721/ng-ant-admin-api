package com.demo.app.controller.other;

import com.demo.app.service.other.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.del.BatchDeleteDto;
import model.dto.other.InsertCustomer;
import model.dto.other.UpdateCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;
import util.SearchFilter;

@RestController
@Validated
@Api(value = "OrderController",tags = "訂單管理")
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/list")
    @ApiOperation(value = "取得訂單管理資料")
//    @PreAuthorize("@ss.hasPer('default:system:order')")
    public Result listOrder(@RequestBody @Validated SearchFilter searchFilter) {
        return orderService.listOrder(searchFilter);
    }
}
