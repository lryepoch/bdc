/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : fridge

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2020-12-18 13:56:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comparative_table
-- ----------------------------
DROP TABLE IF EXISTS `comparative_table`;
CREATE TABLE `comparative_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `column_zh` varchar(255) DEFAULT NULL,
  `column` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `parent` int(11) DEFAULT NULL,
  `table` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comparative_table
-- ----------------------------
INSERT INTO `comparative_table` VALUES ('1', '总览', '', 'string', '0', '0', 'info');
INSERT INTO `comparative_table` VALUES ('2', '技术参数', '', 'string', '0', '0', 'info');
INSERT INTO `comparative_table` VALUES ('3', '配置', '', 'string', '0', '0', 'info');
INSERT INTO `comparative_table` VALUES ('4', '特色功能', '', 'string', '0', '0', 'info');
INSERT INTO `comparative_table` VALUES ('5', '经济指标', '', 'string', '0', '0', 'info');
INSERT INTO `comparative_table` VALUES ('6', '示意图', '', 'string', '0', '0', 'else');
INSERT INTO `comparative_table` VALUES ('7', '审美评价', '', 'string', '0', '0', 'rating');
INSERT INTO `comparative_table` VALUES ('8', '使用便捷性评价', '', 'string', '0', '0', 'rating');
INSERT INTO `comparative_table` VALUES ('9', '压缩机', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('10', '冷凝器组件', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('11', '防凝管', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('12', '回气管组件', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('13', '冷藏毛细管', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('14', '变温毛细管', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('15', '冷冻毛细管', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('16', '冷藏蒸发器组件', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('17', '变温蒸发器组件', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('18', '冷冻蒸发器组件', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('19', '排气连接管', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('20', '变温化霜加热器', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('21', '冷冻化霜加热器', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('22', '冷藏风机', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('23', '变温风机', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('24', '冷冻风机', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('25', '发泡层厚度', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('26', '外观装配间隙', '', 'string', '0', '0', 'profession');
INSERT INTO `comparative_table` VALUES ('27', '数据来源', 'dataSource', 'string', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('28', '品牌', 'brand', 'string', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('29', '型号', 'model', 'string', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('30', '门体', 'door', 'string', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('31', '总容积', 'totalVolume', 'double', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('32', '冷藏室容积（升）', 'refrigeVol', 'double', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('33', '冷冻室容积（升）', 'freezerVol', 'double', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('34', '变温室容积（升）', 'variableHouseVol', 'double', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('35', '能效等级', 'level', 'string', '1', '2', 'info');
INSERT INTO `comparative_table` VALUES ('36', '综合耗电量（kW·h/24h）', 'compPowerConsumption', 'double', '1', '2', 'info');
INSERT INTO `comparative_table` VALUES ('37', '冷冻能力（kg/12h）', 'coolingCapacity', 'double', '1', '2', 'info');
INSERT INTO `comparative_table` VALUES ('38', '噪音值(dB)', 'noise', 'double', '1', '2', 'info');
INSERT INTO `comparative_table` VALUES ('39', '压缩机', 'compressor', 'string', '1', '2', 'info');
INSERT INTO `comparative_table` VALUES ('40', '产品尺寸(宽)mm', 'productWidth', 'double', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('41', '产品尺寸(高)mm', 'productHeight', 'double', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('42', '产品尺寸(深)mm', 'productDepth', 'double', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('43', '平台尺寸（宽）', 'platformWidth', 'double', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('44', '平台尺寸（深）', 'platformHeight', 'double', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('45', '平台尺寸（高）', 'platformDepth', 'double', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('46', '面板材质', 'panelMaterial', 'string', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('47', '颜色', 'color', 'string', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('48', '制冷方式', 'refrigerationMode', 'string', '1', '2', 'info');
INSERT INTO `comparative_table` VALUES ('49', '上市时间', 'marketTime', 'time', '0', '1', 'info');
INSERT INTO `comparative_table` VALUES ('50', '制冷剂', 'refrigerant', 'string', '1', '2', 'info');
INSERT INTO `comparative_table` VALUES ('51', '重量(kg)', 'weight', 'double', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('52', '额定电压/频率', 'ratedVoltage', 'string', '1', '2', 'info');
INSERT INTO `comparative_table` VALUES ('53', '搁物架（个）', 'rackNum', 'string', '0', '3', 'info');
INSERT INTO `comparative_table` VALUES ('54', '果菜盒(个)', 'boxNum', 'string', '0', '3', 'info');
INSERT INTO `comparative_table` VALUES ('55', '变温抽屉（个）', 'variableDrawerNum', 'string', '0', '3', 'info');
INSERT INTO `comparative_table` VALUES ('56', '瓶座（个）', 'aquariusNum', 'string', '0', '3', 'info');
INSERT INTO `comparative_table` VALUES ('57', '冷冻抽屉（个）', 'freezingDrawerNum', 'string', '0', '3', 'info');
INSERT INTO `comparative_table` VALUES ('58', '折叠搁物架', 'foldbleRackNum', 'string', '0', '3', 'info');
INSERT INTO `comparative_table` VALUES ('59', '蛋盒（个）', 'eggboxNum', 'string', '0', '3', 'info');
INSERT INTO `comparative_table` VALUES ('60', '红酒架', 'wineRackNum', 'string', '0', '3', 'info');
INSERT INTO `comparative_table` VALUES ('61', '冷藏灯', 'refrigeratedLamp', 'string', '0', '3', 'info');
INSERT INTO `comparative_table` VALUES ('62', '吧台', 'barNum', 'string', '0', '3', 'info');
INSERT INTO `comparative_table` VALUES ('63', '制冰机', 'iceMachineNum', 'string', '0', '3', 'info');
INSERT INTO `comparative_table` VALUES ('64', '速冻功能', 'quickFreezing', 'string', '0', '4', 'info');
INSERT INTO `comparative_table` VALUES ('65', '显示方式', 'displayMode', 'string', '1', '4', 'info');
INSERT INTO `comparative_table` VALUES ('66', '童锁功能', 'childLock', 'string', '1', '4', 'info');
INSERT INTO `comparative_table` VALUES ('67', '假日功能', 'holidayFunction', 'string', '0', '4', 'info');
INSERT INTO `comparative_table` VALUES ('68', '控温方式', 'tempControlMode', 'string', '1', '4', 'info');
INSERT INTO `comparative_table` VALUES ('69', '开门报警', 'doorAlarm', 'string', '0', '4', 'info');
INSERT INTO `comparative_table` VALUES ('70', '功能特色', 'features', 'string', '0', '4', 'info');
INSERT INTO `comparative_table` VALUES ('71', '气候类型', 'climateType', 'string', '1', '2', 'info');
INSERT INTO `comparative_table` VALUES ('72', '面板工艺', 'panelTechnology', 'string', '0', '2', 'info');
INSERT INTO `comparative_table` VALUES ('73', '包装尺寸（宽）', 'packingWidth', 'double', '0', '1', 'info');
INSERT INTO `comparative_table` VALUES ('74', '包装尺寸（深）', 'packingHeight', 'double', '0', '1', 'info');
INSERT INTO `comparative_table` VALUES ('75', '包装尺寸（高）', 'packingDepth', 'double', '0', '1', 'info');
INSERT INTO `comparative_table` VALUES ('76', 'WIFI功能', 'wifi', 'string', '1', '4', 'info');
INSERT INTO `comparative_table` VALUES ('77', '杀菌功能', 'bactericidalFunction', 'string', '0', '4', 'info');
INSERT INTO `comparative_table` VALUES ('78', '冷藏温度范围', 'coldTempRange', 'string', '0', '2', 'info');
INSERT INTO `comparative_table` VALUES ('79', '冷冻温度范围', 'freezingTempRange', 'string', '0', '2', 'info');
INSERT INTO `comparative_table` VALUES ('80', '变温温度范围', 'variableTempRange', 'string', '0', '2', 'info');
INSERT INTO `comparative_table` VALUES ('81', '系统类型', 'systemType', 'string', '0', '2', 'info');
INSERT INTO `comparative_table` VALUES ('82', '散热方式', 'coolingMode', 'string', '0', '2', 'info');
INSERT INTO `comparative_table` VALUES ('83', '温区数量', 'tempAreaNum', 'int', '0', '1', 'info');
INSERT INTO `comparative_table` VALUES ('84', '保修年限', 'warrantyYears', 'double', '1', '5', 'info');
INSERT INTO `comparative_table` VALUES ('85', '数据更新时间', 'updateTime', 'time', '1', '1', 'info');
INSERT INTO `comparative_table` VALUES ('86', '图片', 'compression', 'string', '0', '9', 'profession');
INSERT INTO `comparative_table` VALUES ('87', '厂家及型号', 'manufacturerModel', 'string', '0', '9', 'profession');
INSERT INTO `comparative_table` VALUES ('88', '排量', 'displacement', 'string', '0', '9', 'profession');
INSERT INTO `comparative_table` VALUES ('89', '功率', 'power', 'string', '0', '9', 'profession');
INSERT INTO `comparative_table` VALUES ('90', 'COP', 'cop', 'string', '0', '9', 'profession');
INSERT INTO `comparative_table` VALUES ('91', '吸气管规格（管Φ外径*壁厚）', 'suctionPipeSpecification', 'string', '0', '9', 'profession');
INSERT INTO `comparative_table` VALUES ('92', '排气管规格（管Φ外径*壁厚）', 'exhaustPipeSpecification', 'string', '0', '9', 'profession');
INSERT INTO `comparative_table` VALUES ('93', '工艺管规格（管Φ外径*壁厚）', 'processPipeSpecification', 'string', '0', '9', 'profession');
INSERT INTO `comparative_table` VALUES ('94', '电容', 'capacity', 'string', '0', '9', 'profession');
INSERT INTO `comparative_table` VALUES ('95', '制冷量', 'refrigeratingOutput', 'string', '0', '9', 'profession');
INSERT INTO `comparative_table` VALUES ('96', '图片', 'condenserPicture', 'string', '0', '10', 'profession');
INSERT INTO `comparative_table` VALUES ('97', '形式', 'condenserType', 'string', '0', '10', 'profession');
INSERT INTO `comparative_table` VALUES ('98', '换热管规格（材质*Φ外径*长度）', 'condenserHeatExchange', 'string', '0', '10', 'profession');
INSERT INTO `comparative_table` VALUES ('99', '内容积', 'condenserInsideVol', 'string', '0', '10', 'profession');
INSERT INTO `comparative_table` VALUES ('100', '传热面积（外）', 'condenserHeatTransfer', 'string', '0', '10', 'profession');
INSERT INTO `comparative_table` VALUES ('101', '干燥过滤器规格（材质/Φ外径*长度*壁厚）', 'condenserDryFilter', 'string', '0', '10', 'profession');
INSERT INTO `comparative_table` VALUES ('102', '换热管规格（材质/Φ外径*长度*壁厚）', 'anticoagulantHeatExchange', 'string', '0', '11', 'profession');
INSERT INTO `comparative_table` VALUES ('103', '防凝管位置', 'anticoagulantLocation', 'string', '0', '11', 'profession');
INSERT INTO `comparative_table` VALUES ('104', '图片', 'mufflerPicture', 'string', '0', '12', 'profession');
INSERT INTO `comparative_table` VALUES ('105', '形式（锡焊/穿焊/铝箔贴附）', 'mufflerType', 'string', '0', '12', 'profession');
INSERT INTO `comparative_table` VALUES ('106', '换热管规格（材质*Φ外径*长度*壁厚）', 'mufflerHeatExchange', 'string', '0', '12', 'profession');
INSERT INTO `comparative_table` VALUES ('107', '毛细管规格（材质/Φ外径*长度*壁厚）', 'mufflerCapillary', 'string', '0', '12', 'profession');
INSERT INTO `comparative_table` VALUES ('108', '有效换热长度', 'mufflerEffictiveExchangeLength', 'string', '0', '12', 'profession');
INSERT INTO `comparative_table` VALUES ('109', '管规格（材质/Φ外径*长度*壁厚）', 'refrigeratedCapillarySpecification', 'string', '0', '13', 'profession');
INSERT INTO `comparative_table` VALUES ('110', '管规格（材质/Φ外径*长度*壁厚）', 'variableCapillarySpecification', 'string', '0', '14', 'profession');
INSERT INTO `comparative_table` VALUES ('111', '管规格（材质/Φ外径*长度*壁厚）', 'freezingCapillarySpecification', 'string', '0', '15', 'profession');
INSERT INTO `comparative_table` VALUES ('112', '图片', 'refriEvaporatorPicture', 'string', '0', '16', 'profession');
INSERT INTO `comparative_table` VALUES ('113', '形式', 'refriEvaporatorType', 'string', '0', '16', 'profession');
INSERT INTO `comparative_table` VALUES ('114', '换热管规格（材质/长度*Φ外径*长度*壁厚）', 'refriEvaporatorHeatExchange', 'string', '0', '16', 'profession');
INSERT INTO `comparative_table` VALUES ('115', '翅片规格（材质/长度*宽度*厚度）', 'refriEvaporatorFinSpecification', 'string', '0', '16', 'profession');
INSERT INTO `comparative_table` VALUES ('116', '翅片间距', 'refriEvaporatorFinDistance', 'string', '0', '16', 'profession');
INSERT INTO `comparative_table` VALUES ('117', '内容积', 'refriEvaporatorInsideVol', 'string', '0', '16', 'profession');
INSERT INTO `comparative_table` VALUES ('118', '传热面积（外）', 'refriEvaporatorHeatTransfer', 'string', '0', '16', 'profession');
INSERT INTO `comparative_table` VALUES ('119', '储液器规格（材质/Φ外径*长度*壁厚）', 'refriEvaporatorReservoir', 'string', '0', '16', 'profession');
INSERT INTO `comparative_table` VALUES ('120', '排列方式、管排数及行数', 'refriEvaporatorSortType', 'string', '0', '16', 'profession');
INSERT INTO `comparative_table` VALUES ('121', '管间距', 'refriEvaporatorPipeDistance', 'string', '0', '16', 'profession');
INSERT INTO `comparative_table` VALUES ('122', '图片', 'variableEvaporatorPicture', 'string', '0', '17', 'profession');
INSERT INTO `comparative_table` VALUES ('123', '形式', 'variableEvaporatorType', 'string', '0', '17', 'profession');
INSERT INTO `comparative_table` VALUES ('124', '换热管规格（材质*Φ外径*长度*壁厚）', 'variableEvaporatorHeatExchange', 'string', '0', '17', 'profession');
INSERT INTO `comparative_table` VALUES ('125', '翅片规格', 'variableEvaporatorFin', 'string', '0', '17', 'profession');
INSERT INTO `comparative_table` VALUES ('126', '翅片间距', 'variableEvaporatorFinDistance', 'string', '0', '17', 'profession');
INSERT INTO `comparative_table` VALUES ('127', '内容积', 'variableEvaporatorInsideVol', 'string', '0', '17', 'profession');
INSERT INTO `comparative_table` VALUES ('128', '传热面积（外）', 'variableEvaporatorHeatTransfer', 'string', '0', '17', 'profession');
INSERT INTO `comparative_table` VALUES ('129', '储液器规格（材质/Φ外径*长度*壁厚）', 'variableEvaporatorReservoir', 'string', '0', '17', 'profession');
INSERT INTO `comparative_table` VALUES ('130', '排列方式、管排数及行数', 'variableEvaporatorSortType', 'string', '0', '17', 'profession');
INSERT INTO `comparative_table` VALUES ('131', '管间距', 'variableEvaporatorPipeDistance', 'string', '0', '17', 'profession');
INSERT INTO `comparative_table` VALUES ('132', '图片', 'freezingEvaporatorPicture', 'string', '0', '18', 'profession');
INSERT INTO `comparative_table` VALUES ('133', '形式', 'freezingEvaporatorType', 'string', '0', '18', 'profession');
INSERT INTO `comparative_table` VALUES ('134', '换热管规格（材质*Φ外径*长度*壁厚）', 'freezingEvaporatorHeatExchange', 'string', '0', '18', 'profession');
INSERT INTO `comparative_table` VALUES ('135', '翅片规格', 'freezingEvaporatorFin', 'string', '0', '18', 'profession');
INSERT INTO `comparative_table` VALUES ('136', '翅片间距', 'freezingEvaporatorFinDistance', 'string', '0', '18', 'profession');
INSERT INTO `comparative_table` VALUES ('137', '内容积', 'freezingEvaporatorInsideVol', 'string', '0', '18', 'profession');
INSERT INTO `comparative_table` VALUES ('138', '传热面积（外）', 'freezingEvaporatorHeatTransfer', 'string', '0', '18', 'profession');
INSERT INTO `comparative_table` VALUES ('139', '储液器规格（材质/Φ外径*长度*壁厚）', 'freezingEvaporatorReservoir', 'string', '0', '18', 'profession');
INSERT INTO `comparative_table` VALUES ('140', '排列方式、管排数及行数', 'freezingEvaporatorSortType', 'string', '0', '18', 'profession');
INSERT INTO `comparative_table` VALUES ('141', '管间距', 'freezingEvaporatorPipeDistance', 'string', '0', '18', 'profession');
INSERT INTO `comparative_table` VALUES ('142', '图片', 'exhaustConnectionPipePicture', 'string', '0', '19', 'profession');
INSERT INTO `comparative_table` VALUES ('143', '传热管规格（材质/Φ外径*长度*壁厚）', 'exhaustConnectionHeatTransfer', 'string', '0', '19', 'profession');
INSERT INTO `comparative_table` VALUES ('144', '图片', 'variableDeforstHeaterPicture', 'string', '0', '20', 'profession');
INSERT INTO `comparative_table` VALUES ('145', '功率', 'variableDeforstHeaterPower', 'string', '0', '20', 'profession');
INSERT INTO `comparative_table` VALUES ('146', '图片', 'freezingDeforstHeaterPicture', 'string', '0', '21', 'profession');
INSERT INTO `comparative_table` VALUES ('147', '功率', 'freezingDeforstHeaterPower', 'string', '0', '21', 'profession');
INSERT INTO `comparative_table` VALUES ('148', '图片', 'refriFanPicture', 'string', '0', '22', 'profession');
INSERT INTO `comparative_table` VALUES ('149', '厂家及型号', 'refriFanManufacturerModel', 'string', '0', '22', 'profession');
INSERT INTO `comparative_table` VALUES ('150', '风量', 'refriFanBlowingRate', 'string', '0', '22', 'profession');
INSERT INTO `comparative_table` VALUES ('151', '图片', 'variableFanPicture', 'string', '0', '23', 'profession');
INSERT INTO `comparative_table` VALUES ('152', '厂家及型号', 'variableFanManufacturerModel', 'string', '0', '23', 'profession');
INSERT INTO `comparative_table` VALUES ('153', '风量', 'variableFanBlowingRate', 'string', '0', '23', 'profession');
INSERT INTO `comparative_table` VALUES ('154', '图片', 'freezingFanPicture', 'string', '0', '24', 'profession');
INSERT INTO `comparative_table` VALUES ('155', '厂家及型号', 'freezingFanManufacturerModel', 'string', '0', '24', 'profession');
INSERT INTO `comparative_table` VALUES ('156', '风量', 'freezingFanBlowingRate', 'string', '0', '24', 'profession');
INSERT INTO `comparative_table` VALUES ('157', '冷藏箱顶部泡层', 'foamingThickness1', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('158', '冷藏左右泡层', 'foamingThickness2', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('159', '冷藏背部泡层', 'foamingThickness3', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('160', '藏变中部泡层', 'foamingThickness4', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('161', '变温箱两侧泡层', 'foamingThickness5', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('162', '变温箱背部泡层', 'foamingThickness6', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('163', '冻变中间泡层', 'foamingThickness7', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('164', '冷冻左右泡层', 'foamingThickness8', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('165', '冷冻背部泡层', 'foamingThickness9', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('166', '冷冻底部泡层', 'foamingThickness10', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('167', '冷藏门发泡', 'foamingThickness11', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('168', '变温门发泡', 'foamingThickness12', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('169', '冷冻门发泡', 'foamingThickness13', 'string', '0', '25', 'profession');
INSERT INTO `comparative_table` VALUES ('170', '箱体与门体间隙', 'appearanceDistance1', 'string', '0', '26', 'profession');
INSERT INTO `comparative_table` VALUES ('171', '门体与门体间隙', 'appearanceDistance2', 'string', '0', '26', 'profession');
INSERT INTO `comparative_table` VALUES ('172', '铰链盖与箱体间隙', 'appearanceDistance3', 'string', '0', '26', 'profession');
INSERT INTO `comparative_table` VALUES ('173', '销售渠道', 'distributionChannel', 'string', '1', '5', 'price');
INSERT INTO `comparative_table` VALUES ('174', '价格', 'price', 'double', '1', '5', 'price');
INSERT INTO `comparative_table` VALUES ('175', '生效时间', 'activeTime', 'time', '1', '5', 'price');
INSERT INTO `comparative_table` VALUES ('176', '造型评价', 'profiling', 'double', '0', '7', 'rating');
INSERT INTO `comparative_table` VALUES ('177', '色彩评价', 'color', 'double', '0', '7', 'rating');
INSERT INTO `comparative_table` VALUES ('178', '内饰精致性评价', 'interior', 'double', '0', '7', 'rating');
INSERT INTO `comparative_table` VALUES ('179', '灯光评价', 'light', 'double', '0', '7', 'rating');
INSERT INTO `comparative_table` VALUES ('180', '人体工学评价', 'ergonomics', 'double', '0', '8', 'rating');
INSERT INTO `comparative_table` VALUES ('181', '存储分区评价', 'storage', 'double', '0', '8', 'rating');
INSERT INTO `comparative_table` VALUES ('182', '操作易用性评价', 'operation', 'double', '0', '8', 'rating');

-- ----------------------------
-- Table structure for new_product_info
-- ----------------------------
DROP TABLE IF EXISTS `new_product_info`;
CREATE TABLE `new_product_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_source` varchar(255) NOT NULL COMMENT '数据来源',
  `brand` varchar(255) NOT NULL COMMENT '品牌',
  `model` varchar(255) NOT NULL COMMENT '型号',
  `panel_material` varchar(255) NOT NULL COMMENT '面板材质',
  `color` varchar(255) NOT NULL COMMENT '颜色',
  `update_time` datetime NOT NULL COMMENT '数据更新时间',
  `door` varchar(255) NOT NULL COMMENT '门体',
  `total_volume` double(255,2) NOT NULL COMMENT '总容积',
  `refrige_vol` double(255,2) NOT NULL COMMENT '冷藏室容积',
  `freezer_vol` double(255,2) NOT NULL COMMENT '冷冻室容积',
  `variable_house_vol` double(255,2) NOT NULL COMMENT '变温室容积',
  `temp_area_num` int(11) DEFAULT NULL COMMENT '温区数量',
  `product_width` double(100,2) NOT NULL COMMENT '产品尺寸（宽）',
  `product_height` double(100,2) NOT NULL COMMENT '产品尺寸（高）',
  `product_depth` double(100,2) NOT NULL COMMENT '产品尺寸（深）',
  `platform_width` double(100,2) NOT NULL COMMENT '平台尺寸（宽）',
  `platform_height` double(100,2) NOT NULL COMMENT '平台尺寸（高）',
  `platform_depth` double(100,2) NOT NULL COMMENT '平台尺寸（深）',
  `packing_width` double(100,2) DEFAULT NULL COMMENT '包装尺寸（宽）',
  `packing_height` double(100,2) DEFAULT NULL COMMENT '包装尺寸（高）',
  `packing_depth` double(100,2) DEFAULT NULL COMMENT '包装尺寸（深）',
  `market_time` datetime DEFAULT NULL COMMENT '上市时间',
  `weight` double(100,2) NOT NULL COMMENT '重量',
  `climate_type` varchar(255) NOT NULL COMMENT '气候类型',
  `level` varchar(255) NOT NULL COMMENT '能效等级',
  `system_type` varchar(255) DEFAULT NULL COMMENT '系统类型',
  `comp_power_consumption` double(100,2) NOT NULL COMMENT '综合耗电量',
  `cooling_capacity` double(100,2) NOT NULL COMMENT '冷冻能力',
  `noise` double(100,2) NOT NULL COMMENT '噪音值',
  `compressor` varchar(255) NOT NULL COMMENT '压缩机',
  `refrigeration_mode` varchar(255) NOT NULL COMMENT '制冷方式',
  `cooling_mode` varchar(255) DEFAULT NULL COMMENT '散热方式',
  `refrigerant` varchar(255) NOT NULL COMMENT '制冷剂',
  `rated_voltage` varchar(255) NOT NULL COMMENT '额定电压/频率',
  `cold_temp_range` varchar(255) DEFAULT NULL COMMENT '冷藏温度范围',
  `freezing_temp_range` varchar(255) DEFAULT NULL COMMENT '冷冻温度范围',
  `variable_temp_range` varchar(255) DEFAULT NULL COMMENT '变温温度范围',
  `panel_technology` varchar(255) DEFAULT NULL COMMENT '面板工艺',
  `rack_num` varchar(255) DEFAULT NULL COMMENT '搁物架数量',
  `box_num` varchar(255) DEFAULT NULL COMMENT '果菜盒数量',
  `variable_drawer_num` varchar(255) DEFAULT NULL COMMENT '变温抽屉数量',
  `aquarius_num` varchar(255) DEFAULT NULL COMMENT '瓶座数量',
  `freezing_drawer_num` varchar(255) DEFAULT NULL COMMENT '冷冻抽屉数量',
  `foldble_rack_num` varchar(255) DEFAULT NULL COMMENT 'z折叠搁物架数量',
  `eggbox_num` varchar(255) DEFAULT NULL COMMENT '蛋盒数量',
  `wine_rack_num` varchar(255) DEFAULT NULL COMMENT '红酒架数量',
  `refrigerated_lamp` varchar(255) DEFAULT NULL COMMENT '冷藏灯',
  `bar_num` varchar(255) DEFAULT NULL COMMENT '吧台数量',
  `ice_machine_num` varchar(255) DEFAULT NULL COMMENT '制冰机数量',
  `quick_freezing` varchar(255) DEFAULT NULL COMMENT 's速冻功能',
  `display_mode` varchar(255) NOT NULL COMMENT 'x显示方式',
  `child_lock` varchar(255) NOT NULL COMMENT 't童锁功能',
  `holiday_function` varchar(255) DEFAULT NULL COMMENT '假日功能',
  `temp_control_mode` varchar(255) NOT NULL COMMENT 'k控温方式',
  `door_alarm` varchar(255) DEFAULT NULL COMMENT 'k开门报警',
  `features` varchar(255) DEFAULT NULL COMMENT 'g功能特色',
  `wifi` varchar(255) NOT NULL COMMENT 'wifi功能',
  `bactericidal_function` varchar(255) DEFAULT NULL COMMENT 's杀菌功能',
  `warranty_years` double(100,2) NOT NULL DEFAULT '0.00' COMMENT '报修年限',
  `deleted` int(11) DEFAULT '1' COMMENT '删除（1：启用，0：禁用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of new_product_info
-- ----------------------------

-- ----------------------------
-- Table structure for new_product_price
-- ----------------------------
DROP TABLE IF EXISTS `new_product_price`;
CREATE TABLE `new_product_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `model` varchar(255) NOT NULL COMMENT '型号',
  `price` double(100,2) NOT NULL COMMENT '价格',
  `distribution_channel` varchar(255) DEFAULT NULL COMMENT '销售渠道',
  `active_time` datetime NOT NULL COMMENT '上市时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '删除（1：启用，0：禁用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of new_product_price
