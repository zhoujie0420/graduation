package com.gra.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jiezhou
 * 问诊结果
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer doctorId;
    private Integer patientId;
    /**
     * 会诊日期
     */
    private String consultationDate;
    /**
     * 诊断结果
     */
    private String diagnosis;
    /**
     * 处方
     */
    private String prescription;
    /**
     * videoUrl
     */
    private String videoUrl;
    // 0 未完成 1 已完成
    private Integer status;

    //返回的都是对方的name
    @TableField(exist = false)
    private String otherName;

}
