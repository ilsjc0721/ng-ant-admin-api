package com.demo.app.service.other;

import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.CourseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import model.dto.other.CourseEntity;
import model.dto.other.CourseFeeEntity;
import model.dto.other.SearchCourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import result.Result;
import util.SearchFilter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
public class CourseService {

    @Autowired
    CourseMapper courseMapper;

    public Result listCourse(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());

        SearchCourseDto searchCourseDto = new SearchCourseDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchCourseDto = getSearchCourseDto(searchFilter.getFilters());
        }

        List<CourseEntity> courseList = courseMapper.selectCourse(searchCourseDto);

        for (CourseEntity course : courseList) {
            List<CourseFeeEntity> courseFeeList = courseMapper.selectCourseFee(course.getId());
            course.setCourseFee(courseFeeList);
        }

        PageInfo<CourseEntity> selectCoursePageInfo = new PageInfo<>(courseList);
        return Result.success(selectCoursePageInfo);
    }

    private SearchCourseDto getSearchCourseDto(JSONObject jsonObject) {
        SearchCourseDto searchCourseDto = new SearchCourseDto();
        String name = jsonObject.getString("name");
        String status = jsonObject.getString("status");

        if (Objects.nonNull(name)) {
            searchCourseDto.setName(name);
        }
        if (Objects.nonNull(status)) {
            searchCourseDto.setStatus(status);
        }

        return searchCourseDto;
    }
}
