package com.search.common.base.web.poi;

import com.search.common.base.core.utils.Guava;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by heyanjing on 2018/6/19 18:32.
 */
@Slf4j
public class ExcelTest {
    public static Object[] head = {"序号", "单位名称", "公司联系人", "项目负责人及其联系方式", "项目成员及其联系方式"};
    public static String reg = "([\\u4e00-\\u9fa5]+)|([^\\u4e00-\\u9fa5]+)";

    @Test
    public void test() throws Exception {

        File file = new File("C:/供应商.xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = Excel.getWorkbook(inputStream, file.getName());
        if (workbook != null) {
            int titleRowNum = 1;
            Sheet sheet = workbook.getSheetAt(0);
            int realRowNumber = Excel.getRealRowNumber(sheet);
            Row row = sheet.getRow(titleRowNum);
            if (Excel.matchHead(head, row)) {
                List<ContainerBean> dataList = this.getDataList(sheet, titleRowNum + 1, realRowNumber);
                log.info("{}", dataList);
            }
        }
    }

    private List<ContainerBean> getDataList(Sheet sheet, int dataStartRow, int dataEndRow) {
        //List<List<Object>> cellValuesList = Guava.newArrayList();
        // 所有合并的单元格
        List<CellRangeAddress> mergeCells = Excel.getMergeCells(sheet);
        // 对合并单元格进行排序
        mergeCells.sort((o1, o2) -> {
            int fr1 = o1.getFirstRow(), fc1 = o1.getFirstColumn(), fr2 = o2.getFirstRow(), fc2 = o2.getFirstColumn();
            return fr1 == fr2 ? fc1 - fc2 : fr1 - fr2;
        });
        List<ContainerBean> list = Guava.newArrayList();

        int newRow = dataStartRow;
        boolean nature = true;//自然过渡下一行
        boolean primaryFinished = false;//主表信息是否已读取
        boolean existMerge = false;//默认不存在合并
        ContainerBean containerBean = null;
        for (int i = dataStartRow; i <= dataEndRow; i++) {
            PrimaryBean primaryBean = null;
            SecondaryBean secondaryBean = new SecondaryBean();
            Row rowi = sheet.getRow(i);
            short colNum = rowi.getLastCellNum();// 每行的列数
            for (int j = 0; j < colNum; j++) {
                if (!nature && primaryFinished && (j == 0 || j == 1 || j == 2)) {
                    nature = true;
                    primaryFinished = false;
                    existMerge = false;
                    continue;
                }
                Cell cell = rowi.getCell(j);
                if (cell != null) {
                    CellBean cellPro = Excel.getCompleteCellBean(mergeCells, cell);// 获取cell完整的信息
                    String content = cellPro.getContent();
                    if (j == 1 && StringUtils.isBlank(content)) {
                        break;
                    }
                    if (cellPro.getMerge()) {
                        newRow = cellPro.getLastRow();
                        nature = false;
                        existMerge = true;
                        if (primaryBean == null) {
                            primaryBean = new PrimaryBean();
                        }
                        setPrimaryBean(primaryBean, j, content);
                    } else {
                        if (existMerge) {
                            primaryFinished = true;
                        }
                        if (!primaryFinished && primaryBean == null) {
                            primaryBean = new PrimaryBean();
                        }
                        setPrimaryBean(primaryBean, j, content);
                        if (j == 3) {
                            primaryFinished = true;
                            secondaryBean.setProjectcontactor(content);
                        } else if (j == 4) {
                            secondaryBean.setProjectmember(content);
                        }
                    }

                }
            }

            if (nature) {
                newRow = i + 1;
            }
            if (primaryBean != null) {
                containerBean = new ContainerBean();
                containerBean.setPrimaryBean(primaryBean);
                if (!StringUtils.isBlank(primaryBean.getSuppliername())) {
                    list.add(containerBean);
                }
            }
            containerBean.getSecondaryBeanList().add(secondaryBean);
            log.info("主表:{}---吃表:{}", primaryBean, secondaryBean);
            if (i == (newRow - 1)) {
                log.error("{}", "新行");
                nature = true;
                primaryFinished = false;
                existMerge = false;
            }

        }
        log.warn("{}", Guava.toJson(list));
        return list;
    }

    private void setPrimaryBean(PrimaryBean primaryBean, int j, String content) {
        if (j == 0) {
            primaryBean.setSuppliernum(content);
        } else if (j == 1) {
            primaryBean.setSuppliername(content);
        } else if (j == 2) {
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(content);
            for (int k = 0; k < 2; k++) {
                if (m.find()) {
                    String group = m.group();
                    if (k == 0) {
                        primaryBean.setContactuser(group);
                    } else {
                        primaryBean.setContactphone(group);
                    }
                }
            }
        }
    }
}