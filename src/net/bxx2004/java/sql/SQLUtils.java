package net.bxx2004.java.sql;

import net.bxx2004.java.reflect.PJMethod;

import java.sql.*;

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
    /**
    * @param rs ResultSet
    * @param stmt Statement
    * @param connection SQL链接
    * @param statement PreparedStatement
    * */
    public static void close(ResultSet rs, Statement stmt, Connection connection, PreparedStatement statement){
        try{
            if(rs!=null) {
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if(stmt!=null) {
                stmt.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if(connection != null) {
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if(statement != null) {
                statement.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * 关闭一个流
     * @param obj 对象
     */
    public static void close(Object obj){
        PJMethod method = new PJMethod(obj.getClass());
        method.InPutName("close").run(obj);
    }
}
