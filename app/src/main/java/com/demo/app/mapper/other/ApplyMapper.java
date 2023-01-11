package com.demo.app.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.dto.other.ApplyEntity;
import model.dto.other.ApplyStudentEntity;
import model.dto.other.SearchApplyDto;

import java.util.List;

public interface ApplyMapper extends BaseMapper<ApplyEntity> {
    List<ApplyEntity> selectApply(SearchApplyDto searchApplyDto);
    List<ApplyStudentEntity> selectApplyStudent(Integer id);

    String selectApplyStudentShow(Integer id);
    Integer insertApply(ApplyEntity apply);
    Integer updateApply(ApplyEntity apply);
    Integer insertApplyStudent(List<ApplyStudentEntity> applyStudent);
    void deleteApplyStudent(Integer id);
}
