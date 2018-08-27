package com.search.cap.main.common.enums;

import com.search.common.base.core.utils.Guava;

import java.util.List;

/**
 * Created by heyanjing on 2018/3/6 14:39.
 * 机构类型：审计局、中介机构、嘉宾、建设业主、BT单位、设计单位、勘察单位、监理单位、施工单位、分包单位（？）、招标代理（？）、供应商（？）
 */
public enum OrgTypes {
    /**
     * 审计局
     */
    AUDIT("审计局", 101),
    /**
     * 中介机构
     */
    INTERMEDIARY("中介机构", 102),
    /**
     * 嘉宾
     */
    GUEST("嘉宾", 103),
    /**
     * 建设业主
     */
    PROPRIETOR("建设业主", 104),
    /**
     * BT单位
     */
    BT("BT单位", 105),
    /**
     * 设计单位
     */
    DESIGN("设计单位", 106),
    /**
     * 勘察单位
     */
    PROSPECTING("勘察单位", 107),
    /**
     * 监理单位
     */
    SUPERVISION("监理单位", 108),
    /**
     * 施工单位
     */
    CONSTRUCTION("施工单位", 109),
    /**
     * 代建单位
     */
    DEPUTY("代建单位", 110),
    /**
     * 招标代理
     */
    TENDER("招标代理", 114),

    /**
     * 发改委
     */
    REFORM("发改委", 111),
    /**
     * 财政局
     */
    FINANCE("财政局", 112),
    /**
     * 政府机关
     */
    GOVERNMENT("政府机关", 113),


    /**
     * 机构允许用户人数不受控制
     */
    NO_CTR("机构允许用户人数不受控制", -2),
    /**
     * 机构允许用户人数受全局控制
     */
    GLOBLE_CTR("机构允许用户人数受全局控制", -1),
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

    OrgTypes(String text, Integer value) {

        this.text = text;
        this.value = value;
    }

    public static String getCjdw() {
        List<String> cjdwList = Guava.newArrayList();
        cjdwList.add(OrgTypes.INTERMEDIARY.getValue().toString());
        cjdwList.add(OrgTypes.PROPRIETOR.getValue().toString());
        cjdwList.add(OrgTypes.BT.getValue().toString());
        cjdwList.add(OrgTypes.DESIGN.getValue().toString());
        cjdwList.add(OrgTypes.PROSPECTING.getValue().toString());
        cjdwList.add(OrgTypes.SUPERVISION.getValue().toString());
        cjdwList.add(OrgTypes.CONSTRUCTION.getValue().toString());
        cjdwList.add(OrgTypes.TENDER.getValue().toString());
        return String.join(",", cjdwList);
    }

    public static String getTsdw() {
        List<String> tsdwList = Guava.newArrayList();
        tsdwList.add(OrgTypes.FINANCE.getValue().toString());
        tsdwList.add(OrgTypes.REFORM.getValue().toString());
        return String.join(",", tsdwList);
    }
}
