package com.search.common.base.web.poi;

import com.search.common.base.core.bean.BaseBean;

/**
 * Created by heyanjing on 2018/6/19 20:55.
 */
public class PrimaryBean extends BaseBean {
	private static final long serialVersionUID = 4171135147679533246L;
	
	/**
     *供应商序号
     */
    private String suppliernum;
    /**
     *供应商名称
     */
    private String suppliername;
    /**
     * 联系人
     */
    private String contactuser;
    /**
     * 联系电话
     */
    private String contactphone;

    public String getSuppliernum() {
        return suppliernum;
    }

    public void setSuppliernum(String suppliernum) {
        this.suppliernum = suppliernum;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getContactuser() {
        return contactuser;
    }

    public void setContactuser(String contactuser) {
        this.contactuser = contactuser;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }
}