-- ----------------------------

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_source` varchar(255) NOT NULL COMMENT '数据来源',
  `brand` varchar(255) NOT NULL COMMENT '品牌',
  `model` varchar(255) NOT NULL COMMENT '型号',
  `panel_material` varchar(255) NOT NULL COMMENT '面板材质',
  `color` varchar(255) NOT NULL COMMENT '颜色',
  `update_time` datetime NOT NULL COMMENT '数据更新时间',
  `door` varchar(255) NOT NULL COMMENT '门体',
  `total_volume` double(255,2) NOT NULL COMMENT '总容积',
  `refrige_vol` double(255,2) NOT NULL COMMENT '冷藏室容积',
  `freezer_vol` double(255,2) NOT NULL COMMENT '冷冻室容积',
  `variable_house_vol` double(255,2) NOT NULL COMMENT '变温室容积',
  `temp_area_num` int(11) DEFAULT NULL COMMENT '温区数量',
  `product_width` double(100,2) NOT NULL COMMENT '产品尺寸（宽）',
  `product_height` double(100,2) NOT NULL COMMENT '产品尺寸（高）',
  `product_depth` double(100,2) NOT NULL COMMENT '产品尺寸（深）',
  `platform_width` double(100,2) NOT NULL COMMENT '平台尺寸（宽）',
  `platform_height` double(100,2) NOT NULL COMMENT '平台尺寸（高）',
  `platform_depth` double(100,2) NOT NULL COMMENT '平台尺寸（深）',
  `packing_width` double(100,2) DEFAULT NULL COMMENT '包装尺寸（宽）',
  `packing_height` double(100,2) DEFAULT NULL COMMENT '包装尺寸（高）',
  `packing_depth` double(100,2) DEFAULT NULL COMMENT '包装尺寸（深）',
  `market_time` datetime DEFAULT NULL COMMENT '上市时间',
  `weight` double(100,2) NOT NULL COMMENT '重量',
  `climate_type` varchar(255) NOT NULL COMMENT '气候类型',
  `level` varchar(255) NOT NULL COMMENT '能效等级',
  `system_type` varchar(255) DEFAULT NULL COMMENT '系统类型',
  `comp_power_consumption` double(100,2) NOT NULL COMMENT '综合耗电量',
  `cooling_capacity` double(100,2) NOT NULL COMMENT '冷冻能力',
  `noise` double(100,2) NOT NULL COMMENT '噪音值',
  `compressor` varchar(255) NOT NULL COMMENT '压缩机',
  `refrigeration_mode` varchar(255) NOT NULL COMMENT '制冷方式',
  `cooling_mode` varchar(255) DEFAULT NULL COMMENT '散热方式',
  `refrigerant` varchar(255) NOT NULL COMMENT '制冷剂',
  `rated_voltage` varchar(255) NOT NULL COMMENT '额定电压/频率',
  `cold_temp_range` varchar(255) DEFAULT NULL COMMENT '冷藏温度范围',
  `freezing_temp_range` varchar(255) DEFAULT NULL COMMENT '冷冻温度范围',
  `variable_temp_range` varchar(255) DEFAULT NULL COMMENT '变温温度范围',
  `panel_technology` varchar(255) DEFAULT NULL COMMENT '面板工艺',
  `rack_num` varchar(255) DEFAULT NULL COMMENT '搁物架数量',
  `box_num` varchar(255) DEFAULT NULL COMMENT '果菜盒数量',
  `variable_drawer_num` varchar(255) DEFAULT NULL COMMENT '变温抽屉数量',
  `aquarius_num` varchar(255) DEFAULT NULL COMMENT '瓶座数量',
  `freezing_drawer_num` varchar(255) DEFAULT NULL COMMENT '冷冻抽屉数量',
  `foldble_rack_num` varchar(255) DEFAULT NULL COMMENT 'z折叠搁物架数量',
  `eggbox_num` varchar(255) DEFAULT NULL COMMENT '蛋盒数量',
  `wine_rack_num` varchar(255) DEFAULT NULL COMMENT '红酒架数量',
  `refrigerated_lamp` varchar(255) DEFAULT NULL COMMENT '冷藏灯',
  `bar_num` varchar(255) DEFAULT NULL COMMENT '吧台数量',
  `ice_machine_num` varchar(255) DEFAULT NULL COMMENT '制冰机数量',
  `quick_freezing` varchar(255) DEFAULT NULL COMMENT 's速冻功能',
  `display_mode` varchar(255) NOT NULL COMMENT 'x显示方式',
  `child_lock` varchar(255) NOT NULL COMMENT 't童锁功能',
  `holiday_function` varchar(255) DEFAULT NULL COMMENT '假日功能',
  `temp_control_mode` varchar(255) NOT NULL COMMENT 'k控温方式',
  `door_alarm` varchar(255) DEFAULT NULL COMMENT 'k开门报警',
  `features` varchar(255) DEFAULT NULL COMMENT 'g功能特色',
  `wifi` varchar(255) NOT NULL COMMENT 'wifi功能',
  `bactericidal_function` varchar(255) DEFAULT NULL COMMENT 's杀菌功能',
  `warranty_years` double(100,2) NOT NULL DEFAULT '0.00' COMMENT '报修年限',
  `deleted` int(11) DEFAULT '1' COMMENT '删除（1：启用，0：禁用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_info
-- ----------------------------

-- ----------------------------
-- Table structure for product_price
-- ----------------------------
DROP TABLE IF EXISTS `product_price`;
CREATE TABLE `product_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `model` varchar(255) NOT NULL COMMENT '型号',
  `price` double(100,2) NOT NULL COMMENT '价格',
  `distribution_channel` varchar(255) DEFAULT NULL COMMENT '销售渠道',
  `active_time` datetime NOT NULL COMMENT '上市时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '删除（1：启用，0：禁用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_price
