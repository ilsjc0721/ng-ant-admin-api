package com.demo.app.service.other;

import com.alibaba.fastjson.JSONObject;
import com.demo.app.mapper.other.FeeMapper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import enums.MenuEnum;
import model.dto.other.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import result.Result;
import util.SearchFilter;
import util.StringUtils;
import util.date.DateUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSocketFactory;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Service
public class FeeService {

    @Autowired
    FeeMapper feeMapper;

    public Result updateFeeStatus(UpdateFeeStatusDto updateFeeStatusDto) {
        if(updateFeeStatusDto.getUserId().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("userId"));
        }

        if(updateFeeStatusDto.getId().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("id"));
        }

        if(updateFeeStatusDto.getStatus().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("status"));
        }

        if (feeMapper.updateFeeStatus(
                updateFeeStatusDto.getId(),
                updateFeeStatusDto.getStatus(),
                updateFeeStatusDto.getUserId(),
                updateFeeStatusDto.getMemo()) > 0) {
            return Result.success();
        } else {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        }
    }
    public Result getFee(SearchFeeReportDto searchFeeReportDto) {
        searchFeeReportDto.setUserId(null);
        List<FeeReportEntity> feeList = feeMapper.getFee(searchFeeReportDto);

        for (FeeReportEntity fee : feeList) {
            List<FeeDetailReportEntity> feeDetailList = feeMapper.getFeeDetail(fee.getId());
            fee.setFeeDetailReport(feeDetailList);
        }

        return Result.success(feeList);
    }
    public Result getCoachFee(SearchFeeReportDto searchFeeReportDto) {
        if(searchFeeReportDto.getType().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("type"));
        }

        List<FeeReportEntity> feeList = feeMapper.getFee(searchFeeReportDto);

        for (FeeReportEntity fee : feeList) {
            List<FeeDetailReportEntity> feeDetailList = feeMapper.getFeeDetail(fee.getId());
            fee.setFeeDetailReport(feeDetailList);
        }

        return Result.success(feeList);
    }
    public Result getTuitionFee(SearchFeeReportDto searchFeeReportDto) {
        if(searchFeeReportDto.getType().equals(null)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_MISSING_PARAMS.setParam("type"));
        }

        List<FeeReportEntity> feeList = feeMapper.getFee(searchFeeReportDto);

        for (FeeReportEntity fee : feeList) {
            List<FeeDetailReportEntity> feeDetailList = feeMapper.getFeeDetail(fee.getId());
            fee.setFeeDetailReport(feeDetailList);
        }

        return Result.success(feeList);
    }

    public Result sendFeeMail(@RequestBody FeeMailRequest feeMailRequest){
        String pwd = "euezgnczlagymohe";
        String mailFrom = "ilsjc0721@gmail.com";
        if (!feeMailRequest.getPeriod().isEmpty()){
            SearchFeeReportDto searchFee = new SearchFeeReportDto();
            if (feeMailRequest.getPeriod() != null)
                searchFee.setPeriod(feeMailRequest.getPeriod());
            if (feeMailRequest.getFeeId() != null)
                searchFee.setFeeId(feeMailRequest.getFeeId());
            List<FeeReportEntity> feeList = feeMapper.getFee(searchFee);
            for (FeeReportEntity fee : feeList) {
                if (fee.getEmail() == null){
                    continue;
                }
                List<FeeDetailReportEntity> feeDetailList = feeMapper.getFeeDetail(fee.getId());
                StringBuilder str = new StringBuilder();
                str.append("Fee period:" + feeMailRequest.getPeriod() + "<br/>");
                str.append("Type:" + fee.getType() + "<br/>");
                str.append("Total hours:" + fee.getHours().toString() + "<br/>");
                str.append("Total fee:" + fee.getAmount().toString() + "<br/>");
                str.append("Status:" + fee.getStatus() + "<br/>");
                str.append("Memo:" + fee.getMemo() + "<br/>");
                str.append("<table border=\"1\" cellpadding=\"1\" cellspacing=\"1\"> <tbody>");
                str.append("<tr>");
                str.append("<td>Class Date</td>");
                str.append("<td>Class Name</td>");
                str.append("<td>Hours</td>");
                str.append("<td>Fee</td>");
                str.append("<td>Coach</td>");
                str.append("<td>Student</td>");
                str.append("</tr>");
                for (FeeDetailReportEntity feeDetail : feeDetailList){
                    str.append("<tr>");
                    str.append("<td>" + DateUtils.parseDateToStr("yyyy-MM-dd", feeDetail.getClassDate()) +"</td>");
                    str.append("<td>" + feeDetail.getClassName() +"</td>");
                    str.append("<td>" + feeDetail.getClassHours().toString() +"</td>");
                    str.append("<td>" + feeDetail.getClassFee().toString() +"</td>");
                    str.append("<td>" + feeDetail.getClassCoachName() +"</td>");
                    str.append("<td>" + feeDetail.getClassStudentName() +"</td>");
                    str.append("</tr>");
                }
                str.append("</tbody> </table>");
//                sendMail(mailFrom, pwd, fee.getEmail(), feeMailRequest.getSubject(), str.toString());
                sendMail(mailFrom, pwd, "ilsjc0721@gmail.com", feeMailRequest.getSubject(), str.toString());
            }
        }
        return Result.success();
    }

    private Result sendMail(String mailFrom, String pwd, String mailTo, String subject, String content){
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.user", mailFrom);
        properties.setProperty("mail.smtp.password", pwd);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.debug", "true");

        Auth auth = new Auth(mailFrom, pwd);
        Session session = Session.getDefaultInstance(properties, auth);

        try{
            MimeMessage message = new MimeMessage(session);
            message.setSender(new InternetAddress(mailFrom));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSubject(subject);
            message.setContent(content, "text/html;charset=UTF-8");
            Transport transport = session.getTransport();
            transport.send(message);
            transport.close();
        }catch (MessagingException mex) {
            mex.printStackTrace();
            return Result.failure(566, mex.toString());
        }
        return Result.success();
    }
}

class Auth extends Authenticator{

    private String userName;
    private String password;

    public Auth(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        PasswordAuthentication pa = new PasswordAuthentication(userName, password);
        return pa;
    }
}
