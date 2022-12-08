package com.demo.app.controller.other;

import com.demo.app.service.other.OrderService;
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
@Api(value = "OrderController",tags = "訂房管理")
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/list")
    @ApiOperation(value = "取得訂房管理資料")
//    @PreAuthorize("@ss.hasPer('default:system:order')")
    public Result listOrder(@RequestBody @Validated SearchFilter searchFilter) {
        return orderService.listOrder(searchFilter);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "取得訂房明細")
//    @PreAuthorize("@ss.hasPer('default:system:customer')")
    public Result orderDetail(@PathVariable Integer id) {
        return orderService.orderDetail(id);
    }

    @PostMapping
    @ApiOperation(value = "新增訂房")
//    @PreAuthorize("@ss.hasPer('default:system:customer:add')")
    public Result insertCustomer(@RequestBody @Validated InsertOrder insertOrder) {
        return orderService.insertOrder(insertOrder);
    }

    @PutMapping
    @ApiOperation(value = "修改訂房資料")
//    @PreAuthorize("@ss.hasPer('default:system:customer:edit')")
    public Result updateCustomer(@RequestBody @Validated UpdateOrder updateOrder) {
        return orderService.updateOrder(updateOrder);
    }

    @PostMapping("/del")
    @ApiOperation(value = "刪除訂房資料")
//    @PreAuthorize("@ss.hasPer('default:system:customer:del')")
    public Result delCustomer(@RequestBody @Validated BatchDeleteDto batchDelete) {
        return orderService.delOrder(batchDelete);
    }

    @PostMapping("/checkin")
    @ApiOperation(value = "訂房入住")
//    @PreAuthorize("@ss.hasPer('default:system:customer:checkin')")
    public Result checkIn(@RequestBody @Validated CheckIn checkIn) {
        return orderService.checkIn(checkIn);
    }

    @PostMapping("/report")
    @ApiOperation(value = "帳款報表")
//    @PreAuthorize("@ss.hasPer('default:system:customer:report')")
    public Result report(@RequestBody @Validated SearchFilter searchFilter) {
        return orderService.report(searchFilter);
    }
}
