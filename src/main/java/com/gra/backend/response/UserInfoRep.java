package com.gra.backend.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author: elk
 * @create: 2024-02-01 16:27
 **/
@Data
public class UserInfoRep {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String phone;
    /**
     * 身份 医生 2 患者 1
     */
    private Integer role;
    private Integer roleId;

    private String departmentName;
}
