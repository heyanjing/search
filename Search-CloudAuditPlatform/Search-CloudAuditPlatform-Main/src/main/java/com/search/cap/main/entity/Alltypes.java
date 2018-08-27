package com.search.cap.main.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * ALLTYPES
 */
@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Alltypes extends com.search.cap.main.base.entity.BaseEntityWithStringId implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ISTATE
     */
    private java.lang.Integer istate;
    /**
     * LDTCREATETIME
     */
    private java.time.LocalDateTime ldtcreatetime;
    /**
     * LDTUPDATETIME
     */
    private java.time.LocalDateTime ldtupdatetime;
    /**
     * PASSWORD
     */
    private java.lang.String password;
    /**
     * SALT
     */
    private java.lang.String salt;
    /**
     * T_DATE
     */
    private java.time.LocalDate t_date;
    /**
     * T_DATETIME
     */
    private java.time.LocalDateTime t_datetime;
    /**
     * T_DOUBLE
     */
    private java.lang.Double t_double;
    /**
     * T_INT
     */
    private java.lang.Integer t_int;
    /**
     * T_LONG
     */
    private java.lang.Long t_long;
    /**
     * T_STRING1
     */
    private java.lang.String t_string1;
    /**
     * T_STRING2
     */
    private java.lang.String t_string2;
    /**
     * T_STRING3
     */
    private java.lang.String t_string3;
    /**
     * T_STRING4
     */
    private java.lang.String t_string4;
    /**
     * USER_NAME
     */
    private java.lang.String user_name;
    /**
     * USERNAME
     */
    private java.lang.String username;
    /**
     * SCREATEUSERID
     */
    private java.lang.String screateuserid;
    /**
     * SUPDATEUSERID
     */
    private java.lang.String supdateuserid;
}