package com.gra.backend.controller;

import com.gra.backend.common.result.Result;
import com.gra.backend.dto.RecordAddDto;
import com.gra.backend.service.RecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author: jiezhou
 **/

@RestController
@RequestMapping("/api/record")
public class RecordController {
    @Resource
    private RecordService recordService;

    @PostMapping("addRecord")
    public Result<?> addRecord() {
        return recordService.addRecord();
    }

    @PostMapping("getRecords")
    public Result<?> getRecords() {
        return recordService.getRecords();
    }
}
