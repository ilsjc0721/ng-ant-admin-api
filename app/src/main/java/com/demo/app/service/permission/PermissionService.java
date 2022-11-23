package com.demo.app.service.permission;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.app.mapper.permission.PermissionMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import enums.MenuEnum;
import model.dto.sys.permission.InsertPermissionDto;
import model.dto.sys.permission.UpdatePermissionDto;
import model.entity.sys.SysPermission;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import result.CommonConstants;
import result.Result;
import util.SearchFilter;
import util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    public Result selectSysPermissionList(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(),searchFilter.getPageSize());
        JSONObject filters = searchFilter.getFilters();
        QueryWrapper<SysPermission> permissionQueryWrapper = new QueryWrapper<SysPermission>().orderByAsc("order_num");

        if(Objects.nonNull(filters)){
            String menuName = filters.getString("menuName");
            if(Objects.nonNull(menuName)){
                permissionQueryWrapper.like("menu_name",menuName);
            }
            Boolean visible = filters.getBoolean("visible");
            if(Objects.nonNull(visible)){
                permissionQueryWrapper.eq("visible",visible);
            }
            Boolean status = filters.getBoolean("status");
            if(Objects.nonNull(status)){
                permissionQueryWrapper.eq("status",status);
            }
        }
        List<SysPermission> SysPermission = permissionMapper.selectList(permissionQueryWrapper);
        PageInfo<SysPermission> permissionVoPageInfo = new PageInfo<>(SysPermission);
        return Result.success(permissionVoPageInfo);
    }

    public Result selectSysPermissionById(Integer id) {
        SysPermission SysPermission = permissionMapper.selectById(id);
        return Result.success(SysPermission);
    }

    public Result insertSysPermission(InsertPermissionDto permissionDto) {
        String menuType = permissionDto.getMenuType();
        String path = permissionDto.getPath();
        // 菜单不能没有路由
        if(Objects.equals(menuType, MenuEnum.MENU.getValue()) && StringUtils.isEmpty(path)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("path"));
        }
        QueryWrapper<SysPermission> permissionQueryWrapper = new QueryWrapper<SysPermission>().eq("menu_name", permissionDto.getMenuName()).or().eq("code", permissionDto.getCode());
        SysPermission permission = permissionMapper.selectOne(permissionQueryWrapper);
        if(Objects.nonNull(permission)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_MENU_CODE_REPETITION);
        }
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(permissionDto,sysPermission);
        int res =  permissionMapper.insert(sysPermission);
        if (res == CommonConstants.DataBaseOperationStatus.IS_FAILURE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
        }
        return Result.success();
    }

    public Result updateSysPermission(UpdatePermissionDto permissionDto) {
        String menuType = permissionDto.getMenuType();
        String path = permissionDto.getPath();
        // 菜单不能没有路由
        if(Objects.equals(menuType, MenuEnum.MENU.getValue()) && StringUtils.isEmpty(path)){
                return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("path"));
        }

        List<Integer> integers = permissionMapper.selectPermission(permissionDto.getMenuName(), permissionDto.getCode(), permissionDto.getId());
        if(CollectionUtil.isNotEmpty(integers)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_MENU_CODE_REPETITION);
        }
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(permissionDto,sysPermission);
        int res =  permissionMapper.updateById(sysPermission);
        if (res == CommonConstants.DataBaseOperationStatus.IS_FAILURE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        }
        return Result.success();
    }

    public Result deleteSysPermissionByIds(List<Integer> ids) {
        Integer id = ids.get(0);
        List<SysPermission> SysPermission = permissionMapper.selectList(new QueryWrapper<>());
        for (SysPermission permission : SysPermission) {
            if(permission.getFatherId().equals(id)){
                return Result.failure(ErrorCodeEnum.SYS_ERR_DELETE_MENU);
            }
        }
        int res = permissionMapper.deleteById(id);
        if (res == CommonConstants.DataBaseOperationStatus.IS_FAILURE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_DELETE_FAILED);
        }
        return Result.success();
    }

    public Result selectUserMenuPer(Integer userId){
        List<SysPermission> sysPermissions = permissionMapper.selectMenuPerByUserId(userId).stream().distinct().collect(Collectors.toList());
        return Result.success(sysPermissions);
    }
}
