package com.lryepoch.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.ResultEnum;
import com.lryepoch.dao.InfoQueryMapper;
import com.lryepoch.entity.dto.ProductListDTO;
import com.lryepoch.entity.vo.InfoFilterVO;
import com.lryepoch.service.QueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lryepoch
 * @date 2020/10/31 14:58
 * @description TODO 提供用户查询产品数据
 */
@Api(description = "产品查询控制器")
@RestController
@RequestMapping(value = "/product")
public class ProductQueryController {
    @Resource
    private QueryService queryService;
    @Resource
    private InfoQueryMapper infoQueryMapper;

    /**
     * 构造查询条件-分别从info表和price表中查询对应的字段的各种结果
     */
    @ApiOperation(value = "获取查询筛选器")
    @GetMapping(value = "/getFilter")
    public CommonResult getProductFilter() {
        return queryService.getFilter();
    }

    /**
     * 检查机型是否存在
     */
    @ApiOperation(value = "检查该机型是否存在")
    @GetMapping(value = "/judgeModel")
    public CommonResult judgeModel(@RequestParam("model") String model) {
        if (queryService.judgeIfExist(model)) {
            return CommonResult.fail(ResultEnum.ERR.getCode(), "该机型已存在");
        } else {
            return CommonResult.success(ResultEnum.SUCCESS.getCode(), "校验通过");
        }
    }

    /**
     * 首页-产品统计[品牌]和[门体]（利用stream流的groupingBy和counting）
     */
    @ApiOperation(value = "获取首页统计图数据")
    @GetMapping(value = "/getIndexTotal")
    public CommonResult getIndexTotal() {
        LinkedList<ProductListDTO> list = infoQueryMapper.getProductList(new InfoFilterVO());
        //根据品牌统计
        Map<String, Long> brandMap = list.parallelStream().collect(Collectors.groupingBy(ProductListDTO::getBrand, Collectors.counting()));
        //根据门体统计
        Map<String, Long> doorMap = list.parallelStream().collect(Collectors.groupingBy(ProductListDTO::getDoor, Collectors.counting()));

        JSONObject json = new JSONObject(true);
        json.put("brand", brandMap);
        json.put("door", doorMap);
        return CommonResult.success(json);
    }

    /**
     * 数据展示-图表统计
     */
    @ApiOperation(value = "获取产品统计数据")
    @PostMapping(value = "/getProductTotal")
//    @PageLog
    public CommonResult getProductTotal(@RequestBody InfoFilterVO infoFilterVO) {
        return queryService.getProductTotal(infoFilterVO);
    }

    /**
     * 首页-最新数据
     */
    @ApiOperation(value = "获取首页最新产品列表")
    @PostMapping(value = "/infoIndexList")
    public CommonResult getProductIndexList(@RequestBody InfoFilterVO infoFilterVO, HttpServletRequest request) {
        return queryService.getProductList(infoFilterVO, request);
    }

    /**
     * 按条件查询产品列表
     */
    @ApiOperation(value = "根据筛选条件获取产品列表")
    @PostMapping(value = "/infoList")
//    @PageLog
    public CommonResult getProductList(@RequestBody InfoFilterVO infoFilterVO, HttpServletRequest request) {
        return queryService.getProductList(infoFilterVO, request);
    }

    /**
     * 数据管理-数据维护-展示页面产品列表
     */
    @ApiOperation(value = "获取数据维护页面产品列表")
    @PostMapping(value = "/infoMaintainList")
    public CommonResult getProductMaintainList(@RequestBody InfoFilterVO infoFilterVO, HttpServletRequest request) {
        return queryService.getProductList(infoFilterVO, request);
    }

    /**
     * 根据id获取产品的详细数据，包括info/price/photo……
     */
    @ApiOperation(value = "获取产品详细信息")
    @GetMapping(value = "/getProductDetail")
    public CommonResult getProductDetail(int id, HttpServletRequest request) {
        return queryService.getProductDetail(id, request);
    }

    /**
    * 有bug
    */
    @ApiOperation(value = "获取产品对比信息")
    @PostMapping(value = "/getProductContrast")
    public CommonResult getProductContrast(@RequestBody Integer[] ids) {
        JSONObject json = queryService.getProductContrast(ids);
        if (json.getJSONArray("array") != null && json.getJSONArray("array").size() > 0) {
            return CommonResult.success(ResultEnum.SUCCESS.getCode(), JSON.toJSONString(json, SerializerFeature.WriteMapNullValue));
        } else {
            return CommonResult.fail(ResultEnum.ERR.getCode(), "id对应机型不存在");
        }
    }

    /**
     * 首页-待确认数据，包括info和price
     */
    @ApiOperation(value = "获取首页全部爬取数据", notes = "根据更新时间倒序排")
    @PostMapping(value = "/getReptileIndexData")
    public CommonResult getReptileIndexData(@RequestBody InfoFilterVO infoFilterVO) {
        return queryService.getReptileProductList(infoFilterVO);
    }

    /**
     * 数据管理-数据确认-展示页面爬虫表数据，包括info和price
     */
    @ApiOperation(value = "获取全部爬取数据", notes = "根据更新时间倒序排")
    @PostMapping(value = "/getReptileList")
//    @PageLog
    public CommonResult getReptileList(@RequestBody InfoFilterVO infoFilterVO) {
        return queryService.getReptileProductList(infoFilterVO);
    }

    /**
     * 根据id查询爬虫表详细数据，包括info和price两部分
     */
    @ApiOperation(value = "获取爬取数据详细信息", notes = "根据更新时间倒序排")
    @PostMapping(value = "/getReptileDetail")
    public CommonResult getReptileDetail(int id) {
        return queryService.getReptileDetails(id);
    }

}
