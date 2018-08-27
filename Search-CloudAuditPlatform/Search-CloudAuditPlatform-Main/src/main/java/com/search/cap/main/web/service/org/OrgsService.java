package com.search.cap.main.web.service.org;

import com.search.cap.main.Capm;
import com.search.cap.main.common.Commons;
import com.search.cap.main.common.enums.CommonAttachTypes;
import com.search.cap.main.common.enums.DictionarieTypes;
import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.common.enums.OrgTypes;
import com.search.cap.main.common.enums.PermissionTypes;
import com.search.cap.main.common.enums.States;
import com.search.cap.main.common.enums.UserTypes;
import com.search.cap.main.entity.Commonattachs;
import com.search.cap.main.entity.Dictionaries;
import com.search.cap.main.entity.Functiongroupanduserrefs;
import com.search.cap.main.entity.Functiongroups;
import com.search.cap.main.entity.Intermediarys;
import com.search.cap.main.entity.Organduserrefs;
import com.search.cap.main.entity.Orgdisableref;
import com.search.cap.main.entity.Orgoruseranddictionarierefs;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Users;
import com.search.cap.main.web.dao.ChargeorgsDao;
import com.search.cap.main.web.dao.CommonattachsDao;
import com.search.cap.main.web.dao.DictionariesDao;
import com.search.cap.main.web.dao.FunctionGroupsDao;
import com.search.cap.main.web.dao.FunctiongroupanduserrefsDao;
import com.search.cap.main.web.dao.IntermediarysDao;
import com.search.cap.main.web.dao.OrganduserrefsDao;
import com.search.cap.main.web.dao.OrgdisablerefDao;
import com.search.cap.main.web.dao.OrgoruseranddictionarierefsDao;
import com.search.cap.main.web.dao.OrgsDao;
import com.search.cap.main.web.dao.UsersDao;
import com.search.cap.main.web.service.users.UsersService;
import com.search.common.base.core.bean.Result;
import com.search.common.base.jpa.hibernate.PageObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lirui on 2018/3/21 10:22.
 */
@Service
@Slf4j
public class OrgsService {

    @Autowired
    private OrgsDao orgsDao;
    @Autowired
    private OrgoruseranddictionarierefsDao orgoruseranddictionarierefsDao;
    @Autowired
    private DictionariesDao dictionariesDao;
    @Autowired
    private CommonattachsDao commonattachsDao;
    @Autowired
    private IntermediarysDao intermediarysDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private OrganduserrefsDao organduserrefsDao;
    @Autowired
    private UsersService usersService;
    @Autowired
    private OrgdisablerefDao orgdisablerefDao;
    @Autowired
    private FunctiongroupanduserrefsDao functiongroupanduserrefsDao;
    @Autowired
    private FunctionGroupsDao functionGroupsDao;

    //*********************************************************heyanjing--start*******************************************************************************************************************************

    /**
     * @return 所有审计非部门机构
     */
    public Result findAuditNoDepartment() {
        return Result.successWithData(this.orgsDao.findAuditNoDepartment());
    }

    /**
     * @return 所有非部门机构
     */
    public Result findNoDepartmet() {
        return Result.successWithData(this.orgsDao.findByIisdepartmentAndIstate(Nums.NO.getValue(), States.ENABLE.getValue()));
    }

    //*********************************************************heyanjing--end*********************************************************************************************************************************

    /**
     * 查询机构
     *
     * @param usertype
     * @param orgid
     * @param iisdepartment
     * @param ipermissionlevel
     * @param userid
     * @param type
     * @param state
     * @param sname
     * @param itype
     * @param iisdepartment2
     * @return
     * @author lirui 2018年3月21日
     */
    public List<Map<String, Object>> queryOrgs(Integer usertype, String orgid, List<Integer> orgtype, Integer state, Integer iisdepartment2, String itype, String sname, Integer iisdepartment, String sparentid) {
        List<Map<String, Object>> list = orgsDao.queryOrgs(state, iisdepartment2, itype, sname);//全部org
        List<Map<String, Object>> orglist = new ArrayList<>();//展示的org
        if (usertype.equals(UserTypes.ADMIN.getValue())) {//用户类型为admin
            //展示所有审计机构和部门
            for (Map<String, Object> map : list) {
                if (map.get("itype").toString().equals(OrgTypes.AUDIT.getValue().toString())) {
                    map.put("isedit", 1);
                    orglist.add(map);
                }
            }
            //            orglist.addAll(list);
        } else {//用户类型为其他类型
            List<String> org = new ArrayList<>();
            if (iisdepartment == Nums.YES.getValue()) {//是部门
                org.add(sparentid);
            } else {//不是部门
                org.add(orgid);
            }
            for (Map<String, Object> map : list) {
                if (org.get(0).equals(map.get("sid").toString())) {
                    map.put("isedit", 1);
                    orglist.add(map);
                }
            }
            List<String> idlist = new ArrayList<>();
            //展示所属机构和部门
            for (int i = 0; i == 0; ) {
                for (int j = 0; j < org.size(); j++) {
                    for (Map<String, Object> map : list) {
                        if (org.get(j).equals(map.get("sparentid"))) {
                            if (orgtype.get(0) != OrgTypes.AUDIT.getValue()) {
                                if (map.get("iisdepartment").toString().equals(Nums.YES.getValue().toString()) && orgid.equals(map.get("sparentid").toString())) {//是部门
                                    map.put("isedit", 1);
                                } else if (iisdepartment == Nums.YES.getValue() && orgid.equals(map.get("sid").toString())) {//是部门
                                    map.put("isedit", 1);
                                } else if (iisdepartment == Nums.NO.getValue() && orgid.equals(map.get("sid").toString())) {//不是部门
                                    map.put("isedit", 1);
                                } else {
                                    map.put("isedit", 2);
                                }
                            } else {
                                map.put("isedit", 1);
                            }
                            orglist.add(map);
                            idlist.add(map.get("sid").toString());
                        }
                    }
                }
                org = idlist;
                for (int j = 0; j < org.size(); j++) {
                    idlist.remove(0);
                }
                if (org.size() == 0) {
                    break;
                }
            }
        }
        //            if (ipermissionlevel == PermissionTypes.OWNED_ORG_ALL.getValue()) {//权限为分管机构所有项目
        //                List<Map<String, Object>> qclist = new ArrayList<>();//判断去重
        //                List<String> idlist = new ArrayList<>();//临时存放orgid
        //                //本机构
        //                for (Map<String, Object> map : list) {
        //                    if (map.get("sid").toString().equals(orgid)) {
        //                        orglist.add(map);
        //                        qclist.add(map);
        //                    }
        //                }
        //                //分管机构
        //                List<Chargeorgs> co = chargeorgsDao.getBySuseridAndIstate(userid, States.ENABLE.getValue());
        //                for (Map<String, Object> m : list) {
        //                    for (Chargeorgs c : co) {
        //                        if (m.get("sid").toString().equals(c.getSorgid().toString())) {
        //                            orglist.add(m);
        //                            qclist.add(m);
        //                        }
        //                    }
        //                }
        //                //用户机构类型为审计局非部门时展示本机构在机构与机构关系表中的关联机构
        //                if (orgtype.get(0).equals(OrgTypes.AUDIT.getValue())) {//机构类型为审计局
        //                    if (iisdepartment == Nums.NO.getValue()) {//非部门
        //                        List<Intermediarys> intermediaryslist = intermediarysDao.getByIstate(States.ENABLE.getValue());
        //                        for (Intermediarys intermediarys : intermediaryslist) {
        //                            if (intermediarys.getSauditorgid().toString().equals(orgid)) {
        //                                boolean flag = true;
        //                                //去重
        //                                for (Map<String, Object> qc : qclist) {
        //                                    if (intermediarys.getSintermediaryorgid().toString().equals(qc.get("sid").toString())) {
        //                                        flag = false;
        //                                    }
        //                                }
        //                                if (flag) {
        //                                    idlist.add(intermediarys.getSintermediaryorgid());
        //                                }
        //                            }
        //                        }
        //                        for (Map<String, Object> m : list) {
        //                            for (String id : idlist) {
        //                                if (m.get("sid").toString().equals(id)) {
        //                                    orglist.add(m);
        //                                }
        //                            }
        //                        }
        //                    }
        //                }
        //            } else if (ipermissionlevel == PermissionTypes.ALL.getValue() && orgtype.get(0).equals(OrgTypes.AUDIT.getValue()) && iisdepartment == Nums.YES.getValue()) {//权限为本机构及所有子机构所有项目且是审计局部门
        //                String parentid = "";//父级id
        //                //获取本机构父级id
        //                for (Map<String, Object> map : list) {
        //                    if (map.get("sid").toString().equals(orgid)) {
        //                        parentid = map.get("sparentid").toString();
        //                    }
        //                }
        //                for (Map<String, Object> map : list) {
        //                    if (map.get("sid").toString().equals(parentid)) {
        //                        orglist.add(map);
        //                        List<Orgs> orgslist = orgsDao.findBySparentidAndIstateNotAndIstateNot(parentid, States.DELETE.getValue());
        //                        List<String> orgidlist = new ArrayList<>();
        //                        List<String> org = new ArrayList<>();
        //                        List<String> str = new ArrayList<>();
        //                        int num = 0;
        //                        if (orgslist.size() > 0) {
        //                            for (int i = 0; i < 1; ) {
        //                                List<Orgs> listorg = new ArrayList<>();
        //                                listorg.addAll(orgslist);
        //                                if (num > 0) {
        //                                    //清空listorg
        //                                    int num1 = listorg.size();
        //                                    for (int j = 0; j < num1; j++) {
        //                                        listorg.remove(0);
        //                                    }
        //                                    //根据上一节点的所有子节点为父级查询所有下一级节点
        //                                    List<Orgs> sonorg = orgsDao.findBySparentidInAndIstateNot(str, States.DELETE.getValue());
        //                                    if (sonorg.size() > 0) {
        //                                        listorg.addAll(sonorg);
        //                                        //清空str
        //                                        int num2 = str.size();
        //                                        for (int k = 0; k < num2; k++) {
        //                                            str.remove(0);
        //                                        }
        //                                        num = 0;
        //                                    } else {
        //                                        break;
        //                                    }
        //                                }
        //                                for (Orgs o : listorg) {
        //                                    orgidlist.add(o.getSid());
        //                                    str.add(o.getSid());
        //                                    num++;
        //                                }
        //                            }
        //                        }
        //                        //用户机构类型为审计局部门时展示本机构的父级机构在机构与机构关系表中的关联机构
        //                        List<Intermediarys> intermediaryslist = intermediarysDao.getByIstate(States.ENABLE.getValue());
        //                        for (Intermediarys intermediarys : intermediaryslist) {
        //                            if (intermediarys.getSauditorgid().toString().equals(parentid)) {
        //                                boolean flag = true;
        //                                for (String id : orgidlist) {
        //                                    if (intermediarys.getSintermediaryorgid().toString().equals(id)) {
        //                                        flag = false;
        //                                    }
        //                                }
        //                                if (flag) {
        //                                    org.add(intermediarys.getSintermediaryorgid());
        //                                }
        //                            }
        //                        }
        //
        //                        org.addAll(orgidlist);
        //                        for (Map<String, Object> m : list) {
        //                            for (String id : org) {
        //                                if (m.get("sid").toString().equals(id)) {
        //                                    orglist.add(m);
        //                                }
        //                            }
        //                        }
        //                    }
        //                }
        //            } else {
        //                for (Map<String, Object> map : list) {
        //                    if (map.get("sid").toString().equals(orgid)) {
        //                        orglist.add(map);
        //                        List<Orgs> orgslist = orgsDao.findBySparentidAndIstateNotAndIstateNot(orgid, States.DELETE.getValue());
        //                        List<String> orgidlist = new ArrayList<>();
        //                        List<String> org = new ArrayList<>();
        //                        List<String> str = new ArrayList<>();
        //                        int num = 0;
        //                        if (orgslist.size() > 0) {
        //                            for (int i = 0; i < 1; ) {
        //                                List<Orgs> listorg = new ArrayList<>();
        //                                listorg.addAll(orgslist);
        //                                if (num > 0) {
        //                                    //清空listorg
        //                                    int num1 = listorg.size();
        //                                    for (int j = 0; j < num1; j++) {
        //                                        listorg.remove(0);
        //                                    }
        //                                    //根据上一节点的所有子节点为父级查询所有下一级节点
        //                                    List<Orgs> sonorg = orgsDao.findBySparentidInAndIstateNot(str, States.DELETE.getValue());
        //                                    if (sonorg.size() > 0) {
        //                                        listorg.addAll(sonorg);
        //                                        //清空str
        //                                        int num2 = str.size();
        //                                        for (int k = 0; k < num2; k++) {
        //                                            str.remove(0);
        //                                        }
        //                                        num = 0;
        //                                    } else {
        //                                        break;
        //                                    }
        //                                }
        //                                for (Orgs o : listorg) {
        //                                    orgidlist.add(o.getSid());
        //                                    str.add(o.getSid());
        //                                    num++;
        //                                }
        //                            }
        //                        }
        //                        //用户机构类型为审计局非部门时展示本机构在机构与机构关系表中的关联机构
        //                        if (orgtype.get(0).equals(OrgTypes.AUDIT.getValue())) {//机构类型为审计局
        //                            if (iisdepartment == Nums.NO.getValue()) {//非部门
        //                                List<Intermediarys> intermediaryslist = intermediarysDao.getByIstate(States.ENABLE.getValue());
        //                                for (Intermediarys intermediarys : intermediaryslist) {
        //                                    if (intermediarys.getSauditorgid().toString().equals(orgid)) {
        //                                        boolean flag = true;
        //                                        for (String id : orgidlist) {
        //                                            if (intermediarys.getSintermediaryorgid().toString().equals(id)) {
        //                                                flag = false;
        //                                            }
        //                                        }
        //                                        if (flag) {
        //                                            org.add(intermediarys.getSintermediaryorgid());
        //                                        }
        //                                    }
        //                                }
        //                            }
        //                        }
        //                        org.addAll(orgidlist);
        //                        for (Map<String, Object> m : list) {
        //                            for (String id : org) {
        //                                if (m.get("sid").toString().equals(id)) {
        //                                    orglist.add(m);
        //                                }
        //                            }
        //                        }
        //                    }
        //                }
        //            }
        ////			if(orgtype.get(0).equals(com.search.cap.main.common.enums.Orgs.AUDIT.getValue())){//机构类型为审计局
        ////				if(iisdepartment == Numbers.YES.getValue()){//是部门
        ////					for(Map<String, Object> map : list){
        ////						if(map.get("sid").toString().equals(orgid)){
        ////							orglist.add(map);
        ////						}
        ////					}
        ////				}else{//非部门
        ////					if(ipermissionlevel == Permissions.OWNED_ORG_ALL.getValue()){//权限为分管机构所有项目
        ////						List<Chargeorgs> co = chargeorgsDao.getBySuseridAndIstate(userid,States.ENABLE.getValue());
        ////						for(Map<String, Object> m : list){
        ////							for(Chargeorgs c : co){
        ////								if(m.get("sid").toString().equals(c.getSorgid().toString())){
        ////									orglist.add(m);
        ////								}
        ////							}
        ////						}
        ////					}else{//其他
        ////						for(Map<String, Object> map : list){
        ////							if(map.get("sid").toString().equals(orgid)){
        ////								orglist.add(map);
        ////								List<Orgs> orgslist = orgsDao.findBySparentidAndIstateNotAndIstateNot(orgid, States.DELETE.getValue());
        ////								List<String> orgidlist = new ArrayList<>();
        ////								List<String> org = new ArrayList<>();
        ////								List<String> str = new ArrayList<>();
        ////								int num = 0;
        ////								if(orgslist.size()>0){
        ////									for(int i = 0;i<1;){
        ////										List<Orgs> listorg = new ArrayList<>();
        ////										listorg.addAll(orgslist);
        ////										if(num>0){
        ////											//清空listorg
        ////											int num1 = listorg.size();
        ////											for(int j = 0;j<num1;j++){
        ////												listorg.remove(0);
        ////											}
        ////											//根据上一节点的所有子节点为父级查询所有下一级节点
        ////											List<Orgs> sonorg = orgsDao.findBySparentidInAndIstateNot(str, States.DELETE.getValue());
        ////											if(sonorg.size()>0){
        ////												listorg.addAll(sonorg);
        ////												//清空str
        ////												int num2 = str.size();
        ////												for(int k = 0;k<num2;k++){
        ////													str.remove(0);
        ////												}
        ////												num = 0;
        ////											}else{
        ////												break;
        ////											}
        ////										}
        ////										for(Orgs o : listorg){
        ////											orgidlist.add(o.getSid());
        ////											str.add(o.getSid());
        ////											num++;
        ////										}
        ////									}
        ////								}
        ////								List<Intermediarys> intermediaryslist = intermediarysDao.getByIstate(States.ENABLE.getValue());
        ////								for(Intermediarys intermediarys : intermediaryslist){
        ////									if(intermediarys.getSauditorgid().toString().equals(orgid)){
        ////										boolean flag = true;
        ////										for(String id : orgidlist){
        ////											if(intermediarys.getSintermediaryorgid().toString().equals(id)){
        ////												flag = false;
        ////											}
        ////										}
        ////										if(flag){
        ////											org.add(intermediarys.getSintermediaryorgid());
        ////										}
        ////									}
        ////								}
        ////								org.addAll(orgidlist);
        ////								for(Map<String, Object> m : list){
        ////									for(String id : org){
        ////										if(m.get("sid").toString().equals(id)){
        ////											orglist.add(m);
        ////										}
        ////									}
        ////								}
        ////							}
        ////						}
        ////					}
        ////				}
        ////			}else if(orgtype.get(0).equals(com.search.cap.main.common.enums.Orgs.intermediary.getValue())){//机构类型为中介机构
        ////				for(Map<String, Object> map : list){
        ////					if(map.get("sid").toString().equals(orgid)){
        ////						List<Intermediarys> intermediaryslist = intermediarysDao.getByIstate(States.ENABLE.getValue());
        ////						List<String> sauditorgidlist = new ArrayList<>();
        ////						for(Intermediarys i : intermediaryslist){
        ////							if(i.getSintermediaryorgid().equals(orgid)){
        ////								sauditorgidlist.add(i.getSauditorgid());
        ////							}
        ////						}
        ////						List<String> orgidlist = new ArrayList<>();
        ////						for(String sauditorgid : sauditorgidlist){
        ////							for(Intermediarys i : intermediaryslist){
        ////								if(i.getSauditorgid().equals(sauditorgid)&&!i.getSauditorgid().equals(orgid)){
        ////									orgidlist.add(i.getSintermediaryorgid());
        ////								}
        ////							}
        ////						}
        ////						orgidlist.addAll(sauditorgidlist);
        ////						for(Map<String, Object> m : list){
        ////							for(String id : orgidlist){
        ////								if(m.get("sid").toString().equals(id)){
        ////									orglist.add(m);
        ////								}
        ////							}
        ////						}
        ////					}
        ////				}
        ////			}else{//其他类型
        ////				for(Map<String, Object> map : list){
        ////					if(map.get("sid").toString().equals(orgid)){
        ////						orglist.add(map);
        ////					}
        ////				}
        ////			}
        return orglist;
    }