-- ----------------------------

-- ----------------------------
-- Table structure for product_professional_parameters
-- ----------------------------
DROP TABLE IF EXISTS `product_professional_parameters`;
CREATE TABLE `product_professional_parameters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model` varchar(50) NOT NULL COMMENT '型号',
  `compression` varchar(50) DEFAULT NULL COMMENT '压缩机',
  `manufacturer_model` varchar(50) DEFAULT NULL COMMENT '厂家及型号',
  `displacement` varchar(50) DEFAULT NULL COMMENT 'p排量',
  `power` varchar(50) DEFAULT NULL COMMENT 'g功率',
  `cop` varchar(50) DEFAULT NULL COMMENT 'cop',
  `suction_pipe_specification` varchar(50) DEFAULT NULL COMMENT '吸气管规格（管外@径*壁厚）',
  `exhaust_pipe_specification` varchar(50) DEFAULT NULL COMMENT '排气管规格（管外@径*壁厚）',
  `process_pipe_specification` varchar(50) DEFAULT NULL COMMENT '工艺管规格（管外@径*壁厚）',
  `capacity` varchar(255) DEFAULT NULL COMMENT '电容',
  `refrigerating_output` varchar(255) DEFAULT NULL COMMENT 'z制冷量',
  `condenser_picture` varchar(255) DEFAULT NULL COMMENT '冷凝器图片',
  `condenser_type` varchar(255) DEFAULT NULL COMMENT 'l冷凝器形式',
  `condenser_heat_exchange` varchar(255) DEFAULT NULL COMMENT '换热管规格（材质*外径*长度）',
  `condenser_inside_vol` varchar(255) DEFAULT NULL COMMENT 'n内容积',
  `condenser_heat_transfer` varchar(255) DEFAULT NULL COMMENT 'c传热面积（外）',
  `condenser_dry_filter` varchar(255) DEFAULT NULL COMMENT '干燥过滤器规格（材质/外径*长度*壁厚）',
  `anticoagulant_heat_exchange` varchar(255) DEFAULT NULL COMMENT '换热管规格（材质/外径*长度*壁厚）',
  `anticoagulant_location` varchar(255) DEFAULT NULL COMMENT '防凝管位置',
  `muffler_picture` varchar(255) DEFAULT NULL COMMENT '回气管图片',
  `muffler_type` varchar(255) DEFAULT NULL COMMENT '回气管形式（锡焊/穿焊/铝箔贴附）',
  `muffler_heat_exchange` varchar(255) DEFAULT NULL,
  `muffler_capillary` varchar(255) DEFAULT NULL,
  `muffler_effictive_exchange_length` double DEFAULT NULL,
  `refrigerated_capillary_specification` varchar(255) DEFAULT NULL,
  `variable_capillary_specification` varchar(255) DEFAULT NULL,
  `freezing_capillary_specification` varchar(255) DEFAULT NULL,
  `refri_evaporator_picture` varchar(255) DEFAULT NULL,
  `refri_evaporator_type` varchar(255) DEFAULT NULL,
  `refri_evaporator_heat_exchange` varchar(255) DEFAULT NULL,
  `refri_evaporator_fin_specification` varchar(255) DEFAULT NULL,
  `refri_evaporator_fin_distance` varchar(255) DEFAULT NULL,
  `refri_evaporator_inside_vol` varchar(255) DEFAULT NULL,
  `refri_evaporator_heat_transfer` varchar(255) DEFAULT NULL,
  `refri_evaporator_reservoir` varchar(255) DEFAULT NULL,
  `refri_evaporator_sort_type` varchar(255) DEFAULT NULL,
  `refri_evaporator_pipe_distance` double DEFAULT NULL,
  `variable_evaporator_picture` varchar(255) DEFAULT NULL,
  `variable_evaporator_type` varchar(255) DEFAULT NULL,
  `variable_evaporator_heat_exchange` varchar(255) DEFAULT NULL,
  `variable_evaporator_fin` varchar(255) DEFAULT NULL,
  `variable_evaporator_fin_distance` double DEFAULT NULL,
  `variable_evaporator_inside_vol` varchar(255) DEFAULT NULL,
  `variable_evaporator_heat_transfer` varchar(255) DEFAULT NULL,
  `variable_evaporator_reservoir` varchar(255) DEFAULT NULL,
  `variable_evaporator_sort_type` varchar(255) DEFAULT NULL,
  `variable_evaporator_pipe_distance` double DEFAULT NULL,
  `freezing_evaporator_picture` varchar(255) DEFAULT NULL,
  `freezing_evaporator_type` varchar(255) DEFAULT NULL,
  `freezing_evaporator_heat_exchange` varchar(255) DEFAULT NULL,
  `freezing_evaporator_fin` varchar(255) DEFAULT NULL,
  `freezing_evaporator_fin_distance` varchar(255) DEFAULT NULL,
  `freezing_evaporator_inside_vol` varchar(255) DEFAULT NULL,
  `freezing_evaporator_heat_transfer` varchar(255) DEFAULT NULL,
  `freezing_evaporator_reservoir` varchar(255) DEFAULT NULL,
  `freezing_evaporator_sort_type` varchar(255) DEFAULT NULL,
  `freezing_evaporator_pipe_distance` double DEFAULT NULL,
  `exhaust_connection_pipe_picture` varchar(255) DEFAULT NULL,
  `exhaust_connection_heat_transfer` varchar(255) DEFAULT NULL,
  `variable_deforst_heater_picture` varchar(255) DEFAULT NULL,
  `variable_deforst_heater_power` varchar(255) DEFAULT NULL,
  `freezing_deforst_heater_picture` varchar(255) DEFAULT NULL,
  `freezing_deforst_heater_power` varchar(255) DEFAULT NULL,
  `refri_fan_picture` varchar(255) DEFAULT NULL,
  `refri_fan_manufacturer_model` varchar(255) DEFAULT NULL,
  `refri_fan_blowing_rate` varchar(255) DEFAULT NULL,
  `variable_fan_picture` varchar(255) DEFAULT NULL,
  `variable_fan_manufacturer_model` varchar(255) DEFAULT NULL,
  `variable_fan_blowing_rate` varchar(255) DEFAULT NULL,
  `freezing_fan_picture` varchar(255) DEFAULT NULL,
  `freezing_fan_manufacturer_model` varchar(255) DEFAULT NULL,
  `freezing_fan_blowing_rate` varchar(255) DEFAULT NULL,
  `foaming_thickness1` varchar(255) DEFAULT NULL COMMENT '冷藏箱顶部泡层',
  `foaming_thickness2` varchar(255) DEFAULT NULL COMMENT '冷藏左右泡层',
  `foaming_thickness3` varchar(255) DEFAULT NULL COMMENT '冷藏背部泡层',
  `foaming_thickness4` varchar(255) DEFAULT NULL,
  `foaming_thickness5` varchar(255) DEFAULT NULL,
  `foaming_thickness6` varchar(255) DEFAULT NULL,
  `foaming_thickness7` varchar(255) DEFAULT NULL,
  `foaming_thickness8` varchar(255) DEFAULT NULL,
  `foaming_thickness9` varchar(255) DEFAULT NULL,
  `foaming_thickness10` varchar(255) DEFAULT NULL,
  `foaming_thickness11` varchar(255) DEFAULT NULL,
  `foaming_thickness12` varchar(255) DEFAULT NULL,
  `foaming_thickness13` varchar(255) DEFAULT NULL,
  `appearance_distance1` varchar(255) DEFAULT NULL COMMENT '箱体与门体间隙',
  `appearance_distance2` varchar(255) DEFAULT NULL COMMENT '门体与门体间隙',
  `appearance_distance3` varchar(255) DEFAULT NULL COMMENT '铰链盖与箱体间隙',
  `deleted` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`model`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_professional_parameters
