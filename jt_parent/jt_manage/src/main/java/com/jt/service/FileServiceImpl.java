package com.jt.service;

import com.jt.vo.EasyUiFile;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Auther WangHai
 * @Date 2019/11/29
 * @Describle what
 */
@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {
    // 定义自家放图片的文件地址
    @Value("${image.localDirPath}")
    private String localDir;
    @Value("${image.urlDirPath}")
    private String localDirUrl;

    /**
     * 实现图片的上传
     * 1、判断文件是否为图片，
     * 2、为了防止修改后缀名的恶意程序，查询图片的长宽属性
     * 3、图片分文件保存，按照日期
     * 4、防止重名  使用UUID
     * 5、实现文件的上传
     *
     * @param upLoadFile
     * @return
     */
    @Override
    public EasyUiFile fileUpLoad(MultipartFile upLoadFile) {

        EasyUiFile easyUiFile = new EasyUiFile();

        // 1、判断文件是否为图片，这里是判断文件的尾缀名jpg png jpng gif
        String fileName = upLoadFile.getOriginalFilename();// 获得文件的真实名称
        // 将字符串全部转换成小写
        fileName = fileName.toLowerCase();
        // 使用正则表达式进行校验  regular expression 简称regex
        if (!fileName.matches("^.+\\.(jpg|png|gif)$")) {
            // 如果不满足，就直接停止，并响应错误
            return EasyUiFile.fail();
        }

        // 运行至此，说明尾缀校验成功
        try {
            // 2、为了防止修改后缀名的恶意程序，查询图片的长宽属性
            BufferedImage bufferedImage = ImageIO.read(upLoadFile.getInputStream());

            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            // 判断长宽是否为空
            if (width == 0 || height == 0) {
                return EasyUiFile.fail();
            }

            // 运行至此，说明不是恶意程序
            // 3、图片分文件保存，按照日期yyyy/MM/dd
            // 获得当前日期的字符串如下：2019/11/30
            String dataDir = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
            // 生成文件目录  localDirl + dateDir
            String fileDirPath = localDir + dataDir;
            // 判断是否存在该文件夹，没有则进行多级创建
            File dirFile = new File(fileDirPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            // 4、生成新的文件名，防止文件名冲突
            // 截取文件的尾缀名
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            // 生成新的文件名
            String newFileName = UUID.randomUUID().toString() + fileType;

            // 5、实现文件上传
            upLoadFile.transferTo(new File(fileDirPath + newFileName));

            // 这时候还需要回显文件的地址，没有使用nginx之前，先使用加的
            String url = localDirUrl + dataDir + newFileName;
            easyUiFile.setUrl(url)
                    .setHeight(height).setWidth(width);
        } catch (IOException e) {
            e.printStackTrace();
            return EasyUiFile.fail();
        }

        return easyUiFile;
    }
}
