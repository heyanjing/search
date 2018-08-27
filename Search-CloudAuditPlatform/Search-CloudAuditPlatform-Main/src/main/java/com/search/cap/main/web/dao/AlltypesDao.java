package com.search.cap.main.web.dao;

import com.search.cap.main.entity.demo.Alltypes;
import com.search.cap.main.web.dao.custom.AlltypesCustomDao;
import com.search.common.base.jpa.repo.BaseRepo;

/**
 * Created by heyanjing on 2017/12/19 10:29.
 * 在查询时，通常需要同时根据多个属性进行查询，且查询的条件也格式各样（大于某个值、在某个范围等等），Spring Data JPA 为此提供了一些表达条件查询的关键字，大致如下：
 * <p>
 * And --- 等价于 SQL 中的 and 关键字，比如 findByUsernameAndPassword(String user, Striang pwd)；
 * Or --- 等价于 SQL 中的 or 关键字，比如 findByUsernameOrAddress(String user, String addr)；
 * Between --- 等价于 SQL 中的 between 关键字，比如 findBySalaryBetween(int max, int min)；
 * LessThan --- 等价于 SQL 中的 "<"，比如 findBySalaryLessThan(int max)；
 * GreaterThan --- 等价于 SQL 中的">"，比如 findBySalaryGreaterThan(int min)；
 * IsNull --- 等价于 SQL 中的 "is null"，比如 findByUsernameIsNull()；
 * IsNotNull --- 等价于 SQL 中的 "is not null"，比如 findByUsernameIsNotNull()；
 * NotNull --- 与 IsNotNull 等价；
 * Like --- 等价于 SQL 中的 "like"，比如 findByUsernameLike(String user)；
 * NotLike --- 等价于 SQL 中的 "not like"，比如 findByUsernameNotLike(String user)；
 * OrderBy --- 等价于 SQL 中的 "order by"，比如 findByUsernameOrderBySalaryAsc(String user)；
 * Not --- 等价于 SQL 中的 "！ ="，比如 findByUsernameNot(String user)；
 * In --- 等价于 SQL 中的 "in"，比如 findByUsernameIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
 * NotIn --- 等价于 SQL 中的 "not in"，比如 findByUsernameNotIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
 */
public interface AlltypesDao extends BaseRepo<Alltypes, String>, AlltypesCustomDao<Alltypes> {

    Alltypes getBySid(String sid);


}
