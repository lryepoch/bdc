package com.resource.download.controller;

import com.resource.common.aop.Log;
import com.resource.download.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lryepoch
 * @date 2020/12/22 15:10
 * @description TODO
 */
@RestController
@RequestMapping("/download")
public class ConfigController {
    @Autowired
    private ConfigService configService;

    @GetMapping(value = "/download")
    @Log(description="下载文件")
    public Object download(HttpServletResponse response, String path, String fileName, String token){
        configService.download(response, path, fileName, token);
        return 1;
    }
}
