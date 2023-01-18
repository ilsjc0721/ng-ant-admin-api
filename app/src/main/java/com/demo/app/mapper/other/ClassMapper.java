package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.*;
import model.entity.sys.UserChild;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import result.Result;

import java.util.List;

public interface ClassMapper extends BaseMapper<ClassEntity> {
    List<ClassResponse> selectClass(SearchClassDto searchClassDto);

    List<ClassCoachResponse> selectClassCoach(Integer classId);

    List<ClassStudentResponse> selectClassStudent(Integer classId);

    List<ClassFeeResponse> getClassFee(Integer classId);

    List<ClassStudentResponse> listClassStudent(String name);

    Integer insertClassCoachByList(List<ClassCoachEntity> classCoachList);

    Integer insertClassStudentByList(List<ClassStudentEntity> classStudentList);
    Integer updateClassById(ClassRequest classRequest);

    void deleteClassCoach(Integer id);
    void deleteClassStudent(Integer id);

    List<ClassCalendar> getClassCalendar_All(String classMonth);
    List<ClassCalendar> getClassCalendar_Coach(Integer id, String classMonth);
    List<ClassCalendar> getClassCalendar_Student(Integer id, String classMonth);

    Integer updateClassStatus(ClassConfirmRequest classConfirmRequest);
    Integer updateClassStudent(ClassStudentRequest classStudentEntity);
}