-- ----------------------------

-- ----------------------------
-- Table structure for product_rate_weight
-- ----------------------------
DROP TABLE IF EXISTS `product_rate_weight`;
CREATE TABLE `product_rate_weight` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level_weight` double DEFAULT NULL COMMENT '冰箱能效等级',
  `noise_weight` double DEFAULT NULL COMMENT '冰箱噪音值',
  `refri_weight` double DEFAULT NULL COMMENT 'b冰箱冷冻能力',
  `total_vol_weight` double DEFAULT NULL COMMENT 'b冰箱容积率',
  `climate_weight` double DEFAULT NULL COMMENT 'b冰箱气候类型',
  `price_weight` double DEFAULT NULL COMMENT 'b冰箱价格',
  `warrant_year_weight` double DEFAULT NULL COMMENT 'b冰箱保修年限',
  `profiling_weight` double DEFAULT NULL COMMENT 'z造型评价',
  `color_weight` double DEFAULT NULL COMMENT 's色彩评价',
  `interior_weight` double DEFAULT NULL COMMENT '内饰精致性',
  `light_weight` double DEFAULT NULL COMMENT 'd灯光',
  `ergonomics_weight` double DEFAULT NULL COMMENT '人体工学',
  `storage_weight` double DEFAULT NULL COMMENT 'c存储分区',
  `operation_weight` double DEFAULT NULL COMMENT 'c操作易用性',
  `performance_weight` double DEFAULT NULL COMMENT 'x性能指标',
  `economy_weight` double DEFAULT NULL COMMENT 'j经济指标',
  `aesthetic_index_weight` double DEFAULT NULL COMMENT 's审美指标',
  `ease_weight` double DEFAULT NULL COMMENT '使用便捷性指标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_rate_weight
-- ----------------------------
INSERT INTO `product_rate_weight` VALUES ('1', '0', '0', '0', '0', '0.2', '0', '0', '0', '0.3', '0', '0', '0.1', '0.5', '0', '0', '0.1', '0.1', '0.1');

-- ----------------------------
-- Table structure for product_rating
-- ----------------------------
DROP TABLE IF EXISTS `product_rating`;
CREATE TABLE `product_rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `mid` int(11) NOT NULL COMMENT '型号id',
  `profiling` double(100,2) NOT NULL COMMENT '造型评价',
  `color` double(100,2) NOT NULL COMMENT '色彩评价',
  `interior` double(100,2) NOT NULL COMMENT 'n内饰精致性评价',
  `light` double(100,2) NOT NULL COMMENT '灯光评价',
  `ergonomics` double(100,2) NOT NULL COMMENT 'r人体工学评价',
  `storage` double(100,2) NOT NULL COMMENT '存储分区评价',
  `operation` double(100,2) NOT NULL COMMENT '操作易用性评价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_rating
