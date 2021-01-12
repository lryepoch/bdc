package com.lryepoch.controller;

import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.PathEntity;
import com.lryepoch.config.entity.ResultEnum;
import com.lryepoch.entity.vo.ProfPictureVO;
import com.lryepoch.util.FileDecryptionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/11/9 19:19
 * @description TODO 对图片的读取、上传、修改服务
 */
@Api(description = "图片控制器")
@RestController
@RequestMapping("/picture")
public class PictureController {
    @Autowired
    private PathEntity pathEntity;


    @ApiOperation(value = "根据id和类型获取图片", notes = "pid：型号ID， type:图片种类，name:图片名称")
    @GetMapping(value = "/{pid}/{type}/{name}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public byte[] getPicture(@PathVariable("pid") String pid, @PathVariable("type") String type, @PathVariable("name") String name) throws IOException {
        String aimPath = pathEntity.getPicturePath() + pid + "/" + type + "/" + name;
        if ("lackPicture".equals(type)) {
            aimPath = pathEntity.getPicturePath() + "/" + type + "/" + name;
        }

        File aimFile = new File(aimPath);
        if (!aimFile.exists()) {
            return null;
        }
        FileInputStream in = new FileInputStream(aimPath);
        byte[] bytes = new byte[in.available()];
        int i = in.read(bytes, 0, in.available());
        System.out.println("图片" + i + "已读入");
        in.close();
        return bytes;
    }


    @ApiOperation(value = "上传主视图图片接口", notes = "冰箱id，主视图displayPic，其他图others")
    @PostMapping(value = "/upload")
    public CommonResult uploadPicturesMain(Integer id, @RequestParam(name = "displayPic", required = false) MultipartFile display,
                                           @RequestParam(name = "others", required = false) MultipartFile[] others, String displayName, String saveNames) throws IOException {
        String mainPath = id + "/main";
        List<String> disName = displayName == null || "".equals(displayName.trim()) ? new ArrayList<>() : Arrays.asList(displayName.split(","));
        List<String> otherName = saveNames == null || "".equals(saveNames.trim()) ? new ArrayList<>() : Arrays.asList(saveNames.split(","));
        if (display != null) {
            FileDecryptionUtils.decryFile(pathEntity.getPicturePath() + mainPath + "/", Collections.singletonList(display), disName);
        }

        String othersPath = id + "/others";
        if (others != null && others.length > 0) {
            FileDecryptionUtils.decryFile(pathEntity.getPicturePath() + othersPath + "/", Arrays.asList(others), otherName);
        }
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), "图片上传成功");
    }


    @ApiOperation(value = "上传专业参数图片", notes = "id为传入的冰箱id，filesName为需要保留的图片名称，其余为对应的要上传的文件")
    @PostMapping(value = "/profPicUpload")
    public CommonResult uploadProfPicture(ProfPictureVO pictureVO) throws IOException, IllegalAccessException {
        Integer id = pictureVO.getId();
        String[] filesName = pictureVO.getFilesName();
        Class clazz = ProfPictureVO.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("id") || field.getName().equals("filesName")) {
                continue;
            }
            if (field.get(pictureVO) != null && ((MultipartFile[]) field.get(pictureVO)).length > 0) {
                FileDecryptionUtils.decryFile(pathEntity.getPicturePath() + id + "/" + field.getName() + "/", Arrays.asList((MultipartFile[]) field.get(pictureVO)),
                        filesName == null ? new ArrayList<>() : Arrays.asList(filesName));
            }
        }
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), "图片上传成功");
    }

}