    /**
     * 根据sid查询机构
     *
     * @return
     * @author lirui 2018年3月21日
     */
    public Map<String, Object> getOrgBySid(String sid) {
        List<Map<String, Object>> map = orgsDao.getOrgBySid(sid);
        List<Users> users = usersDao.findUserByOrgId(sid);
        if (users.size() > 0) {
            map.get(0).put("username", users.get(0).getSname());
            map.get(0).put("userphone", users.get(0).getSphone());
        }
        if (!map.get(0).get("itype").toString().equals(OrgTypes.AUDIT.getValue().toString()) && !map.get(0).get("itype").toString().equals(OrgTypes.REFORM.getValue().toString()) && !map.get(0).get("itype").toString().equals(OrgTypes.GOVERNMENT.getValue().toString()) && !map.get(0).get("itype").toString().equals(OrgTypes.FINANCE.getValue().toString())) {
            List<Intermediarys> ilist = intermediarysDao.getBySintermediaryorgidAndIstate(sid, States.ENABLE.getValue());
            String str = "";
            for (int i = 0; i < ilist.size(); i++) {
                if (i == 0) {
                    str += ilist.get(i).getSauditorgid();
                } else {
                    str += "," + ilist.get(i).getSauditorgid();
                }
            }
            map.get(0).put("sauditorgid", str);
        }
        //查询机构资质附件
        List<Orgoruseranddictionarierefs> oou = orgoruseranddictionarierefsDao.findBySorgidoruserid(sid);
        List<Map<String, Object>> list1 = new ArrayList<>();
        for (Orgoruseranddictionarierefs o : oou) {
            List<Commonattachs> com1 = commonattachsDao.findBySdataidAndIstateAndItype(o.getSid(), States.ENABLE.getValue(), CommonAttachTypes.ORG_APTITUDE.getValue());
            Map<String, Object> m = new HashMap<>();
            if (com1.size() > 0) {
                m.put("sname", com1.get(0).getSname());
                m.put("spath", com1.get(0).getSpath());
            }
            //			m.put("sid", com1.get(0).getSid());
            m.put("sid", o.getSid());
            m.put("sdictionarieid", o.getSdictionarieid());
            m.put("sdesc", o.getSdesc());
            list1.add(m);
        }
        map.get(0).put("attch2", list1);
        //查询机构营业执照附件
        List<Commonattachs> com2 = commonattachsDao.findBySdataidAndIstateAndItype(sid, States.ENABLE.getValue(), CommonAttachTypes.ORG_LICENSE.getValue());
        List<Map<String, Object>> list2 = new ArrayList<>();
        for (Commonattachs c : com2) {
            Map<String, Object> m = new HashMap<>();
            m.put("sname", c.getSname());
            m.put("spath", c.getSpath());
            m.put("sid", c.getSid());
            list2.add(m);
        }
        map.get(0).put("attch1", list2);
        return map.get(0);
    }

