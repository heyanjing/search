package com.search.common.base.web.poi;

import com.alibaba.fastjson.JSON;
import com.search.common.base.core.bean.Result;
import com.search.common.base.core.utils.Guava;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by heyanjing on 2017/12/4 10:20.
 */
public class Excel {
    private static final Logger log = LoggerFactory.getLogger(Excel.class);

//    **********************************************************************************************************************************
//    ***********************************// // HEINFO: 2018/6/19 17:16 导出 2007的excel**********************************************************************************
//    **********************************************************************************************************************************

    /**
     * web的导出功能
     *
     * @param fileName  导出文件名
     * @param sheetname sheet名称
     * @param dataList  写入excel的数据
     * @param titleList 标题与标题对应的属性名称的对象集合
     * @param response  response
     * @param request   request
     * @throws Exception Exception
     */
    public static void exportExcel2007(String fileName, String sheetname, List<?> dataList, List<Title> titleList, HttpServletResponse response, HttpServletRequest request) throws Exception {
        Workbook xssfWorkbook = Excel.getExprotXSSFWorkbook(sheetname, dataList, titleList);
        response.reset();
        response.setContentType("application/msexcel;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=\"" + new String((fileName + ".xlsx").getBytes("GB2312"), "ISO8859-1") + "\"");
        OutputStream out = response.getOutputStream();
        xssfWorkbook.write(out);
        out.flush();
        out.close();

    }

    /**
     * 获取导出需要的
     *
     * @param sheetname sheet名称
     * @param dataList  写入excel的数据
     * @param titleList 标题与标题对应的属性名称的对象集合
     * @return XSSFWorkbook
     */
    @SuppressWarnings("unchecked")
    public static Workbook getExprotXSSFWorkbook(String sheetname, List<?> dataList, List<Title> titleList) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFSheet sheet = workbook.createSheet(sheetname);
        sheet.setDefaultColumnWidth(20);
        XSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            XSSFCell cell = titleRow.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(titleList.get(i).getTitle());
        }
        for (int i = 0; i < dataList.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            Object data = dataList.get(i);
            Map<String, String> map = null;
            if (data instanceof Map) {
                map = convertMap((Map<String, Object>) data);
            } else {
                map = getFieldValueMap(data);
            }
            for (int j = 0; j < titleList.size(); j++) {
                row.createCell(j).setCellValue(map.get(titleList.get(j).getProp()));
            }
        }
        return workbook;
    }

    public static Map<String, String> convertMap(Map<String, Object> map) {
        return convertMap(map, true);
    }

    /**
     * @param map
     * @param isDate
     * @return 将原有map的值进行格式化处理
     */
    public static Map<String, String> convertMap(Map<String, Object> map, Boolean isDate) {
        Map<String, String> result = new HashMap<>();
        map.forEach((key, val) -> {
            String strVal = null;
            if (val instanceof Date) {
                DateFormat dateFormat = isDate ? DateFormat.getDateInstance() : DateFormat.getDateTimeInstance();
                strVal = dateFormat.format(val);
            } else if (val instanceof Double) {
                strVal = new DecimalFormat("0.00").format(val);
            } else {
                if (null != val && !val.equals("")) {
                    strVal = String.valueOf(val);
                } else {
                    strVal = "-";
                }
            }
            result.put(key, strVal);
        });

        return result;
    }

    /**
     * @param bean   实例对象
     * @param isDate 是否格式化成2017-09-12
     *               2017-09-12 12:12:12
     *               默认2017-09-12
     * @return 对象字段 对应的值的map集合
     */
    public static Map<String, String> getFieldValueMap(Object bean, Boolean isDate) {
        Class<?> cls = bean.getClass();
        Map<String, String> valueMap = new HashMap<>();
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            try {
                String fieldType = field.getType().getSimpleName();
                String fieldGetName = Excel.parGetName(field.getName());
                Method fieldGetMet = null;
                for (Method met : methods) {
                    if (met.getName().equals(fieldGetName)) {
                        fieldGetMet = met;
                        break;
                    }
                }
                if (fieldGetMet != null) {
                    Object fieldVal = fieldGetMet.invoke(bean);
                    String result = null;
                    if ("Date".equals(fieldType)) {
                        DateFormat dateFormat = isDate ? DateFormat.getDateInstance() : DateFormat.getDateTimeInstance();
                        result = dateFormat.format(fieldVal);
                    } else if ("Double".equals(fieldType)) {
                        result = new DecimalFormat("0.00").format(fieldVal);
                    } else if ("LocalDateTime".equals(fieldType)) {
                        result = ((LocalDateTime) fieldVal).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                    } else if ("LocalDate".equals(fieldType)) {
                        result = ((LocalDate) fieldVal).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    } else {
                        if (null != fieldVal && !fieldVal.equals("")) {
                            result = String.valueOf(fieldVal);
                        } else {
                            result = "-";
                        }
                    }
                    valueMap.put(field.getName(), result);
                }
            } catch (Exception e) {
                log.info("{}", e);
            }
        }
        return valueMap;
    }

    public static Map<String, String> getFieldValueMap(Object bean) {
        return getFieldValueMap(bean, true);
    }

    /**
     * @param fieldName
     * @return 字段对应的get方法名
     */
    public static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_') {
            startIndex = 1;
        }
        return "get" + fieldName.substring(startIndex, startIndex + 1).toUpperCase() + fieldName.substring(startIndex + 1);
    }

    //**********************************************************************************************************************************
