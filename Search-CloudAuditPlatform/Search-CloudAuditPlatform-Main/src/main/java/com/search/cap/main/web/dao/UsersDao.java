package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Commonattachs;
import com.search.cap.main.entity.Users;
import com.search.cap.main.web.dao.custom.UsersCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2017/12/19 10:29.
 */
public interface UsersDao extends BaseRepo<Users, String>, UsersCustomDao {

    /**
     * 通过用户id查询用户
     *
     * @param sid sid
     * @return Users
     */
    Users getBySid(String sid);

    /**
     * 通过用户id查询用户
     *
     * @param sid sid
     * @return Users
     */
    List<Users> getBySidInAndIstate(List<String> sid, Integer istate);

    /**
     * 通过用户名查询用户
     *
     * @param susername 用户名
     * @return Users
     */
    Users getBySusername(String susername);

    /**
     * 通过三个条件中的一个查询用户
     *
     * @param susername 用户名
     * @param semail    邮箱
     * @param sphone    电话
     * @return Users
     */
    Users getBySusernameOrSemailOrSphone(String susername, String semail, String sphone);

    /**
     * 通过id修改状态
     *
     * @param state state
     * @param id    id
     * @return 成功条数
     */
    @Modifying
    @Query("update Users t set t.istate = ?1 where t.sid = ?2")
    Integer updateStateById(Integer state, String id);

    @Modifying
    @Query("update Users t set t.istate = :state where t.sid = :id")
    Integer updateStateById2(@Param("state") Integer state, @Param("id") String id);

    @Modifying
    @Query("update Users t set t.istate = ?1")
    Integer updateState(Integer state);

    /**
     * 根据{@code type}和{@code state}查询字典数据
     *
     * @param type  字典类型
     * @param state 状态
     * @return 字典选择结构数据
     * @author CJH 2018年3月27日
     */
    @Query("select d.sid as id, d.sname as text, d.istate as state from Dictionaries d where d.itype = ?1 and d.istate = ?2")
    public List<Map<String, Object>> findListMapDictionariesByItype(Integer type, Integer state);

    /**
     * 根据{@code sid}、{@code type}和{@code state}查询公共附件
     *
     * @param sid   所属数据ID
     * @param type  类型
     * @param state 状态
     * @return 公共附件对象集合
     * @author CJH 2018年3月29日
     */
    @Query("select c from Commonattachs c where c.sdataid = ?1 and c.itype = ?2 and c.istate = ?3")
    public List<Commonattachs> findCommonAttachsBySdataIdAndItype(String sid, Integer type, Integer state);

    /**
     * 根据{@code state}查询所有机构
     *
     * @param state 状态
     * @return 机构树形结构数据
     * @author CJH 2018年4月3日
     */
    @Query("select o.sid as id, o.sname as text, o.sparentid as pid from Orgs o where o.istate = ?1")
    public List<Map<String, Object>> findListMapOrgs(Integer state);

    /**
     * 根据{@code orgid}查询机构和{@code userid}查询分管机构
     *
     * @param state  状态
     * @param orgid  机构ID
     * @param userid 用户ID
     * @return 机构树形结构数据
     * @author CJH 2018年4月9日
     */
    @Query("select o.sid as id, o.sname as text, o.sparentid as pid from Orgs o where o.istate = ?1 and (o.sid = ?2"
            + " or exists(select 1 from Chargeorgs co where co.sorgid = o.sid and co.istate = ?1 and co.suserid = ?3))")
    public List<Map<String, Object>> findListMapOrgsByOrgidOrChargeorgs(Integer state, String orgid, String userid);

    /**
     * 根据{@code orgid}查询机构
     *
     * @param state 状态
     * @param orgid 审计局机构ID
     * @return 机构树形结构数据
     * @author CJH 2018年4月9日
     */
    @Query("select o.sid as id, o.sname as text, o.sparentid as pid from Orgs o where o.istate = ?1 and exists(select 1"
            + " from Intermediarys i where i.istate = ?1 and i.sintermediaryorgid = o.sid and i.sauditorgid = ?2)")
    public List<Map<String, Object>> findListMapOrgsByIntermediarys(Integer state, String orgid);

    /**
     * 根据{@code orgid}查询该机构下的部门
     *
     * @param state 状态
     * @param orgid 机构ID
     * @return 机构下拉选择数据
     * @author CJH 2018年4月9日
     */
    @Query("select o.sid as id, o.sname as text from Orgs o where o.istate = ?1 and o.iisdepartment = 1 and o.sparentid = ?2")
    public List<Map<String, Object>> findListMapDepartmentByOrgid(Integer state, String orgid);

