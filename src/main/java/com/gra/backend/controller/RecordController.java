package com.gra.backend.controller;

import com.gra.backend.common.result.Result;
import com.gra.backend.dto.RecordAddDto;
import com.gra.backend.pojo.Record;
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
    public Result<?> addRecord(RecordAddDto recordAddDto) {
        return recordService.addRecord(recordAddDto);
    }

    @PostMapping("getRecords")
    public Result<?> getRecords() {
        return recordService.getRecords();
    }

    @PostMapping("updateRecord")
    public Result<?> updateRecord(Record record) {
        return recordService.updateRecord(record);
    }
}
