package com.gra.backend.controller;

import com.gra.backend.pojo.Record;
import com.gra.backend.service.RecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jiezhou
 **/

@RestController
@RequestMapping("/api/record")
public class RecordController {

    @Resource
    private RecordService recordService;
//
//    @PostMapping("list")
//    public Map<String,List<Record>> getListByDocId(@RequestParam Map<String, String> map) {
//        if (Collections.EMPTY_MAP.equals(map)) {
//            return null;
//        }
//        HashMap<String, List<Record>> resMap = new HashMap<>();
//
//        List<Record> res = recordService.getListByDocId(Integer.parseInt(map.get("id")));
//        if(Collections.EMPTY_LIST.equals(res)){
//            return resMap;
//        }
//        resMap.put("data",res);
//        return resMap;
//    }
}
