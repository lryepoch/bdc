package com.lryepoch.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lryepoch.config.ReminderCache;
import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.ResultEnum;
import com.lryepoch.config.entity.ServiceEnum;
import com.lryepoch.dao.ColumnsMapper;
import com.lryepoch.dao.InfoQueryMapper;
import com.lryepoch.dao.ReptileQueryMapper;
import com.lryepoch.dao.jpa.*;
import com.lryepoch.entity.dto.ProductListDTO;
import com.lryepoch.entity.dto.columns.ColumnCache;
import com.lryepoch.entity.dto.columns.UserRatingDTO;
import com.lryepoch.entity.product.*;
import com.lryepoch.entity.vo.InfoFilterVO;
import com.lryepoch.entity.vo.TotalChartListVO;
import com.lryepoch.service.PictureService;
import com.lryepoch.service.QueryService;
import com.lryepoch.service.RateService;
import com.lryepoch.util.ConverterUtils;
import com.lryepoch.util.FileDecryptionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lryepoch
 * @date 2020/10/13 19:05
 * @description TODO 产品查询服务
 */
@Service
public class QueryServiceImpl implements QueryService {
    @Resource
    private InfoQueryMapper infoQueryMapper;
    @Resource
    private ColumnCache cache;
    @Resource
    private ColumnsMapper columnsMapper;
    @Resource
    private FileDecryptionUtils decryptionUtils;
    @Resource
    private ReptileQueryMapper reptileQueryMapper;
    @Resource
    private ReptileJpaMapper reptileJpaMapper;
    @Resource
    private PriceJpaMapper priceJpaMapper;
    @Resource
    private InfoJpaMapper infoJpaMapper;
    @Resource
    private ProfessionalParamMapper professionalParamMapper;
    @Resource
    private RateService rateService;
    @Resource
    private RatingJpaMapper ratingJpaMapper;
    @Resource
    private PictureService pictureService;

    /**
     * @description 当前机型是否存在
     * @author lryepoch
     * @date 2020/11/10 17:19
     */
    @Override
    public boolean judgeIfExist(String model) {
        ProductInfo info = infoQueryMapper.findOneProductInfo(model);
        return info != null;
    }

    /**
     * @description 获取产品查询列表的各筛选器的值
     * @author lryepoch
     * @date 2020/11/10 14:36
     */
    @Override
    public CommonResult getFilter() {
        Class clazz = InfoFilterVO.class;
        Field[] fields = clazz.getDeclaredFields();
        JSONObject json = new JSONObject();
        for (Field field : fields) {
            String filterName = field.getName(), tableName;
            StringBuilder sb = new StringBuilder();

            //价格表里的渠道筛选器另外处理
            String priceFilter = "distributionChannel", numFilter = "tempAreaNum", modelExclude = "model";

            //只需要取出String类型的筛选器的值即可，数字类型的可在自定义范围筛选
            if (cache.getStringList().contains(filterName) || numFilter.equals(filterName)) {
                if (priceFilter.equals(filterName)) {
                    tableName = ServiceEnum.PRICE_TABLE_NAME.getString();
                } else if (modelExclude.equals(filterName)) {
                    continue;
                } else {
                    tableName = ServiceEnum.INFO_TABLE_NAME.getString();
                }

                //实体中的驼峰命名需要转换成下划线才能直接查询数据库
                for (int i = 0; i < filterName.length(); i++) {
                    char c = filterName.charAt(i);
                    if (c >= 65 && c <= 90) {
                        sb.append("_").append((char) (c + 32));
                    } else {
                        sb.append(c);
                    }
                }
                List<String> valueList = columnsMapper.getFilterValue(sb.toString(), tableName);
                //去除常见的非标准元素
                valueList.remove("N/A");
                valueList.remove("");
                valueList.remove(null);
                json.put(filterName, valueList);
            }
        }
        return CommonResult.success(json);
    }

