package com.gra.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gra.backend.common.result.Result;
import com.gra.backend.dto.RecordAddDto;
import com.gra.backend.mapper.RecordMapper;
import com.gra.backend.mapper.UserMapper;
import com.gra.backend.pojo.Record;
import com.gra.backend.pojo.User;
import com.gra.backend.utils.UserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiezhou
 **/

@Service
public class RecordService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RecordMapper recordMapper;


    public Result<?> addRecord(RecordAddDto recordAddDto) {
        Record record = new Record();
        //判断userId是医生还是患者
        User user = userMapper.selectById(recordAddDto.getUserId1());
        if (user == null) {
            return Result.fail("用户不存在");
        }
        if (user.getRole() == 2) {
            record.setDoctorId(Integer.valueOf(recordAddDto.getUserId1()));
            record.setPatientId(Integer.valueOf(recordAddDto.getUserId2()));
        } else if (user.getRole() == 1) {
            record.setDoctorId(Integer.valueOf(recordAddDto.getUserId2()));
            record.setPatientId(Integer.valueOf(recordAddDto.getUserId1()));
        }
        record.setStatus(0);
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
        String formattedDateTime = now.format(formatter);
        record.setConsultationDate(formattedDateTime);
        recordMapper.insert(record);
        return Result.success();
    }

    public Result<?> getRecords() {
        User user = UserUtil.getUser();
        if (user == null) {
            return Result.fail("用户未登录");
        }
        LambdaQueryWrapper<Record> recordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer role = user.getRole();
        recordLambdaQueryWrapper.orderByDesc(Record::getConsultationDate);
        List<Record> records = new ArrayList<>();
        if (role == 2) {
            recordLambdaQueryWrapper.eq(Record::getDoctorId, user.getId());
            records = recordMapper.selectList(recordLambdaQueryWrapper);
            for (Record record : records) {
                User patient = userMapper.selectById(record.getPatientId());
                record.setOtherName(patient.getUsername());
            }
        } else if (role == 1) {
            recordLambdaQueryWrapper.eq(Record::getPatientId, user.getId());
            records = recordMapper.selectList(recordLambdaQueryWrapper);
            for (Record record : records) {
                User doctor = userMapper.selectById(record.getDoctorId());
                record.setOtherName(doctor.getUsername());
            }
        }

        return Result.success(records);
    }

    public Result<?> updateRecord(Record record) {
        record.setStatus(1);
        recordMapper.updateById(record);
        return Result.success();
    }
}
