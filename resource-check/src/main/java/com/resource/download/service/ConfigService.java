package com.resource.download.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lryepoch
 * @date 2020/12/22 15:14
 * @description TODO
 */
public interface ConfigService {
    void download(HttpServletResponse response, String path, String fileName, String token);
}
