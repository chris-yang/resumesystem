package com.beyondsoft.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class DownLoadUtils {
    public void download(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response,String returnName) throws IOException {
        response.setContentType("application/octet-stream;charset=UTF-8");
        //response.setContentType("application/octet-stream");
       // response.setContentType("application/msexcel");
        returnName = URLEncoder.encode(returnName,"UTF-8");
                //response.encode(new String(returnName.getBytes(),"iso8859-1"));//
        response.setHeader("content-disposition","attachment;filename="+returnName);
        response.setContentLength(byteArrayOutputStream.size());
        ServletOutputStream outputStream = response.getOutputStream(); //取得输出流
        byteArrayOutputStream.writeTo(outputStream);                   //写到输出流
        byteArrayOutputStream.close();                                //关闭
        outputStream.flush();                                         //刷数据
    }
}
