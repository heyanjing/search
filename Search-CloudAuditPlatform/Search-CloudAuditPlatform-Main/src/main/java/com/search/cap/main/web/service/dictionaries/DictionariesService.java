package com.search.cap.main.web.service.dictionaries;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Dictionaries;
import com.search.cap.main.web.dao.DictionariesDao;
import com.search.common.base.jpa.hibernate.PageObject;


/**
 * 字典Service
 *
 * @author Administrator
 */
@Service
public class DictionariesService {
    @Autowired
    private DictionariesDao dicDao;

    /**
     * 获取单条数据（根据id）
     *
     * @param id
     * @return
     */
    public Dictionaries getId(String id) {
        return dicDao.getBySid(id);
    }

    /**
     * 保存的方法
     */
    public JSONObject save(Dictionaries dic,String userid) {
        JSONObject json = new JSONObject();
        boolean isSave = true;        //是否执行保存
        Dictionaries dictionarie = dicDao.getBySname(dic.getSname());

        if (dic.getSid() == null) { //新增
            if (dictionarie != null) {
            	dic.setLdtcreatetime(LocalDateTime.now());
                dic.setScreateuserid(userid);
                isSave = false;
                json.put("state", false);
                json.put("message", "名称重复");
            }
        } else { //编辑
            Dictionaries dicone = dicDao.getBySid(dic.getSid());
                if (dictionarie != null) {
                	if (!dicone.getSname().equals(dic.getSname())) {
                		dic.setLdtupdatetime(LocalDateTime.now());
                        dic.setSupdateuserid(userid);
                        isSave = false;
                        json.put("state", false);
                        json.put("message", "名称重复");
                	}
                	
                }
        }
        if (isSave) {
            dic.setIstate(States.ENABLE.getValue());
            dic.setItype(dic.getItype() == 0 ? null : dic.getItype());
            dicDao.save(dic);
            json.put("state", true);
            json.put("message", "操作成功");
        }
        return json;
    }

    /**
     * 分页查询(单表查询)
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageObject<Dictionaries> pageByJpql(Integer pageNumber, Integer pageSize) {
        return dicDao.pageByJpql(pageNumber, pageSize);
    }

    /**
     * 分页查询
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageObject<Map<String, Object>> pageMapListBySql(Integer pageNumber, Integer pageSize, int state, String keyword, Integer itype) {
        String[] str = null;
        if (keyword != null) {
            str = keyword.split(" ");
        }
        return this.dicDao.pageMapListBySql(pageNumber, pageSize, state, str, itype);
    }

    /**
     * 禁用/启用
     *
     * @param state
     * @param id
     */
    public void updteState(int state, String id) {
        String ids[] = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            Dictionaries dic = dicDao.getBySid(ids[i]);
            dic.setIstate(state);
            dicDao.save(dic);
        }
    }

    /**
     * 根据类型查询
     *
     * @param state
     * @return
     */
    public List<Dictionaries> getDictionariesList(Integer itype) {
        return dicDao.getByItype(itype);
    }
}
