package com.docetor.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件输入对象
 */
@Data
public class FileInfoInDto {
    private MultipartFile uploadFile;//上传照片对象

    private String typeCode;//语言类型

    private String serviceId;//服务ID

    private String keyValue;//key值

    private String orgFileName;//原始文件名

    private String curFileName;//当前文件名ss

    private String suffixName;//文件后缀

    private List<String> keys;//下载keys
}
