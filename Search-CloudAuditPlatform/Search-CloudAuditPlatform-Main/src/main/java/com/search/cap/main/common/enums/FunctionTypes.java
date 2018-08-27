package com.search.cap.main.common.enums;

/**
 * Created by heyanjing on 2018/3/6 14:39.
 * 功能类型
 */
public enum FunctionTypes {
    /**
     * 分类
     */
    CLASSIFY("分类", 1),
    /**
     * 模块
     */
    MODULE("模块", 2),
    /**
     * 节点
     */
    NODE("节点", 3),
    /**
     * 标签
     */
    TAG("标签", 4),
    /**
     * 按钮
     */
    BUTTON("按钮", 5),


    /**
     * 个人工作台
     */
    CLASSIFY1("个人工作台", 101),
    /**
     * 项目与计划
     */
    CLASSIFY2("项目与计划", 102),
    /**
     * 资料与送审
     */
    CLASSIFY3("资料与送审", 103),
    /**
     * 管理与考核
     */
    CLASSIFY4("管理与考核", 104),
    /**
     * 数据与分析
     */
    CLASSIFY5("数据与分析", 105),
    /**
     * 统计与查询
     */
    CLASSIFY6("统计与查询", 106),
    /**
     * 系统设置
     */
    CLASSIFY7("系统设置", 107),

    /**
     * 其他
     */
    OTHER("其他", 100);


    /**
     * 文本
     */
    private String text;
    /**
     * 值
     */
    private Integer value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    FunctionTypes(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
