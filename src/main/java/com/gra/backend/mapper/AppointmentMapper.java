package com.gra.backend.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.gra.backend.pojo.Appointment;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppointmentMapper extends MPJBaseMapper<Appointment> {
}