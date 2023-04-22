package com.demo.app.service.other;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.ClassMapper;
import com.demo.app.mapper.other.CourseMapper;
import com.demo.app.mapper.other.FeeDetailMapper;
import com.demo.app.mapper.other.FeeMapper;
import com.demo.app.mapper.user.UserMapper;
import com.demo.app.service.user.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.del.BatchDeleteDto;
import model.dto.other.*;
import model.dto.sys.user.UAndDAndIUserRoleDto;
import model.entity.department.SysDepartment;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassService {
    @Autowired
    ClassMapper classMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    FeeMapper feeMapper;

    @Autowired
    UserService userService;

    @Autowired
    FeeDetailMapper feeDetailMapper;
    public Result list(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());

        SearchClassDto searchClassDto = new SearchClassDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchClassDto = getSearchClassDto(searchFilter.getFilters());
        }

        List<ClassResponse> classResponseList = classMapper.selectClass(searchClassDto);
        for (ClassResponse classResponse : classResponseList) {
             String ClassStatus = classMapper.getClassStatusById(classResponse.getId());
            if (ClassStatus.equals("") || ClassStatus.equals("new")){
                if (classResponse.getCourseChecked()){
                    classResponse.setClassStatus("confirm");
                }
            } else{
                classResponse.setClassStatus("paid");
            }
            //
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
                if (!classStudentResponse.getParentName().equals("")){
                    if (classResponse.getParent() != null){
                        classResponse.setParent(classResponse.getParent() + "," + classStudentResponse.getParentName());
                    } else {
                        classResponse.setParent(classStudentResponse.getParentName());
                    }
//                    classResponse.getStudentId().add(classStudentResponse.getId());
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
//        Integer tuitionFee = 0;
        SearchCourseDto searchCourseDto = new SearchCourseDto();
        searchCourseDto.setId(classRequest.getCourseId());
        List<CourseEntity> courseEntityList = courseMapper.selectCourse(searchCourseDto);
        Optional<CourseEntity> courseEntity = courseEntityList.stream().findFirst();
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
                Optional<CourseFeeEntity> courseFeeEntity = courseFeeList.stream().filter(e -> e.getCoachId() == coachId).findFirst();
                Optional<AdjustAmountEntity> adjustAmountEntity = classRequest.getAdjustAmountList().stream().filter(e -> e.getCoachID() == coachId).findFirst();
                if (courseFeeEntity.isPresent()){
                    classCoach.setCoachType(courseFeeEntity.get().getCoachType());
                }
                if(adjustAmountEntity.isPresent()){
                    classCoach.setCoachFee(adjustAmountEntity.get().getCoachFee());
                    classCoach.setCoachTotal(adjustAmountEntity.get().getAdjustAmount());
                }
                classCoachList.add(classCoach);
            }
            // Student
            for (Integer childId : classRequest.getChildId()){
                ClassStudentEntity classStudent = new ClassStudentEntity();
                classStudent.setClassId(classEntity.getId());
                classStudent.setStudentId(childId);
                Optional<AdjustAmountEntity> adjustAmountEntity = classRequest.getAdjustAmountList().stream().filter(e -> e.getStudentId() == childId).findFirst();
                if(adjustAmountEntity.isPresent()){
                    classStudent.setTuitionFee(adjustAmountEntity.get().getTuitionFee());
                    classStudent.setTuitionTotal(adjustAmountEntity.get().getAdjustAmount());
                }
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

        SearchCourseDto searchCourseDto = new SearchCourseDto();
        searchCourseDto.setId(classRequest.getCourseId());
        List<CourseEntity> courseEntityList = courseMapper.selectCourse(searchCourseDto);
        Optional<CourseEntity> courseEntity = courseEntityList.stream().findFirst();
        List<CourseFeeEntity> courseFeeList = courseMapper.selectCourseFee(classRequest.getCourseId());
        
        List<ClassStudentEntity>  classStudentList = new ArrayList<>();
        List<ClassCoachEntity>  classCoachList = new ArrayList<>();
        // Coach
        for (Integer coachId : classRequest.getCoachId()){
            ClassCoachEntity classCoach = new ClassCoachEntity();
            classCoach.setCoachId(coachId);
            classCoach.setClassId(classRequest.getId());
            Optional<CourseFeeEntity> courseFeeEntity = courseFeeList.stream().filter(e -> e.getCoachId() == coachId).findFirst();
            Optional<AdjustAmountEntity> adjustAmountEntity = classRequest.getAdjustAmountList().stream().filter(e -> e.getCoachID() == coachId).findFirst();
            if (courseFeeEntity.isPresent()){
                classCoach.setCoachType(courseFeeEntity.get().getCoachType());
            }
            if(adjustAmountEntity.isPresent()){
                classCoach.setCoachFee(adjustAmountEntity.get().getCoachFee());
                classCoach.setCoachTotal(adjustAmountEntity.get().getAdjustAmount());
            }
            classCoachList.add(classCoach);
        }
        // Student
        for (Integer childId : classRequest.getChildId()){
            ClassStudentEntity classStudent = new ClassStudentEntity();
            classStudent.setClassId(classRequest.getId());
            classStudent.setStudentId(childId);
            Optional<AdjustAmountEntity> adjustAmountEntity = classRequest.getAdjustAmountList().stream().filter(e -> e.getStudentId() == childId).findFirst();
            if(adjustAmountEntity.isPresent()){
                classStudent.setTuitionFee(adjustAmountEntity.get().getTuitionFee());
                classStudent.setTuitionTotal(adjustAmountEntity.get().getAdjustAmount());
            }
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

    public Result getClassFee(Integer id) {
        List<ClassFeeResponse> classFeeResponseList = classMapper.getClassFee(id);
        return Result.success(classFeeResponseList);
    }

    public Result getClassCalendar(Integer id, String classMonth, String classType) {
        List<ClassCalendar> classCalendar = new ArrayList<>();

        switch(classType){
            case "all" :
                classCalendar = classMapper.getClassCalendar_All(classMonth);
                break;
            case "coach" :
                classCalendar = classMapper.getClassCalendar_Coach(id, classMonth);
                break;
            case "student" :
                classCalendar = classMapper.getClassCalendar_Student(id, classMonth);
                break;
        }
        ClassCalendarResponse classCalendarResponse= new ClassCalendarResponse();

        classCalendarResponse.setOne(classCalendar.stream().filter(p -> p.getDays().equals(1)).collect(Collectors.toList()));
        classCalendarResponse.setTwo(classCalendar.stream().filter(p -> p.getDays().equals(2)).collect(Collectors.toList()));
        classCalendarResponse.setThree(classCalendar.stream().filter(p -> p.getDays().equals(3)).collect(Collectors.toList()));
        classCalendarResponse.setFour(classCalendar.stream().filter(p -> p.getDays().equals(4)).collect(Collectors.toList()));
        classCalendarResponse.setFive(classCalendar.stream().filter(p -> p.getDays().equals(5)).collect(Collectors.toList()));
        classCalendarResponse.setSix(classCalendar.stream().filter(p -> p.getDays().equals(6)).collect(Collectors.toList()));
        classCalendarResponse.setSeven(classCalendar.stream().filter(p -> p.getDays().equals(7)).collect(Collectors.toList()));
        classCalendarResponse.setEight(classCalendar.stream().filter(p -> p.getDays().equals(8)).collect(Collectors.toList()));
        classCalendarResponse.setNine(classCalendar.stream().filter(p -> p.getDays().equals(9)).collect(Collectors.toList()));
        classCalendarResponse.setTen(classCalendar.stream().filter(p -> p.getDays().equals(10)).collect(Collectors.toList()));
        classCalendarResponse.setEleven(classCalendar.stream().filter(p -> p.getDays().equals(11)).collect(Collectors.toList()));
        classCalendarResponse.setTwelve(classCalendar.stream().filter(p -> p.getDays().equals(12)).collect(Collectors.toList()));
        classCalendarResponse.setThirteen(classCalendar.stream().filter(p -> p.getDays().equals(13)).collect(Collectors.toList()));
        classCalendarResponse.setFourteen(classCalendar.stream().filter(p -> p.getDays().equals(14)).collect(Collectors.toList()));
        classCalendarResponse.setFifteen(classCalendar.stream().filter(p -> p.getDays().equals(15)).collect(Collectors.toList()));
        classCalendarResponse.setSixteen(classCalendar.stream().filter(p -> p.getDays().equals(16)).collect(Collectors.toList()));
        classCalendarResponse.setSeventeen(classCalendar.stream().filter(p -> p.getDays().equals(17)).collect(Collectors.toList()));
        classCalendarResponse.setEighteen(classCalendar.stream().filter(p -> p.getDays().equals(18)).collect(Collectors.toList()));
        classCalendarResponse.setNineteen(classCalendar.stream().filter(p -> p.getDays().equals(19)).collect(Collectors.toList()));
        classCalendarResponse.setTwenty(classCalendar.stream().filter(p -> p.getDays().equals(20)).collect(Collectors.toList()));
        classCalendarResponse.setTwentyOne(classCalendar.stream().filter(p -> p.getDays().equals(21)).collect(Collectors.toList()));
        classCalendarResponse.setTwentyTwo(classCalendar.stream().filter(p -> p.getDays().equals(22)).collect(Collectors.toList()));
        classCalendarResponse.setTwentyThree(classCalendar.stream().filter(p -> p.getDays().equals(23)).collect(Collectors.toList()));
        classCalendarResponse.setTwentyFour(classCalendar.stream().filter(p -> p.getDays().equals(24)).collect(Collectors.toList()));
        classCalendarResponse.setTwentyFive(classCalendar.stream().filter(p -> p.getDays().equals(25)).collect(Collectors.toList()));
        classCalendarResponse.setTwentySix(classCalendar.stream().filter(p -> p.getDays().equals(26)).collect(Collectors.toList()));
        classCalendarResponse.setTwentySeven(classCalendar.stream().filter(p -> p.getDays().equals(27)).collect(Collectors.toList()));
        classCalendarResponse.setTwentyEight(classCalendar.stream().filter(p -> p.getDays().equals(28)).collect(Collectors.toList()));
        classCalendarResponse.setTwentyNine(classCalendar.stream().filter(p -> p.getDays().equals(29)).collect(Collectors.toList()));
        classCalendarResponse.setThirty(classCalendar.stream().filter(p -> p.getDays().equals(30)).collect(Collectors.toList()));
        classCalendarResponse.setThirtyOne(classCalendar.stream().filter(p -> p.getDays().equals(31)).collect(Collectors.toList()));

        return Result.success(classCalendarResponse);
    }

    public Result confirmClass(ClassConfirmRequest classConfirmRequest){
        DateFormat dateFormat = new  SimpleDateFormat("yyyyMM");

        List<FeeDetailReportEntity> feeDetailList = new ArrayList<>();
        Date date = new Date(classConfirmRequest.getClassDate().getTime());
        String ym = dateFormat.format(date);
        // Student
        for(ClassStudentRequest classStudent : classConfirmRequest.getStudentList()){
            classMapper.updateClassStudent(classStudent);
            // student fees
            Integer parentId = userService.getParentId(classStudent.getStudentId());
            SearchFeeReportDto searchFeeReportDto = new SearchFeeReportDto();
            searchFeeReportDto.setUserId(parentId);
            searchFeeReportDto.setPeriod(ym);
            List<FeeReportEntity> feeList = feeMapper.getFee(searchFeeReportDto);

            Integer feeId = 0;
            Optional<FeeReportEntity> feeReportEntity = feeList.stream().findFirst();
            if(!feeReportEntity.isPresent()){
                // add fee
                FeeEntity newFee = new FeeEntity();
                newFee.setPeriod(ym);
                newFee.setUserID(parentId);
                newFee.setType("tuition");
                newFee.setStatus("new");
                feeMapper.insert(newFee);
                feeId = newFee.getId();
            } else {
                feeId = feeReportEntity.get().getId();
            }
            // add feeDetail
            FeeDetailEntity feeDetail = new FeeDetailEntity();
            feeDetail.setFeeId(feeId);
            feeDetail.setClassId(classConfirmRequest.getId());
            feeDetail.setClassDate(classConfirmRequest.getClassDate());
            feeDetail.setClassHours(classStudent.getHours());
            feeDetail.setClassFee(classStudent.getTuitionFee());
            feeDetail.setClassName(classConfirmRequest.getClassName());
            feeDetailMapper.insertByEntity(feeDetail);
//            feeDetail.setClassStudentName(classStudent.getStudentName());
//            feeDetailMapper.insert(feeDetail);
            feeMapper.calculateFeeById(feeId, classConfirmRequest.getUpdateUser());
        }
        // coach
        List<ClassFeeResponse> classFeeResponseList = classMapper.getClassFee(classConfirmRequest.getId());
        for(ClassFeeResponse ClassFee : classFeeResponseList){
            if (ClassFee.getCoachId() > 0){
                SearchFeeReportDto searchFeeReportDto = new SearchFeeReportDto();
                searchFeeReportDto.setUserId(ClassFee.getCoachId());
                searchFeeReportDto.setPeriod(ym);
                List<FeeReportEntity> feeList = feeMapper.getFee(searchFeeReportDto);
                Integer feeId = 0;
                Optional<FeeReportEntity> feeReportEntity = feeList.stream().findFirst();
                if(!feeReportEntity.isPresent()){
                    // add fee
                    FeeEntity newFee = new FeeEntity();
                    newFee.setPeriod(ym);
                    newFee.setUserID(ClassFee.getCoachId());
                    newFee.setType("coach");
                    newFee.setStatus("new");
                    feeMapper.insert(newFee);
                    feeId = newFee.getId();
                } else {
                    feeId = feeReportEntity.get().getId();
                }

                FeeDetailEntity newFeeDetail = new FeeDetailEntity();
                newFeeDetail.setFeeId(feeId);
                newFeeDetail.setClassId(classConfirmRequest.getId());
                newFeeDetail.setClassDate(classConfirmRequest.getClassDate());
                newFeeDetail.setClassHours(ClassFee.getHours());
                newFeeDetail.setClassFee(ClassFee.getTotalAmount());
                newFeeDetail.setClassName(classConfirmRequest.getClassName());
                feeDetailMapper.insertByEntity(newFeeDetail);
//                newFeeDetail.setClassCoachName(ClassFee.getName());
//                feeDetailMapper.insert(newFeeDetail);
                feeMapper.calculateFeeById(feeId, classConfirmRequest.getUpdateUser());
            }
        }
        classMapper.updateClassStatus(classConfirmRequest);
        return Result.success();
    }
    public Result rollbackConfirmClass(ClassConfirmRequest classConfirmRequest){
        classMapper.updateClassStatus(classConfirmRequest);
        List<Integer> feeIdList = feeMapper.getFeeIdByClassId(classConfirmRequest.getId());
        feeMapper.deleteFeeDetailByClassId(classConfirmRequest.getId());
        for(Integer feeId : feeIdList){
            feeMapper.calculateFeeById(feeId, classConfirmRequest.getUpdateUser());
        }
        return Result.success();
    }

    private SearchClassDto getSearchClassDto(JSONObject jsonObject) {
        SearchClassDto searchClassDto = new SearchClassDto();
        String name = jsonObject.getString("name");
        Timestamp startDate = jsonObject.getTimestamp("startDate");
        Timestamp endDate = jsonObject.getTimestamp("endDate");
        Integer coach = jsonObject.getInteger("coach");
        Integer student = jsonObject.getInteger("student");
        Boolean notConfirm = jsonObject.getBoolean("notConfirm");
        Boolean joinFee = jsonObject.getBoolean("joinFee");
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