-- ----------------------------

-- ----------------------------
-- Table structure for product_reminder
-- ----------------------------
DROP TABLE IF EXISTS `product_reminder`;
CREATE TABLE `product_reminder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_reminder
-- ----------------------------

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `account` int(255) DEFAULT NULL COMMENT '邮箱',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `page_name` varchar(255) DEFAULT NULL COMMENT '访问页面',
  `request_url` varchar(255) DEFAULT NULL COMMENT '请求url',
  `request_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '请求的功能名称',
  `request_url` varchar(255) DEFAULT NULL COMMENT '请求url',
  `picture_url` varchar(255) DEFAULT NULL COMMENT '图片url',
  `parent_id` int(11) NOT NULL COMMENT '父节点id',
  `type` varchar(10) NOT NULL DEFAULT '2' COMMENT '类型（1：页面，2：操作）',
  `deleted` int(11) NOT NULL DEFAULT '1' COMMENT '删除标记（1：启用，0：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '首页', '/home', null, '0', '1', '0');
INSERT INTO `t_menu` VALUES ('2', '首页待确认数据', '/product/getReptileIndexList', null, '7', '2', '0');
INSERT INTO `t_menu` VALUES ('3', '首页最新数据', '/product/infoIndexList', null, '6', '2', '0');
INSERT INTO `t_menu` VALUES ('4', '首页统计图', '/product/infoListIndexData', null, '1', '2', '0');
INSERT INTO `t_menu` VALUES ('5', '数据管理', null, null, '0', '1', '0');
INSERT INTO `t_menu` VALUES ('6', '数据维护', '/dataMaintain', null, '5', '1', '0');
INSERT INTO `t_menu` VALUES ('7', '数据确认', '/dataConfirm', null, '5', '1', '0');
INSERT INTO `t_menu` VALUES ('8', '获取筛选器', '/product/getFilter', null, '18', '2', '0');
INSERT INTO `t_menu` VALUES ('9', '录入基础表数据', '/data/infoInsert', null, '6', '2', '0');
INSERT INTO `t_menu` VALUES ('10', '更新基础表数据', '/data/infoUpdate', null, '6', '2', '0');
INSERT INTO `t_menu` VALUES ('11', '插入专业表数据', '/data/profParamInsert', null, '6', '2', '0');
INSERT INTO `t_menu` VALUES ('12', '更新专业表数据', '/data/profParamUpdate', null, '6', '2', '0');
INSERT INTO `t_menu` VALUES ('13', 'excel更新基础表数据', '/data/upload/', null, '6', '2', '0');
INSERT INTO `t_menu` VALUES ('14', 'excel模板下载', '/data/download/', null, '6', '2', '0');
INSERT INTO `t_menu` VALUES ('15', '删除产品', '/data/deleteProduct', null, '6', '2', '0');
INSERT INTO `t_menu` VALUES ('16', '更新爬虫基础表', '/data/updateReptileData', null, '7', '2', '0');
INSERT INTO `t_menu` VALUES ('17', '确认爬虫表数据', '/data/ensureReptileData', null, '7', '2', '0');
INSERT INTO `t_menu` VALUES ('18', '数据展示', '/dataShow', null, '0', '1', '0');
INSERT INTO `t_menu` VALUES ('19', '获取产品列表', '/product/infoList', null, '18', '2', '0');
INSERT INTO `t_menu` VALUES ('20', '获取产品详细信息', '/product/productDetail', null, '18', '2', '0');
INSERT INTO `t_menu` VALUES ('21', '获取爬虫表信息', '/product/reptileDetail', null, '7', '2', '0');
INSERT INTO `t_menu` VALUES ('22', '展示页统计数据图', '/product/infoListTotalData', null, '18', '2', '0');
INSERT INTO `t_menu` VALUES ('23', '获取对比信息', '/product/productContrast', null, '18', '2', '0');
INSERT INTO `t_menu` VALUES ('24', '产品评价', '/rating/productRate', null, '18', '2', '0');
INSERT INTO `t_menu` VALUES ('25', '图片上传', '/picture/upload', null, '6', '2', '0');
INSERT INTO `t_menu` VALUES ('26', '校验机型存在', '/product/judgeModel', null, '1', '2', '0');
INSERT INTO `t_menu` VALUES ('27', '系统管理', null, null, '0', '1', '0');
INSERT INTO `t_menu` VALUES ('28', '权重管理', '/weight', null, '27', '1', '0');
INSERT INTO `t_menu` VALUES ('29', '角色管理', '/roles', null, '27', '1', '0');
INSERT INTO `t_menu` VALUES ('30', '用户管理', '/groups', null, '27', '1', '0');
INSERT INTO `t_menu` VALUES ('31', '添加用户', '/user/addUser', null, '30', '2', '0');
INSERT INTO `t_menu` VALUES ('32', '修改用户', '/user/modifyUser', null, '30', '2', '0');
INSERT INTO `t_menu` VALUES ('33', '删除用户', '/user/deleteUser', null, '30', '2', '0');
INSERT INTO `t_menu` VALUES ('34', '获取所有用户列表', '/user/getAllUser', null, '30', '2', '0');
INSERT INTO `t_menu` VALUES ('35', '获取当前用户的权限树', '/user/getMenuTree', null, '1', '2', '0');
INSERT INTO `t_menu` VALUES ('36', '根据用户姓名或者邮箱查询用户', '/user/getSearchUser', null, '30', '2', '0');
INSERT INTO `t_menu` VALUES ('37', '获取当前用户的角色', '/user/getUserRole', null, '1', '2', '0');
INSERT INTO `t_menu` VALUES ('38', '获取当前用户的角色', '/user/getUserRole', null, '1', '2', '0');
INSERT INTO `t_menu` VALUES ('39', '修改用户对应的角色', '/user/modifyUserRole', null, '30', '2', '0');
INSERT INTO `t_menu` VALUES ('40', '新增角色及权限', '/role/addRole', null, '30', '2', '0');
INSERT INTO `t_menu` VALUES ('41', '删除角色', '/role/delRole', null, '30', '2', '0');
INSERT INTO `t_menu` VALUES ('42', '获取所有的角色列表', '/role/getAllRoles', null, '30', '2', '0');
INSERT INTO `t_menu` VALUES ('43', '获取角色拥有的权限树', '/role/getRoleMenuTree', null, '30', '2', '0');
INSERT INTO `t_menu` VALUES ('44', '修改角色及权限', '/role/modifyRole', null, '30', '2', '0');
INSERT INTO `t_menu` VALUES ('45', '获取当前用户信息', '/user/getCurrentUser', null, '1', '2', '0');
INSERT INTO `t_menu` VALUES ('46', '数据维护页面产品列表', '/product/infoMaintainList', null, '6', '2', '0');
INSERT INTO `t_menu` VALUES ('47', '待确认页面获取产品列表', '/product/getReptileList', null, '7', '2', '0');
INSERT INTO `t_menu` VALUES ('48', '设置计算得分的权重', '/rating/setWeight', null, '28', '2', '0');
INSERT INTO `t_menu` VALUES ('49', '获取得分的权重', '/rating/getWeight', null, '28', '2', '0');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` int(11) NOT NULL DEFAULT '1' COMMENT '启用标志(1：启用，0：禁用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', '2020-09-30 10:41:06', '1');
INSERT INTO `t_role` VALUES ('2', '111', '2020-10-08 17:01:09', '1');
INSERT INTO `t_role` VALUES ('3', '111', '2020-10-08 17:00:54', '1');
INSERT INTO `t_role` VALUES ('4', 'rrr', '2020-10-08 16:41:54', '1');
INSERT INTO `t_role` VALUES ('5', null, '2020-10-08 17:03:22', '0');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) DEFAULT NULL,
  `m_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) DEFAULT NULL COMMENT '邮箱账户',
  `user_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `department` varchar(255) DEFAULT NULL COMMENT '部门',
  `company` varchar(255) DEFAULT NULL COMMENT '公司',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` int(11) NOT NULL DEFAULT '1' COMMENT '是否启用（1启用，0停用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '46461', '456', 'string', 'gwerg', '2020-09-30 10:57:24', '1');
INSERT INTO `t_user` VALUES ('2', '262627', '李四', '企管部', 'xx科技', '2020-09-30 10:47:57', '1');
INSERT INTO `t_user` VALUES ('3', '262628', '王五', '财务部', 'xx科技', '2020-09-30 10:48:16', '1');
INSERT INTO `t_user` VALUES ('4', '262629', '赵六', '财务部', 'xx科技', '2020-09-30 10:48:36', '1');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) DEFAULT NULL,
  `r_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('2', '2', '1');
INSERT INTO `t_user_role` VALUES ('3', '3', '1');
INSERT INTO `t_user_role` VALUES ('4', '4', '1');
INSERT INTO `t_user_role` VALUES ('14', '1', '1');
