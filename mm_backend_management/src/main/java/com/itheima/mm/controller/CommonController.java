package com.itheima.mm.controller;


import com.itheima.mm.entry.Result;


import com.itheima.mm.utils.UploadUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @Author YWC
 * @Date 2021/2/7 19:12
 * 1.先获取到客户端上传的文件
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    @RequestMapping("/uploadFile")
    public Result uploadFile( MultipartFile upload, HttpServletRequest request) throws IOException {


        try {
            //1.upload是客户端上传的文件
            //，输出文件到指定目录
            String imgUrl = "img/upload/" + UploadUtils.getDir();


            String uploadPath = request.getServletContext().getRealPath(imgUrl);
            System.out.println(uploadPath);
            //创建一个File
            File file = new File(uploadPath);
            if (!file.exists()) {
                //这个目录不存在
                //将这个目录创建出来
                file.mkdirs();
            }
            //获取上传的文件名字
            String filename = upload.getOriginalFilename();
            //文件名会不会重复呢????最好的办法是生成唯一的文件名
            String uuidName = UploadUtils.getUUIDName(filename);

            //将客户端上传的文件写入到目录中
            upload.transferTo(new File(file, uuidName));

//            将文件名拼接到imgUrl中
            imgUrl += "/" + uuidName;
            System.out.println(imgUrl);


            return new Result(true, "图片上传成功", imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "图片上传失败");

        }
    }
}


