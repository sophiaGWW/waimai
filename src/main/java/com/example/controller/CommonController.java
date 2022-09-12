package com.example.controller;

import com.example.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @create 2022-09-06 15:51
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //获取原始名，切割后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //随机生成uuid重命名文件
        String fileName = UUID.randomUUID().toString() + suffix;
        //创建文件夹
        File dir = new File(basePath);
        if(!dir.exists()){
            //目录不存在，需要创建
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return R.success(fileName);
    }

    @GetMapping("/download")
    public R<String> download(String name, HttpServletResponse response){

        try {
            //输入流
            FileInputStream inputStream = new FileInputStream(new File(basePath+name));
            //输出流将文件输出到浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            inputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
