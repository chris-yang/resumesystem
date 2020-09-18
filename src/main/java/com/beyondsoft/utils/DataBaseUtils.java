package com.beyondsoft.utils;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.generator.config.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@PropertySource("classpath:application.yml")    //设置需要读取配置文件的路径
@Component("dataBaseUtils")
@ConfigurationProperties(prefix = "spring.datasource")    //设置配置至哪个节点,下面举例说明
public class DataBaseUtils {

//    private static final String database = "settlement";//settlement
//    private static final String url = "jdbc:mysql://127.0.0.1:3306/" + database
//            + "?useUnicode=true&useSSL=false&characterEncoding=utf8";
//    private static final String driverName = "com.mysql.cj.jdbc.Driver";
//    private static final String userName = "root";
//    private static final String password = "123456";
    @Value("${url}")
    private static  String url;
    @Value("${driver-class-name}")
    private static  String driverName ;
    @Value("${userName}")
    private static  String userName ;
    @Value("${password}")
    private static  String password ;


//    @Bean
//    public DataBaseUtils dataBaseUtils(){
//        return  new DataBaseUtils();
//    }
     public  List<String> getColumnsFromTable(String tableName){
         initDataSourceConfig();
         return new JdbcRepository().getColumnsFromTable(tableName);
     }
    /**
     * 初始
     *
     * @param author      作者
     * @param packageName 包名
     * @param tablePrefix 表前缀
     */
    public  void init(String author, String packageName, String... tablePrefix) {
//        每一个entity都需要单独设置InjectionConfig, StrategyConfig和TemplateConfig
        Map<String, String> names = new JdbcRepository().getEntityNames(tablePrefix);
        if (names == null || names.isEmpty()) {
            return;
        }
        for (String tableName : names.keySet()) {
            System.out.println(tableName);

        }
    }
    /**
     * 配置数据源
     *
     * @return
     */
    private  DataSourceConfig initDataSourceConfig() {
        return new DataSourceConfig()
                .setUrl(url)
                .setDriverName(driverName)
                .setUsername(userName)
                .setPassword(password);
    }

    public  class JdbcRepository {
        private  Pattern linePattern = Pattern.compile("_(\\w)");
        private JdbcOperations jdbcOperations;

        public JdbcRepository() {
            DataSource dataSource = DataSourceBuilder.create()
                    //如果不指定类型，那么默认使用连接池，会存在连接不能回收而最终被耗尽的问题
                    .type(DriverManagerDataSource.class)
                    .driverClassName(driverName)
                    .url(url)
                    .username(userName)
                    .password(password)
                    .build();
            this.jdbcOperations = new JdbcTemplate(dataSource);
        }


        public  List<String> getColumnsFromTable(String tableName){
            String sql="select COLUMN_NAME from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and TABLE_NAME='"+tableName+"'";
            List<String>  coloumns = jdbcOperations.query(sql, SingleColumnRowMapper.newInstance(String.class));
            return coloumns;
        }
        /**
         * 获取所有实体类的名字,实体类由数据库表名转换而来.
         * 例如: 表前缀为auth,完整表名为auth_first_second,那么entity则为FirstSecond
         *
         * @param tablePrefixArray 数据库表名前缀,可能为空
         * @return
         */
        public Map<String, String> getEntityNames(String... tablePrefixArray) {
            String database="settlement";
            //该sql语句目前支持mysql
            String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "'";
            if (tablePrefixArray != null && tablePrefixArray.length != 0) {
                sql += " and (";
                for (String prefix : tablePrefixArray) {
                    sql += " or table_name like '" + prefix + "_%'";
                }
                sql += ")";
            }
            sql = sql.replaceFirst("or", "");
            List<String> tableNames = jdbcOperations.query(sql, SingleColumnRowMapper.newInstance(String.class));
            if (CollectionUtils.isEmpty(tableNames)) {
                return new HashMap<>();
            }


            Map<String, String> result = new HashMap<>();
            tableNames.forEach(
                    tableName -> {
                        String entityName = underlineToCamel(tableName);
                        if (tablePrefixArray != null && tablePrefixArray.length != 0) {
                            for (String prefix : tablePrefixArray) {
                                //如果有前缀,需要去掉前缀
                                if (tableName.startsWith(prefix)) {
                                    String tableNameRemovePrefix = tableName.substring((prefix + "_").length());
                                    entityName = underlineToCamel(tableNameRemovePrefix);
                                }
                            }
                        }

                        result.put(tableName, entityName);
                    }
            );


            return result;
        }


        /**
         * 下划线转驼峰
         *
         * @param str
         * @return
         */
        private  String underlineToCamel(String str) {
            if (null == str || "".equals(str)) {
                return str;
            }
            str = str.toLowerCase();
            Matcher matcher = linePattern.matcher(str);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            }
            matcher.appendTail(sb);

            str = sb.toString();
            str = str.substring(0, 1).toUpperCase() + str.substring(1);

            return str;
        }
    }
}
