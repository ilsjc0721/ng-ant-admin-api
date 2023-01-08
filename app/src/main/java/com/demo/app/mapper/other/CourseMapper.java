package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.*;

import model.dto.other.CourseEntity;

import java.util.List;

public interface CourseMapper extends BaseMapper<CourseEntity>{
    List<CourseEntity> selectCourse(SearchCourseDto searchCourseDto);
    List<CourseFeeEntity> selectCourseFee(Integer id);
    Integer insertCourse(CourseEntity course);
    Integer updateCourse(CourseEntity course);
    Integer insertCourseFee(List<CourseFeeEntity> courseFee);
    void deleteCourseFee(Integer id);
}
