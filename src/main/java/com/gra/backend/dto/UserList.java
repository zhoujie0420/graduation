package com.gra.backend.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: elk
 * @create: 2024-02-01 15:02
 **/
@Data
public class UserList {

    private List<String> userList;
    private String userRole;
}
