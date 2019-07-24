package com.example.testmybatis.controller;

import com.example.testmybatis.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class UploadController {
    @Value("${filepath}")
    String defaultPath;
    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file){
        String a=FileUtil.uploadFile(file,defaultPath);
        return  "上传失败";
    }


    @PostMapping("/uploads")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files){
       if(files!=null&&files.length>0){
           Arrays.stream(files).forEach(f->{
               FileUtil.uploadFile(f,defaultPath);
           });
        }
       return "redirect:/downLoad";
    }

    @RequestMapping(value = {"/addFile"})
    public String direct(){

     return "addFile";
    }
    @RequestMapping(value = {"/addFiles"})
    public String addFiles(){

        return "addFiles";
    }


    @RequestMapping(value = {"/downLoad"})
    public String downLoad(HttpServletRequest request){
        List<String> list=new ArrayList<>();
        File file=new File(defaultPath);
        Arrays.stream(file.listFiles()).forEach(f->{
            list.add(f.getName());
        });
        request.setAttribute("files",list);
        return "downLoad";
    }
    @RequestMapping(value = {"/"})
    public String main(){

        return "main";
    }
    @RequestMapping("/downLoadFile")
    public void downLoad(String path, HttpServletResponse response) throws IOException {
        FileUtil.downFile(response,defaultPath,path);
    }

    @RequestMapping("/downLoadFiles")
    public ResponseEntity<byte[]> downLoadFiles() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        List<Map<String,String>> list=new ArrayList<>();

        Map<String,String> map=new HashMap<>();
        map.put("fileName","考题答案_mysql.sql");
        map.put("filePath","D:/upload/1563849029848.sql");

        Map<String,String> map2=new HashMap<>();
        map2.put("fileName","common.txt");
        map2.put("filePath","d:/upload/1e4febe4e3f043798a9a430fed39a4f1.txt");
        list.add(map);
        list.add(map2);
        FileUtil.batchFileToZIP(list, byteArrayOutputStream);
        HttpHeaders headers = new HttpHeaders();

        String fileName = null;
        try {
            fileName = new String("附件.zip".getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        headers.setContentDispositionFormData("attachment", fileName);// 文件名称

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), headers,
                HttpStatus.CREATED);

        return responseEntity;




     }

}
