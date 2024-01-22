package com.gra.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gra.backend.mapper.ConsultationRecordsMapper;
import com.gra.backend.mapper.UserMapper;
import com.gra.backend.pojo.Record;
import com.gra.backend.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jiezhou
 **/

@Service
public class RecordService {
    @Resource
    private ConsultationRecordsMapper consultationRecordsMapper;
    @Resource
    private UserMapper userMapper;
    public List<Record> getListByDocId(int id) {
        LambdaQueryWrapper<Record> consultationRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        consultationRecordLambdaQueryWrapper.eq(Record::getDoctorId, id);
        List<Record> records = consultationRecordsMapper.selectList(consultationRecordLambdaQueryWrapper);
        for (Record record : records) {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getId, record.getDoctorId());
            User user = userMapper.selectOne(userLambdaQueryWrapper);
            record.setDoctorName(user.getUsername());
            LambdaQueryWrapper<User> userLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper1.eq(User::getId, record.getPatientId());
            User user1 = userMapper.selectOne(userLambdaQueryWrapper1);
            record.setPatientName(user1.getUsername());
        }
        return records;
    }
}
