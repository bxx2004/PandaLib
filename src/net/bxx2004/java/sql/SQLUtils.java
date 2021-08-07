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
    /*
    * @param rs ResultSet
    * @param stmt Statement
    * @param connection SQL链接
    * @param pstmt PreparedStatement
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
}
