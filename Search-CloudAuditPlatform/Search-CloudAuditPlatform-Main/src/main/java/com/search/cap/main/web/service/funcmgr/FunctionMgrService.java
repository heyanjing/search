/**
 * Copyright (c) 2018 协成科技
 * All rights reserved.
 * <p>
 * File：FunctionMgrService.java
 * History:
 * 2018年3月19日: Initially created, wangjb.
 */
package com.search.cap.main.web.service.funcmgr;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.search.cap.main.common.enums.FunctionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.cap.main.common.enums.States;
import com.search.cap.main.entity.Functions;
import com.search.cap.main.web.dao.FunctionMgrDao;

/**
 * 功能管理业务处理。
 * @author wangjb
 */
@Service
public class FunctionMgrService {

    /**
     * 功能管理数据接口。
     */
    private @Autowired
    FunctionMgrDao funcDao;

    /**
     * 获取功能管理数据。
     * @author wangjb 2018年3月19日。
     * @param istate 功能状态。
     * @return
     */
    public List<Map<String, Object>> findFuncMgrListService(int istate, String keyword, Integer itype) {
        String[] str = null;
        if (keyword != null) {
            str = keyword.split(" ");
        }
        return this.funcDao.findFuncMgrListByiStateDao(istate, str, itype);
    }

    /**
     * 根据功能ID查询功能对象数据。
     * @author wangjb 2018年3月19日。
     * @param sid 功能ID。
     * @return
     */
    public Functions getFuncObjBySidService(String sid) {
        return this.funcDao.getBySid(sid);
    }

    /**
     * 更新功能对象数据。
     * @author wangjb 2018年3月19日。
     * @param func 功能对象。
     */
    public Map<String, Object> updateFuncObjService(Functions func, String userid) {
        if (func.getIsupportphone() == -1 ) func.setIsupportphone(null);
        if (func.getSparentid() == "-1" || "-1".equals(func.getSparentid())) func.setSparentid(null);
        //List<Map<String, Object>> sidList = this.funcDao.findBysNameAndsParentId(func.getSname(), func.getSparentid(), States.DELETE.getValue());
        List<Functions> sidList = this.funcDao.findBySnameAndSparentid(func.getSname(), func.getSparentid());
        if (func.getItype() != FunctionTypes.BUTTON.getValue()) func.setSbtnlocation(null);
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        if (func.getSid() == null || "".equals(func.getSid())) {
            if (sidList.size() > 0) flag = true;
            else {
                func.setScreateuserid(userid);
                func.setLdtcreatetime(LocalDateTime.now());
                func.setIstate(States.ENABLE.getValue());
                this.funcDao.save(func);
            }
        } else {
            if (sidList.size() > 0) {
                for (Functions mapid : sidList) {
                    if (!mapid.getSid().equals(func.getSid())) {
                    	flag = true;
//                        setFunctionVal(func, userid, mapid);
//                        this.funcDao.save(mapid);
                    }
                    //if (((String) mapid.get("sid")).equals(func.getSid())) {
                    //    this.funcDao.updateFuncObjdao(func.getSname(), func.getSparentid(), func.getItype(), func.getSicon(), func.getSpcmethod(), func.getIsupportphone(), func.getSandroidmethod(),
                    //            func.getIorder(), func.getSdesc(), func.getIsupportproject(), userid, LocalDateTime.now(), func.getSbtnlocation(), func.getSbindevent(), func.getIjoinprocess(), func.getSjoinprocesstable(),func.getSminicon(), func.getSid());
                    //} else flag = true;
                }
                if(!flag){
                	Functions mapid = this.funcDao.getBySid(func.getSid());
                    setFunctionVal(func, userid, mapid);
                    this.funcDao.save(mapid);
                }
            } else {
                Functions mapid = this.funcDao.getBySid(func.getSid());
                setFunctionVal(func, userid, mapid);
                this.funcDao.save(mapid);
                //this.funcDao.updateFuncObjdao(func.getSname(), func.getSparentid(), func.getItype(), func.getSicon(), func.getSpcmethod(), func.getIsupportphone(), func.getSandroidmethod(),
                //        func.getIorder(), func.getSdesc(), func.getIsupportproject(), userid, LocalDateTime.now(), func.getSbtnlocation(), func.getSbindevent(), func.getIjoinprocess(), func.getSjoinprocesstable(),func.getSminicon(), func.getSid());
            }
        }

        if (flag) {
            map.put("state", false);
            map.put("isSuccess", "存在重复功能名称!");
        } else {
            map.put("state", true);
            map.put("isSuccess", "操作成功!");
        }

        return map;
    }

