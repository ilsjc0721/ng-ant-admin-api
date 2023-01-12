package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.*;
import result.Result;

import java.util.List;

public interface ClassMapper extends BaseMapper<ClassEntity> {
    List<ClassResponse> selectClass(SearchClassDto searchClassDto);

    List<ClassCoachResponse> selectClassCoach(Integer classId);

    List<ClassStudentResponse> selectClassStudent(Integer classId);

    List<ClassStudentResponse> listClassStudent(String name);
}
