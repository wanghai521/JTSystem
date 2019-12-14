package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.EasyUiFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 实现文件上传
 *
 * @Auther WangHai
 * @Date 2019/11/29
 * @Describle what
 */
@RestController
public class FileController {

    private String filePath = "D:/java/workspaceJT/UpImage";
    @Autowired
    private FileService fileService;
    /**
     * 1、准备一个文件的存储目录
     * 2、获取文件的名称
     * 3、利用工具API实现文件的上传
     *
     * @param fileImage
     * @return
     */
    @RequestMapping("/file")
    public String filre(MultipartFile fileImage) throws IOException {
        // 1、准备一个文件的存储目录
        File fileDir = new File(filePath);
        // 判断文件夹是否存在
        if (!fileDir.exists()) {
            // 如果目录不存在，就创建多级目录
            fileDir.mkdirs();
        }

        // 2、获取文件的名称，也就是图片的名称
        String fileName = fileImage.getOriginalFilename();
        String path = filePath + fileName;

        // 3、利用工具API实现文件的上传
        fileImage.transferTo(new File(path));

        return "文件上传成功";
    }

    /**
     * 实现商品的文件上传
     * @return
     */
    @RequestMapping("/pic/upload")
    public EasyUiFile filrUpLoad(MultipartFile uploadFile){


        return fileService.fileUpLoad(uploadFile);
    }
}
