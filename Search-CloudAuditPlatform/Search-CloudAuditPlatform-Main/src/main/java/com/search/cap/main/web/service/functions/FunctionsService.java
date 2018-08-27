package com.search.cap.main.web.service.functions;

import com.search.cap.main.bean.Classify2Bean;
import com.search.cap.main.bean.IndexBean;
import com.search.cap.main.bean.ViewBean;
import com.search.cap.main.bean.api.QuickFunctionInfoBean;
import com.search.cap.main.common.enums.FunctionTypes;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.web.dao.FunctionsDao;
import com.search.common.base.core.utils.Guava;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2017/12/19 10:37.
 */
@Service
@Slf4j
@SuppressWarnings({"unused"})
public class FunctionsService {
    @Autowired
    private FunctionsDao functionsDao;

    //*********************************************************heyanjing--start*******************************************************************************************************************************
    public List<QuickFunctionInfoBean> phoneAll(String refId) {
        //return this.functionsDao.findByIstateAndIsupportphone(States.ENABLE.getValue(),Nums.YES.getValue());
        // HETODO: 2018/7/17 17:08 重新组装数据
        return this.functionsDao.findByRefId(refId);
    }
    //*********************************************************heyanjing--end*********************************************************************************************************************************

    /**
     * @param userType 用户类型
     * @param refId    用户Id
     * @param viewMap  用户所在机构对应的特殊视图
     * @return 加载分类和对应的一二级菜单
     */
    public IndexBean findByUserType(Integer userType, String refId, Map<String, String> viewMap) {
        List<Functions> classifyList;
        boolean admin = UserTypes.ADMIN.getValue().equals(userType);
        if (admin) {
            classifyList = this.functionsDao.findByItypeAndIstateAndIsupportprojectOrderByIorderAsc(FunctionTypes.CLASSIFY.getValue(), States.ENABLE.getValue(), Nums.NO.getValue());
        } else {
            //classifyList = this.functionsDao.findClassfiyByRefId(refId);asdf
            classifyList = this.functionsDao.findByParentIdAndRefIdAndType(null, refId, FunctionTypes.CLASSIFY.getValue());
        }
        this.functionsDao.clear();
        List<ViewBean> moduleAndNodeList = Guava.newArrayList();

        for (Functions fc : classifyList) {
            List<String> list = Arrays.asList(fc.getSpcmethod().split(","));
            String divId = list.get(0);
            Integer viewType = Integer.valueOf(list.get(1));
            ViewBean vb = null;
            if (Nums.CLASSIFY1.getValue().equals(viewType)) {
                //个人主页
                vb = new ViewBean(Nums.CLASSIFY1.getValue());
            } else if (Nums.CLASSIFY2.getValue().equals(viewType)) {
                //带功能菜单
                List<Classify2Bean> classify2List = Guava.newArrayList();
                List<Functions> moduleList;
                if (admin) {
                    moduleList = this.functionsDao.findByParentId(fc.getSid());
                } else {
                    moduleList = this.functionsDao.findByParentIdAndRefIdAndType(fc.getSid(), refId, FunctionTypes.MODULE.getValue());

                }
                for (Functions fm : moduleList) {
                    List<Functions> nodeList;
                    if (admin) {
                        nodeList = this.functionsDao.findByParentId(fm.getSid());
                    } else {
                        nodeList = this.functionsDao.findByParentIdAndRefIdAndType(fm.getSid(), refId, FunctionTypes.NODE.getValue());
                    }
                    classify2List.add(new Classify2Bean(fm, nodeList));
                }
                vb = new ViewBean(Nums.CLASSIFY2.getValue(), classify2List);
            } else if (Nums.CLASSIFY3.getValue().equals(viewType)) {
                //市，区，其他机构各自不同
                vb = new ViewBean(Nums.CLASSIFY3.getValue());
            } else if (Nums.CLASSIFY4.getValue().equals(viewType)) {
                //个人设置
                vb = new ViewBean(Nums.CLASSIFY4.getValue());
            } else {

            }
            if (viewMap != null && !viewMap.isEmpty()) {
                divId = viewMap.get(fc.getSid());
            }
            fc.setSpcmethod(divId);
            if (vb != null) {
                vb.setDivId(divId);
                moduleAndNodeList.add(vb);
            }
        }
        //classifyList.forEach(fc -> {
        //    List<String> list = Arrays.asList(fc.getSpcmethod().split(","));
        //    String divId = list.get(0);
        //    Integer viewType = Integer.valueOf(list.get(1));
        //    ViewBean vb = null;
        //    if (Nums.CLASSIFY1.getValue().equals(viewType)) {
        //        //个人主页
        //        vb = new ViewBean(Nums.CLASSIFY1.getValue());
        //    } else if (Nums.CLASSIFY2.getValue().equals(viewType)) {
        //        //带功能菜单
        //        List<Classify2Bean> classify2List = Guava.newArrayList();
        //        List<Functions> moduleList = this.functionsDao.findByParentIdAndRefIdAndType(fc.getSid(), refId, FunctionTypes.MODULE.getValue());
        //        for (Functions fm : moduleList) {
        //            List<Functions> nodeList = this.functionsDao.findByParentIdAndRefIdAndType(fm.getSid(), refId, FunctionTypes.NODE.getValue());
        //            classify2List.add(new Classify2Bean(fm, nodeList));
        //        }
        //        vb = new ViewBean(Nums.CLASSIFY2.getValue(), classify2List);
        //    } else if (Nums.CLASSIFY3.getValue().equals(viewType)) {
        //        //市，区，其他机构各自不同
        //        vb = new ViewBean(Nums.CLASSIFY3.getValue());
        //    } else if (Nums.CLASSIFY4.getValue().equals(viewType)) {
        //        //个人设置
        //        vb = new ViewBean(Nums.CLASSIFY4.getValue());
        //    } else {
        //
        //    }
        //    if (viewMap != null && !viewMap.isEmpty()) {
        //        divId = viewMap.get(fc.getSid());
        //    }
        //    fc.setSpcmethod(divId);
        //    if (vb != null) {
        //        vb.setDivId(divId);
        //        moduleAndNodeList.add(vb);
        //    }
        //});
        return new IndexBean(moduleAndNodeList, classifyList);
    }

    public List<Functions> findByParentIdAndRefIdAndType(String parentId, String refId) {
        return this.functionsDao.findByParentIdAndRefIdAndType(parentId, refId, FunctionTypes.TAG.getValue());
    }

    public List<Functions> findByParentId(String parentId, Integer userType, String refId) {
        if (UserTypes.ADMIN.getValue().equals(userType)) {
            return this.functionsDao.findByParentId(parentId);
        } else {
            return this.functionsDao.findByParentIdAndRefIdAndType(parentId, refId, FunctionTypes.TAG.getValue());
        }
    }
}