//******************************************// // HEINFO: 2018/6/19 17:16  导入 2003 或2007的excel*************************************************************************
//**********************************************************************************************************************************

    /**
     * @param inputStream excel的输入流
     * @param name        文件名
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inputStream, String name) throws Exception {
        Workbook wb = null;
        if (name.endsWith(".xls")) {
            wb = new HSSFWorkbook(new POIFSFileSystem(inputStream));
        } else if (name.endsWith(".xlsx")) {
            wb = new XSSFWorkbook(OPCPackage.open(inputStream));
        }
        return wb;
    }

    /**
     * @param head 表头
     * @param row  表头对应的行
     * @return 表头是否匹配
     */
    public static boolean matchHead(Object[] head, Row row) {
        List<Object> headCellValues = getCellValues(row);
        //region Description
        log.info("{}", Arrays.asList(head));
        log.info("{}", headCellValues);
        log.info("{}", Arrays.equals(head, headCellValues.toArray()));
        //endregion
        if (Arrays.equals(head, headCellValues.toArray())) {
            return true;
        }
        return false;
    }

    /**
     * @param inputStream excel的输入流
     * @param name        文件名
     * @param head        excel的表头
     * @param headRow     表头所在行，从0开始
     * @param existMerge  数据是否存在合并单元格中
     * @return Result 的data中存放读取的数据List<Object> headCellValues
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static Result getWorkbookResult(InputStream inputStream, String name, Object[] head, int headRow, boolean existMerge) throws Exception {
//        existMerge=false;
        Result result = Result.success();
        Workbook wb = getWorkbook(inputStream, name);
        if (wb == null) {
            return Result.failure();
        }
        Sheet sheet = wb.getSheetAt(0);
        int realRowNumber = getRealRowNumber(sheet);
        Row row = sheet.getRow(headRow);
        if (matchHead(head, row)) {
            List<List<Object>> cellValuesList = getCellValuesList(sheet, headRow + 1, realRowNumber);
            //["CL2017000001","通用设备","轿车","轿车","2017-11-30","2017年9月20号、11月31号","2017-11-30 00:00:00","原值","1","0.00","198,000.00","新购","在用","自用","","","","","0.000000","重庆市城市建设综合开发管理办公室","2017-12-14 10:18:00","SVW71810BU","","不提折旧","平均年限法","0.000","198,000.00","0","","领导实物用车","","","","LSVD76A43HN117083","上海","121555"]
            log.info(JSON.toJSONString(cellValuesList));
            result.setResult(cellValuesList);
            return result;
        } else {
            return Result.failure("请下载标准模板");
        }
    }

    public static Result getWorkbookResult(InputStream inputStream, String name, Object[] head, int headRow) throws Exception {
        return getWorkbookResult(inputStream, name, head, headRow, false);
    }

    /**
     * @param sheet
     * @param realRowNumber
     * @return 获取整个sheet的数据
     */
    public static List<List<Object>> getCellValuesList(Sheet sheet, int dataStartRow, int dataEndRow) {
        List<List<Object>> cellValuesList = Guava.newArrayList();
        for (int i = dataStartRow; i <= dataEndRow; i++) {
            Row rowi = sheet.getRow(i);
            List<Object> cellValues = getCellValues(rowi);
            log.debug("****************");
            cellValuesList.add(cellValues);
        }
        return cellValuesList;
    }


    /**
     * @param sheet
     * @return 移除空白行，获取真实行数
     */
    public static int getRealRowNumber(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i < lastRowNum; i++) {
            Row row1 = sheet.getRow(i);
            if (row1 == null) {
                sheet.shiftRows(i + 1, lastRowNum, -1);
                lastRowNum = sheet.getLastRowNum();
                i = i - 1;
            } else {
                Boolean isRemove = true;//默认移除该行
                for (Cell cell : row1) {
                    if (cell.getCellTypeEnum() != CellType.BLANK) {
                        isRemove = false;
                        break;
                    }
                }
                if (isRemove) {
                    if (i == sheet.getLastRowNum()) {
                        sheet.removeRow(row1);
                    } else {
                        sheet.shiftRows(i + 1, lastRowNum, -1);
                        lastRowNum = sheet.getLastRowNum();
                        i = i - 1;
                    }
                }
            }
        }
        return sheet.getLastRowNum();
    }

    /**
     * @param row
     * @return 获取一行的数据
     */
    public static List<Object> getCellValues(Row row) {
        List<Object> list = Guava.newArrayList();
        for (Cell cell : row) {
            Object cellValue = getCellValue(cell);
            list.add(cellValue);
        }
        return list;
    }

    /**
     * @param cell
     * @return 获取一个单元格的值
     */
    public static Object getCellValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            CellType cellType = cell.getCellTypeEnum();