    /**
     * 新增或编辑机构
     *
     * @param userid
     * @param sauditorgid
     * @param sfunctiongroupid
     * @param orgid
     * @return
     * @throws Exception
     * @author lirui 2018年3月21日
     */
    public Map<String, Object> updateOrg(String sid, String sname, String sdes, String sparentid, String itype, String sdesc, String userid, String urlsname, String urlspath, String filesname, String filespath, String filesdesc, String filesdictionarieid, String filesid, String del, String delqua, String register, String sauditorgid, String username, String userphone, Integer iisdepartment, String areaid, String sfunctiongroupid, boolean flag2) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] snameattch = null;
        String[] spathattch = null;
        String[] filesnameattch = null;
        String[] filespathattch = null;
        String[] filesdescattch = null;
        String[] filesdictionarieidattch = null;
        String[] delid = null;
        String[] delquaid = null;
        String[] filesidattch = null;
        String[] sauditorgidlist = null;
        if (sauditorgid != null) {
            sauditorgidlist = sauditorgid.split(",");
        }
        if (urlspath != null) {
            snameattch = urlsname.split(",");
            spathattch = urlspath.split(",");
        }
        if (filespath != null) {
            filesnameattch = filesname.split(",");
            filespathattch = filespath.split(",");
            filesdescattch = filesdesc.split(",");
            filesdictionarieidattch = filesdictionarieid.split(",");
            filesidattch = filesid.split(",");
        }
        if (del != null) {
            delid = del.split(",");
            for (String id : delid) {
                if (!"".equals(id) && id != null) {
                    Commonattachs c = commonattachsDao.getBySid(id);
                    commonattachsDao.delete(c);
                }
            }
        }
        if (delqua != null) {
            delquaid = delqua.split(",");
            for (String id : delquaid) {
                if (!"".equals(id) && id != null) {
                    Orgoruseranddictionarierefs oou = orgoruseranddictionarierefsDao.getBySid(id);
                    Commonattachs c = commonattachsDao.getBySdataid(id);
                    if (c != null) {
                        commonattachsDao.delete(c);
                    }
                    orgoruseranddictionarierefsDao.delete(oou);
                }
            }
        }
        //判断机构允许用户人数是否不受控制
        int lusernumber = Nums.GLOBLE_LIMIT.getValue();
        List<String> str = Capm.ORG_TYPE;
        if (str != null) {
            String[] itypelist = itype.split(",");
            for (String s : itypelist) {
                for (int i = 0; i < str.size(); i++) {
                    if (str.get(i).equals(s)) {
                        lusernumber = Nums.NO_LIMIT.getValue();
                    }
                }
            }
        }
        List<Orgs> orgs = new ArrayList<>();
        if (iisdepartment == Nums.NO.getValue()) {
            orgs = orgsDao.findByIstateNotAndIstateNot(States.DELETE.getValue(), States.REJECT.getValue());
        } else {
            orgs = orgsDao.findBySparentidAndIstateNotAndIstateNot(sparentid, States.DELETE.getValue(), States.REJECT.getValue());
        }
        boolean flag = false;//判断是否重复
        String id = "";//重复数据的id
        for (Orgs o : orgs) {
            if (sname.equals(o.getSname())) {
                flag = true;
                id = o.getSid();
            }
        }
        Orgs org = orgsDao.getBySareaidAndIisdepartmentAndIstateAndItypeLike(areaid, Nums.NO.getValue(), States.ENABLE.getValue(), OrgTypes.AUDIT.getValue().toString());
        if ("".equals(sid) || sid == null) {//新增
            //判断名称是否重复
            if (flag) {
                map.put("state", false);
                map.put("isSuccess", "存在重复机构名称!");
            } else {
                if (org != null && iisdepartment == Nums.NO.getValue()) {
                    map.put("state", false);
                    map.put("isSuccess", "该地区已有审计局存在!");
                    return map;
                }
                //新增org并保存
                Orgs o = new Orgs();
                if ("register".equals(register)) {
                    o.setIstate(States.APPLY.getValue());
                } else {
                    o.setIstate(States.ENABLE.getValue());
                }
                //如果不为审计机构，是否显示该库为空
                //				if(itype==com.search.cap.main.common.enums.Orgs.AUDIT.getValue()){
                //					o.setIsupportshow(isupportshow);
                //				}else{
                //					o.setIsupportshow(null);
                //				}
                //				o.setIsupportleaf(isupportleaf);
                o.setItype(itype);
                o.setSdes(sdes);
                o.setSname(sname);
                o.setSparentid(sparentid);
                o.setScreateuserid(userid);
                o.setSupdateuserid(userid);
                o.setLdtcreatetime(LocalDateTime.now());
                o.setLdtupdatetime(LocalDateTime.now());
                o.setLusernumber(lusernumber);
                o.setIisdepartment(iisdepartment);
                o.setSareaid(areaid);
                Orgs newo = orgsDao.saveAndFlush(o);
                //判断不为审计局时，新增数据到关系库
                if (!itype.equals(OrgTypes.AUDIT.getValue().toString()) && sauditorgidlist != null && iisdepartment == Nums.NO.getValue()) {
                    for (String newsauditorgid : sauditorgidlist) {
                        Intermediarys i = new Intermediarys();
                        String[] itypelist = itype.split(",");
                        boolean itypeflag = false;
                        for (int j = 0; j < itypelist.length; j++) {
                            if (itypelist[j].equals(OrgTypes.INTERMEDIARY.getValue().toString()) && itypelist.length > 1) {
                                itypeflag = true;
                            }
                        }
                        if ("register".equals(register)) {
                            i.setIstate(States.APPLY.getValue());
                        } else {
                            i.setIstate(States.ENABLE.getValue());
                        }
                        i.setScreateuserid(userid);
                        i.setSupdateuserid(userid);
                        i.setLdtcreatetime(LocalDateTime.now());
                        i.setLdtupdatetime(LocalDateTime.now());
                        i.setSintermediaryorgid(newo.getSid());
                        i.setSauditorgid(newsauditorgid);
                        if (itypeflag) {
                            String itypestr1 = OrgTypes.INTERMEDIARY.getValue().toString();
                            String itypestr2 = "";
                            for (int j = 0; j < itypelist.length; j++) {
                                if (!itypelist[j].equals(OrgTypes.INTERMEDIARY.getValue().toString())) {
                                    itypestr2 += itypelist[j] + ",";
                                }
                            }
                            itypestr2 = itypestr2.substring(0, itypestr2.length() - 1);
                            Intermediarys ii = new Intermediarys();
                            i.setSorgtype(itypestr1);
                            intermediarysDao.save(i);
                            if ("register".equals(register)) {
                                ii.setIstate(States.APPLY.getValue());
                            } else {
                                ii.setIstate(States.ENABLE.getValue());
                            }
                            ii.setScreateuserid(userid);
                            ii.setSupdateuserid(userid);
                            ii.setLdtcreatetime(LocalDateTime.now());
                            ii.setLdtupdatetime(LocalDateTime.now());
                            ii.setSintermediaryorgid(newo.getSid());
                            ii.setSauditorgid(newsauditorgid);
                            ii.setSorgtype(itypestr2);
                            intermediarysDao.save(ii);
                        } else {
                            i.setSorgtype(itype);
                            intermediarysDao.save(i);
                        }
                    }
                }
                //不是部门的机构新增默认用户和关系数据
                if (iisdepartment == Nums.NO.getValue()) {
                    Users user = new Users();
                    user.setSname(username);
                    user.setSphone(userphone);
                    if ("register".equals(register)) {
                        user.setIstate(States.APPLY.getValue());
                    } else {
                        user.setIstate(States.ENABLE.getValue());
                    }
                    user.setItype(UserTypes.ORDINARY.getValue());
                    user.setScreateuserid(userid);
                    user.setSupdateuserid(userid);
                    user.setLdtcreatetime(LocalDateTime.now());
                    user.setLdtupdatetime(LocalDateTime.now());
                    user.setSusername(usersService.getEnableUserName());
                    user.setSpassword(Commons.getDefaultPasswordByPhone(userphone));
                    Users newuser = usersDao.save(user);
                    //管理员
                    Organduserrefs oamanager = new Organduserrefs();
                    oamanager.setScreateuserid(userid);
                    oamanager.setSupdateuserid(userid);
                    oamanager.setLdtcreatetime(LocalDateTime.now());
                    oamanager.setLdtupdatetime(LocalDateTime.now());
                    oamanager.setIstate(States.ENABLE.getValue());
                    oamanager.setSorgid(o.getSid());
                    oamanager.setIpermissionlevel(PermissionTypes.ALL.getValue());
                    oamanager.setIusertype(UserTypes.MANAGER.getValue());
                    Organduserrefs newoau = organduserrefsDao.save(oamanager);
                    //普通用户
                    Organduserrefs oau = new Organduserrefs();
                    oau.setScreateuserid(userid);
                    oau.setSupdateuserid(userid);
                    oau.setLdtcreatetime(LocalDateTime.now());
                    oau.setLdtupdatetime(LocalDateTime.now());
                    oau.setIstate(States.ENABLE.getValue());
                    oau.setSorgid(o.getSid());
                    oau.setSuserid(newuser.getSid());
                    oau.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
                    oau.setIusertype(UserTypes.ORDINARY.getValue());
                    oau.setSmanagerid(newoau.getSid());
                    organduserrefsDao.save(oau);
                    if (sfunctiongroupid != null) {
                        String fgidlist[] = sfunctiongroupid.split(",");
                        for (String fgid : fgidlist) {
                            Functiongroupanduserrefs fgaur = new Functiongroupanduserrefs();
                            fgaur.setIstate(States.ENABLE.getValue());
                            fgaur.setLdtcreatetime(LocalDateTime.now());
                            fgaur.setLdtupdatetime(LocalDateTime.now());
                            fgaur.setScreateuserid(userid);
                            fgaur.setSupdateuserid(userid);
                            fgaur.setSrefid(newoau.getSid());
                            fgaur.setSorgid(o.getSparentid());
                            fgaur.setSfunctiongroupid(fgid);
                            functiongroupanduserrefsDao.save(fgaur);
                        }
                    }
                }
                //新增附件相关
                if (spathattch != null && spathattch.length > 0) {
                    for (int i = 0; i < spathattch.length; i++) {
                        Commonattachs c = new Commonattachs();
                        c.setItype(CommonAttachTypes.ORG_LICENSE.getValue());
                        c.setIstate(States.ENABLE.getValue());
                        c.setLdtcreatetime(LocalDateTime.now());
                        c.setLdtupdatetime(LocalDateTime.now());
                        c.setScreateuserid(userid);
                        c.setSupdateuserid(userid);
                        c.setSdataid(newo.getSid());
                        c.setSname(snameattch[i]);
                        c.setSpath(spathattch[i]);
                        commonattachsDao.save(c);
                    }
                }
                if (filespathattch != null && filespathattch.length > 0) {
                    for (int i = 0; i < filespathattch.length; i++) {
                        //新增Orgoruseranddictionarierefs并保存
                        Orgoruseranddictionarierefs oou = new Orgoruseranddictionarierefs();
                        oou.setIstate(States.ENABLE.getValue());
                        oou.setSdesc(sdesc);
                        oou.setSdictionarieid(filesdictionarieidattch[i]);
                        oou.setSorgidoruserid(newo.getSid());
                        if (filesdescattch[i].equals("0")) {
                            oou.setSdesc(null);
                        } else {
                            oou.setSdesc(filesdescattch[i]);
                        }
                        oou.setLdtcreatetime(LocalDateTime.now());
                        oou.setLdtupdatetime(LocalDateTime.now());
                        oou.setScreateuserid(userid);
                        oou.setSupdateuserid(userid);
                        Orgoruseranddictionarierefs newoou = orgoruseranddictionarierefsDao.save(oou);
                        if (!filespathattch[i].equals("0")) {
                            Commonattachs c = new Commonattachs();
                            c.setItype(CommonAttachTypes.ORG_APTITUDE.getValue());
                            c.setIstate(States.ENABLE.getValue());
                            c.setLdtcreatetime(LocalDateTime.now());
                            c.setLdtupdatetime(LocalDateTime.now());
                            c.setScreateuserid(userid);
                            c.setSupdateuserid(userid);
                            c.setSdataid(newoou.getSid());
                            c.setSname(filesnameattch[i]);
                            c.setSpath(filespathattch[i]);
                            commonattachsDao.save(c);
                        }
                    }
                }
                map.put("state", true);
                map.put("isSuccess", "操作成功!");
            }
        } else {//编辑
            //判断名称是否重复
            if (flag && !id.equals(sid)) {
                map.put("state", false);
                map.put("isSuccess", "存在重复机构名称!");
            } else {
                if (org != null && !sid.equals(org.getSid()) && iisdepartment == Nums.NO.getValue()) {
                    map.put("state", false);
                    map.put("isSuccess", "该地区已有审计局存在!");
                    return map;
                }
                //获取orgs
                Orgs o = orgsDao.getBySid(sid);
                String type = o.getItype().toString();
                if (iisdepartment == Nums.YES.getValue() && o.getIisdepartment() == Nums.NO.getValue()) {
                    List<Orgs> iisdepartmentlist = orgsDao.findBySparentidAndIstateNotAndIstateNot(sid, States.DELETE.getValue(), States.REJECT.getValue());
                    if (iisdepartmentlist.size() > 0) {
                        map.put("state", false);
                        map.put("isSuccess", "该机构无法更改为部门!");
                        return map;
                    }
                }

                o.setItype(itype);
                o.setSdes(sdes);
                o.setSname(sname);
                o.setSupdateuserid(userid);
                o.setLdtupdatetime(LocalDateTime.now());
                o.setLusernumber(lusernumber);
                o.setSparentid(sparentid);
                o.setIisdepartment(iisdepartment);
                o.setSareaid(areaid);
                orgsDao.save(o);
                //不是部门的机构新增默认用户和关系数据
                List<Users> users = usersDao.findUserByOrgId(o.getSid());
                if (users.size() == 0) {
                    if (iisdepartment == Nums.NO.getValue()) {
                        Users user = new Users();
                        user.setSname(username);
                        user.setSphone(userphone);
                        user.setIstate(States.ENABLE.getValue());
                        user.setItype(UserTypes.ORDINARY.getValue());
                        user.setScreateuserid(userid);
                        user.setSupdateuserid(userid);
                        user.setLdtcreatetime(LocalDateTime.now());
                        user.setLdtupdatetime(LocalDateTime.now());
                        user.setSusername(usersService.getEnableUserName());
                        user.setSpassword(Commons.getDefaultPasswordByPhone(userphone));
                        Users newuser = usersDao.save(user);
                        //管理员
                        Organduserrefs oamanager = new Organduserrefs();
                        oamanager.setScreateuserid(userid);
                        oamanager.setSupdateuserid(userid);
                        oamanager.setLdtcreatetime(LocalDateTime.now());
                        oamanager.setLdtupdatetime(LocalDateTime.now());
                        oamanager.setIstate(States.ENABLE.getValue());
                        oamanager.setSorgid(o.getSid());
                        oamanager.setIpermissionlevel(PermissionTypes.ALL.getValue());
                        oamanager.setIusertype(UserTypes.MANAGER.getValue());
                        Organduserrefs newoau = organduserrefsDao.save(oamanager);
                        //普通用户
                        Organduserrefs oau = new Organduserrefs();
                        oau.setScreateuserid(userid);
                        oau.setSupdateuserid(userid);
                        oau.setLdtcreatetime(LocalDateTime.now());
                        oau.setLdtupdatetime(LocalDateTime.now());
                        oau.setIstate(States.ENABLE.getValue());
                        oau.setSorgid(o.getSid());
                        oau.setSuserid(newuser.getSid());
                        oau.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
                        oau.setIusertype(UserTypes.ORDINARY.getValue());
                        oau.setSmanagerid(newoau.getSid());
                        organduserrefsDao.save(oau);
                        if (sfunctiongroupid != null) {
                            String fgidlist[] = sfunctiongroupid.split(",");
                            for (String fgid : fgidlist) {
                                Functiongroupanduserrefs fgaur = new Functiongroupanduserrefs();
                                fgaur.setIstate(States.ENABLE.getValue());
                                fgaur.setLdtcreatetime(LocalDateTime.now());
                                fgaur.setLdtupdatetime(LocalDateTime.now());
                                fgaur.setScreateuserid(userid);
                                fgaur.setSupdateuserid(userid);
                                fgaur.setSrefid(newoau.getSid());
                                fgaur.setSorgid(o.getSparentid());
                                fgaur.setSfunctiongroupid(fgid);
                                functiongroupanduserrefsDao.save(fgaur);
                            }
                        }
                    }
                } else {
                    if (iisdepartment == Nums.NO.getValue()) {
                        users.get(0).setSname(username);
                        users.get(0).setSphone(userphone);
                        users.get(0).setLdtupdatetime(LocalDateTime.now());
                        users.get(0).setSupdateuserid(userid);
                        usersDao.save(users.get(0));
                        List<Map<String, Object>> list = functionGroupsDao.getCheckByFAU(o.getSid());
                        Organduserrefs oo = organduserrefsDao.getBySorgidAndIusertype(o.getSid(), UserTypes.MANAGER.getValue());
                        if (sfunctiongroupid != null) {
                            String fgidlist[] = sfunctiongroupid.split(",");
                            List<String> alllist = new ArrayList<>();
                            List<String> checklist = new ArrayList<>();
                            List<String> dellist = new ArrayList<>();
                            for (String fgid : fgidlist) {
                                alllist.add(fgid);
                            }
                            for (String fgid : alllist) {
                                for (Map<String, Object> m : list) {
                                    if (m.get("sfunctiongroupid").toString().equals(fgid)) {
                                        checklist.add(fgid);
                                    }
                                }
                            }
                            alllist.removeAll(checklist);
                            for (String fgid : alllist) {
                                Functiongroupanduserrefs fgaur = new Functiongroupanduserrefs();
                                fgaur.setIstate(States.ENABLE.getValue());
                                fgaur.setLdtcreatetime(LocalDateTime.now());
                                fgaur.setLdtupdatetime(LocalDateTime.now());
                                fgaur.setScreateuserid(userid);
                                fgaur.setSupdateuserid(userid);
                                fgaur.setSrefid(oo.getSid());
                                fgaur.setSorgid(o.getSparentid());
                                fgaur.setSfunctiongroupid(fgid);
                                functiongroupanduserrefsDao.save(fgaur);
                            }
                            alllist.addAll(checklist);
                            for (Map<String, Object> m : list) {
                                Boolean f = true;
                                for (String fgid : alllist) {
                                    if (m.get("sfunctiongroupid").toString().equals(fgid)) {
                                        f = false;
                                    }
                                }
                                if (f) {
                                    dellist.add(m.get("sfunctiongroupid").toString());
                                }
                            }
                            List<Functiongroupanduserrefs> functiongroupanduserrefslist = functiongroupanduserrefsDao.findBySrefidAndSfunctiongroupidIn(oo.getSid(), dellist);
                            functiongroupanduserrefsDao.deleteAll(functiongroupanduserrefslist);
                        } else if (list != null && flag2) {
                            List<String> dellist = new ArrayList<>();
                            for (Map<String, Object> m : list) {
                                dellist.add(m.get("sfunctiongroupid").toString());
                            }
                            List<Functiongroupanduserrefs> functiongroupanduserrefslist = functiongroupanduserrefsDao.findBySrefidAndSfunctiongroupidIn(oo.getSid(), dellist);
                            functiongroupanduserrefsDao.deleteAll(functiongroupanduserrefslist);
                        }
                    } else {
                        List<String> orgidlist = new ArrayList<>();
                        orgidlist.add(o.getSid());
                        List<Organduserrefs> oo = organduserrefsDao.getBySorgidIn(orgidlist);
                        for (Organduserrefs oaur : oo) {
                            if (oaur.getIusertype().equals(UserTypes.MANAGER.getValue())) {
                                organduserrefsDao.delete(oaur);
                            } else {
                                oaur.setSmanagerid(null);
                                organduserrefsDao.save(oaur);
                            }
                        }

                    }
                }

                /*//判断为机构类型不为审计局时，新增数据到关系库
                if (!itype.equals(OrgTypes.AUDIT.getValue().toString()) && type.equals(OrgTypes.AUDIT.getValue().toString())) {
                    for (String newsauditorgid : sauditorgidlist) {
                        Intermediarys i = new Intermediarys();
                        i.setIstate(States.ENABLE.getValue());
                        i.setScreateuserid(userid);
                        i.setSupdateuserid(userid);
                        i.setLdtcreatetime(LocalDateTime.now());
                        i.setLdtupdatetime(LocalDateTime.now());
                        i.setSintermediaryorgid(o.getSid());
                        i.setSauditorgid(newsauditorgid);
                        intermediarysDao.save(i);
                    }
                }
                //判断机构类型从其他机构修改为审计局时删除关系库数据
                if (itype.equals(OrgTypes.AUDIT.getValue().toString()) && !type.equals(OrgTypes.AUDIT.getValue().toString())) {
                    List<Intermediarys> ilist = intermediarysDao.getBySintermediaryorgidAndIstate(sid, States.ENABLE.getValue());
                    for (Intermediarys i : ilist) {
                        i.setIstate(States.DELETE.getValue());
                        i.setLdtupdatetime(LocalDateTime.now());
                        i.setSupdateuserid(userid);
                    }
                    intermediarysDao.saveAll(ilist);
                }
                //修改Intermediarys
                if (!itype.equals(OrgTypes.AUDIT.getValue().toString()) && !type.equals(OrgTypes.AUDIT.getValue().toString())) {
                    List<Intermediarys> ilist = intermediarysDao.getBySintermediaryorgidAndIstate(sid, States.ENABLE.getValue());
                    for (int i = 0; i < ilist.size(); i++) {
                        boolean f = false;
                        for (int j = 0; j < sauditorgidlist.length; j++) {
                            if (ilist.get(i).getSauditorgid().equals(sauditorgidlist[j])) {
                                f = true;
                                break;
                            }
                        }
                        if (!f) {
                            ilist.get(i).setIstate(States.DELETE.getValue());
                            ilist.get(i).setLdtupdatetime(LocalDateTime.now());
                            ilist.get(i).setSupdateuserid(userid);
                        }
                    }
                    for (int j = 0; j < sauditorgidlist.length; j++) {
                        boolean f = false;
                        for (int i = 0; i < ilist.size(); i++) {
                            if (ilist.get(i).getSauditorgid().equals(sauditorgidlist[j])) {
                                f = true;
                                break;
                            }
                        }
                        if (!f) {
                            Intermediarys i = new Intermediarys();
                            i.setIstate(States.ENABLE.getValue());
                            i.setScreateuserid(userid);
                            i.setSupdateuserid(userid);
                            i.setLdtcreatetime(LocalDateTime.now());
                            i.setLdtupdatetime(LocalDateTime.now());
                            i.setSintermediaryorgid(o.getSid());
                            i.setSauditorgid(sauditorgidlist[j]);
                            ilist.add(i);
                        }
                    }
                    intermediarysDao.saveAll(ilist);
                }*/
                if (spathattch != null && spathattch.length > 0) {
                    for (int i = 0; i < spathattch.length; i++) {
                        Commonattachs c = new Commonattachs();
                        c.setItype(CommonAttachTypes.ORG_LICENSE.getValue());
                        c.setIstate(States.ENABLE.getValue());
                        c.setLdtcreatetime(LocalDateTime.now());
                        c.setLdtupdatetime(LocalDateTime.now());
                        c.setScreateuserid(userid);
                        c.setSupdateuserid(userid);
                        c.setSdataid(o.getSid());
                        c.setSname(snameattch[i]);
                        c.setSpath(spathattch[i]);
                        commonattachsDao.save(c);
                    }
                }
                if (filespathattch != null && filespathattch.length > 0) {
                    for (int i = 0; i < filespathattch.length; i++) {
                        //根据orgid获取Orgoruseranddictionarierefs修改后保存
                        Orgoruseranddictionarierefs newoou = new Orgoruseranddictionarierefs();
                        if (filesidattch[i].equals("0")) {
                            Orgoruseranddictionarierefs oounew = new Orgoruseranddictionarierefs();
                            oounew.setIstate(States.ENABLE.getValue());
                            oounew.setSdictionarieid(filesdictionarieidattch[i]);
                            oounew.setSorgidoruserid(o.getSid());
                            if (filesdescattch[i].equals("0")) {
                                oounew.setSdesc(null);
                            } else {
                                oounew.setSdesc(filesdescattch[i]);
                            }
                            oounew.setLdtcreatetime(LocalDateTime.now());
                            oounew.setLdtupdatetime(LocalDateTime.now());
                            oounew.setScreateuserid(userid);
                            oounew.setSupdateuserid(userid);
                            newoou = orgoruseranddictionarierefsDao.save(oounew);
                            if (!filespathattch[i].equals("0")) {
                                Commonattachs c = new Commonattachs();
                                c.setItype(CommonAttachTypes.ORG_APTITUDE.getValue());
                                c.setIstate(States.ENABLE.getValue());
                                c.setLdtcreatetime(LocalDateTime.now());
                                c.setLdtupdatetime(LocalDateTime.now());
                                c.setScreateuserid(userid);
                                c.setSupdateuserid(userid);
                                c.setSdataid(newoou.getSid());
                                c.setSname(filesnameattch[i]);
                                c.setSpath(filespathattch[i]);
                                commonattachsDao.save(c);
                            }
                        } else {
                            Orgoruseranddictionarierefs oou = orgoruseranddictionarierefsDao.getBySid(filesidattch[i]);
                            oou.setSdictionarieid(filesdictionarieidattch[i]);
                            if (filesdescattch[i].equals("0")) {
                                oou.setSdesc(null);
                            } else {
                                oou.setSdesc(filesdescattch[i]);
                            }
                            oou.setLdtupdatetime(LocalDateTime.now());
                            oou.setSupdateuserid(userid);
                            newoou = orgoruseranddictionarierefsDao.save(oou);
                            Commonattachs oldc = commonattachsDao.getBySdataid(newoou.getSid());
                            if (oldc == null) {
                                if (!filespathattch[i].equals("0")) {
                                    Commonattachs c = new Commonattachs();
                                    c.setItype(CommonAttachTypes.ORG_APTITUDE.getValue());
                                    c.setIstate(States.ENABLE.getValue());
                                    c.setLdtcreatetime(LocalDateTime.now());
                                    c.setLdtupdatetime(LocalDateTime.now());
                                    c.setScreateuserid(userid);
                                    c.setSupdateuserid(userid);
                                    c.setSdataid(newoou.getSid());
                                    c.setSname(filesnameattch[i]);
                                    c.setSpath(filespathattch[i]);
                                    commonattachsDao.save(c);
                                }
                            } else {
                                if (!filespathattch[i].equals("0")) {
                                    oldc.setSname(filesnameattch[i]);
                                    oldc.setSpath(filespathattch[i]);
                                    oldc.setLdtupdatetime(LocalDateTime.now());
                                    oldc.setSupdateuserid(userid);
                                    commonattachsDao.save(oldc);
                                } else {
                                    commonattachsDao.delete(oldc);
                                }
                            }
                        }
                    }
                }
                map.put("state", true);
                map.put("isSuccess", "操作成功!");
            }
        }
        return map;
    }

    /**
     * 查询机构授权功能组
     *
     * @return 功能组对象
     * @author lirui 2018年5月23日
     */
    public Map<String, Object> getAuthorizationFunctionGroups(String orgid, Integer usertype, String orgtype, String parentid, Integer iisdepartment) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> checklist = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> headlist = new ArrayList<Map<String, Object>>();
        List<Functiongroups> functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
        for (Functiongroups fun : functiongroups) {
            Map<String, Object> map = new HashMap<>();
            map.put("head", fun.getSid().toString());
            map.put("sname", fun.getSname().toString());
            headlist.add(map);
        }
        List<Map<String, Object>> userslist = usersDao.getUsersByOrg();
        List<Map<String, Object>> userlist = new ArrayList<>();
        if (usertype.equals(UserTypes.ADMIN.getValue())) {
            userlist.addAll(userslist);
        } else if (orgtype.equals(OrgTypes.AUDIT.getValue().toString())) {
            Boolean flag = false;
            if (parentid == null) {
                flag = true;
            } else if (parentid != null && iisdepartment.equals(Nums.YES.getValue())) {
                Orgs o = orgsDao.getBySid(parentid);
                if (o.getSparentid() == null) {
                    flag = true;
                }
            }
            if (flag) {
                for (Map<String, Object> m : userslist) {
                    if (m.get("sparentid") != null) {
                        userlist.add(m);
                    }
                }
            }
        } else {
            return null;
        }
        List<Functiongroupanduserrefs> Functiongroupanduserrefs = functiongroupanduserrefsDao.findByIstate(States.ENABLE.getValue());
        for (Functiongroups fun : functiongroups) {
            for (Map<String, Object> map : userlist) {
                for (Functiongroupanduserrefs f : Functiongroupanduserrefs) {
                    if (fun.getSid().equals(f.getSfunctiongroupid()) && map.get("sid").equals(f.getSrefid())) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("check", map.get("sid").toString() + "_" + fun.getSid());
                        checklist.add(m);
                    }
                }
            }
        }
        for (Map<String, Object> map : userlist) {
            Map<String, Object> m = new HashMap<>();
            for (Functiongroups fun : functiongroups) {
                m.put(fun.getSid(), map.get("sid").toString() + "_" + fun.getSid());
            }
            m.put("sname", map.get("sname"));
            m.put("id", map.get("sid"));
            list.add(m);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("checklist", checklist);
        map.put("headlist", headlist);
        return map;
    }

    /**
     * 新增或者编辑功能组授权数据
     *
     * @param authorization 授权字符串
     * @return 操作结果信息
     * @author lirui 2018年4月11日
     */
    public Result UpdateAuthorizationOrg(String authorization, String userid, String orgid, Integer usertype, String orgtype, String parentid, Integer iisdepartment) {
        LocalDateTime time = LocalDateTime.now();
        List<Map<String, Object>> check = new ArrayList<>();
        //已勾选数据
        List<Functiongroups> functiongroups = functionGroupsDao.getBySorgidAndIstate(orgid, States.ENABLE.getValue());
        List<Map<String, Object>> userslist = usersDao.getUsersByOrg();
        List<Map<String, Object>> userlist = new ArrayList<>();
        if (usertype.equals(UserTypes.ADMIN.getValue())) {
            userlist.addAll(userslist);
        } else if (orgtype.equals(OrgTypes.AUDIT.getValue().toString())) {
            Boolean flag = false;
            if (parentid == null) {
                flag = true;
            } else if (parentid != null && iisdepartment.equals(Nums.YES.getValue())) {
                Orgs o = orgsDao.getBySid(parentid);
                if (o.getSparentid() == null) {
                    flag = true;
                }
            }
            if (flag) {
                for (Map<String, Object> m : userslist) {
                    if (m.get("sparentid") != null) {
                        userlist.add(m);
                    }
                }
            }
        } else {
            return null;
        }
        List<Functiongroupanduserrefs> Functiongroupanduserrefs = functiongroupanduserrefsDao.findByIstate(States.ENABLE.getValue());
        for (Functiongroups fun : functiongroups) {
            for (Map<String, Object> map : userlist) {
                for (Functiongroupanduserrefs f : Functiongroupanduserrefs) {
                    if (fun.getSid().equals(f.getSfunctiongroupid()) && map.get("sid").equals(f.getSrefid())) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("functiongroupid", f.getSfunctiongroupid());
                        m.put("userid", f.getSrefid());
                        m.put("sid", f.getSid());
                        check.add(m);
                    }
                }
            }
        }
        List<String> idlist = new ArrayList<>();//需删除数据id
        if (authorization == null) {
            for (Map<String, Object> m : check) {
                idlist.add(m.get("sid").toString());
            }
            List<Functiongroupanduserrefs> functiongroupanduserrefs = functiongroupanduserrefsDao.getBySidIn(idlist);
            for (Functiongroupanduserrefs f : functiongroupanduserrefs) {
                f.setIstate(States.DELETE.getValue());
                f.setLdtupdatetime(time);
                f.setSupdateuserid(userid);
                functiongroupanduserrefsDao.save(f);
            }
            return Result.success("操作成功！");
        }
        String[] authorizationlist = authorization.split(",");//全部勾选数据数组
        List<Functiongroupanduserrefs> list = new ArrayList<>();//新增勾选数据
        for (Map<String, Object> m : check) {//提取需删除数据id
            boolean flag = true;//判断是否取消勾选
            for (String s : authorizationlist) {
                String[] str = s.split("_");
                if (str[0].equals(m.get("userid").toString()) && str[1].equals(m.get("functiongroupid").toString())) {
                    flag = false;
                }
            }
            if (flag) {
                idlist.add(m.get("sid").toString());
            }
        }
        for (String s : authorizationlist) {
            boolean flag = true;//判断是否为新增数据
            String[] str = s.split("_");
            for (Map<String, Object> m : check) {
                if (str[0].equals(m.get("userid").toString()) && str[1].equals(m.get("functiongroupid").toString())) {
                    flag = false;
                }
            }
            if (flag) {
                Functiongroupanduserrefs fgaur = new Functiongroupanduserrefs();
                fgaur.setIstate(States.ENABLE.getValue());
                fgaur.setLdtcreatetime(time);
                fgaur.setLdtupdatetime(time);
                fgaur.setScreateuserid(userid);
                fgaur.setSupdateuserid(userid);
                fgaur.setSrefid(str[0]);
                fgaur.setSorgid(orgid);
                fgaur.setSfunctiongroupid(str[1]);
                list.add(fgaur);
            }
        }
        //删除数据
        List<Functiongroupanduserrefs> functiongroupanduserrefs = functiongroupanduserrefsDao.getBySidIn(idlist);
        for (Functiongroupanduserrefs f : functiongroupanduserrefs) {
            f.setIstate(States.DELETE.getValue());
            f.setLdtupdatetime(time);
            f.setSupdateuserid(userid);
            functiongroupanduserrefsDao.save(f);
        }
        functiongroupanduserrefsDao.saveAll(list);
        return Result.success("操作成功！");
    }

    /**
     * 启用或禁用或删除机构
     *
     * @param sid
     * @param istate
     * @return
     * @author lirui 2018年3月22日
     */
    public Map<String, Object> enableOrDisableOrgs(String sid, Integer istate, String userid) {
        Map<String, Object> map = new HashMap<String, Object>();
        Orgs o = orgsDao.getBySid(sid);
        o.setIstate(istate);
        orgsDao.save(o);
        List<String> idlist = new ArrayList<>();
        idlist.add(o.getSid());
        //同时更改所有子节点状态
        List<Orgs> list = orgsDao.findBySparentidAndIstateNotAndIstateNotAndIstateNot(sid, States.APPLY.getValue(), States.DELETE.getValue(), States.REJECT.getValue());
        List<String> str = new ArrayList<>();
        int num = 0;
        if (list.size() > 0) {
            for (int i = 0; i < 1; ) {
                List<Orgs> listorg = new ArrayList<>();
                listorg.addAll(list);
                if (num > 0) {
                    //清空listorg
                    int num1 = listorg.size();
                    for (int j = 0; j < num1; j++) {
                        listorg.remove(0);
                    }
                    //根据上一节点的所有子节点为父级查询所有下一级节点
                    List<Orgs> sonorg = orgsDao.findBySparentidInAndIstateNotAndIstateNotAndIstateNot(str, States.APPLY.getValue(), States.DELETE.getValue(), States.REJECT.getValue());
                    if (sonorg.size() > 0) {
                        listorg.addAll(sonorg);
                        //清空str
                        int num2 = str.size();
                        for (int k = 0; k < num2; k++) {
                            str.remove(0);
                        }
                        num = 0;
                    } else {
                        break;
                    }
                }
                //更改所有子级状态
                for (Orgs org : listorg) {
                    org.setIstate(istate);
                    org.setLdtupdatetime(LocalDateTime.now());
                    org.setSupdateuserid(userid);
                    str.add(org.getSid());
                    idlist.add(org.getSid());
                    num++;
                }
                orgsDao.saveAll(listorg);
            }
        }
        //更改对应user状态
        List<Organduserrefs> organduserrefslist = organduserrefsDao.getBySorgidIn(idlist);
        if (organduserrefslist != null) {
            if (istate == States.DISABLE.getValue() || istate == States.DELETE.getValue()) {
                List<String> useridlist = new ArrayList<>();
                for (Organduserrefs of : organduserrefslist) {
                    if (of.getSuserid() != null) {
                        useridlist.add(of.getSuserid());
                    }
                }
                List<Users> userlist = new ArrayList<>();
                if (istate == States.DELETE.getValue()) {
                    userlist = usersDao.getBySidInAndIstate(useridlist, States.DISABLE.getValue());
                } else {
                    userlist = usersDao.getBySidInAndIstate(useridlist, States.ENABLE.getValue());
                }
                for (Users u : userlist) {
                    u.setSupdateuserid(userid);
                    u.setLdtupdatetime(LocalDateTime.now());
                    u.setIstate(istate);
                }
                usersDao.saveAll(userlist);
                if (istate == States.DISABLE.getValue()) {
                    for (Organduserrefs of : organduserrefslist) {
                        if (of.getSuserid() != null) {
                            Orgdisableref orgdisableref = new Orgdisableref();
                            orgdisableref.setIstate(States.ENABLE.getValue());
                            orgdisableref.setLdtcreatetime(LocalDateTime.now());
                            orgdisableref.setLdtupdatetime(LocalDateTime.now());
                            orgdisableref.setScreateuserid(userid);
                            orgdisableref.setSupdateuserid(userid);
                            orgdisableref.setSorgid(of.getSorgid());
                            orgdisableref.setSuserid(of.getSuserid());
                            orgdisablerefDao.save(orgdisableref);
                        }
                    }
                }
            }
            if (istate == States.ENABLE.getValue()) {
                List<Orgdisableref> orgdisablereflist = orgdisablerefDao.getBySorgidInAndIstate(idlist, States.ENABLE.getValue());
                List<String> useridlist = new ArrayList<>();
                for (Orgdisableref orgdisableref : orgdisablereflist) {
                    useridlist.add(orgdisableref.getSuserid());
                    orgdisableref.setIstate(States.DISABLE.getValue());
                    orgdisableref.setLdtupdatetime(LocalDateTime.now());
                    orgdisableref.setSupdateuserid(userid);
                }
                orgdisablerefDao.saveAll(orgdisablereflist);
                List<Users> userlist = usersDao.getBySidInAndIstate(useridlist, States.DISABLE.getValue());
                for (Users u : userlist) {
                    u.setSupdateuserid(userid);
                    u.setLdtupdatetime(LocalDateTime.now());
                    u.setIstate(istate);
                }
                usersDao.saveAll(userlist);
            }
        }
        map.put("meg", "操作成功!");
        return map;
    }

    /**
     * 查询机构资质数据。
     *
     * @return
     * @author lirui 2018年3月22日。
     */
    public List<Map<String, Object>> getOrgQua() {
        List<Dictionaries> dic = dictionariesDao.getByItype(DictionarieTypes.ORG_APTITUDE.getValue());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Dictionaries d : dic) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", d.getSname());
            map.put("id", d.getSid());
            list.add(map);
        }
        return list;
    }

    /**
     * 查询非部门机构数据。
     *
     * @return
     * @author lirui 2018年5月23日。
     */
    public List<Map<String, Object>> getIsNotDepartmentOrgs() {
        List<Orgs> orgs = orgsDao.findByIstateAndIisdepartment(States.ENABLE.getValue(), Nums.NO.getValue());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = new HashMap<>();
        m.put("sname", "请选择");
        m.put("sid", "-1");
        m.put("sparentid", null);
        m.put("sparenttype", null);
        list.add(m);
        for (Orgs o : orgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("sname", o.getSname());
            map.put("sid", o.getSid());
            map.put("sparentid", o.getSparentid());
            map.put("sparenttype", o.getItype());
            list.add(map);
        }
        return list;
    }

    /**
     * 查询机构数据。
     *
     * @param sid
     * @return
     * @author lirui 2018年3月24日。
     */
    public List<Map<String, Object>> getOrgs() {
        List<Orgs> orgs = orgsDao.findByIstate(States.ENABLE.getValue());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        //从orgs中删除当前机构
        for (Orgs o : orgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("sname", o.getSname());
            map.put("sid", o.getSid());
            map.put("sparentid", o.getSparentid());
            map.put("sparenttype", o.getItype());
            list.add(map);
        }
        return list;
    }

    /**
     * 查询审计机构数据。
     *
     * @return
     * @author lirui 2018年3月26日。
     */
    public List<Map<String, Object>> getRegisterOrgs(String sid) {
        List<Orgs> orgs = new ArrayList<>();
        if ("".equals(sid) || sid == null) {
            orgs = orgsDao.findByIstateAndIisdepartmentAndItype(States.ENABLE.getValue(), Nums.NO.getValue(), OrgTypes.AUDIT.getValue().toString());
        } else {
            orgs = orgsDao.findByIstateAndIisdepartmentAndItypeAndSidNot(States.ENABLE.getValue(), Nums.NO.getValue(), OrgTypes.AUDIT.getValue().toString(), sid);
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        //封装list
        for (Orgs o : orgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("sname", o.getSname());
            map.put("sid", o.getSid());
            map.put("sparentid", o.getSparentid());
            list.add(map);
        }
        return list;
    }

    /**
     * 查询审计机构数据。
     *
     * @return
     * @author lirui 2018年6月29日。
     */
    public List<Map<String, Object>> getAuditOrgs() {
        List<Orgs> orgs = orgsDao.findByIstateAndIisdepartmentAndItype(States.ENABLE.getValue(), Nums.NO.getValue(), OrgTypes.AUDIT.getValue().toString());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = new HashMap<>();
        m.put("sname", "请选择");
        m.put("sid", "-1");
        m.put("sparentid", null);
        m.put("sparenttype", null);
        list.add(m);
        for (Orgs o : orgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("sname", o.getSname());
            map.put("sid", o.getSid());
            map.put("sparentid", o.getSparentid());
            map.put("sparenttype", o.getItype());
            list.add(map);
        }
        return list;
    }

    /**
     * 查询父级机构数据。
     *
     * @return
     * @author lirui 2018年4月9日。
     */
    public List<Map<String, Object>> getParentOrgs() {
        List<Orgs> orgs = orgsDao.findByIstateAndItypeNotAndIisdepartment(States.ENABLE.getValue(), OrgTypes.AUDIT.getValue().toString(), Nums.NO.getValue());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        //封装list
        for (Orgs o : orgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("sname", o.getSname());
            map.put("sid", o.getSid());
            map.put("sparentid", o.getSparentid());
            list.add(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("sname", "请选择父级机构");
        map.put("sid", "-1");
        list.add(0, map);
        return list;
    }

    /**
     * 根据sid查询机构详情
     *
     * @return
     * @author lirui 2018年3月21日
     */
    public Map<String, Object> getOrgInfo(String sid) {
        List<Map<String, Object>> map = orgsDao.getOrgBySid(sid);
        List<Users> users = usersDao.findUserByOrgId(sid);
        if (users.size() > 0) {
            map.get(0).put("username", users.get(0).getSname());
            map.get(0).put("userphone", users.get(0).getSphone());
        }
        if (!map.get(0).get("itype").toString().equals(OrgTypes.AUDIT.getValue().toString()) && !map.get(0).get("itype").toString().equals(OrgTypes.REFORM.getValue().toString()) && !map.get(0).get("itype").toString().equals(OrgTypes.GOVERNMENT.getValue().toString()) && !map.get(0).get("itype").toString().equals(OrgTypes.FINANCE.getValue().toString())) {
            List<Intermediarys> ilist = intermediarysDao.getBySintermediaryorgidAndIstate(sid, States.ENABLE.getValue());
            List<String> ids = new ArrayList<>();
            for (int i = 0; i < ilist.size(); i++) {
                ids.add(ilist.get(i).getSauditorgid());
            }
            List<Orgs> orgs = orgsDao.findBySidInAndIstate(ids, States.ENABLE.getValue());
            String str = "";
            for (Orgs o : orgs) {
                if (str.equals("")) {
                    str = o.getSname();
                } else {
                    str += "," + o.getSname();
                }
            }
            map.get(0).put("sauditorgid", str);
        }
        //查询机构资质附件
        List<Map<String, Object>> maplist = orgoruseranddictionarierefsDao.getCommonattachsBySorgidoruserid(sid);
        map.get(0).put("attch2", maplist);
        //查询机构营业执照附件
        List<Commonattachs> com2 = commonattachsDao.findBySdataidAndIstateAndItype(sid, States.ENABLE.getValue(), CommonAttachTypes.ORG_LICENSE.getValue());
        List<Map<String, Object>> list2 = new ArrayList<>();
        for (Commonattachs c : com2) {
            Map<String, Object> m = new HashMap<>();
            m.put("sname", c.getSname());
            m.put("spath", c.getSpath());
            m.put("sid", c.getSid());
            list2.add(m);
        }
        map.get(0).put("attch1", list2);
        return map.get(0);
    }

    /**
     * 查询机构数据(弹出框)。
     *
     * @return
     * @author lirui 2018年5月11日。
     */
    public List<Map<String, Object>> getParentOrgs(String sname) {
        return orgsDao.getOrgs(sname);
    }

    /**
     * 新增编辑页面父级机构下拉框数据。
     *
     * @return
     * @author lirui 2018年5月15日。
     */
    public List<Map<String, Object>> getUpdataOrgParentIdData(Integer usertype, String orgid, List<Integer> orgtype, Integer iisdepartment, String sparentid, String sid) {
        List<Map<String, Object>> list = orgsDao.queryOrgs(States.ENABLE.getValue(), null, null, null);//全部org
        List<Map<String, Object>> orglist = new ArrayList<>();//展示的org
        if (usertype.equals(UserTypes.ADMIN.getValue())) {//用户类型为admin
            //展示所有审计机构和部门
            for (Map<String, Object> map : list) {
                if (map.get("itype").toString().equals(OrgTypes.AUDIT.getValue().toString()) && map.get("iisdepartment").toString().equals(Nums.NO.getValue().toString())) {
                    orglist.add(map);
                }
            }
            //            orglist.addAll(list);
        } else {//用户类型为其他类型
            List<String> org = new ArrayList<>();
            if (iisdepartment == Nums.YES.getValue()) {//是部门
                org.add(sparentid);
            } else {//不是部门
                org.add(orgid);
            }
            for (Map<String, Object> map : list) {
                if (org.get(0).equals(map.get("sid").toString())) {
                    orglist.add(map);
                }
            }
            List<String> idlist = new ArrayList<>();
            //展示所属机构和部门
            for (int i = 0; i == 0; ) {
                for (int j = 0; j < org.size(); j++) {
                    for (Map<String, Object> map : list) {
                        if (org.get(j).equals(map.get("sparentid")) && map.get("iisdepartment").toString().equals(Nums.NO.getValue().toString())) {
                            orglist.add(map);
                            idlist.add(map.get("sid").toString());
                        }
                    }
                }
                org = idlist;
                for (int j = 0; j < org.size(); j++) {
                    idlist.remove(0);
                }
                if (org.size() == 0) {
                    break;
                }
            }
        }
        if (!"".equals(sid) && sid != null) {
            List<String> delorgs = new ArrayList<>();
            List<String> dellist = new ArrayList<>();
            delorgs.add(sid);
            for (Map<String, Object> map : list) {
                if (delorgs.get(0).equals(map.get("sid").toString())) {
                    orglist.remove(map);
                }
            }
            //展示所属机构和部门
            for (int i = 0; i == 0; ) {
                for (int j = 0; j < delorgs.size(); j++) {
                    for (Map<String, Object> map : list) {
                        if (delorgs.get(j).equals(map.get("sparentid"))) {
                            orglist.remove(map);
                            dellist.add(map.get("sid").toString());
                        }
                    }
                }
                delorgs = dellist;
                for (int j = 0; j < delorgs.size(); j++) {
                    dellist.remove(0);
                }
                if (delorgs.size() == 0) {
                    break;
                }
            }
        }
        return orglist;
    }

    /**
     * 查询特殊机构
     *
     * @return
     * @author lirui 2018年5月17日
     */
    public List<Map<String, Object>> querySpecialOrgs(Integer state, String sname, Integer usertype, String areaid, String parentareaid) {
        return orgsDao.querySpecialOrgs(state, sname, usertype, areaid, parentareaid);
    }

    /**
     * 新增特殊机构
     *
     * @param orgid
     * @return
     * @throws Exception
     * @author lirui 2018年5月21日
     */
    public Map<String, Object> AddSpecialOrg(String sname, String sdes, String sparentid, String itype, String username, String userphone, String areaid, String userid, Integer usertype, String parentareaid, String sfunctiongroupid, String orgid) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (usertype != UserTypes.ADMIN.getValue() && parentareaid != null && sparentid == null) {
            List<Map<String, Object>> list = orgsDao.queryParentIdIsNullSpecialOrgs(parentareaid);
            sparentid = list.get(0).get("sid").toString();
        }
        //查询名称重复
        List<Orgs> orgs = orgsDao.findBySparentidAndIstateNotAndIstateNot(sparentid, States.DELETE.getValue(), States.REJECT.getValue());
        for (Orgs o : orgs) {
            if (sname.equals(o.getSname())) {
                map.put("state", false);
                map.put("isSuccess", "存在重复机构名称!");
                return map;
            }
        }
        //判断机构允许用户人数是否不受控制
        int lusernumber = Nums.GLOBLE_LIMIT.getValue();
        List<String> str = Capm.ORG_TYPE;
        if (str != null) {
            String[] itypelist = itype.split(",");
            for (String s : itypelist) {
                for (int i = 0; i < str.size(); i++) {
                    if (str.get(i).equals(s)) {
                        lusernumber = Nums.NO_LIMIT.getValue();
                    }
                }
            }
        }
        //新增特殊机构
        Orgs o = new Orgs();
        o.setIstate(States.ENABLE.getValue());
        o.setItype(itype);
        o.setSdes(sdes);
        o.setSname(sname);
        o.setSparentid(sparentid);
        o.setScreateuserid(userid);
        o.setSupdateuserid(userid);
        o.setLdtcreatetime(LocalDateTime.now());
        o.setLdtupdatetime(LocalDateTime.now());
        o.setLusernumber(lusernumber);
        o.setIisdepartment(Nums.NO.getValue());
        o.setSareaid(areaid);
        Orgs neworg = orgsDao.saveAndFlush(o);
        if (userphone != null) {
            Users user = new Users();
            user.setSname(username);
            user.setSphone(userphone);
            user.setIstate(States.ENABLE.getValue());
            user.setItype(UserTypes.ORDINARY.getValue());
            user.setScreateuserid(userid);
            user.setSupdateuserid(userid);
            user.setLdtcreatetime(LocalDateTime.now());
            user.setLdtupdatetime(LocalDateTime.now());
            user.setSusername(usersService.getEnableUserName());
            user.setSpassword(Commons.getDefaultPasswordByPhone(userphone));
            Users newuser = usersDao.save(user);
            //管理员
            Organduserrefs oamanager = new Organduserrefs();
            oamanager.setScreateuserid(userid);
            oamanager.setSupdateuserid(userid);
            oamanager.setLdtcreatetime(LocalDateTime.now());
            oamanager.setLdtupdatetime(LocalDateTime.now());
            oamanager.setIstate(States.ENABLE.getValue());
            oamanager.setSorgid(neworg.getSid());
            oamanager.setIpermissionlevel(PermissionTypes.ALL.getValue());
            oamanager.setIusertype(UserTypes.MANAGER.getValue());
            Organduserrefs newoau = organduserrefsDao.save(oamanager);
            //普通用户
            Organduserrefs oau = new Organduserrefs();
            oau.setScreateuserid(userid);
            oau.setSupdateuserid(userid);
            oau.setLdtcreatetime(LocalDateTime.now());
            oau.setLdtupdatetime(LocalDateTime.now());
            oau.setIstate(States.ENABLE.getValue());
            oau.setSorgid(neworg.getSid());
            oau.setSuserid(newuser.getSid());
            oau.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
            oau.setIusertype(UserTypes.ORDINARY.getValue());
            oau.setSmanagerid(newoau.getSid());
            organduserrefsDao.save(oau);
            if (sfunctiongroupid != null) {
                String fgidlist[] = sfunctiongroupid.split(",");
                for (String fgid : fgidlist) {
                    Functiongroupanduserrefs fgaur = new Functiongroupanduserrefs();
                    fgaur.setIstate(States.ENABLE.getValue());
                    fgaur.setLdtcreatetime(LocalDateTime.now());
                    fgaur.setLdtupdatetime(LocalDateTime.now());
                    fgaur.setScreateuserid(userid);
                    fgaur.setSupdateuserid(userid);
                    fgaur.setSrefid(newoau.getSid());
                    fgaur.setSorgid(o.getSparentid());
                    fgaur.setSfunctiongroupid(fgid);
                    functiongroupanduserrefsDao.save(fgaur);
                }
            }
        }
        map.put("state", true);
        map.put("isSuccess", "操作成功!");
        return map;
    }

    /**
     * 查询特殊机构新增页面父级下拉选项
     *
     * @return
     * @author lirui 2018年5月17日
     */
    public List<Map<String, Object>> queryParentSpecialOrgs(Integer usertype, String areaid, String parentareaid, String sid) {
        List<Map<String, Object>> list = orgsDao.queryParentSpecialOrgs(usertype, areaid, parentareaid);
        List<Map<String, Object>> orglist = new ArrayList<>();
        for (Map<String, Object> map : list) {
            orglist.add(map);
        }
        if (sid != null && !"".equals(sid)) {
            List<String> delorgs = new ArrayList<>();
            List<String> dellist = new ArrayList<>();
            delorgs.add(sid);
            for (Map<String, Object> map : list) {
                if (delorgs.get(0).equals(map.get("sid").toString())) {
                    orglist.remove(map);
                    break;
                }
            }
            //展示所属机构和部门
            for (int i = 0; i == 0; ) {
                for (int j = 0; j < delorgs.size(); j++) {
                    for (Map<String, Object> map : list) {
                        if (delorgs.get(j).equals(map.get("sparentid"))) {
                            orglist.remove(map);
                            dellist.add(map.get("sid").toString());
                        }
                    }
                }
                for (int j = 0; j < delorgs.size(); j++) {
                    delorgs.remove(0);
                }
                for (String id : dellist) {
                    delorgs.add(id);
                }
                for (int j = 0; j < delorgs.size(); j++) {
                    dellist.remove(0);
                }
                if (delorgs.size() == 0) {
                    break;
                }
            }
        }
        return orglist;
    }

    /**
     * 修改特殊机构
     *
     * @return
     * @throws Exception
     * @author lirui 2018年5月21日
     */
    public Map<String, Object> editSpecialOrg(String sid, String sname, String sdes, String sparentid, String itype, String areaid, String userid, Integer usertype, String parentareaid) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (usertype != UserTypes.ADMIN.getValue() && parentareaid != null && sparentid == null) {
            List<Map<String, Object>> list = orgsDao.queryParentIdIsNullSpecialOrgs(parentareaid);
            sparentid = list.get(0).get("sid").toString();
        }
        Orgs o = orgsDao.getBySid(sid);
        if (!sname.equals(o.getSname())) {
            //查询名称重复
            List<Orgs> orgs = orgsDao.findBySparentidAndIstateNotAndIstateNot(sparentid, States.DELETE.getValue(), States.REJECT.getValue());
            List<String> idlist = new ArrayList<>();
            Boolean flag = false;
            for (Orgs oo : orgs) {
                if (sname.equals(oo.getSname())) {
                    idlist.add(oo.getSname());
                    flag = true;
                }
            }
            if (flag && (!idlist.get(0).equals(sid) || idlist.size() > 1)) {
                map.put("state", false);
                map.put("isSuccess", "存在重复机构名称!");
                return map;
            }
        }
        o.setIstate(States.ENABLE.getValue());
        o.setItype(itype);
        o.setSdes(sdes);
        o.setSname(sname);
        o.setSparentid(sparentid);
        o.setSupdateuserid(userid);
        o.setLdtupdatetime(LocalDateTime.now());
        o.setSareaid(areaid);
        orgsDao.save(o);
        map.put("state", true);
        map.put("isSuccess", "操作成功!");
        return map;
    }

    /**
     * 新增特殊机构管理员
     *
     * @param sid
     * @param refid
     * @param ismanmager
     * @return
     * @throws Exception
     * @author lirui 2018年5月23日
     */
    public Map<String, Object> AddSpecialOrgManager(String username, String userphone, String userid, String sfunctiongroupid, String orgid, String sid, String refid, Integer ismanmager) {
        Map<String, Object> map = new HashMap<>();
        if (userphone != null) {
            Users user = new Users();
            user.setSname(username);
            user.setSphone(userphone);
            user.setIstate(States.ENABLE.getValue());
            user.setItype(UserTypes.ORDINARY.getValue());
            user.setScreateuserid(userid);
            user.setSupdateuserid(userid);
            user.setLdtcreatetime(LocalDateTime.now());
            user.setLdtupdatetime(LocalDateTime.now());
            user.setSusername(usersService.getEnableUserName());
            user.setSpassword(Commons.getDefaultPasswordByPhone(userphone));
            Users newuser = usersDao.save(user);
            //管理员
            if (ismanmager.equals(Nums.YES.getValue())) {
                if (refid != null) {
                    Organduserrefs oldmanager = organduserrefsDao.getBySorgidAndSmanageridIsNotNull(sid);
                    oldmanager.setSmanagerid(null);
                    oldmanager.setLdtupdatetime(LocalDateTime.now());
                    oldmanager.setSupdateuserid(userid);
                    organduserrefsDao.save(oldmanager);
                    Organduserrefs oau = new Organduserrefs();
                    oau.setScreateuserid(userid);
                    oau.setSupdateuserid(userid);
                    oau.setLdtcreatetime(LocalDateTime.now());
                    oau.setLdtupdatetime(LocalDateTime.now());
                    oau.setIstate(States.ENABLE.getValue());
                    oau.setSorgid(sid);
                    oau.setSuserid(newuser.getSid());
                    oau.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
                    oau.setIusertype(UserTypes.ORDINARY.getValue());
                    oau.setSmanagerid(refid);
                    organduserrefsDao.save(oau);
                    List<Map<String, Object>> list = functionGroupsDao.getCheckByFAU(sid);
                    if (sfunctiongroupid != null) {
                        String fgidlist[] = sfunctiongroupid.split(",");
                        List<String> alllist = new ArrayList<>();
                        List<String> checklist = new ArrayList<>();
                        List<String> dellist = new ArrayList<>();
                        for (String fgid : fgidlist) {
                            alllist.add(fgid);
                        }
                        for (String fgid : alllist) {
                            for (Map<String, Object> m : list) {
                                if (m.get("sfunctiongroupid").toString().equals(fgid)) {
                                    checklist.add(fgid);
                                }
                            }
                        }
                        alllist.removeAll(checklist);
                        for (String fgid : alllist) {
                            Functiongroupanduserrefs fgaur = new Functiongroupanduserrefs();
                            fgaur.setIstate(States.ENABLE.getValue());
                            fgaur.setLdtcreatetime(LocalDateTime.now());
                            fgaur.setLdtupdatetime(LocalDateTime.now());
                            fgaur.setScreateuserid(userid);
                            fgaur.setSupdateuserid(userid);
                            fgaur.setSrefid(refid);
                            fgaur.setSorgid(orgid);
                            fgaur.setSfunctiongroupid(fgid);
                            functiongroupanduserrefsDao.save(fgaur);
                        }
                        alllist.addAll(checklist);
                        for (Map<String, Object> m : list) {
                            Boolean f = true;
                            for (String fgid : alllist) {
                                if (m.get("sfunctiongroupid").toString().equals(fgid)) {
                                    f = false;
                                }
                            }
                            if (f) {
                                dellist.add(m.get("sfunctiongroupid").toString());
                            }
                        }
                        List<Functiongroupanduserrefs> functiongroupanduserrefslist = functiongroupanduserrefsDao.findBySrefidAndSfunctiongroupidIn(refid, dellist);
                        functiongroupanduserrefsDao.deleteAll(functiongroupanduserrefslist);
                    } else if (list != null) {
                        List<String> dellist = new ArrayList<>();
                        for (Map<String, Object> m : list) {
                            dellist.add(m.get("sfunctiongroupid").toString());
                        }
                        List<Functiongroupanduserrefs> functiongroupanduserrefslist = functiongroupanduserrefsDao.findBySrefidAndSfunctiongroupidIn(refid, dellist);
                        functiongroupanduserrefsDao.deleteAll(functiongroupanduserrefslist);
                    }
                } else {
                    Organduserrefs oamanager = new Organduserrefs();
                    oamanager.setScreateuserid(userid);
                    oamanager.setSupdateuserid(userid);
                    oamanager.setLdtcreatetime(LocalDateTime.now());
                    oamanager.setLdtupdatetime(LocalDateTime.now());
                    oamanager.setIstate(States.ENABLE.getValue());
                    oamanager.setSorgid(sid);
                    oamanager.setIpermissionlevel(PermissionTypes.ALL.getValue());
                    oamanager.setIusertype(UserTypes.MANAGER.getValue());
                    Organduserrefs newoau = organduserrefsDao.save(oamanager);
                    //普通用户
                    Organduserrefs oau = new Organduserrefs();
                    oau.setScreateuserid(userid);
                    oau.setSupdateuserid(userid);
                    oau.setLdtcreatetime(LocalDateTime.now());
                    oau.setLdtupdatetime(LocalDateTime.now());
                    oau.setIstate(States.ENABLE.getValue());
                    oau.setSorgid(sid);
                    oau.setSuserid(newuser.getSid());
                    oau.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
                    oau.setIusertype(UserTypes.ORDINARY.getValue());
                    oau.setSmanagerid(newoau.getSid());
                    organduserrefsDao.save(oau);
                    if (sfunctiongroupid != null) {
                        String fgidlist[] = sfunctiongroupid.split(",");
                        for (String fgid : fgidlist) {
                            Functiongroupanduserrefs fgaur = new Functiongroupanduserrefs();
                            fgaur.setIstate(States.ENABLE.getValue());
                            fgaur.setLdtcreatetime(LocalDateTime.now());
                            fgaur.setLdtupdatetime(LocalDateTime.now());
                            fgaur.setScreateuserid(userid);
                            fgaur.setSupdateuserid(userid);
                            fgaur.setSrefid(newoau.getSid());
                            fgaur.setSorgid(orgid);
                            fgaur.setSfunctiongroupid(fgid);
                            functiongroupanduserrefsDao.save(fgaur);
                        }
                    }
                }
            } else {
                //普通用户
                Organduserrefs oau = new Organduserrefs();
                oau.setScreateuserid(userid);
                oau.setSupdateuserid(userid);
                oau.setLdtcreatetime(LocalDateTime.now());
                oau.setLdtupdatetime(LocalDateTime.now());
                oau.setIstate(States.ENABLE.getValue());
                oau.setSorgid(sid);
                oau.setSuserid(newuser.getSid());
                oau.setIpermissionlevel(PermissionTypes.AUTHORIZATION.getValue());
                oau.setIusertype(UserTypes.ORDINARY.getValue());
                organduserrefsDao.save(oau);
            }
        }
        map.put("state", true);
        map.put("isSuccess", "操作成功!");
        return map;
    }

    /**
     * 分页查询机构对应用户
     *
     * @param pageIndex 页数
     * @param pageSize  每页大小
     * @return 功能组分页对象
     * @author lirui 2018年5月28日
     */
    public PageObject<Map<String, Object>> getOrgUsers(Integer pageIndex, Integer pageSize, String orgid) {
        List<String> idlist = new ArrayList<>();
        idlist.add(orgid);
        List<Orgs> list = orgsDao.findBySparentidAndIstate(orgid, States.ENABLE.getValue());
        List<String> str = new ArrayList<>();
        int num = 0;
        if (list.size() > 0) {
            for (int i = 0; i < 1; ) {
                List<Orgs> listorg = new ArrayList<>();
                listorg.addAll(list);
                if (num > 0) {
                    //清空listorg
                    int num1 = listorg.size();
                    for (int j = 0; j < num1; j++) {
                        listorg.remove(0);
                    }
                    //根据上一节点的所有子节点为父级查询所有下一级节点
                    List<Orgs> sonorg = orgsDao.findBySparentidInAndIstate(str, States.ENABLE.getValue());
                    if (sonorg.size() > 0) {
                        listorg.addAll(sonorg);
                        //清空str
                        int num2 = str.size();
                        for (int k = 0; k < num2; k++) {
                            str.remove(0);
                        }
                        num = 0;
                    } else {
                        break;
                    }
                }
                //更改所有子级状态
                for (Orgs org : listorg) {
                    str.add(org.getSid());
                    idlist.add(org.getSid());
                    num++;
                }
            }
        }
        String idstr = "";
        for (int i = 0; i < idlist.size(); i++) {
            if (i == 0) {
                idstr = "'" + idlist.get(i) + "'";
            } else {
                idstr += ",'" + idlist.get(i) + "'";
            }
        }
        PageObject<Map<String, Object>> page = orgsDao.getOrgUsers(pageIndex, pageSize, idstr);
        return page;
    }

    /**
     * 机构用户升为管理员
     *
     * @param sid
     * @param istate
     * @return
     * @author lirui 2018年5月29日
     */
    public Map<String, Object> updataUserIsManager(String sid, String orgid, String userid) {
        Organduserrefs newmanager = organduserrefsDao.getBySid(sid);
        Organduserrefs oldmanager = organduserrefsDao.getBySorgidAndSmanageridIsNotNull(orgid);
        newmanager.setSmanagerid(oldmanager.getSmanagerid());
        newmanager.setSupdateuserid(userid);
        newmanager.setLdtupdatetime(LocalDateTime.now());
        oldmanager.setSmanagerid(null);
        oldmanager.setSupdateuserid(userid);
        oldmanager.setLdtupdatetime(LocalDateTime.now());
        organduserrefsDao.save(newmanager);
        organduserrefsDao.save(oldmanager);
        Map<String, Object> map = new HashMap<>();
        map.put("state", true);
        map.put("isSuccess", "操作成功!");
        return map;
    }

    /**
     * 根据所属区域获取特殊机构功能组（表头）
     *
     * @return 页面路径
     * @author lirui 2018年6月5日
     */
    public List<Map<String, Object>> getFunctionGroupsByAreaId(String areaid) {
        List<Map<String, Object>> headlist = new ArrayList<Map<String, Object>>();
        Orgs org = orgsDao.getBySareaidAndIisdepartmentAndIstateAndItypeLike(areaid, Nums.NO.getValue(), States.ENABLE.getValue(), OrgTypes.AUDIT.getValue().toString());
        if (org == null) {
            return null;
        }
        List<Functiongroups> functiongroups = functionGroupsDao.getBySorgidAndIstate(org.getSid(), States.ENABLE.getValue());
        for (Functiongroups fun : functiongroups) {
            Map<String, Object> map = new HashMap<>();
            map.put("head", fun.getSid().toString());
            map.put("sname", fun.getSname().toString());
            headlist.add(map);
        }
        return headlist;
    }

    /**
     * 根据所属区域获取特殊机构功能组
     *
     * @param usertype
     * @param sorgid
     * @return 页面路径
     * @author lirui 2018年6月5日
     */
    public Map<String, Object> getThisAreaByOrgFunctionGroups(String areaid) {
        Orgs org = orgsDao.getBySareaidAndIisdepartmentAndIstateAndItypeLike(areaid, Nums.NO.getValue(), States.ENABLE.getValue(), OrgTypes.AUDIT.getValue().toString());
        if (org == null) {
            return null;
        }
        List<Functiongroups> functiongroups = functionGroupsDao.getBySorgidAndIstate(org.getSid(), States.ENABLE.getValue());
        Map<String, Object> map = new HashMap<>();
        for (Functiongroups fun : functiongroups) {
            map.put(fun.getSid().toString(), "");
        }
        return map;
    }

    /**
     * 新增或者编辑功能组授权数据
     *
     * @param authorization 授权字符串
     * @return 操作结果信息
     * @author lirui 2018年4月11日
     */
    public Result UpdateAuthorizationSpecialOrg(String authorization, String userid, String orgid, String areaid) {
        LocalDateTime time = LocalDateTime.now();
        List<Map<String, Object>> check = new ArrayList<>();
        //已勾选数据
        Orgs org = orgsDao.getBySareaidAndIisdepartmentAndIstateAndItypeLike(areaid, Nums.NO.getValue(), States.ENABLE.getValue(), OrgTypes.AUDIT.getValue().toString());
        List<Functiongroups> functiongroups = functionGroupsDao.getBySorgidAndIstate(org.getSid(), States.ENABLE.getValue());
        List<Map<String, Object>> userlist = usersDao.getUsersBySpecialOrgId(orgid);
        List<Functiongroupanduserrefs> Functiongroupanduserrefs = functiongroupanduserrefsDao.findByIstate(States.ENABLE.getValue());
        for (Functiongroups fun : functiongroups) {
            for (Map<String, Object> map : userlist) {
                for (Functiongroupanduserrefs f : Functiongroupanduserrefs) {
                    if (fun.getSid().equals(f.getSfunctiongroupid()) && map.get("sid").equals(f.getSrefid())) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("functiongroupid", f.getSfunctiongroupid());
                        m.put("userid", f.getSrefid());
                        m.put("sid", f.getSid());
                        check.add(m);
                    }
                }
            }
        }
        List<String> idlist = new ArrayList<>();//需删除数据id
        if (authorization == null) {
            for (Map<String, Object> m : check) {
                idlist.add(m.get("sid").toString());
            }
            List<Functiongroupanduserrefs> functiongroupanduserrefs = functiongroupanduserrefsDao.getBySidIn(idlist);
            for (Functiongroupanduserrefs f : functiongroupanduserrefs) {
                f.setIstate(States.DELETE.getValue());
                f.setLdtupdatetime(time);
                f.setSupdateuserid(userid);
                functiongroupanduserrefsDao.save(f);
            }
            return Result.success("操作成功！");
        }
        String[] authorizationlist = authorization.split(",");//全部勾选数据数组
        List<Functiongroupanduserrefs> list = new ArrayList<>();//新增勾选数据
        for (Map<String, Object> m : check) {//提取需删除数据id
            boolean flag = true;//判断是否取消勾选
            for (String s : authorizationlist) {
                String[] str = s.split("_");
                if (str[0].equals(m.get("userid").toString()) && str[1].equals(m.get("functiongroupid").toString())) {
                    flag = false;
                }
            }
            if (flag) {
                idlist.add(m.get("sid").toString());
            }
        }
        for (String s : authorizationlist) {
            boolean flag = true;//判断是否为新增数据
            String[] str = s.split("_");
            for (Map<String, Object> m : check) {
                if (str[0].equals(m.get("userid").toString()) && str[1].equals(m.get("functiongroupid").toString())) {
                    flag = false;
                }
            }
            if (flag) {
                Functiongroupanduserrefs fgaur = new Functiongroupanduserrefs();
                fgaur.setIstate(States.ENABLE.getValue());
                fgaur.setLdtcreatetime(time);
                fgaur.setLdtupdatetime(time);
                fgaur.setScreateuserid(userid);
                fgaur.setSupdateuserid(userid);
                fgaur.setSrefid(str[0]);
                fgaur.setSorgid(orgid);
                fgaur.setSfunctiongroupid(str[1]);
                list.add(fgaur);
            }
        }
        //删除数据
        List<Functiongroupanduserrefs> functiongroupanduserrefs = functiongroupanduserrefsDao.getBySidIn(idlist);
        for (Functiongroupanduserrefs f : functiongroupanduserrefs) {
            f.setIstate(States.DELETE.getValue());
            f.setLdtupdatetime(time);
            f.setSupdateuserid(userid);
            functiongroupanduserrefsDao.save(f);
        }
        functiongroupanduserrefsDao.saveAll(list);
        return Result.success("操作成功！");
    }

    /**
     * 查询来源机构数据。
     *
     * @return
     * @author lirui 2018年6月7日。
     */
    public List<Map<String, Object>> getAuthorizationOrgs(String refid) {
        List<Functiongroupanduserrefs> functiongroupanduserrefs = functiongroupanduserrefsDao.getBySrefid(refid);
        List<String> idlist = new ArrayList<>();
        for (Functiongroupanduserrefs f : functiongroupanduserrefs) {
            if (f.getSorgid() != null) {
                idlist.add(f.getSorgid());
            }
        }
        List<Orgs> orgs = orgsDao.getBySidIn(idlist);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = new HashMap<>();
        m.put("sname", "请选择");
        m.put("sid", "-1");
        m.put("sparentid", null);
        list.add(m);
        //封装list
        for (Orgs o : orgs) {
            Map<String, Object> map = new HashMap<>();
            map.put("sname", o.getSname());
            map.put("sid", o.getSid());
            map.put("sparentid", o.getSparentid());
            list.add(map);
        }
        return list;
    }

    /**
     * 查询机构管理员信息。
     *
     * @return
     * @author lirui 2018年6月13日。
     */
    public Boolean getOrgManager(String userid) {
        Organduserrefs o = organduserrefsDao.getByIstateAndSuseridAndSmanageridIsNotNull(States.ENABLE.getValue(), userid);
        if (o != null) {
            return true;
        } else {
            return false;
        }
    }

    //*********************************************************huanghao--start***********************************************
    public List<Orgs> queryAddUserGetOrgs() {
        return orgsDao.findByIstateAndTpye(States.ENABLE.getValue());
    }

    public List<Orgs> queryOrgsByAUDIT() {
        List<Orgs> list = orgsDao.findByIstateAndByAUDIT(States.ENABLE.getValue());
        Orgs org = new Orgs();
        org.setSid("0");
        org.setSname("请选择");
        list.add(0, org);
        return list;
    }
    //*********************************************************huanghao--end***************************************************

}
