package com.lryepoch.entity.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lryepoch
 * @date 2020/11/10 11:44
 * @description TODO
 */
@Data
public class ProfPictureVO {
    /**
    * id为传入的冰箱id
    */
    private Integer id;
    private MultipartFile[] compression;
    private MultipartFile[] condenserPicture;
    private MultipartFile[] mufflerPicture;
    private MultipartFile[] refriEvaporatorPicture;
    private MultipartFile[] variableEvaporatorPicture;
    private MultipartFile[] freezingEvaporatorPicture;
    private MultipartFile[] exhaustConnectionPicture;
    private MultipartFile[] variableDeforstHearterPicture;
    private MultipartFile[] freezingDeforstHeaterPicture;
    private MultipartFile[] refriFanPicture;
    private MultipartFile[] variableFanPicture;
    private MultipartFile[] freezingFanPicture;

    private String[] filesName;
}
