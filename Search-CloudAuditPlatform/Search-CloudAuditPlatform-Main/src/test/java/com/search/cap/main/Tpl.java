package com.search.cap.main;

import com.search.common.base.core.utils.Guava;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by heyanjing on 2018/2/10 9:39.
 */
@Slf4j
public class Tpl {
    String controllerPath = "C:/repository/Search-CloudAuditPlatform/Search-CloudAuditPlatform-Main/src/main/java/com/search/cap/main/web/controller";
    String serverPath = "C:/repository/Search-CloudAuditPlatform/Search-CloudAuditPlatform-Main/src/main/java/com/search/cap/main/web/service";
    String daoPath = "C:/repository/Search-CloudAuditPlatform/Search-CloudAuditPlatform-Main/src/main/java/com/search/cap/main/web/dao";


    String serverText = "package com.search.cap.main.web.service.prefix;\nimport com.search.cap.main.web.dao.PrefixDao;\nimport lombok.extern.slf4j.Slf4j;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.stereotype.Service;@Service\n@Slf4j\npublic class PrefixService {\n@Autowired\nprivate PrefixDao prefixDao;\n\n}";
    String daoText = "package com.search.cap.main.web.dao; \nimport com.search.cap.main.entity.ClassName;\nimport com.search.cap.main.web.dao.custom.PrefixCustomDao;\nimport com.search.common.base.jpa.repo.BaseRepo;\npublic interface PrefixDao extends BaseRepo<ClassName, String>, PrefixCustomDao<ClassName> {\n\n}";
    String customDaoText = "package com.search.cap.main.web.dao.custom;\npublic interface PrefixCustomDao<ClassName> {\n\n}";
    String daoImplText = "package com.search.cap.main.web.dao.impl;\nimport com.search.cap.main.entity.ClassName;\nimport com.search.cap.main.web.dao.custom.PrefixCustomDao;\nimport com.search.common.base.jpa.hibernate.BaseDao;\nimport lombok.extern.slf4j.Slf4j;@Slf4j\npublic class PrefixDaoImpl extends BaseDao<ClassName> implements PrefixCustomDao<ClassName> {\n\n}";

    @Before
    public void before() {

    }

    @Test
    public void test() throws Exception {

        Pattern p = Pattern.compile("[A-Z]");
        String reg = "[A-Z]";
        log.info("{}", Pattern.matches(reg, "a"));
        log.info("{}", Pattern.matches(reg, "aB"));
        log.info("{}", Pattern.matches(reg, "A"));
        log.info("{}", Pattern.matches(reg, "AA"));
        //Matcher m=p.matcher("22bb23");


        log.info("{}", firstLetterUppercase("asdfasd"));
    }

    /**
     * 生成Dao,CustomDao,DaoImpl的模板
     */
    //@Test
    public void tpl(String prefix) throws Exception {
        if (StringUtils.isBlank(prefix)) {
            prefix = "MailTpls";
        }
        String className = prefix;

        int upperCaseCount = 0;
        char[] charArr = prefix.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (Character.isUpperCase(charArr[i])) {
                upperCaseCount++;
                if (upperCaseCount > 1) {
                    className = firstLetterUppercase(className.toLowerCase());
                    break;
                }
            }
        }
        log.info("前缀：{}----类名：{}", prefix, className);
        //region Description
        //dao
        File daoDirFile = new File(daoPath);
        File daoFile = new File(daoDirFile, prefix + "Dao.java");
        if (!daoFile.exists()) {
            daoFile.createNewFile();
        }
        FileUtils.write(daoFile, daoText.replaceAll("Prefix", prefix).replaceAll("ClassName", className), "UTF-8");
//customdao
        File customDaoDirFile = new File(daoPath, "custom");
        File customDaoFile = new File(customDaoDirFile, prefix + "CustomDao.java");
        if (!customDaoFile.exists()) {
            customDaoFile.createNewFile();
        }
        FileUtils.write(customDaoFile, customDaoText.replaceAll("Prefix", prefix).replaceAll("ClassName", className), "UTF-8");
//daoimpl
        File daoImplDirFile = new File(daoPath, "impl");
        File daoImplFile = new File(daoImplDirFile, prefix + "DaoImpl.java");
        if (!daoImplFile.exists()) {
            daoImplFile.createNewFile();
        }
        FileUtils.write(daoImplFile, daoImplText.replaceAll("Prefix", prefix).replaceAll("ClassName", className), "UTF-8");
        //endregion
        //service
        File serverDirFile = new File(serverPath, prefix.toLowerCase());
        if (!serverDirFile.exists()) {
            serverDirFile.mkdirs();
        }
        File serverFile = new File(serverDirFile, prefix + "Service.java");
        if (!serverFile.exists()) {
            serverFile.createNewFile();
        }
        FileUtils.write(serverFile, serverText.replaceAll("Prefix", prefix).replaceAll("prefix", prefix.toLowerCase()), "UTF-8");
        log.info("{}", "执行完成");
    }
    @Test
    public void tpls() throws Exception {
        // HEWARN: 2018/7/31 17:11 自动生成service，dao,daoImpl
        List<String> list = Guava.newArrayList();
        list.add("FileTpls");
        list.add("DataAndAuditAttachs");
        list.add("AuditTpls");
        list.add("AuditTplDetails");
        list.add("Applys");
        list.add("AuditTplDetailCopys");
        list.forEach(prefix -> {
            try {
                tpl(prefix);
            } catch (Exception e) {
                log.error("错误表名：{}", prefix);
                e.printStackTrace();
            }
        });

    }

    /**
     * 首字母大写
     */
    public static String firstLetterUppercase(String name) {
        char[] cs = name.toCharArray();
        if (Pattern.matches("[a-z]", String.valueOf(cs[0]))) {
            cs[0] -= 32;
        }
        return String.valueOf(cs);

    }
}