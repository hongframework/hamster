package com.hframework.datacenter.utils;

import com.hframework.beans.exceptions.BusinessException;
import com.hframework.common.resource.ResourceWrapper;
import com.hframework.common.util.message.JsonUtils;
import com.hframework.smartsql.client.DBClient;
import com.hframework.hamster.cfg.domain.model.CfgDatasource;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

/**
 * Created by zhangquanhong on 2016/10/20.
 */
public class DBUtils {
    private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);

    private static DataSource createDataSource(String url, String user, String password) throws SQLException {

        return DBClient.getDataSourceInternal(url,user, password);
    }

    public static void testConnection(String url, String user, String password) throws PropertyVetoException, SQLException {
        List query = query(url, user, password, "select '连接成功' from dual");
        System.out.println(query.get(0));
    }

    public static List<Map> query(CfgDatasource cfgDatasource, String sql) throws PropertyVetoException, SQLException {
        return query("jdbc:mysql://" + cfgDatasource.getUrl() + "/" + cfgDatasource.getDb() + "?useUnicode=true", cfgDatasource.getUsername(), cfgDatasource.getPassword(), sql);
    }

    public static List<Map> query(String url, String user, String password, String sql) throws PropertyVetoException, SQLException {
        List result = new ArrayList();
        DataSource dataSource = createDataSource(url, user, password);
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int columnCount = resultSet.getMetaData().getColumnCount();
            Set<String> columns = new HashSet<>();
            for (int i = 0; i < columnCount; i++) {
                columns.add(resultSet.getMetaData().getColumnLabel(i + 1));
            }

            while (resultSet.next()) {
                Map<String, String> map = new HashMap<>();
                for (String column : columns) {
                    map.put(ResourceWrapper.JavaUtil.getJavaVarName(column), resultSet.getString(column));
                }
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw  e;
        }finally {
            try {
                if(statement != null)statement.close();
                if(connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static void main(String[] args) throws PropertyVetoException, SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List query = query("jdbc:mysql://127.0.0.1:3306/beetle", "canal", "NHY67ujm", "SHOW COLUMNS FROM browse_log;");

        Map<String, String> map = new HashMap<>();
        map.put("abc", "1");
        map.put("ad", "2");


        String abc = BeanUtils.getProperty(map, "ad");
        System.out.println(abc);
    }

    /**
     * 关闭所有资源
     */
    private static void closeAll(ResultSet resultSet, PreparedStatement preparedStatement, CallableStatement callableStatement, Connection connection) {
        // 关闭结果集对象
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // 关闭PreparedStatement对象
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // 关闭CallableStatement 对象
        if (callableStatement != null) {
            try {
                callableStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // 关闭Connection 对象
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    /**
     * 获取返回数据结构
     * @param sql SQL语句
     * @return List 结果集
     * @throws Exception
     */
    public static Map<String, Integer> executeQueryStruts(String dbKey, String sql, Object[] params)  {
        logger.debug("db query => {}|{}|{}",dbKey, sql, Arrays.toString(params));

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // 执行SQL获得结果集
        // 创建ResultSetMetaData对象
        ResultSetMetaData rsmd = null;

        Map<String, Integer> map = new LinkedHashMap<>();
        try {
            // 获得连接
            connection = DBClient.getDataSource(dbKey).getConnection();
            // 调用SQL
            preparedStatement = connection.prepareStatement(sql);

            // 参数赋值
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }

            // 执行
            resultSet = preparedStatement.executeQuery();

            rsmd = resultSet.getMetaData();
            // 结果集列数
            int columnCount  = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                map.put(rsmd.getColumnLabel(i), rsmd.getColumnType(i));
            }
        } catch (Exception e) {
            logger.error("db query error => {}", ExceptionUtils.getFullStackTrace(e));
            throw new BusinessException("100000","数据查询失败！");
        } finally {
            // 释放资源
            closeAll(resultSet, preparedStatement, null, connection);
        }

        try {
            logger.debug("db query result => {}", JsonUtils.writeValueAsString(map));
        } catch (IOException e) {
            logger.debug("db query result => {}", map);
        }
        return map;
    }
}