    /**
     * @description 获取产品列表，包含品牌/门体/型号/数据来源以及图片地址
     * @author lryepoch
     * @date 2020/11/10 17:27
     */
    @Override
    public CommonResult getProductList(InfoFilterVO infoFilterVO, HttpServletRequest request) {
        //查询出所有的数据
        LinkedList<ProductListDTO> list = infoQueryMapper.getProductList(infoFilterVO);
        //根据用户名获取缓存中的集合，该集合包含上周日上新价格对应的冰箱ID。pid就是冰箱id
        Set<Integer> set = ReminderCache.map.get(((Map) request.getSession().getAttribute("ssoUserInfo")).get("UserName").toString());
        set = (set == null) ? new HashSet<>() : set;

        //设置提醒标志位
        for (ProductListDTO dto : list) {
            if (set.contains(dto.getId())) {
                dto.setPriceReminder(true);
            } else {
                dto.setPriceReminder(false);
            }
        }

        //根据提醒和更新时间排序
        List<ProductListDTO> remindList = list.stream().sorted(Comparator.comparing(ProductListDTO::getPriceReminder, Comparator.reverseOrder()).thenComparing(ProductListDTO::getUpdateTime, Comparator.reverseOrder())).collect(Collectors.toList());
        //设置每个型号最新价格
        remindList.forEach(p -> {
            if (p.getPrices() == null || p.getPrices().size() == 0) {
                p.setNewestPrice(null);
            } else {
                p.setNewestPrice(p.getPrices().get(0).getPrice());
            }
        });

        //取出分页数据，pageHelper不支持一对多结果嵌套，且过程嵌套相比内存分页消耗更大
        if (infoFilterVO.getPage() != null && infoFilterVO.getRow() != null) {
            List<ProductListDTO> newList = pageSize(infoFilterVO, remindList);
            for (ProductListDTO dto : newList) {
                dto.setPictureUrl(decryptionUtils.getPictureUrls(dto.getId(), "main").size() > 0 ? decryptionUtils.getPictureUrls(dto.getId(), "main").get(0) : null);
            }
            return CommonResult.success(JSON.toJSON(newList));
        } else {
            return CommonResult.success(JSON.toJSON(remindList));
        }
    }


    /**
     * @description 根据筛选器取出分页数据
     * @author lryepoch
     * @date 2020/11/11 9:55
     */
    private List<ProductListDTO> pageSize(InfoFilterVO infoFilterVO, List<ProductListDTO> list) {
        int start;
        int end;
        int pages = Integer.parseInt(infoFilterVO.getPage());
        int rows = Integer.parseInt(infoFilterVO.getRow());
        start = pages * rows - rows;
        end = pages * rows > list.size() ? list.size() : pages * rows;

        return list.size() >= rows ? list.subList(start, end) : list;
    }


