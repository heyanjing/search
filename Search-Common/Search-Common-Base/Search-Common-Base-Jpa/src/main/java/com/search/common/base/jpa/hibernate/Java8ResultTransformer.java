package com.search.common.base.jpa.hibernate;

import com.search.common.base.core.converter.Sql2LocalDate;
import com.search.common.base.core.converter.Sql2LocalDateTime;
import com.search.common.base.jpa.Constant;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.HibernateException;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by heyanjing on 2018/2/9 14:14.
 */
public class Java8ResultTransformer implements ResultTransformer {
    private static final Logger log = LoggerFactory.getLogger(Java8ResultTransformer.class);
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final Class<?> resultClass;
    private List<Field> fields = new ArrayList<>();

    public Java8ResultTransformer(final Class<?> resultClass) {
        this.resultClass = resultClass;
        Class<?> tempClass = resultClass;
        int i = 0;
        while (tempClass != null && i < Constant.WHILE_OUT) {
            fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
            i++;
        }
        //Field[] fields1 = this.resultClass.getSuperclass().getDeclaredFields();
        //for (Field field : fields1) {
        //    log.debug("super_{}", field.getName());
        //}
        //Field[] fields2 = this.resultClass.getDeclaredFields();
        //for (Field field : fields2) {
        //    log.debug("chil_{}", field.getName());
        //}
        //fields.addAll(Arrays.asList(this.resultClass.getSuperclass().getDeclaredFields()));
        //fields.addAll(Arrays.asList(this.resultClass.getDeclaredFields()));
        //this.fields = this.resultClass.getDeclaredFields();
        init();
    }

    public static Java8ResultTransformer aliasToBean(final Class<?> resultClass) {
        return new Java8ResultTransformer(resultClass);
    }

    public void init() {
        ConvertUtils.register(Sql2LocalDate.newInstance(), LocalDate.class);
        ConvertUtils.register(Sql2LocalDateTime.newInstance(), LocalDateTime.class);
    }

    @Override
    public Object transformTuple(final Object[] tuple, final String[] aliases) {
        Object result;
        try {
            if (this.resultClass.equals(Integer.class) || this.resultClass.equals(Long.class) || this.resultClass.equals(String.class)) {
                result = tuple[0];
            } else {
                result = this.resultClass.newInstance();
                for (int i = 0; i < aliases.length; i++) {
                    String alias = aliases[i];
                    Object value = tuple[i];
                    if (value != null) {
                        log.debug("别名：{}---类名：{}", alias, value.getClass().getName());
                    }
                    for (Field field : this.fields) {
                        String fieldName = field.getName();
                        if (fieldName.equalsIgnoreCase(alias)) {
                            BeanUtils.setProperty(result, fieldName, value);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName(), e);
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List transformList(final List collection) {
        return collection;
    }
}