//            log.debug("{}", cellType);
            //region Description
            switch (cellType) {
                case STRING:
                    cellValue = cell.getStringCellValue().trim();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue();
                    } else {
                        cellValue = cell.getNumericCellValue();
                        if(cellValue!=null){
                            String content = cellValue.toString();
                            if(content.endsWith(".0")){
                                cellValue=content.substring(0,content.length()-2);
                            }
                            if(content.endsWith(".00")){
                                cellValue=content.substring(0,content.length()-3);
                            }
                        }
                    }
                    break;
                case BOOLEAN:
                    cellValue = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    cell.getCellFormula();
                    break;
                case BLANK:
                    cellValue = "";
                    break;
                case ERROR:
                    cellValue = "";
                    break;
                case _NONE:
                    cellValue = "";
                    break;
                default:
                    cellValue = "";
                    break;
            }
            //endregion
        } else {
            cellValue = "";
        }
        return cellValue;
    }
//// HEINFO: 2018/6/19 17:55 存在合并单元格的导入
//
//    public static List<ContainerBean> getCellValuesList2(Sheet sheet, int dataStartRow, int dataEndRow) {
//        List<List<Object>> cellValuesList = Guava.newArrayList();
//        // 所有合并的单元格
//        List<CellRangeAddress> mergeCells = getMergeCells(sheet);
//        //所有合并的单元格的封装集合
//        List<CellBean> cellPros = Guava.newArrayList();
//        // 对合并单元格进行排序
//        mergeCells.sort((o1, o2) -> {
//            int fr1 = o1.getFirstRow(), fc1 = o1.getFirstColumn(), fr2 = o2.getFirstRow(), fc2 = o2.getFirstColumn();
//            return fr1 == fr2 ? fc1 - fc2 : fr1 - fr2;
//        });
//        List<ContainerBean> list = Guava.newArrayList();
//
//        int newRow = dataStartRow;
//        boolean nature = true;//自然过渡下一行
//        boolean primaryFinished = false;//主表信息是否已读取
//        ContainerBean containerBean = null;
//        for (int i = dataStartRow; i < dataEndRow; i++) {
////            log.warn("第{}行", i);
//
//            PrimaryBean primaryBean = null;
//            SecondaryBean secondaryBean = new SecondaryBean();
//            Row rowi = sheet.getRow(i);
//            short colNum = rowi.getLastCellNum();// 每行的列数
//            for (int j = 0; j < colNum; j++) {
//                if (!nature && primaryFinished && (j == 0 || j == 1 || j == 2)) {
//                    continue;
//                }
//                Cell cell = rowi.getCell(j);
//                if (cell != null) {
//                    CellBean cellPro = getCompleteCellBean(mergeCells, cell);// 获取cell完整的信息
//                    String content = cellPro.getContent();
//                    if (cellPro.getMerge()) {
//                        if (primaryBean == null) {
//                            primaryBean = new PrimaryBean();
//                        }
//                        newRow = cellPro.getLastRow();
//                        nature = false;
//                        if (j == 1) {
//                            primaryBean.setSuppliername(content);
//                        } else if (j == 2) {
//                            Pattern p = Pattern.compile("([\\u4e00-\\u9fa5]+)|([^\\u4e00-\\u9fa5]+)");
//                            Matcher m = p.matcher(content);
//                            for (int k = 0; k < 2; k++) {
//                                if (m.find()) {
//                                    String group = m.group();
////                                    log.info("{}", group);
//                                    if (k == 0) {
//                                        primaryBean.setContactuser(group);
//                                    } else {
//                                        primaryBean.setContactphone(group);
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        if (!primaryFinished) {
////                            log.warn("{}", "主表信息已完");
//                        }
//                        primaryFinished = true;
//
//                        if (j == 3) {
//                            secondaryBean.setProjectcontactor(content);
//                        } else if (j == 4) {
//                            secondaryBean.setProjectmember(content);
//                        }
//                    }
////                    log.info("{}", cellPro);
//                }
//            }
//
//            if (nature) {
//                newRow = i + 1;
//            }
//            if (primaryBean != null) {
//                containerBean = new ContainerBean();
//                list.add(containerBean);
//                containerBean.setPrimaryBean(primaryBean);
//            }
//            containerBean.getSecondaryBeanList().add(secondaryBean);
//            log.info("主表:{}---吃表:{}", primaryBean, secondaryBean);
//
//
//            if (i == (newRow - 1)) {
//                log.error("{}", "新行");
//                nature = true;
//                primaryFinished = false;
//            }
//
//        }
//        log.warn("{}",Guava.toJson(list));
//        return list;
//    }

    /**
     * 获取sheet中所有的合并单元格
     */
    public static List<CellRangeAddress> getMergeCells(Sheet sheet) {
        List<CellRangeAddress> list = Guava.newArrayList();
        int sheetmergerCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetmergerCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
        return list;
    }

    /**
     * 判读cell是否在合并单元格中； 默认不在单元格中，只占一行一列 ，如果是合并单元格设置所占的行数、列数
     */
    private static CellBean isMergeCell(List<CellRangeAddress> mergeCells, Cell cell) {
        CellBean cellPro = new CellBean(false, 1, 1);
        int firstC = 0, lastC = 0, firstR = 0, lastR = 0;
        for (CellRangeAddress ca : mergeCells) {
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if (cell.getColumnIndex() <= lastC && cell.getColumnIndex() >= firstC && cell.getRowIndex() <= lastR && cell.getRowIndex() >= firstR) {
                cellPro.setMerge(true);
                cellPro.setOccupyColumn(lastC - firstC + 1);
                cellPro.setOccupyRow(lastR - firstR + 1);
                cellPro.setFirstColumn(firstC + 1);
                cellPro.setLastColumn(lastC + 1);
                cellPro.setFirstRow(firstR + 1);
                cellPro.setLastRow(lastR + 1);
                return cellPro;
            }
        }
        return cellPro;
    }

    /**
     * 给cellBean设置相关属性从而获取一个完整的单元格信息
     */
    public static CellBean getCompleteCellBean(List<CellRangeAddress> mergeCells, Cell cell) {
        return getCompleteCellBean(mergeCells, cell, null, null);
    }

    public static CellBean getCompleteCellBean(List<CellRangeAddress> mergeCells, Cell cell, Float rowHeight, Float columnWidth) {
        CellBean cellPro = isMergeCell(mergeCells, cell);
        cellPro.setColumnWidth(columnWidth);
        cellPro.setRowHeight(rowHeight);
        Object cellValue = getCellValue(cell);
        if (cellValue != null) {
            cellPro.setContent(cellValue.toString().replaceAll(" ", ""));
        }
        return cellPro;
    }

    /**
     * 判断cellPro是否在合并单元格集合中
     *
     * @param cellPros 合并单元格集合
     * @param cellPro  当前单元格
     * @return 返回合并单元格中的有效值
     */
    public static CellBean isInMergeList(List<CellBean> cellPros, CellBean cellPro) {
        Boolean flag = false;
        int index = 0;
        CellBean temp = null;
        for (int i = 0; i < cellPros.size(); i++) {
            CellBean cp = cellPros.get(i);
            if (cp.getFirstRow() <= cellPro.getFirstRow() && cp.getLastRow() >= cellPro.getLastRow() && cp.getFirstColumn() <= cellPro.getFirstColumn() && cellPro.getLastColumn() <= cp.getLastColumn()) {
                flag = true;
                index = i;
                temp = cp;
                break;
            }
        }
        if (flag) {
            //当前cell在合并单元格中，他的内容不为空，而之前的单元格中的内容为空则替换之前的单元格
            if (StringUtils.isNotBlank(cellPro.getContent()) && StringUtils.isBlank(temp.getContent())) {
                cellPros.remove(index);
                cellPros.add(index, cellPro);
                temp = cellPro;
            }
        } else {
            // 不在合并单元格集合中，则添加到集合中
            cellPros.add(cellPro);
        }
        return temp;
    }

}
