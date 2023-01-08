package com.demo.app.service.other;

import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.CourseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.del.BatchDeleteDto;
import model.dto.other.CourseEntity;
import model.dto.other.CourseFeeEntity;
import model.dto.other.SearchCourseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.CommonConstants;
import result.Result;
import util.SearchFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CourseService {

    @Autowired
    CourseMapper courseMapper;

    public Result list(SearchFilter searchFilter) {
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

    @Transactional(rollbackFor = Exception.class)
    public Result insert(CourseEntity insertCourse) {
        CourseEntity course = new CourseEntity();
        BeanUtils.copyProperties(insertCourse, course);
        int res = courseMapper.insertCourse(course);
        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
        } else {
            ArrayList<CourseFeeEntity> courseFee = new ArrayList<>();
            insertCourse.getCourseFee().forEach((e) -> {
                CourseFeeEntity fee = new CourseFeeEntity();
                fee.setCourseId(course.getId());
                fee.setCoachId(e.getCoachId());
                fee.setCoachType(e.getCoachType());
                fee.setCoachFee(e.getCoachFee());
                fee.setTuitionFee(e.getTuitionFee());
                courseFee.add(fee);
            });
            int rescourseFee = courseMapper.insertCourseFee(courseFee);
            if (rescourseFee == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
                ArrayList<Integer> delIds = new ArrayList<>();
                delIds.add(course.getId());
                courseMapper.deleteBatchIds(delIds);
                return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
            } else {
                return Result.success();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result update(CourseEntity updateCourse) {
        CourseEntity course = new CourseEntity();
        BeanUtils.copyProperties(updateCourse, course);

        int res = courseMapper.updateCourse(course);

        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        } else {
            ArrayList<CourseFeeEntity> courseFee = new ArrayList<>();
            updateCourse.getCourseFee().forEach((e) -> {
                CourseFeeEntity fee = new CourseFeeEntity();
                fee.setCourseId(course.getId());
                fee.setCoachId(e.getCoachId());
                fee.setCoachType(e.getCoachType());
                fee.setCoachFee(e.getCoachFee());
                fee.setTuitionFee(e.getTuitionFee());
                courseFee.add(fee);
            });
            courseMapper.deleteCourseFee(course.getId());
            int rescourseFee = courseMapper.insertCourseFee(courseFee);
            if (rescourseFee == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
                return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
            } else {
                return Result.success();
            }
        }
    }
    public Result del(BatchDeleteDto batchDelete) {
        courseMapper.deleteBatchIds(batchDelete.getIds());
        return Result.success();
    }

}
