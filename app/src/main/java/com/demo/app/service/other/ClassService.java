package com.demo.app.service.other;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.ClassMapper;
import com.demo.app.mapper.other.CourseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.del.BatchDeleteDto;
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

    @Autowired
    CourseMapper courseMapper;

    public Result list(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());

        SearchClassDto searchClassDto = new SearchClassDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchClassDto = getSearchClassDto(searchFilter.getFilters());
        }

        List<ClassResponse> classResponseList = classMapper.selectClass(searchClassDto);

        for (ClassResponse classResponse : classResponseList) {
            classResponse.setCoachId(new ArrayList<>());
            classResponse.setStudentId(new ArrayList<>());
            List<ClassCoachResponse> classCoachResponseList = classMapper.selectClassCoach(classResponse.getId());
            for (ClassCoachResponse classCoachResponse : classCoachResponseList){
                if (!classCoachResponse.getName().equals("")){
                    if (classResponse.getCoach() != null){
                        classResponse.setCoach(classResponse.getCoach() + "," + classCoachResponse.getName());
                    } else {
                        classResponse.setCoach(classCoachResponse.getName());
                    }
                    classResponse.getCoachId().add(classCoachResponse.getId());
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
                    classResponse.getStudentId().add(classStudentResponse.getId());
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
        List<CourseFeeEntity> courseFeeList = courseMapper.selectCourseFee(classRequest.getCourseId());
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
//        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
//            return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
//        }
        return Result.success();
    }

    public Result updateClass(@RequestBody @Validated ClassRequest classRequest){
//        if (isUniqueUserName(insertUserDto.getUserName())) {
//            return Result.failure(ErrorCodeEnum.SYS_ERR_ACCOUNT);
//        }
        classMapper.updateClassById(classRequest);
        classMapper.deleteClassCoach(classRequest.getId());
        classMapper.deleteClassStudent(classRequest.getId());

        List<ClassStudentEntity>  classStudentList = new ArrayList<>();
        List<ClassCoachEntity>  classCoachList = new ArrayList<>();
        // Coach
        for (Integer coachId : classRequest.getCoachId()){
            ClassCoachEntity classCoach = new ClassCoachEntity();
            classCoach.setClassId(classRequest.getId());
            classCoach.setCoachId(coachId);
            classCoachList.add(classCoach);
        }
        // Student
        for (Integer childId : classRequest.getChildId()){
            ClassStudentEntity classStudent = new ClassStudentEntity();
            classStudent.setClassId(classRequest.getId());
            classStudent.setStudentId(childId);
            classStudentList.add(classStudent);
        }
        if (classCoachList.size() > 0){
            classMapper.insertClassCoachByList(classCoachList);
        }
        if (classStudentList.size() > 0){
            classMapper.insertClassStudentByList(classStudentList);
        }
        return Result.success();
    }

    public Result delClass(BatchDeleteDto batchDeleteDto) {
        classMapper.deleteBatchIds(batchDeleteDto.getIds());
        for (Integer id : batchDeleteDto.getIds()){
            classMapper.deleteClassCoach(id);
            classMapper.deleteClassStudent(id);
        }
        return Result.success();
    }

    public Result getClassStudentDetail(Integer id) {
        List<ClassStudentResponse> classStudentResponseList = classMapper.selectClassStudent(id);
        return Result.success(classStudentResponseList);
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