    /**
     * @description 获取产品筛选后的统计数据
     * @author lryepoch
     * @date 2020/11/11 11:24
     */
    @Override
    public CommonResult getProductTotal(InfoFilterVO infoFilterVO) {
        //查询出所有的数据
        List<ProductListDTO> list = infoQueryMapper.getProductList(infoFilterVO);
        //Supplier只是为我们提供了一个创建好的对象
        Supplier<Stream<ProductListDTO>> streamSupplier = list::parallelStream;
        //流中页面需要排除的机型
        List<String> excludeList = infoFilterVO.getExcludeMachine() == null ? new ArrayList<>() : infoFilterVO.getExcludeMachine();
        List<ProductListDTO> filteredList = streamSupplier.get().filter(p -> !excludeList.contains(p.getModel())).collect(Collectors.toList());
        Supplier<Stream<ProductListDTO>> filterSupplier = filteredList::stream;

        //根据压缩机/制冷模式/门体/机型/总容积/品牌以及价格等字段统计数据
        //1.压缩机（定/变频机型分布比例）
        Map<String, List<ProductListDTO>> compressorMap = filterSupplier.get().collect(Collectors.groupingBy(ProductListDTO::getCompressor));
        JSONArray compressorArray = new JSONArray();
        compressorMap.forEach((k, v) -> {
            JSONObject json = new JSONObject(true);
            json.put("compressor", k);
            json.put("weight", v.size());
            json.put("list", ConverterUtils.toListVO(v, TotalChartListVO.class));
            compressorArray.add(json);
        });

        //2.制冷模式(风/直冷机型分布比例)
        Map<String, List<ProductListDTO>> refriMode = filterSupplier.get().collect(Collectors.groupingBy(ProductListDTO::getRefrigerationMode));
        JSONArray refriModeArray = new JSONArray();
        compressorMap.forEach((k, v) -> {
            JSONObject json = new JSONObject(true);
            json.put("refrigerationMode", k);
            json.put("weight", v.size());
            json.put("list", ConverterUtils.toListVO(v, TotalChartListVO.class));
            refriModeArray.add(json);
        });

        //3.竞品品牌的平台及平台对应机型清单，为平台深度和平台宽度
        Map<String, List<ProductListDTO>> heightWidthMachNum = filterSupplier.get().collect(Collectors.groupingBy(p -> p.getPlatformDepth() + ":" + p.getPlatformWidth() + ":" + p.getBrand()));
        JSONArray heightWidthMachArray = new JSONArray();
        //分离深度和宽度、品牌为坐标属性
        heightWidthMachNum.forEach((k, v) -> {
            JSONObject json = new JSONObject(true);
            json.put("depth", Double.valueOf(k.split(":")[0]));
            json.put("width", Double.valueOf(k.split(":")[1]));
            json.put("brand", k.split(":")[2]);
            json.put("weight", v.size());
            json.put("list", ConverterUtils.toListVO(v, TotalChartListVO.class));
            heightWidthMachArray.add(json);
        });

        //4.展示竞品品牌的平台及平台对应的门体和容积分布
        Map<String, List<ProductListDTO>> doorVolMap = filterSupplier.get().collect(Collectors.groupingBy(p -> p.getDoor() + ":" + p.getTotalVolume() + ":" + p.getBrand()));
        JSONArray doorVolMachArray = new JSONArray();
        doorVolMap.forEach((k, v) -> {
            JSONObject json = new JSONObject(true);
            json.put("door", k.split(":")[0]);
            json.put("totalVolume", Double.valueOf(k.split(":")[1]));
            json.put("brand", k.split(":")[2]);
            json.put("weight", v.size());
            json.put("list", ConverterUtils.toListVO(v, TotalChartListVO.class));
            doorVolMachArray.add(json);
        });

        //5.筛选条件下的价格和容积段的分布情况
        JSONArray priceVolArray = new JSONArray();
        //取出每个型号下的所有价格，组成价格-机型一对一的数组（故一个机型可能对应了几条数据）
        filterSupplier.get().filter(p -> p.getPrices() != null && p.getPrices().size() > 0).forEach(productListDTO -> {
            JSONObject json = (JSONObject) JSON.toJSON(productListDTO);
            List<ProductPrice> priceList = productListDTO.getPrices();
            //找到每个型号最新的一次价格，没有价格则按0计算
            ProductPrice lastTimePrice = priceList.parallelStream().max(Comparator.comparing(ProductPrice::getActiveTime)).orElseGet(ProductPrice::new);
            //找到距离最新一次价格时间小于七天的所有价格，然后找出其中最小值，若没有则赋值0，如果要改成字符串，修改price的类型以及orElse的参数即可
            double price = priceList.parallelStream().filter(pri -> lastTimePrice.getActiveTime().getTime() - pri.getActiveTime().getTime() <= (1000 * 60 * 60 * 24 * 7))
                    .mapToDouble(ProductPrice::getPrice).min().orElse(0);
            json.put("priceValue", price);
            json.remove("price");
            priceVolArray.add(json);
        });

        //根据价格-容积-品牌对数组进行分组
        Map<String, List<Object>> priceVolMap = priceVolArray.parallelStream().collect(Collectors.groupingBy(p -> {
            JSONObject json = (JSONObject) p;
            return json.getString("priceValue") + ":" + json.getString("totalVolume") + ":" + json.getString("brand");
        }));

        JSONArray priceVolTotalArray = new JSONArray();
        priceVolMap.forEach((k, v) -> {
            JSONObject json = new JSONObject(true);
            json.put("price", Double.valueOf(k.split(":")[0]));
            json.put("totalVolume", Double.valueOf(k.split(":")[1]));
            json.put("brand", k.split(":")[2]);
            json.put("weight", v.size());
            json.put("list", v);
            priceVolTotalArray.add(json);
        });

        JSONObject totalJson = new JSONObject(true);
        totalJson.put("1", compressorArray);
        totalJson.put("2", refriModeArray);
        totalJson.put("3", heightWidthMachArray);
        totalJson.put("4", doorVolMachArray);
        totalJson.put("5", priceVolTotalArray);

        return CommonResult.success(totalJson);
    }


