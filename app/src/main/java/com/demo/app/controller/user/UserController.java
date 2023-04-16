package com.demo.app.controller.user;

import com.demo.app.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.del.BatchDeleteDto;
import model.dto.sys.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;
import util.SearchFilter;


/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-29 14:52
 **/
@RestController
@Validated
@Api(value = "UserRoleController", tags = "用户管理")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/list")
    @ApiOperation(value = "取得用戶清單")
//    @PreAuthorize("@ss.hasPer('default:system:account')")
    public Result listUser(@RequestBody @Validated SearchFilter searchFilter) {
        return userService.listUser(searchFilter);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "取得用戶明細")
//    @PreAuthorize("@ss.hasPer('default:system:account')")
    public Result userDetail(@PathVariable Integer id) {
        return userService.userDetail(id);
    }

    @PostMapping
    @ApiOperation(value = "註冊新用戶")
    @PreAuthorize("@ss.hasPer('default:system:account:add')")
    public Result insertUser(@RequestBody @Validated InsertUserDto insertUserDto) {
        return userService.insertUser(insertUserDto);
    }

    @PutMapping
    @ApiOperation(value = "修改用戶明細")
//    @PreAuthorize("@ss.hasPer('default:system:account:edit')")
    public Result updateUser(@RequestBody @Validated UpdateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }

    @PutMapping("/psd")
    @ApiOperation(value = "修改用户密码")
//    @PreAuthorize("@ss.hasPer('default:system:account:edit')")
    public Result updateUserPsd(@RequestBody @Validated UpdateUserPsdDto updateUserPsdDto) {
        return userService.updateUserPsd(updateUserPsdDto);
    }

    @PutMapping("/picturefilename")
    @ApiOperation(value = "修改用戶圖片檔案名")
//    @PreAuthorize("@ss.hasPer('default:system:account:edit')")
    public Result updateUserPictureFileName(@RequestBody @Validated UpdateUserPictureFileNameDto updateUserPictureFileNameDto) {
        return userService.updateUserPictureFileName(updateUserPictureFileNameDto);
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除用户")
    @PreAuthorize("@ss.hasPer('default:system:account:del')")
    public Result delUser(@RequestBody @Validated BatchDeleteDto batchDeleteDto) {
        return userService.delUser(batchDeleteDto);
    }

    @PostMapping("/child")
    @ApiOperation(value = "取得學生清單")
    //@PreAuthorize("@ss.hasPer('default:system:course')")
    public Result listUserChild(@RequestBody @Validated SearchFilter searchFilter) {
        return userService.listUserChild(searchFilter);
    }

    @PutMapping("/psd/reset")
    @ApiOperation(value = "重置用户密码")
    @PreAuthorize("@ss.hasPer('default:system:account:edit')")
    public Result resetUserPsd(@RequestBody @Validated ResetUserPsdDto resetUserPsdDto) {
        return userService.resetUserPsd(resetUserPsdDto);
    }
}
