package com.demo.app.service.other;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.ClassMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.other.*;
import model.dto.sys.user.UAndDAndIUserRoleDto;
import model.entity.sys.SysUser;
import model.entity.sys.UserChild;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import result.CommonConstants;
import result.Result;
import util.SearchFilter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ClassService {
    @Autowired
    ClassMapper classMapper;

    public Result list(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());

        SearchClassDto searchClassDto = new SearchClassDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchClassDto = getSearchClassDto(searchFilter.getFilters());
        }

        List<ClassResponse> classResponseList = classMapper.selectClass(searchClassDto);

        for (ClassResponse classResponse : classResponseList) {
            List<ClassCoachResponse> classCoachResponseList = classMapper.selectClassCoach(classResponse.getId());
            for (ClassCoachResponse classCoachResponse : classCoachResponseList){
                if (!classCoachResponse.getName().equals("")){
                    if (classResponse.getCoach() != null){
                        classResponse.setCoach(classResponse.getCoach() + "," + classCoachResponse.getName());
                    } else {
                        classResponse.setCoach(classCoachResponse.getName());
                    }
                }
            }
            List<ClassStudentResponse> classStudentResponseList = classMapper.selectClassStudent(classResponse.getId());
            for (ClassStudentResponse classStudentResponse : classStudentResponseList){
                if (!classStudentResponse.getName().equals("")){
                    if (classResponse.getStudent() != null){
                        classResponse.setStudent(classResponse.getStudent() + "," + classStudentResponse.getName());
                    } else {
                        classResponse.setStudent(classStudentResponse.getName());
                    }
                }
            }
        }

        PageInfo<ClassResponse> selectClaseePageInfo = new PageInfo<>(classResponseList);
        return Result.success(selectClaseePageInfo);
    }

    public Result listClassStudent(SearchFilter searchFilter){
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());

        String name = "";
        if (Objects.nonNull(searchFilter.getFilters())) {
            JSONObject filters = searchFilter.getFilters();
            name = filters.getString("name");
        }

        List<ClassStudentResponse> classStudentResponseList = classMapper.listClassStudent(name);
        PageInfo<ClassStudentResponse> selectClassStudentPageInfo = new PageInfo<>(classStudentResponseList);
        return Result.success(selectClassStudentPageInfo);
    }

    public Result insertClass(@RequestBody @Validated ClassRequest classRequest){
//        if (isUniqueUserName(insertUserDto.getUserName())) {
//            return Result.failure(ErrorCodeEnum.SYS_ERR_ACCOUNT);
//        }
        List<ClassStudentEntity>  classStudentList = new ArrayList<>();
        List<ClassCoachEntity>  classCoachList = new ArrayList<>();
        for (ClassDateEntity classDateEntity : classRequest.getClassDateList()){
            ClassEntity classEntity = new ClassEntity();
            classEntity.setCourseId(classRequest.getCourseId());
            classEntity.setHours(classRequest.getHours());
            classEntity.setUpdateUser(classRequest.getUpdateUser());
            classEntity.setStartDatetime(classDateEntity.getStartDatetime());
            classEntity.setEndDatetime(classDateEntity.getEndDatetime());
            int res = classMapper.insert(classEntity);
            // Coach
            for (Integer coachId : classRequest.getCoachId()){
                ClassCoachEntity classCoach = new ClassCoachEntity();
                classCoach.setClassId(classEntity.getId());
                classCoach.setCoachId(coachId);
                classCoachList.add(classCoach);
            }
            // Student
            for (Integer childId : classRequest.getChildId()){
                ClassStudentEntity classStudent = new ClassStudentEntity();
                classStudent.setClassId(classEntity.getId());
                classStudent.setStudentId(childId);
                classStudentList.add(classStudent);
            }
        }
        if (classCoachList.size() > 0){
            classMapper.insertClassCoachByList(classCoachList);
        }
        if (classStudentList.size() > 0){
            classMapper.insertClassStudentByList(classStudentList);
        }
//        SysUser sysUser = new SysUser();
//        // 密码加密
//        String encodePassword = passwordEncoder.encode(insertUserDto.getPassword());
//        BeanUtils.copyProperties(insertUserDto, sysUser);
//        sysUser.setPassword(encodePassword);
//        // 插入用户
//        int res = userMapper.insert(sysUser);
//
//        if (CollectionUtil.isNotEmpty(insertUserDto.getRoleId())) {
//            // 插入用户角色
//            UAndDAndIUserRoleDto uAndDAndIUserRoleDto = new UAndDAndIUserRoleDto();
//            uAndDAndIUserRoleDto.setUserId(sysUser.getId());
//            uAndDAndIUserRoleDto.setRoleId(insertUserDto.getRoleId());
//            insertRoles(uAndDAndIUserRoleDto);
//        }
//        if (CollectionUtil.isNotEmpty(insertUserDto.getUserChildVoList())) {
//            for (UserChild userChild : insertUserDto.getUserChildVoList()) {
//                userChild.setParentId(sysUser.getId());
//            }
//            userMapper.insertUserChildByList(insertUserDto.getUserChildVoList());
//        }
//        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
//            return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
//        }
        return Result.success();
    }

    private SearchClassDto getSearchClassDto(JSONObject jsonObject) {
        SearchClassDto searchClassDto = new SearchClassDto();
        String name = jsonObject.getString("name");
        Timestamp startDate = jsonObject.getTimestamp("startDate");
        Timestamp endDate = jsonObject.getTimestamp("endDate");
        Integer coach = jsonObject.getInteger("coach");
        Integer student = jsonObject.getInteger("student");
        Boolean notConfirm = jsonObject.getBoolean("notConfirm");
        if (Objects.nonNull(name)) {
            searchClassDto.setName(name);
        }
        if (Objects.nonNull(startDate)) {
            searchClassDto.setStartDate(startDate);
        }
        if (Objects.nonNull(endDate)) {
            searchClassDto.setEndDate(endDate);
        }
        if (Objects.nonNull(coach)) {
            searchClassDto.setCoach(coach);
        }
        if (Objects.nonNull(notConfirm)) {
            searchClassDto.setNotConfirm(notConfirm);
        }
        if (Objects.nonNull(student)) {
            searchClassDto.setStudent(student);
        }

        return searchClassDto;
    }
}
