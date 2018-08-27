package com.search.common.base.web.poi;


import com.search.common.base.core.bean.BaseBean;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

public class CellBean extends BaseBean {
    private static final long serialVersionUID = -9129622925443897745L;
    private Boolean merge;      // 是否合并单元格
    private Integer occupyRow;    // 合并所占行数
    private Integer occupyColumn; // 合并所占列数
    private String content;      // 内容
    private Integer firstColumn;  // 起始列
    private Integer lastColumn;   // 结束列
    private Integer firstRow;     // 起始行
    private Integer lastRow;      // 结束行
    private Float columnWidth;  // 列宽
    private Float rowHeight;    // 行高

    public CellBean(Boolean merge, Integer occupyRow, Integer occupyColumn) {
        this.merge = merge;
        this.occupyRow = occupyRow;
        this.occupyColumn = occupyColumn;
    }

    public Boolean getMerge() {
        return merge;
    }

    public void setMerge(Boolean merge) {
        this.merge = merge;
    }

    public Integer getOccupyRow() {
        return occupyRow;
    }

    public void setOccupyRow(Integer occupyRow) {
        this.occupyRow = occupyRow;
    }

    public Integer getOccupyColumn() {
        return occupyColumn;
    }

    public void setOccupyColumn(Integer occupyColumn) {
        this.occupyColumn = occupyColumn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFirstColumn() {
        return firstColumn;
    }

    public void setFirstColumn(Integer firstColumn) {
        this.firstColumn = firstColumn;
    }

    public Integer getLastColumn() {
        return lastColumn;
    }

    public void setLastColumn(Integer lastColumn) {
        this.lastColumn = lastColumn;
    }

    public Integer getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(Integer firstRow) {
        this.firstRow = firstRow;
    }

    public Integer getLastRow() {
        return lastRow;
    }

    public void setLastRow(Integer lastRow) {
        this.lastRow = lastRow;
    }

    public Float getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(Float columnWidth) {
        this.columnWidth = columnWidth;
    }

    public Float getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(Float rowHeight) {
        this.rowHeight = rowHeight;
    }
}
