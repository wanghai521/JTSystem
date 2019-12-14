package com.jt.service;

import com.jt.vo.EasyUiFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传的业务层接口
 */
public interface FileService {

    /**
     * 实现图片的上传
     * @param upLoadFile
     * @return
     */
    public EasyUiFile fileUpLoad(MultipartFile upLoadFile);
}
