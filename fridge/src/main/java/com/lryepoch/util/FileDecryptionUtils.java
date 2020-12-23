package com.lryepoch.util;

import com.lryepoch.config.entity.PathEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lryepoch
 * @date 2020/11/10 8:47
 * @description TODO 文件上传解密工具类
 */
@Component
public class FileDecryptionUtils {
    @Autowired
    private PathEntity pathEntity;

    /**
     * @description 将文件解密，即通过浏览器上传文件到服务器临时文件夹
     * @author lryepoch
     * @date 2020/11/10 10:50
     */
    public static void decryFile(String tempPath, List<MultipartFile> files, List<String> stringList) throws IOException {

        List<String> existList = stringList.size() > 0 ? stringList : new ArrayList<>();

        //删除文件夹下不包含在stringList中的文件，需要一个个遍历删除，无法删除整个文件夹
        File parentFile, templeFile = new File(tempPath);
        if (templeFile.exists()) {
            for (File file : Objects.requireNonNull(templeFile.listFiles())) {
                if (!existList.contains(file.getName())) {
                    System.out.println(file.getName() + "删除结果：" + file.delete());
                }
            }
        }

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if ("".equals(fileName)) {
                continue;
            }
            fileName = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + "." + (fileName != null ? (fileName.split("\\."))[1] : new String[0]);

            //目的地址
            File serverPath = new File(tempPath + fileName);

            if ((parentFile = serverPath.getParentFile()).exists() || parentFile.mkdirs()) {
                file.transferTo(serverPath);
            }
        }
    }

    public List<String> getPictureUrls(int id, String type) {
        String parentPath = id + "/" + type + "/";
        File file = new File(pathEntity.getPicturePath() + parentPath);
        List<String> fileNames = new ArrayList<>();
        if(file.exists()||Objects.requireNonNull(file.listFiles()).length==0){
            //若主视图缺失，以预设图片显示
            if ("main".equals(type)){
                fileNames.add(pathEntity.getLocalPath()+"/picture/1/lackPicture/nopicture.jpg");
            }
            return fileNames;
        }
        for (File f:Objects.requireNonNull(file.listFiles())){
            fileNames.add(pathEntity.getLocalPath()+"/picture/"+parentPath+f.getName());
        }
        return fileNames;
    }

}
