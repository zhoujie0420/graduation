package com.gra.backend.controller;/**
 * @author zhouj
 * @create 2023/6/10 17:37
 * @ClassName : OssController  //类名
 * @Description :   //描述
 * @Author : dell //作者
 * @Date: 2023/6/10  17:37
 * @ClassName : OssController  //类名
 * @Description :   //描述
 * @Author : dell //作者
 * @Date: 2023/6/10  17:37
 * @ClassName : OssController  //类名
 * @Description :   //描述
 * @Author : dell //作者
 * @Date: 2023/6/10  17:37
 */

/**
 * @ClassName : OssController  //类名
 * @Description :   //描述
 * @Author : dell //作者
 * @Date: 2023/6/10  17:37
 */


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Oss相关操作接口
 */
@RestController
public class OssController {
    @GetMapping("/oss")
    public String oss() {
        String url = null;
        return "oss";
    }

    @PostMapping("/upload")
    public String upload(@RequestPart("file") MultipartFile file) {
        String url = null;
        return url;
    }


    @PostMapping("/uploadImages")
    public String uploadImages(@RequestPart("file") MultipartFile file) {
        // 获取上传头像的文件 MultipartFile
        // 返回上传的oss路径
        String url = null;
        return url;
    }


}