package com.group.zhtx.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;

@RestController
public class ImageController {
    @RequestMapping("/userPortrait/{name}")
    public void getImage(@PathVariable String name, HttpServletResponse response, HttpSession session)throws Exception{
        System.out.println("客户端传过来的参数："+name);
        File file=new File("E:\\numberThree\\up_semester\\sx\\androidweb\\zhtx\\src\\main\\resources\\static\\userPortrait");
        File imageFile=new File(file.getAbsolutePath(),name);
        FileInputStream inputStream;
        if(imageFile.exists()){
            inputStream=new FileInputStream(imageFile);
            byte [] bytes=new byte[inputStream.available()];
            inputStream.read(bytes,0,bytes.length);
            response.getOutputStream().write(bytes);
        }else{
            File defaultFile=new File(file.getAbsolutePath(),"12345678.png");
            inputStream=new FileInputStream(defaultFile);
            byte [] bytes=new byte[inputStream.available()];
            inputStream.read(bytes,0,bytes.length);
            response.getOutputStream().write(bytes);
        }
    }
}
