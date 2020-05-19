package com.mylawyer.community.controller;

import com.mylawyer.community.dto.FileDTO;
import com.mylawyer.community.exception.CustomizeErrorCode;
import com.mylawyer.community.exception.CustomizeException;
import com.mylawyer.community.provider.OSSProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author bsnowflake04
 * Date on 2020/5/17  18:28
 */
@Controller
public class FileController {
    @Autowired
    private OSSProvider ossProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        try {
            if (file != null && file.getOriginalFilename() != null) {
                String objName = null;
                String[] split = file.getOriginalFilename().split("\\.");
                if (split.length > 1) {
                    objName = UUID.randomUUID().toString() + "." + split[split.length - 1];
                    String signedURL = ossProvider.upload(file.getInputStream(), objName);
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setSuccess(1);
                    fileDTO.setUrl(signedURL);
                    return fileDTO;
                }else {
                    throw new CustomizeException(CustomizeErrorCode.UPLOADING_FAILED_CAUSE_NAME);
                }
            }else {
                throw new CustomizeException(CustomizeErrorCode.UPLOADING_FAILED);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
