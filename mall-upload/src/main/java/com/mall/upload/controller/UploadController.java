package com.mall.upload.controller;
import com.mall.upload.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传模块controller
 * @author pan
 * @create 2020-04-21-15:41
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 图片上传
     * @param file 要上传的图片文件
     * @return
     * @throws IOException
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {

        String s = uploadService.uploadImage(file);
        if(StringUtils.isNotBlank(s)){
            return ResponseEntity.ok(s);
        }
        return ResponseEntity.badRequest().build();
    }
}
