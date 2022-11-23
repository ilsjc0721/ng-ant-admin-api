package com.demo.app.controller.permission;

import com.demo.app.service.permission.RolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.sys.permission.UpdateRolePermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;


/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-22 15:19
 **/
@RestController
@Validated
@Api(value = "RolePermissionController", tags = "角色權限配置")
@RequestMapping("/api/permission")
public class RolePermissionController {

    @Autowired
    RolePermissionService rolePermissionService;

    @GetMapping("/{roleId}")
    @PreAuthorize("@ss.hasPer('default:system:role-manager')")
    @ApiOperation(value = "取得角色權限")
    public Result userPermission(@PathVariable Integer roleId) {
        return rolePermissionService.userPermission(roleId);
    }

    @PutMapping
    @PreAuthorize("@ss.hasPer('default:system:role-manager:set-role')")
    @ApiOperation(value = "設定角色權限")
    public Result updatePermission(@RequestBody @Validated UpdateRolePermissionDto updatePermissionDto) {
        return rolePermissionService.updatePermission(updatePermissionDto);
    }
}
