package com.search.common.base.jpa.jdbc;

import com.search.common.base.core.utils.Guava;
import com.search.common.base.jpa.util.Sqls;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class JdbcTemplate extends org.springframework.jdbc.core.JdbcTemplate {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcTemplate(DataSource dataSource) {
        super(dataSource);
        log.debug("{}", dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    // **********************************************************************************************************************************
    // MEINFO:2017/12/18 16:57 find by entityClass
    // **********************************************************************************************************************************
    public <E> List<E> find(String sql, Class<E> entityClass) {
        return super.query(sql, BeanPropertyRowMapper.newInstance(entityClass));
    }

    public <E> List<E> find(String sql, Class<E> entityClass, Map<String, ?> params) {
        return this.namedParameterJdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(entityClass));
    }

    public <E> List<E> find(String sql, Class<E> entityClass, Object... params) {
        return super.query(sql, BeanPropertyRowMapper.newInstance(entityClass), params);
    }

    // **********************************************************************************************************************************
    // MEINFO:2017/12/18 16:57 page by entityClass pageNumber从1开始
    // **********************************************************************************************************************************
    public <E> Page<E> page(String sql, Class<E> entityClass, int pageNumber, int pageSize) {
        return this.page(sql, entityClass, pageNumber, pageSize, Guava.newHashMap());
    }

    public <E> Page<E> page(String sql, Class<E> entityClass, int pageNumber, int pageSize, Map<String, ?> params) {
        int count = this.queryForCount(sql, params);
        List<E> entities = Guava.newArrayList();
        if (count > 0) {
            entities = this.find(Sqls.buildPageSql(sql, pageNumber, pageSize, Sqls.Db.ORACAL), entityClass, params);
        }
        return new PageImpl<>(entities, PageRequest.of(pageNumber - 1, pageSize), count);
    }

    public <E> Page<E> page(String sql, Class<E> entityClass, int pageNumber, int pageSize, Object... params) {
        int count = this.queryForCount(sql, params);
        List<E> entities = Guava.newArrayList();
        if (count > 0) {
            entities = this.find(Sqls.buildPageSql(sql, pageNumber, pageSize, Sqls.Db.ORACAL), entityClass, params);
        }
        return new PageImpl<>(entities, PageRequest.of(pageNumber - 1, pageSize), count);
    }

    // **********************************************************************************************************************************
    // MEINFO:2017/12/18 16:57 find by rowmapper
    // **********************************************************************************************************************************
    public <E> List<E> find(String sql, RowMapper<E> mapper) {
        return super.query(sql, mapper);
    }

    public <E> List<E> find(String sql, RowMapper<E> mapper, Map<String, ?> params) {
        return this.namedParameterJdbcTemplate.query(sql, params, mapper);
    }

    public <E> List<E> find(String sql, RowMapper<E> mapper, Object... params) {
        return super.query(sql, mapper, params);
    }

    // **********************************************************************************************************************************
    // MEINFO:2017/12/18 16:57 page by rowmapper
    // **********************************************************************************************************************************
    public <E> Page<E> page(String sql, RowMapper<E> mapper, int pageNumber, int pageSize) {
        return this.page(sql, mapper, pageNumber, pageSize, Guava.newHashMap());
    }

    public <E> Page<E> page(String sql, RowMapper<E> mapper, int pageNumber, int pageSize, Map<String, ?> params) {
        int count = this.queryForCount(sql, params);
        List<E> entities = Guava.newArrayList();
        if (count > 0) {
            entities = this.find(Sqls.buildPageSql(sql, pageNumber, pageSize, Sqls.Db.ORACAL), mapper, params);
        }
        return new PageImpl<>(entities, PageRequest.of(pageNumber - 1, pageSize), count);
    }

    public <E> Page<E> page(String sql, RowMapper<E> mapper, int pageNumber, int pageSize, Object... params) {
        int count = this.queryForCount(sql, params);
        List<E> entities = Guava.newArrayList();
        if (count > 0) {
            entities = this.find(Sqls.buildPageSql(sql, pageNumber, pageSize, Sqls.Db.ORACAL), mapper, params);
        }
        return new PageImpl<>(entities, PageRequest.of(pageNumber - 1, pageSize), count);
    }

    // **********************************************************************************************************************************
    // MEINFO:2017/12/18 16:57 query for count
    // **********************************************************************************************************************************
    public Integer queryForCount(String sql, Map<String, ?> params) {
        return this.namedParameterJdbcTemplate.queryForObject(this.buildCountSql(sql), params, Integer.class);
    }

    public Integer queryForCount(String sql, Object... params) {
        return super.queryForObject(this.buildCountSql(sql), params, Integer.class);
    }

    private String buildCountSql(String sql) {
        return "SELECT COUNT(*) " + StringUtils.substring(sql, StringUtils.indexOfIgnoreCase(sql, "from", 0));
    }
}
