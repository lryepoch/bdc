package com.lryepoch.config;

import com.lryepoch.config.entity.FtpEntity;
import com.sun.org.apache.bcel.internal.generic.I2F;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/11/12 14:16
 * @description TODO FTP文件服务器配置
 */
@Component
public class FtpConfig {
    private final FtpEntity ftpEntity;
    private FTPClient client;

    public FtpConfig(FtpEntity ftpEntity) {
        this.ftpEntity = ftpEntity;
    }

    private FTPClient connectToServer() throws IOException {
        int replay;

        FTPClient ftp = new FTPClient();
        if (client != null && FTPReply.isPositiveCompletion(client.getReply())) {
            return client;
        }
        ftp.setControlEncoding("utf-8");
        ftp.connect(ftpEntity.getHost(), ftpEntity.getPort());
        ftp.login(ftpEntity.getUsername(), ftpEntity.getPassword());
        replay = ftp.getReplyCode();

        if (!FTPReply.isPositiveCompletion(replay)) {
            ftp.disconnect();
            System.out.println("ftp连接失败，请检查服务器及网络");
            return null;
        }

        //ftp基础目录为/frigroot
        String path = ftpEntity.getPicPath();
        ftp.changeWorkingDirectory(path);

        client = ftp;
        return client;
    }

    /**
     * @description 上传文件
     * @author lryepoch
     * @date 2020/11/12 14:24
     */
    public boolean uploadFile(MultipartFile multipartFile, String aimPath) throws IOException {
        FTPClient client = connectToServer();
        if (client == null) {
            return false;
        }
        createDirIfNotExist(client, aimPath);
        client.setFileType(FTP.BINARY_FILE_TYPE);
        InputStream inputStream = multipartFile.getInputStream();
        if (!client.storeFile(multipartFile.getOriginalFilename(), inputStream)) {
            inputStream.close();
            return false;
        }
        inputStream.close();
        return true;
    }

    /**
     * @description 跳转到（不存在则创建）文件对应的目录
     * @author lryepoch
     * @date 2020/11/12 14:24
     */
    private void createDirIfNotExist(FTPClient client, String aimPath) throws IOException {
        String[] dirs = aimPath.split("/");
        for (String dir : dirs) {
            if (dir == null || "".equals(dir)) {
                continue;
            }
            if (!client.changeWorkingDirectory(dir)) {
                client.makeDirectory(dir);
                client.changeWorkingDirectory(dir);
            }
        }
    }


    /**
     * @description 删除路径的所有文件
     * @author lryepoch
     * @date 2020/11/12 14:31
     */
    public void deleteFile(String dictionary) throws IOException {
        FTPClient client = connectToServer();
        if (client != null) {
            client.changeWorkingDirectory(dictionary);
            for (FTPFile ftpFile : client.listFiles()) {
                client.dele(ftpFile.getName());
            }
        }
    }

    /**
    * @description 获取机型的图片地址
    * @author lryepoch
    * @date 2020/11/12 14:36
    *
    */
    public List<String> getPictureUrls(int id, String type) throws IOException {
        FTPClient client;
        String parentPath = id + "/" + type + "/";
        List<String> list = new ArrayList<>();

        client = connectToServer();
        createDirIfNotExist(client, parentPath);
        if (client != null) {
            for (FTPFile ftpFile : client.listFiles()) {
                list.add("/picture/" + parentPath + ftpFile.getName());
            }
        }
        return list;
    }
}