    @Override
    public CommonResult getProductDetail(int id, HttpServletRequest request) {
        //若查看过某个机型，则取消该机型的上新提醒
        String userName = ((Map) request.getSession().getAttribute("ssoUserInfo")).get("UserName").toString();
        ReminderCache.removeId(userName, id);

        Optional<ProductInfo> info = infoJpaMapper.findById(id);
        ProductInfo infoResult;
        if (info.isPresent()) {
            infoResult = info.get();
        } else {
            return CommonResult.fail(ResultEnum.ERR.getCode(), "id对应机型不存在");
        }
        List<ProductPrice> prices = priceJpaMapper.findByModelOrderByActiveTimeDesc(infoResult.getModel());
        ProductProfessionalParameters profession = professionalParamMapper.findByModel(infoResult.getModel());
        JSONObject obj = new JSONObject(true);

        //获取图片地址
        //拼接info、profession所有的图片地址，注入结果
        JSONObject pictureObj = new JSONObject(true);
        JSONArray pictureArray = new JSONArray();

        pictureArray.addAll(decryptionUtils.getPictureUrls(infoResult.getId(), "main"));
        pictureArray.addAll(decryptionUtils.getPictureUrls(infoResult.getId(), "others"));
        //基本参数图片
        pictureObj.put("infoUrl", pictureArray);
        //专业参数图片
        pictureObj.put("profUrl", pictureService.packingProfPictures(infoResult.getId()));

        //获取机型用户平均评价分
        UserRatingDTO rating = rateService.getUserAvgRating(infoResult.getId());

        obj.put("info", infoResult);
        obj.put("price", prices);
        obj.put("professions", profession);
        obj.put("pictureUrl", pictureObj);
        obj.put("userRating", rating);
        //检测当前用户是否已对该机型评价
        obj.put("rateFlag", ratingJpaMapper.existsByUidAndMid(1, id));
        //查询当前用户对当前机型的评分
        obj.put("lastRating", ConverterUtils.toVO(ratingJpaMapper.findByUidAndMid(1, id).orElseGet(ProductRating::new), UserRatingDTO.class));

        return CommonResult.success(ResultEnum.SUCCESS.getCode(), JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue));
    }


    @Override
    public JSONObject getProductContrast(Integer[] ids) {
        List<ProductInfo> infoList = infoJpaMapper.findAllById(new ArrayList<>(Arrays.asList(ids)));
        if (infoList.size()<0){
            return null;
        }
        List<String> modelList = new LinkedList<>();
        infoList.forEach(info -> modelList.add(info.getModel()));

        //获取价格表
        List<ProductPrice> priceList = priceJpaMapper.findByModelListOrderByActiveTimeDesc(modelList);

        //获取专业参数列表
        List<ProductProfessionalParameters> professions = professionalParamMapper.findByModel(modelList);

        //根据传入的id顺序拼接页面各部分参数数组
        JSONArray array = new JSONArray();
        //把各个机型的评分表抽出计算雷达图
        JSONArray chartArray = new JSONArray();
        for (Integer id: ids){
            JSONObject obj = new JSONObject(true);
            infoList.stream()
                    .filter(in->in.getId()==id)
                    .forEach(in2->{
                        JSONArray priceArray = new JSONArray();
                        priceList.parallelStream()
                                .filter(pr->pr.getModel().equals(in2.getModel()))
                                .forEach(priceArray::add);
                        professions.stream()
                                .filter(pro->pro.getModel().equals(in2.getModel()))
                                .forEach(pro2->obj.put("professional", pro2));
                        UserRatingDTO ratingDTO = rateService.getUserAvgRating(in2.getId());
                        //没有用户评分则默认是0
                        if(ratingDTO==null){
                            ratingDTO = new UserRatingDTO();
                        }
                        //价格排序
                        priceArray.sort((p1,p2)->{
                            if (((ProductPrice)p1).getActiveTime().after(((ProductPrice)p2).getActiveTime())){
                                return -1;
                            } else if(((ProductPrice)p1).getActiveTime().before(((ProductPrice)p2).getActiveTime())){
                                return 1;
                            } else {
                                return 0;
                            }
                        });
                        //取出最大最小值，形成一个范围【min~max】
                        String priceField;
                        if (priceArray.size()>=2){
                            priceField="["+((ProductPrice)priceArray.get(priceArray.size()-1)).getPrice()+"~"+((ProductPrice)priceArray.get(0)).getPrice()+"]";
                        } else if(priceArray.size()==1){
                            priceField = "["+((ProductPrice)priceArray.get(priceArray.size()-1)).getPrice()+"]";
                        } else{
                            priceField="暂无价格";
                        }
                        obj.put("info", in2);
                        obj.put("mainPic", decryptionUtils.getPictureUrls(in2.getId(), "main"));
                        obj.put("otherPic", decryptionUtils.getPictureUrls(in2.getId(), "main"));
                        obj.put("price", priceField);
                        obj.putIfAbsent("professional", null);
                        obj.put("profPicture", pictureService.packingProfPictures(in2.getId()));
                        JSONObject chartObj = rateService.getTotalRating(in2, JSON.parseArray(priceArray.toJSONString(), ProductPrice.class), ratingDTO).getJSONObject("chart");
                        chartArray.add(chartObj);
                        obj.put("rating", rateService.getTotalRating(in2, JSON.parseArray(priceArray.toJSONString(), ProductPrice.class), ratingDTO));
                    });
            array.add(obj);
        }
        //抽取雷达图数据，并一起封装
        return getChartData(array, chartArray);
    }

    /**
    * @description 抽取数据中的评分，拼成雷达图数据格式，并与参数数据一起封装
    * @author lryepoch
    * @date 2020/11/12 9:13
    *
    */
    private JSONObject getChartData(JSONArray array, JSONArray chartArray) {
        //雷达图求最大值
        JSONArray indicatorArray = new JSONArray();
        JSONArray dataArray = new JSONArray();
        List<Double> sortList = new ArrayList<>();
        List<String> colList = Arrays.asList(ServiceEnum.PERFORMANCE.getString(), ServiceEnum.ECONOMY.getString(), ServiceEnum.AESTHETICINDEX.getString(), ServiceEnum.EASE.getString());
        //对每个指标进行遍历取最大值
        colList.forEach(col->{
            sortList.clear();
            //最大值数组
            JSONObject indicator = new JSONObject(true);
            chartArray.forEach(c->sortList.add(((JSONObject)c).getDouble(col)));
            indicator.put("name", col);
            indicator.put("max", sortList.parallelStream().max(Double::compareTo).orElseGet(()-> Double.valueOf(0)));
            indicatorArray.add(indicator);
        });
        //取出每个型号的值成一个数组
        chartArray.forEach(c->{
            //每个型号的所有指标数据
            JSONObject data = new JSONObject(true);
            data.put("name", ((JSONObject)c).getString("model"));
            JSONArray values = new JSONArray();
            colList.forEach(col2->values.add(((JSONObject)c).getDouble(col2)));
            data.put("value", values);
            dataArray.add(data);
        });

        //封装所有数据
        JSONObject resultObj = new JSONObject();
        resultObj.put("indicator", indicatorArray);
        resultObj.put("data", dataArray);

        JSONObject totalResult = new JSONObject();
        totalResult.put("array", array);
        totalResult.put("chartData", resultObj);

        return totalResult;
    }


    @Override
    public CommonResult getReptileProductList(InfoFilterVO infoFilterVO) {
        //查询出所有数据
        List<ProductListDTO> list = reptileQueryMapper.getReptileProductList(infoFilterVO);
        //设置每个型号的最新价格
        list.forEach(p -> {
            if (p.getPrices() == null || p.getPrices().size() == 0) {
                p.setNewestPrice(null);
            } else {
                p.setNewestPrice(p.getPrices().get(0).getPrice());
            }
        });

        //取出分页数据，pageHelper不支持一对多结果嵌套，且过程嵌套相比内存分页消耗更大
        if (infoFilterVO.getPage() != null && infoFilterVO.getRow() != null) {
            List<ProductListDTO> newList = pageSize(infoFilterVO, list);
            return CommonResult.success(JSON.toJSON(newList));
        } else {
            return CommonResult.success(JSON.toJSON(list));
        }
    }

    @Override
    public CommonResult getReptileDetails(int id) {
        Optional<ReptileInfo> info = reptileJpaMapper.findById(id);
        ReptileInfo infoResult;
        if (info.isPresent()) {
            infoResult = info.get();
        } else {
            return CommonResult.fail(ResultEnum.ERR.getCode(), "id对应机型不存在");
        }
        List<ProductPrice> price = priceJpaMapper.findReptilePriceByModelOrderByActiveTimeDesc(infoResult.getModel());

        //对保修年限为空的值初始化
        if (infoResult.getWarrantyYears() == null) {
            infoResult.setWarrantyYears((double) 0);
        }

        //把爬取数据里面包装尺寸取产品尺寸向下对10位取整
        //深度-70 十位取整
        infoResult.setPlatformDepth(new BigDecimal(infoResult.getPlatformDepth()).subtract(new BigDecimal(70)).setScale(-1, BigDecimal.ROUND_DOWN).doubleValue());
        //高度 十位取整
        infoResult.setPlatformHeight(new BigDecimal(infoResult.getPlatformHeight()).setScale(-1, BigDecimal.ROUND_DOWN).doubleValue());
        //宽度 十位取整
        infoResult.setPlatformWidth(new BigDecimal(infoResult.getPlatformWidth()).setScale(-1, BigDecimal.ROUND_DOWN).doubleValue());


        JSONObject json = new JSONObject(true);
        json.put("info", infoResult);
        json.put("price", price);
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), JSON.toJSONString(json, SerializerFeature.WriteMapNullValue));
    }


}
