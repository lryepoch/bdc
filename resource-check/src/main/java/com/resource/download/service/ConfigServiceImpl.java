package com.resource.download.service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import com.auth0.jwt.JWT;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lryepoch
 * @date 2020/12/22 15:15
 * @description TODO
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Override
    public void download(HttpServletResponse response, String path, String fileName, String token) {
        String hostname = "10.2.21.41";
        String username = "root";
        String password = "123456";

        String email = JWT.decode(token).getAudience().get(0);

        if (fileName.contains(".keytab") && !fileName.substring(0, fileName.indexOf(".")).equals(email)) {
            //没有权限
        }
        Connection connection = null;
        ServletOutputStream fos = null;
        InputStream inputStream = null;

        try {
            connection = new Connection(hostname);
            connection.connect();
            boolean isAuthenticated = connection.authenticateWithPassword(username, password);
            if (isAuthenticated == false) {
                //连接失败
            }
            SCPClient scpClient = connection.createSCPClient();
            System.out.println(scpClient.toString());

            File file = new File(path + fileName);
            inputStream = new FileInputStream(file);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/zip");
            //设置输出文件头
            response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("ISO8859-1"),"UTF-8"));
            response.setContentLength((int) file.length());

            fos=response.getOutputStream();
            fos.flush();

            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b))>0){
                fos.write(b, 0, length);
            }
            fos.flush();
            return;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
