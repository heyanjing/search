package com.search.cap.main.common.enums;

/**
 * Created by heyanjing on 2018/3/6 14:39.
 * 单纯的数字，或判断
 */
public enum Nums {
    /**
     * 是
     */
    YES("是", 1),
    /**
     * 否
     */
    NO("否", 2),
    /**
     * 男
     */
    MALE("男", 1),
    /**
     * 女
     */
    FEMALE("女", 2),
    /**
     * 机构用户数不受限制
     */
    NO_LIMIT("不受限制", -2),
    /**
     * 当全局开启用户限制时， 机构用户数受全局限制
     */
    GLOBLE_LIMIT("当全局开启用户限制时， 机构用户数受全局限制", -1),
    /**
     * 3
     */
    THREE("3", 3),
    /**
     * 需要手机验证码
     */
    VCODE("需要手机验证码", 101),
    /**
     * 用户不存在
     */
    NO_USER("用户不存在", 102),
    /**
     * 密码错误
     */
    ERROR_PASSWORD("密码错误", 103),
    /**
     * 文件在允许范围内
     */
    FILE_OK("文件在允许范围内", 200),
    /**
     * 文件格式不正确
     */
    FILE_EXT_ERROR("文件格式不正确", 201),
    /**
     * 文件大小超出限制
     */
    FILE_SIZE_ERROR("文件大小超出限制", 202),
    /**
     * 文件不存在
     */
    FILE_NO_EXIST("文件不存在", 203),
    /**
     * 合并分块失败
     */
    FILE_MERGE_FAILURE("合并分块失败", 204),

    /**
     * 个人工作台视图
     */
    CLASSIFY1("", 301),
    /**
     * 项目与计划，资料与送审，数据与分析，统计与查询,系统设置等视图
     */
    CLASSIFY2("", 302),
    /**
     * 管理与考核视图
     */
    CLASSIFY3("", 303),
    /**
     *
     */
    CLASSIFY4("", 304),
    /**
     * 其他
     */
    OTHER("其他", 999);


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

    Nums(String text, Integer value) {

        this.text = text;
        this.value = value;
    }
}
