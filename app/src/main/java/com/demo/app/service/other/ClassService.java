package com.demo.app.service.other;

import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.ClassMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import model.dto.other.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import result.Result;
import util.SearchFilter;

import java.sql.Timestamp;
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
