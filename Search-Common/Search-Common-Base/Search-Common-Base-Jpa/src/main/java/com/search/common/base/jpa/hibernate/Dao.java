/**
 * Copyright (c) 2017 协成科技
 * All rights reserved.
 *
 * File：Dao.java
 * History:
 *         2017年1月5日: Initially created, Chrise.
 */
package com.search.common.base.jpa.hibernate;

import java.util.List;

/**
 * 数据访问对象。
 * @author Chrise
 */
public interface Dao<T> {
	/**
	 * 查询有序的GUID。
	 * @author Liangjing 2017年3月20日
	 * @return 字符串类型的有序GUID。
	 * @throws Exception 数据库异常。
	 */
	String querySequentialGuid() throws Exception;
	
	/**
	 * 插入对象。
	 * @author Chrise 2017年1月5日
	 * @param object 将要插入数据库的对象。
	 * @throws Exception 数据库异常。
	 */
	void insertObject(final T object) throws Exception;
	
	/**
	 * 批量插入对象。
	 * @author Chrise 2017年3月25日
	 * @param list 将要插入数据库的对象集合。
	 * @throws Exception 数据库异常。
	 */
	void insertForBatch(final List<T> list) throws Exception;
	
	/**
	 * 更新对象。
	 * @author Liangjing 2017年3月20日
	 * @param object 将要更新的数据库对象。
	 * @throws Exception 数据库异常。
	 */
	void updateObject(final T object) throws Exception;
	
	/**
	 * 批量更新对象。
	 * @author Chrise 2017年3月25日
	 * @param list 将要更新的数据库对象集合。
	 * @throws Exception 数据库异常。
	 */
	void updateForBatch(final List<T> list) throws Exception;
	
	/**
	 * 删除对象。
	 * @author Chrise 2017年3月25日
	 * @param object 将要删除的数据库对象。
	 * @throws Exception 数据库异常。
	 */
	void deleteObject(final T object) throws Exception;
	
	/**
	 * 批量删除对象。
	 * @author Chrise 2017年3月25日
	 * @param list 将要删除的数据库对象集合。
	 * @throws Exception 数据库异常。
	 */
	void deleteForBatch(final List<T> list) throws Exception;
	
	/**
	 * 插入自定义对象。
	 * @author Liangjing 2017年3月20日
	 * @param <C> 自定义类型。
	 * @param object 将要插入数据库的自定义对象。
	 * @throws Exception 数据库异常。
	 */ 
	<C> void insertCustomObject(final C object) throws Exception;
	
	/**
	 * 批量插入自定义对象。
	 * @author Chrise 2017年3月25日
	 * @param <C> 自定义类型。
	 * @param list 将要插入数据库的自定义对象集合。
	 * @throws Exception 数据库异常。
	 */
	<C> void insertCustomObjectForBatch(final List<C> list) throws Exception;
	
	/**
	 * 更新自定义对象。
	 * @author Liangjing 2017年3月20日
	 * @param <C> 自定义类型。
	 * @param object 将要更新的自定义数据库对象。
	 * @throws Exception 数据库异常。
	 */
	<C> void updateCustomObject(final C object) throws Exception;
	
	/**
	 * 批量更新自定义对象。
	 * @author Chrise 2017年3月25日
	 * @param <C> 自定义类型。
	 * @param list 将要更新的自定义数据库对象集合。
	 * @throws Exception 数据库异常。
	 */
	<C> void updateCustomObjectForBatch(final List<C> list) throws Exception;
	
	/**
	 * 删除自定义对象。
	 * @author Chrise 2017年3月25日
	 * @param <C> 自定义类型。
	 * @param object 将要删除的自定义数据库对象。
	 * @throws Exception 数据库异常。
	 */
	<C> void deleteCustomObject(final C object) throws Exception;
	
	/**
	 * 批量删除自定义对象。
	 * @author Chrise 2017年3月25日
	 * @param <C> 自定义类型。
	 * @param list 将要删除的自定义数据库对象集合。
	 * @throws Exception 数据库异常。
	 */
	<C> void deleteCustomObjectForBatch(final List<C> list) throws Exception;
}
