package com.lryepoch.controller;

import com.alibaba.fastjson.JSONObject;
import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.PathEntity;
import com.lryepoch.config.entity.ResultEnum;
import com.lryepoch.entity.product.ProductInfo;
import com.lryepoch.entity.product.ProductProfessionalParameters;
import com.lryepoch.service.DataService;
import com.lryepoch.service.QueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/10/10 11:13
 * @description TODO
 */
@Api(description = "产品增删改控制器")
@RestController
@RequestMapping("/data")
public class DataOperationController {
    @Autowired
    private DataService dataService;
    @Autowired
    private PathEntity path;
    @Autowired
    private QueryService queryService;

    @ApiOperation(value = "插入或更新一条Info表数据及价格表数据", notes = "附id为更新，无id为增加")
    @PostMapping(value = {"/infoInsert", "/infoUpdate"})
    public CommonResult insertInfoData(@RequestBody JSONObject json) {
        if (json == null) {
            return CommonResult.fail(ResultEnum.ERR.getCode(), "数据为空");
        }
        //检查一下该机型是否存在
        String column = "model", id = "id", model;
        if (json.getString(id) == null || "".equals(json.getString(id))) {
            //如果id为空，则是插入操作
            if ((model = json.getString(column)) != null && queryService.judgeIfExist(model)) {
                return CommonResult.fail(ResultEnum.ERR.getCode(), "插入失败，该机型已存在");
            }
        }
        //内容格式检查
        String errorName;
        if ((errorName = dataService.judgeService(json, ProductInfo.class)) == null) {
            //错误字段为null，则插入或者更新
            return dataService.infoAndPriceAddAndUpdate(json);
        } else {
            return CommonResult.fail(ResultEnum.ERR.getCode(), errorName);
        }
    }

    @ApiOperation(value = "excel批量更新info/price表数据", notes = "路径的最后一个值为请求类型'model'或者'price'，限定只能一个文件，且对应key为'excel'")
    @PostMapping(value = "/upload/{requestType}")
    public CommonResult uploadExcel(@PathVariable("requestType") String uploadType, @RequestParam("excel") MultipartFile file) throws IOException {
        //price.xlsx
        String fileName = file.getOriginalFilename();
        //xlsx
        String fileType = (fileName != null ? fileName.split("\\.") : new String[0])[1];

        //转化并放进数据库
        String model = "model", price = "price";
        if (model.equals(uploadType)) {
            return dataService.excelSaveInfo(file.getInputStream(), fileType);
        } else if (price.equals(uploadType)) {
            return dataService.excelSavePrice(file.getInputStream(), fileType);
        } else {
            return CommonResult.fail(ResultEnum.ERR.getCode(), "上传格式不存在");
        }
    }

    @ApiOperation(value = "根据ProductInfo id 删除Info/price以及professional数据（deleted=1）", notes = "传入一个id的数组")
    @PostMapping("/deleteProduct")
    public CommonResult deleteProduct(@RequestBody List<Integer> ids) {
        dataService.deleteProduct(ids);
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), "删除成功");
    }

    @ApiOperation(value = "更新或者插入一条professional表数据", notes = "附id为更新，无id为增加")
    @PostMapping(value = {"/profParamInsert", "/profParamUpdate"})
    public CommonResult insertProfParamData(@RequestBody JSONObject json) {
        String key = "model";
        if (json == null || json.getString(key) == null || "".equals(json.getString(key))) {
            return CommonResult.fail(ResultEnum.ERR.getCode(), "数据为空或者【型号】为空");
        }
        ProductProfessionalParameters profParam = json.toJavaObject(ProductProfessionalParameters.class);
        dataService.profInsertAndUpdate(profParam);
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
    }

    @ApiOperation(value = "下载模板文件", notes = "下载模板文件，路径的最后一个值为请求类型'model'或者'price'下载指定类型", produces = "application/octet-stream")
    @GetMapping(value = "/download/{requestType}")
    public void download(@PathVariable("requestType") String requestType, HttpServletResponse response) {
        File file = new File(path.getPicturePath() + "template/" + requestType + ".xlsx");
        if (file.exists()) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + requestType + ".xlsx");
            byte[] buffer = new byte[1024];
            BufferedInputStream bis = null;
            try {
                bis = new BufferedInputStream(new FileInputStream(file));
                OutputStream out = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    out.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @ApiOperation(value = "录入爬虫数据表数据到正式表", notes = "根据id录入爬虫数据")
    @PostMapping(value = "/ensureReptileData")
    public CommonResult insertReptileData(@RequestBody List<Integer> ids){
        return dataService.ensureReptileData(ids);
    }

    @ApiOperation(value = "更新爬虫数据表数据", notes = "需要id, 且model不能修改，不能与爬虫表中的其他数据的机型相同")
    @PostMapping(value = "/updateReptileData")
    public CommonResult updateReptileData(@RequestBody JSONObject json){
        return dataService.updateReptileData(json);
    }

}