    private void setFunctionVal(Functions func, String userid, Functions mapid) {
        mapid.setSname(func.getSname());
        mapid.setItype(func.getItype());
        mapid.setSicon(func.getSicon());
        mapid.setSpcmethod(func.getSpcmethod());
        mapid.setSandroidmethod(func.getSandroidmethod());
        mapid.setIsupportphone(func.getIsupportphone());
        mapid.setSminicon(func.getSminicon());
        mapid.setIorder(func.getIorder());
        mapid.setSdesc(func.getSdesc());
        mapid.setSparentid(func.getSparentid());
        mapid.setIsupportproject(func.getIsupportproject());
        mapid.setSbtnlocation(func.getSbtnlocation());
        mapid.setSbindevent(func.getSbindevent());
        mapid.setIjoinprocess(func.getIjoinprocess());
        mapid.setSjoinprocesstable(func.getSjoinprocesstable());
        mapid.setSupdateuserid(userid);
        mapid.setLdtupdatetime(LocalDateTime.now());
    }

    /**
     * 根据功能ID修改状态。
     * @author wangjb 2018年3月19日。
     * @param sid 功能ID。
     * @param userid 用户ID。
     * @param state 状态。
     */
    public void updateFuncStateService(String sid, String userid, int state, Map<String, Object> mpd) {
        LocalDateTime ldtUpdateTime = LocalDateTime.now(); //更新时间
        this.funcDao.updateFuncStateDao(userid, ldtUpdateTime, state, sid);
        if (States.ENABLE.getValue() == state) {
            Functions funcObj = this.funcDao.getBySid(sid);
            this.updateParentFuncStateService(userid, ldtUpdateTime, state, funcObj.getSparentid());
        } else this.updateChildFuncStateService(userid, ldtUpdateTime, state, sid);
        mpd.put("meg", "操作成功!");
    }

    /**
     * 恢复父级功能数据。
     * @author wangjb 2018年4月17日。
     * @param userid 用户ID。
     * @param ldtUpdateTime 更新时间。
     * @param state 状态。
     * @param sparentid 父级ID。
     */
    private void updateParentFuncStateService(String userid, LocalDateTime ldtUpdateTime, int state, String sparentid) {
        while (true) {
            Functions funcObj = this.funcDao.getBySid(sparentid);
            if (funcObj != null) {
                this.funcDao.updateFuncStateDao(userid, ldtUpdateTime, state, funcObj.getSid());
                this.updateParentFuncStateService(userid, ldtUpdateTime, state, funcObj.getSparentid());
            }
            break;
        }
    }

    /**
     * 修改子级功能状态。
     * @author wangjb 2018年3月21日。
     * @param userid 更新用户。
     * @param ldtUpdateTime 更新时间。
     * @param state 功能状态。
     * @param sid 功能ID。
     */
    private void updateChildFuncStateService(String userid, LocalDateTime ldtUpdateTime, int state, String sid) {
        while (true) {
            List<Functions> funcList = this.funcDao.findBySparentid(sid);
            if (funcList.size() > 0) {
                for (Functions funcid : funcList) {
                    this.funcDao.updateFuncStateDao(userid, ldtUpdateTime, state, funcid.getSid());
                    this.updateChildFuncStateService(userid, ldtUpdateTime, state, funcid.getSid());
                }
            }
            break;
        }
    }

    /**
     * 查询父级功能
     * @author wangjb 2018年3月20日。
     * @return
     */
    public List<Map<String, Object>> findParentFuncListService() {
        List<Map<String, Object>> listMap = this.funcDao.findParentFuncListDao(States.ENABLE.getValue(), FunctionTypes.BUTTON.getValue());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sid", "-1");
        map.put("sname", "顶层节点");
        listMap.add(0, map);
        return listMap;
    }

    /**
     * 查询功能详情。
     * @author wangjb 2018年4月19日。
     * @param sid 功能ID。
     * @return
     */
    public Map<String, Object> getFuncDetailService(String sid) {
        Map<String, Object> map = this.funcDao.getFuncDetailService(sid);
        String str = "";
        if (map.get("sbtnlocation") != null) {
            str = (String) map.get("sbtnlocation");
            str = str.replace("101", "顶部");
            str = str.replace("102", "尾部");
            str = str.replace("103", "右键菜单");
        }

        map.put("sbtnlocation", str);
        return map;
    }

}
