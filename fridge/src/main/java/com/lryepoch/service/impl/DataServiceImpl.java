package com.lryepoch.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.ResultEnum;
import com.lryepoch.config.entity.ServiceEnum;
import com.lryepoch.dao.ColumnsMapper;
import com.lryepoch.dao.InfoQueryMapper;
import com.lryepoch.dao.PriceQueryMapper;
import com.lryepoch.dao.ProfQueryMapper;
import com.lryepoch.dao.jpa.*;
import com.lryepoch.entity.dto.columns.ColumnCache;
import com.lryepoch.entity.dto.columns.ColumnDTO;
import com.lryepoch.entity.product.ProductInfo;
import com.lryepoch.entity.product.ProductPrice;
import com.lryepoch.entity.product.ProductProfessionalParameters;
import com.lryepoch.entity.product.ReptileInfo;
import com.lryepoch.service.DataService;

import com.lryepoch.service.QueryService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author lryepoch
 * @date 2020/10/10 18:36
 * @description TODO
 */
@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private ColumnCache columnCache;

    @Autowired
    private InfoQueryMapper infoQueryMapper;
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private PriceQueryMapper priceQueryMapper;
    @Autowired
    private ProfQueryMapper profQueryMapper;
    @Autowired
    private ProfessionalParamMapper profMapper;
    @Autowired
    private PriceJpaMapper priceJpaMapper;
    @Autowired
    private DataService dataService;
    @Autowired
    private ColumnsMapper columnsMapper;
    @Autowired
    private QueryService queryService;
    @Autowired
    private InfoJpaMapper infoJpaMapper;
    @Autowired
    private ReptileJpaMapper reptileJpaMapper;


    @Override
    public String judgeService(JSONObject productInfoJson, Class productInfoClazz) {
        //使用了反射
        Field[] fields = productInfoClazz.getDeclaredFields();
        //为null?
        Map<String, String> columnZh = columnCache.getColumnsZh();
        //映射对应表全部字段，并对字段对应属性进行校验。一个一个校验的
        for (Field field : fields) {
            if (!judgeNullAndType(productInfoJson, field)) {
                return "【" + columnZh.get(field.getName()) + "】格式错误或者为空";
            }
        }
        return null;
    }


    /**
     * @description 检验属性是否为空或者满足格式（String/int/double）正确为true，失败为false
     * @author lryepoch
     * @date 2020/10/30 10:50
     */
    private boolean judgeNullAndType(JSONObject productInfoJson, Field field) {
        //这里用内存存下数据库comparative_table表中存储的各个字段对应的格式，当前端点击刷新后重新去数据库读取
        //若数据库没有，则默认不检验
        List<String> stringList = columnCache.getStringList(), intList = columnCache.getIntList(),
                doubleList = columnCache.getDoubleList(), timeList = columnCache.getTimeList();
        //flag
        Map<String, Integer> columnsFlag = columnCache.getColumnsFlag();
        //key
        String columnName = field.getName();
        //对应value
        String str = productInfoJson.getString(columnName) != null && (!"".equals(productInfoJson.getString(columnName))) ? productInfoJson.getString(columnName).trim() : null;

        //string
        if (stringList.contains(columnName)) {
            return columnsFlag.get(columnName) != 1 || (columnsFlag.get(columnName) == 1 && str != null);
        }
        //以下三种类型均需要先检验是否必要（flag==1），若不必要则可以为null或者属于该类型，若必要则不为null并且属于该类型
        //int
        else if (intList.contains(columnName)) {
            return (columnsFlag.get(columnName) != 1 && (str == null || str.matches("[0-9]*"))) || (columnsFlag.get(columnName) == 1 && (str != null) && str.matches("[0-9]*"));
        }
        //double
        else if (doubleList.contains(columnName)) {
            return (columnsFlag.get(columnName) != 1 && (str == null || str.matches("[0-9]+(.[0-9]+)?"))) || (columnsFlag.get(columnName) == 1 && (str != null) && str.matches("[0-9]+(.[0-9]+)?"));
        }
        //time
        else if (timeList.contains(columnName)) {
            String ymd = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
            String ymdhms = "((\\d{2}(([02468][048])|([13579][26]))[\\-/\\s]?((((0?[13578])|(1[02]))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-/\\s]?((((0?[13578])|(1[02]))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3])):([0-5]?[0-9])((\\s)|(:([0-5]?[0-9])))))";
            Pattern p1 = Pattern.compile(ymd);
            Pattern p2 = Pattern.compile(ymdhms);
            return (columnsFlag.get(columnName) != 1 && (str == null || p1.matcher(str).matches() || p2.matcher(str).matches())) || (columnsFlag.get(columnName) == 1 && (str != null) && (p1.matcher(str).matches() || p2.matcher(str).matches()));
        }
        //其他格式的属性这里一概没有检验，均默认为true
        else {
            return true;
        }

    }

    /**
     * jpa的新增和修改都是使用save()方法的，此处可同时使用
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult infoAndPriceAddAndUpdate(JSONObject json) {
        String priceStr = "price";
        //拿到价格数组
        JSONArray priceArray = json.getJSONArray(priceStr);
        List<ProductPrice> list = new ArrayList<>();

        //删除当前型号已存在的价格信息
        priceJpaMapper.deleteByModel(json.getString("model"));
        //拿到价格数组
        if (priceArray != null && priceArray.size() != 0) {
            for (Object obj : priceArray) {
                JSONObject jsonObject = new JSONObject((Map<String, Object>) obj);
                jsonObject.put("model", json.get("model"));
                //检验字段
                String errorName;
                if ((errorName = dataService.judgeService(jsonObject, ProductPrice.class)) != null) {
                    return CommonResult.fail(ResultEnum.ERR.getCode(), errorName);
                }
                ProductPrice price = jsonObject.toJavaObject(ProductPrice.class);
                //设置更新时间
                price.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
                list.add(price);
            }
            //先新增该型号的价格信息
            if (list.size() > 0) {
                priceJpaMapper.saveAll(list);
            }
        }

        //插入一条info数据
        ProductInfo productInfo = json.toJavaObject(ProductInfo.class);
        productInfo.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        //默认增加和更新状态为1
        productInfo.setDeleted(1);
        productInfoMapper.save(productInfo);
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), "插入成功");
    }


    /**
     * @description excel导入info表数据
     * @author lryepoch
     * @date 2020/10/31 14:38
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult excelSaveInfo(InputStream inputStream, String fileType) throws IOException {
        List<ColumnDTO> list = columnsMapper.getExcelInfoList();
        //1.抽取excel中的数据
        JSONArray array = getExcelJsonArray(inputStream, fileType, list);
        //2.校验抽取出来的array数据
        List<String> errorList = judgeAndSaveBatchInfoData(array);

        JSONObject resultJson = new JSONObject();
        if (errorList.size() == 0) {
            resultJson.put("total", "共" + array.size() + "条数据，导入成功");
            return CommonResult.success(JSON.toJSON(resultJson));
        }

        resultJson.put("total", "共" + array.size() + "条数据，导入失败");
        resultJson.put("error", JSON.toJSON(errorList));
        return CommonResult.fail(JSON.toJSON(resultJson));
    }

    /**
     * @description 判断JSONArray中的元素是否满足规定
     * @author lryepoch
     * @date 2020/10/31 14:50
     */
    @Transactional(rollbackFor = Exception.class)
    List<String> judgeAndSaveBatchInfoData(JSONArray array) {
        //errorList接收有问题的字段
        List<String> errorList = new LinkedList<>();
        List<ProductInfo> successList = new LinkedList<>();
        List<String> modelList = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            //执行字段类型检验，检验成功转为对象并放入saveList，否则失败信息放入errorList
            String errorName;
            if ((errorName = dataService.judgeService(obj, ProductInfo.class)) == null) {
                ProductInfo productInfo = obj.toJavaObject(ProductInfo.class);
                productInfo.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));

                //queryService.judgeIfExist(productInfo.getModel())查数据库
                //modelList.contains(productInfo.getModel())查excel表
                if (queryService.judgeIfExist(productInfo.getModel()) || modelList.contains(productInfo.getModel())) {
                    errorList.add("第" + (i + 1) + "行 机型" + productInfo.getModel() + "与Excel或者数据库重复");
                } else {
                    successList.add(productInfo);
                    modelList.add(productInfo.getModel());
                }
            } else {
                errorList.add("第" + (i + 1) + "行 机型" + obj.getString("model") + errorName);
            }
        }
        if (successList.size() == array.size()) {
            infoJpaMapper.saveAll(successList);
        }
        return errorList;
    }


    /**
     * @description excel导入price表数据
     * @author lryepoch
     * @date 2020/10/31 14:38
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult excelSavePrice(InputStream inputStream, String fileType) throws IOException {
        List<String> errorList = new LinkedList<>();
        List<ProductPrice> successList = new ArrayList<>();
        List<ColumnDTO> list = columnsMapper.getExcelPriceList();
        //在索引为0的地方，新增一个model字段
        list.add(0, new ColumnDTO().setColumn("model").setType("string"));
        //抽取excel中数据
        JSONArray array = getExcelJsonArray(inputStream, fileType, list);

        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            List<String> modelList = columnsMapper.getFilterValue("model", ServiceEnum.INFO_TABLE_NAME.getString());

            //先判断info表是否存在该机型
            if (!modelList.contains(obj.getString("model"))) {
                errorList.add("第" + (i + 2) + "行 机型【" + obj.getString("model") + "】不存在");
                continue;
            }
            String errorName;
            //存在，则进一步检验字段
            if ((errorName = dataService.judgeService(obj, ProductPrice.class)) != null) {
                errorList.add("第" + (i + 2) + "行 " + errorName);
                continue;
            }
            ProductPrice price = obj.toJavaObject(ProductPrice.class);
            price.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            successList.add(price);
        }

        if (successList.size() == array.size()) {
            priceJpaMapper.saveAll(successList);
        }

        JSONObject resultJson = new JSONObject();
        if (errorList.size() == 0) {
            resultJson.put("total", "共" + array.size() + "条数据，导入成功");
            return CommonResult.success(JSON.toJSON(resultJson));
        }

        resultJson.put("total", "共" + array.size() + "条数据，导入失败");
        resultJson.put("error", JSON.toJSON(errorList));
        return CommonResult.fail(JSON.toJSON(resultJson));
    }


    /**
     * @return
     * @description 获取excel中的数据，并注入到JsonArray中
     * @author lryepoch
     * @date 2020/10/30 19:33
     */
    private JSONArray getExcelJsonArray(InputStream inputStream, String fileType, List<ColumnDTO> list) throws IOException {
        JSONArray array = new JSONArray();

        InputStream in;
        Workbook wb;
        String xls = "xls", xlsx = "xlsx";
        //行数
        int rowNum;
        in = inputStream;
        if (xls.equals(fileType)) {
            wb = new HSSFWorkbook(in);
        } else if (xlsx.equals(fileType)) {
            wb = new XSSFWorkbook(in);
        } else {
            return new JSONArray();
        }
        //只取第一个sheet
        Sheet sheet = wb.getSheetAt(0);
        //获取最后一行
        rowNum = sheet.getLastRowNum();
        int aimNum = rowNum;


        //1.排除空的行
        //获取最大行，需要从最后一条开始倒序排除为空的行和带格式的行
        boolean breakFlag = false;
        for (int i = 1; i < aimNum; i++) {
            Row lastRow = sheet.getRow(aimNum - 1);
            //排除只带格式的行
            if (lastRow == null) {
                rowNum--;
                continue;
            }
            //排除完全空或者都是空格的行，j小于等于list.size()列。即只要有一个单元格不为空都通过
            for (int j = 0; j < list.size(); j++) {
                Cell indexCell = lastRow.getCell(j);
                if (indexCell != null && !"".equals(indexCell.getStringCellValue().trim())) {
                    breakFlag = true;
                    break;
                }
            }
            //找到带数据的最后一行就跳出循环，否则再往上一行继续循环检验
            if (breakFlag) {
                break;
            } else {
                //行数递减
                rowNum--;
            }
        }

        //2.读取剩余的有效行，到array中并返回

        //迭代每行
        for (int i = 1; i <= rowNum; i++) {
            Row row = sheet.getRow(i);
            JSONObject obj = new JSONObject(true);

            //迭代每个单元格，这里默认excel格式标准，列数等于Info字段数
            for (int j = 0; j < list.size(); j++) {
                Cell cell = row.getCell(j);
                //对单元格进行判断，不为空把该字段及对应值就放进json对象
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    String cellValue;
                    //字段格式不为time时，都转为String统一处理
                    if (!"time".equals(list.get(j).getType())) {
                        cell.setCellType(CellType.STRING);
                        cellValue = cell.toString();
                    }
                    //为time时，判断是否为时间格式，如不是则放入字符串NULL，后续检验直接报错该条数据
                    else if (cell.getCellType() == CellType.STRING) {
                        cellValue = "NULL";
                    } else {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cellValue = sdf.format(cell.getDateCellValue());
                    }
                    //意味着excel表中的字段顺序必须固定
                    obj.put(list.get(j).getColumn(), cellValue);
                }
                //为空直接把null放进去
                else {
                    obj.put(list.get(j).getColumn(), null);
                }
            }
            array.add(obj);
        }
        in.close();
        return array;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(List<Integer> ids) {
        //删除专业参数
        profQueryMapper.deleteProParamById(ids);
        //删除价格参数
        priceQueryMapper.deletePriById(ids);
        //删除基本信息
        infoQueryMapper.deleteProductInfo(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void profInsertAndUpdate(ProductProfessionalParameters prof) {
        ProductProfessionalParameters profParam;
        //这里需要查询一遍prof表，若该型号专业参数已存在则根据id更新数据
        //更新的时候因为save方法若对象中的参数未赋值，则默认为空或者0，而id数据表中的主键，不能重复，若修改两次则会出现两个0，导致后续更新都会失败
        if ((profParam = profMapper.findByModel(prof.getModel())) != null) {
            prof.setId(profParam.getId());
        }
        profMapper.save(prof);
    }


    /**
     * @description 检验爬虫表数据，插入正式表
     * @author lryepoch
     * @date 2020/10/31 15:40
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult ensureReptileData(List<Integer> ids) {
        //检查爬虫表数据是否存在
        List<ReptileInfo> reptileInfos = reptileJpaMapper.findAllById(ids);
        if (reptileInfos.size() == 0) {
            return CommonResult.fail(ResultEnum.ERR.getCode(), "爬虫表未找到该型号，请确认是否存在");
        }
        //把爬取数据里面报修年限为null的设为0
        reptileInfos.parallelStream().filter(rep -> rep.getWarrantyYears() == null).forEach(reptileInfo -> reptileInfo.setWarrantyYears(Double.valueOf(0)));

        reptileInfos.parallelStream().forEach(reptileInfo -> {
            //把爬取数据里面包装尺寸取产品尺寸向下对10位取整
            //深度-70 十位取整
            reptileInfo.setPlatformDepth(new BigDecimal(reptileInfo.getPlatformDepth()).subtract(new BigDecimal(70)).setScale(-1, BigDecimal.ROUND_DOWN).doubleValue());
            //高度 十位取整
            reptileInfo.setPlatformHeight(new BigDecimal(reptileInfo.getPlatformHeight()).setScale(-1, BigDecimal.ROUND_DOWN).doubleValue());
            //宽度 十位取整
            reptileInfo.setPlatformWidth(new BigDecimal(reptileInfo.getPlatformWidth()).setScale(-1, BigDecimal.ROUND_DOWN).doubleValue());
        });

        JSONArray array = JSONArray.parseArray(JSON.toJSONString(reptileInfos, SerializerFeature.WriteMapNullValue));

        //删除在爬虫表中标记已确认导入的数据（标志）
        reptileInfos.forEach(reptileInfo -> reptileInfo.setDeleted(0));
        reptileJpaMapper.saveAll(reptileInfos);

        //判断和插入新的数据
        List<String> errorList = judgeAndSaveBatchInfoData(array);

        //检验并返回导入的结果，若有错误则回滚上面的删除标记
        if (errorList.size() > 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        JSONObject resultJson = new JSONObject();
        if (errorList.size() == 0) {
            resultJson.put("total", "共" + array.size() + "条数据，导入成功");
            return CommonResult.success(JSON.toJSON(resultJson));
        }

        resultJson.put("total", "共" + array.size() + "条数据，导入失败");
        resultJson.put("error", JSON.toJSON(errorList));
        return CommonResult.fail(JSON.toJSON(resultJson));
    }

    /**
     * @description 更新爬虫信息表中的数据
     * @author lryepoch
     * @date 2020/10/31 15:41
     */
    @Override
    public CommonResult updateReptileData(JSONObject json) {
        String id = json.getString("id");
        String model = json.getString("model");
        //如果报修年限为null，默认补0，后续表设置为不允许warrantyYears为null之后此判断可不用
        if (json.getString("warrantyYears") == null) {
            json.put("warrantyYears", 0);
        }
        if (model != null && reptileJpaMapper.findByModel(model).isPresent()) {
            if (reptileJpaMapper.findByModel(model).get().getId() != Integer.valueOf(id)) {
                return CommonResult.fail(ResultEnum.ERR.getCode(), "爬虫表里该型号已存在");
            }
        }
        //如果id不存在 或者 id对应的数据不存在 则拒绝插入
        if (id == null || !reptileJpaMapper.findById(Integer.valueOf(id)).isPresent()) {
            return CommonResult.success(ResultEnum.ERR.getCode(), "该机型在爬虫表中不存在，爬虫表不允许手动新增数据");
        }
        //内容格式检查
        String errorName;
        if ((errorName = dataService.judgeService(json, ReptileInfo.class)) == null) {
            ReptileInfo reptileInfo = json.toJavaObject(ReptileInfo.class);
            reptileInfo.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            reptileJpaMapper.save(reptileInfo);
            return CommonResult.success(ResultEnum.SUCCESS.getCode(), String.valueOf(reptileInfo.getId()));
        } else {
            return CommonResult.fail(ResultEnum.ERR.getCode(), errorName);
        }
    }
}
