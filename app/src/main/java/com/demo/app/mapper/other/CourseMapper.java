package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import model.dto.other.*;
import org.apache.ibatis.annotations.Param;

import model.dto.other.CourseEntity;

import java.util.List;

public interface CourseMapper extends BaseMapper<CourseEntity>{
    List<CourseEntity> selectCourse(SearchCourseDto searchCourseDto);
    List<CourseFeeEntity> selectCourseFee(Integer id);
}
