package com.demo.app.controller.other;

import com.demo.app.service.other.CustomerService;
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
@Api(value = "CustomerController",tags = "客戶管理")
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/list")
    @ApiOperation(value = "取得客戶清單")
//    @PreAuthorize("@ss.hasPer('default:system:customer')")
    public Result listCustomer(@RequestBody @Validated SearchFilter searchFilter) {
        return customerService.listCustomer(searchFilter);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "取得客戶明細")
//    @PreAuthorize("@ss.hasPer('default:system:customer')")
    public Result customerDetail(@PathVariable Integer id) {
        return customerService.customerDetail(id);
    }

    @PostMapping
    @ApiOperation(value = "註冊新客戶")
//    @PreAuthorize("@ss.hasPer('default:system:customer:add')")
    public Result insertCustomer(@RequestBody @Validated InsertCustomer insertCustomer) {
        return customerService.insertCustomer(insertCustomer);
    }

    @PutMapping
    @ApiOperation(value = "修改客戶明細")
//    @PreAuthorize("@ss.hasPer('default:system:customer:edit')")
    public Result updateCustomer(@RequestBody @Validated UpdateCustomer updateCustomer) {
        return customerService.updateCustomer(updateCustomer);
    }

    @PostMapping("/del")
    @ApiOperation(value = "刪除客戶")
//    @PreAuthorize("@ss.hasPer('default:system:customer:del')")
    public Result delCustomer(@RequestBody @Validated BatchDeleteDto batchDeleteDto) {
        return customerService.delCustomer(batchDeleteDto);
    }
}