    /**
     * 更新{@code sid}和不等于{@code chargeorgList}数据的状态
     *
     * @param state         状态
     * @param userId        更新人
     * @param time          更新时间
     * @param sid           用户ID
     * @param chargeorgList 机构ID
     * @return 执行sql受影响行数
     * @author CJH 2018年4月9日
     */
    @Modifying
    @Query("update Chargeorgs co set co.istate = ?1, co.supdateuserid = ?2, co.ldtupdatetime = ?3 where co.istate = 1"
            + " and co.suserid = ?4 and co.sorgid not in ?5")
    public Integer updateChargeOrgsStateByUseridNotinOrgId(Integer state, String userId, LocalDateTime time, String sid,
                                                           List<String> chargeorgList);

    /**
     * 更新{@code sid}数据的状态
     *
     * @param state  状态
     * @param userId 更新人
     * @param time   更新时间
     * @param sid    用户ID
     * @return 执行sql受影响行数
     * @author CJH 2018年4月9日
     */
    @Modifying
    @Query("update Chargeorgs co set co.istate = ?1, co.supdateuserid = ?2, co.ldtupdatetime = ?3 where co.istate = 1"
            + " and co.suserid = ?4")
    public Integer updateChargeOrgsStateByUserid(Integer state, String userId, LocalDateTime time, String sid);

    /**
     * 根据{@code sid}查询分管机构ID
     *
     * @param sid 用户ID
     * @return 分管机构ID
     * @author CJH 2018年4月9日
     */
    @Query("select co.sorgid from Chargeorgs co where co.istate = 1 and co.suserid = ?1")
    public List<String> findChargeOrgsByUserid(String sid);

    /**
     * 根据{@code orgid}查询管理员用户
     *
     * @param orgid 机构ID
     * @return 用户对象
     * @author CJH 2018年4月9日
     */
    @Query("select u from Users u left join Organduserrefs ou on ou.suserid = u.sid and ou.istate = 1 where u.istate = 1 and"
            + " ou.sorgid = ?1 and u.itype = 2")
    public Users findManagerByOrgid(String orgid);

    /**
     * 根据{@code sid}查询分管机构名称
     *
     * @param sid 用户ID
     * @return 分管机构ID
     * @author CJH 2018年4月13日
     */
    @Query("select o.sname from Chargeorgs co left join Orgs o on o.sid = co.sorgid and o.istate = 1 where co.istate = 1 and co.suserid = ?1")
    public List<String> findChargeOrgsNameByUserid(String sid);

    /**
     * 根据{@code sphone}查询用户对象
     *
     * @param sphone 电话号码
     * @param istate 状态
     * @return 用户对象
     * @author CJH 2018年5月7日
     */
    public Users getUsersBySphoneAndIstate(String sphone, Integer istate);

    /**
     * 根据{@code semail}查询用户对象
     *
     * @param semail 电子邮箱
     * @param istate 状态
     * @return 用户对象
     * @author CJH 2018年5月7日
     */
    public Users getUsersBySemailAndIstate(String semail, Integer istate);

    /**
     * 根据{@code state}和{@code phone}更改密码为{@code password}
     *
     * @param state    状态
     * @param phone    手机号码
     * @param password 密码
     * @return 执行SQL受影响条数
     * @author CJH 2018年5月7日
     */
    @Modifying
    @Query("update Users t set t.spassword = ?3 where t.istate = ?1 and t.sphone = ?2")
    public Integer updatePasswordByPhone(Integer state, String phone, String password);

    /**
     * 根据{@code state}和{@code email}更改密码为{@code password}
     *
     * @param state    状态
     * @param email    邮箱地址
     * @param password 密码
     * @return 执行SQL受影响条数
     * @author CJH 2018年5月7日
     */
    @Modifying
    @Query("update Users t set t.spassword = ?3 where t.istate = ?1 and t.semail = ?2")
    public Integer updatePasswordByEmail(Integer state, String email, String password);


    /**
     * 根据{@code sphone}或{@code sidcard}或{@code semail}查询用户信息
     *
     * @param sphone  电话号码
     * @param sidcard 身份证号码
     * @param semail  邮箱地址
     * @return 用户信息
     * @author CJH 2018年5月9日
     */
    public List<Users> findBySphoneOrSidcardOrSemail(String sphone, String sidcard, String semail);

    /**
     * 根据{@code sphone}查询用户信息
     *
     * @param sphone 电话号码
     * @return 用户信息
     * @author CJH 2018年5月9日
     */
    public List<Users> findBySphone(String sphone);

    /**
     * 根据{@code sidcard}查询用户信息
     *
     * @param sidcard 身份证号码
     * @return 用户信息
     * @author CJH 2018年5月9日
     */
    public List<Users> findBySidcard(String sidcard);

    /**
     * 根据{@code semail}查询用户信息
     *
     * @param semail 邮箱地址
     * @return 用户信息
     * @author CJH 2018年5月9日
     */
    public List<Users> findBySemail(String semail);

    //*********************************************************heyanjing--start*******************************************************************************************************************************
    public Users getBySnameAndSidNot(String sname, String sid);
    //*********************************************************heyanjing--end*********************************************************************************************************************************
}
