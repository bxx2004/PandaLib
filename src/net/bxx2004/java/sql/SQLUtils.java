package net.bxx2004.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * SQL连接工具
 */
public class SQLUtils {
    /**
     * 获取一个SQL连接
     * @param url 地址
     * @param username 用户名
     * @param password 密码
     * @param database 数据库
     * @return SQL连接
     */
    public static Connection getConnection(String url,String username,String password,String database){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String[] urls = url.split(":");
            String ip = "jdbc:mysql://"+ urls[0] + ":" + urls[1] +"/"+  database + "?useSSL=false";
            return DriverManager.getConnection(ip, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
