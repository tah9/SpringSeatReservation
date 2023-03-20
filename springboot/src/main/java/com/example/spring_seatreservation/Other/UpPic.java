package com.example.spring_seatreservation.Other;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class UpPic {
    //当日23:59:59时间戳
//    long curDateEndTime = startTime / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset() + 86400000L - 1;

    //将接口上传的所有文件保存到本地指定路径下，返回文件名
    public static String upPics(HttpServletRequest request) throws Exception {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        if (files.size() == 0) {
            return null;
        }
        File rootFile = new File("D:/BookApp");
        if (!rootFile.exists()) {
            rootFile.mkdir();
        }
        List<String> resultsPath = new ArrayList<>();
        for (MultipartFile file : files) {
            String randomNumber = getRandomNumber(10);
            File newFile = new File("D:/BookApp/" + randomNumber + ".jpg");
            file.transferTo(newFile);
            String url =/* request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +*/
                    "/BookApp/" + randomNumber + ".jpg";
            resultsPath.add(url);
        }
        String result = resultsPath.toString();
        //list转string后去除首尾[]中括号
        return result.substring(1, result.length() - 1).replaceAll(" ", "");
    }

    public static String getRandomNumber(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }
}
