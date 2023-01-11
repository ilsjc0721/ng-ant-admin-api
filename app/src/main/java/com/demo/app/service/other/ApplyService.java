package com.demo.app.service.other;

import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.ApplyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.del.BatchDeleteDto;
import model.dto.other.ApplyEntity;
import model.dto.other.ApplyStudentEntity;
import model.dto.other.SearchApplyDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.CommonConstants;
import result.Result;
import util.SearchFilter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ApplyService {

    @Autowired
    ApplyMapper applyMapper;

    public Result list(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());

        SearchApplyDto searchApplyDto = new SearchApplyDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchApplyDto = getSearchApplyDto(searchFilter.getFilters());
        }

        List<ApplyEntity> applyList = applyMapper.selectApply(searchApplyDto);

        for (ApplyEntity apply : applyList) {
            List<ApplyStudentEntity> applyStudentList = applyMapper.selectApplyStudent(apply.getId());
            apply.setApplyStudent(applyStudentList);
            String studentShow = applyMapper.selectApplyStudentShow(apply.getId());
            apply.setApplyStudentShow(studentShow);
        }

        PageInfo<ApplyEntity> selectApplyPageInfo = new PageInfo<>(applyList);
        return Result.success(selectApplyPageInfo);
    }
    private SearchApplyDto getSearchApplyDto(JSONObject jsonObject) {
        SearchApplyDto searchApplyDto = new SearchApplyDto();
        Timestamp startDate = jsonObject.getTimestamp("startDate");
        Timestamp endDate = jsonObject.getTimestamp("endDate");
        Integer updateUser = jsonObject.getInteger("updateUser");

        if (Objects.nonNull(startDate)) {
            searchApplyDto.setStartDate(startDate);
        }
        if (Objects.nonNull(endDate)) {
            searchApplyDto.setEndDate(endDate);
        }

        if (Objects.nonNull(updateUser)) {
            searchApplyDto.setUpdateUser(updateUser);
        }

        return searchApplyDto;
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insert(ApplyEntity insertApply) {
        ApplyEntity apply = new ApplyEntity();
        BeanUtils.copyProperties(insertApply, apply);
        int res = applyMapper.insertApply(apply);
        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
        } else {
            ArrayList<ApplyStudentEntity> applyStudent = new ArrayList<>();
            insertApply.getApplyStudent().forEach((e) -> {
                ApplyStudentEntity student = new ApplyStudentEntity();
                student.setApplyId(apply.getId());
                student.setStudentId(e.getStudentId());
                applyStudent.add(student);
            });
            int resapplyStudent = applyMapper.insertApplyStudent(applyStudent);
            if (resapplyStudent == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
                ArrayList<Integer> delIds = new ArrayList<>();
                delIds.add(apply.getId());
                applyMapper.deleteBatchIds(delIds);
                return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
            } else {
                return Result.success();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result update(ApplyEntity updateApply) {
        ApplyEntity apply = new ApplyEntity();
        BeanUtils.copyProperties(updateApply, apply);

        int res = applyMapper.updateApply(apply);

        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        } else {
            ArrayList<ApplyStudentEntity> applyStudent = new ArrayList<>();
            updateApply.getApplyStudent().forEach((e) -> {
                ApplyStudentEntity student = new ApplyStudentEntity();
                student.setApplyId(apply.getId());
                student.setStudentId(e.getStudentId());
                applyStudent.add(student);
            });
            applyMapper.deleteApplyStudent(apply.getId());
            int resapplyStudent = applyMapper.insertApplyStudent(applyStudent);
            if (resapplyStudent == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
                return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
            } else {
                return Result.success();
            }
        }
    }
    public Result del(BatchDeleteDto batchDelete) {
        applyMapper.deleteBatchIds(batchDelete.getIds());
        return Result.success();
    }
}
