package com.mall.upload.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author pan
 * @create 2020-04-21-15:46
 */
@Service
public class UploadService {

    /**
     * 一个集合，里面存放了可以被保存的合法文件的content-type
     */
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif");

    /**
     * 图片上传
     * @param file 符合要求的图片
     * @return 图片存储的url地址，如1.img文件，返回image.mall.com/1.mag
     * @throws IOException
     */
    public String uploadImage(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        //判断该文件是否合法
        if(CONTENT_TYPES.contains(contentType)){
            //保存文件
            file.transferTo(new File("F:\\graduation-project\\img\\" + file.getOriginalFilename()));
            //返回文件保存的路径，这里前缀加上相应的图片域名，以便以后把图片上传到相应的图片服务器
            return "http://image.mall.com/" + file.getOriginalFilename();
        }
        return null;

    }
}
