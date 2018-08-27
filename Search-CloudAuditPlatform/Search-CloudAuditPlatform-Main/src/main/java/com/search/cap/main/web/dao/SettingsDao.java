package com.search.cap.main.web.dao;

import com.search.cap.main.entity.Logorgdetails;
import com.search.cap.main.entity.Logsettings;
import com.search.cap.main.entity.Orgs;
import com.search.cap.main.entity.Settings;
import com.search.cap.main.web.dao.custom.SettingsCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by heyanjing on 2017/12/19 10:29.
 * 在查询时，通常需要同时根据多个属性进行查询，且查询的条件也格式各样（大于某个值、在某个范围等等），Spring
 * Data JPA 为此提供了一些表达条件查询的关键字，大致如下：
 * <p>
 * And --- 等价于 SQL 中的 and 关键字，比如
 * findByUsernameAndPassword(String user, Striang pwd)； Or
 * --- 等价于 SQL 中的 or 关键字，比如 findByUsernameOrAddress(String
 * user, String addr)； Between --- 等价于 SQL 中的 between 关键字，比如
 * findBySalaryBetween(int max, int min)； LessThan --- 等价于
 * SQL 中的 "<"，比如 findBySalaryLessThan(int max)； GreaterThan
 * --- 等价于 SQL 中的">"，比如 findBySalaryGreaterThan(int min)；
 * IsNull --- 等价于 SQL 中的 "is null"，比如
 * findByUsernameIsNull()； IsNotNull --- 等价于 SQL 中的 "is not
 * null"，比如 findByUsernameIsNotNull()； NotNull --- 与
 * IsNotNull 等价； Like --- 等价于 SQL 中的 "like"，比如
 * findByUsernameLike(String user)； NotLike --- 等价于 SQL 中的
 * "not like"，比如 findByUsernameNotLike(String user)； OrderBy
 * --- 等价于 SQL 中的 "order by"，比如
 * findByUsernameOrderBySalaryAsc(String user)； Not --- 等价于
 * SQL 中的 "！ ="，比如 findByUsernameNot(String user)； In ---
 * 等价于 SQL 中的 "in"，比如 findByUsernameIn(Collection<String>
 * userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数； NotIn
 * --- 等价于 SQL 中的 "not in"，比如
 * findByUsernameNotIn(Collection<String> userList)
 * ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
 */
public interface SettingsDao extends BaseRepo<Settings, String>, SettingsCustomDao {

    /**
     * 根据{@code sid}查询系统设置
     *
     * @param sid 系统设置ID
     * @return 系统设置对象
     * @author CJH 2018年3月23日
     */
    public Settings getBySid(String sid);

    /**
     * 根据{@code id}查询系统设置日志对象
     *
     * @param id 系统设置日志ID
     * @return 系统设置日志对象
     * @author CJH 2018年3月24日
     */
    @Query("select ls from Logsettings ls where ls.sid = ?1")
    public Logsettings getLogSettingsById(String id);

    /**
     * 查询大于等于{@code ldtcreatetime}的系统设置日志
     *
     * @param ldtcreatetime 时间
     * @param sort          排序对象
     * @return 系统设置日志对象集合
     * @author CJH 2018年3月24日
     */
    @Query("select ls from Logsettings ls where ls.ldtcreatetime >= ?1")
    public List<Logsettings> getLogSettingsByGreaterAndEqualTime(LocalDateTime ldtcreatetime, Sort sort);

    /**
     * 查询等于{@code userNumber}不等于{@code state}的机构
     *
     * @param userNumber 机构允许用户人数
     * @param state      状态
     * @return 机构对象集合
     * @author CJH 2018年3月23日
     */
    @Query("select o from Orgs o where o.lusernumber = ?1 and o.istate <> ?2")
    public List<Orgs> findOrgsByLuserNumberNotIstate(Integer userNumber, Integer state);

    /**
     * 根据{@code slogsettingid}查询更改机构详情日志
     *
     * @param slogsettingid 系统设置日志ID
     * @return 更改机构详情日志对象集合
     * @author CJH 2018年3月24日
     */
    @Query("select lod from Logorgdetails lod where lod.slogsettingid = ?1")
    public List<Logorgdetails> findLogOrgDetailsBySlogSettingId(String slogsettingid);

}