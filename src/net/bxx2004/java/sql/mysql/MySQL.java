package net.bxx2004.java.sql.mysql;

import net.bxx2004.java.sql.SQLConnection;
import net.bxx2004.java.sql.SQLData;
import net.bxx2004.java.sql.SQLHandler;

import java.sql.Connection;

public class MySQL implements SQLHandler {
    public MySQL(MySQLConnection connection){
        this.connection = connection;
    }
    public MySQLConnection connection;
    @Override
    public void set(SQLConnection connection) {
        this.connection = (MySQLConnection) connection;
    }

    @Override
    public void createTable(String name, boolean primary_key_ID, SQLData... data) {
        String datalang = "";
        String sql = "CREATE TABLE " + name + " (" + datalang + ")";
        if (primary_key_ID){
            datalang += "ID INT PRIMARY KEY AUTO_INCREMENT, ";
            for (SQLData d : data){
                MySQLData mySQLData = (MySQLData) d;
                datalang += mySQLData.name + " " + mySQLData.type.name() + ", ";
            }
        }else {
            for (SQLData d : data){
                MySQLData mySQLData = (MySQLData) d;
                datalang += mySQLData.name + " " + mySQLData.type.name() + ", ";
            }
        }
        sql += datalang.substring(0,datalang.length() - 2);
        run(sql);
    }

    @Override
    public void deleteTable(String name) {
        run("DROP TABLE " + name);
    }
    private void run(String sql){
        try {
            connection.getConnection().createStatement().execute(sql);
        }catch (Exception E){
            E.printStackTrace();
        }
    }

    /**
     * 获得MySQLBase对象
     * @param tableName 要操作的表
     * @return 获得MySQLBase对象
     */
    public MySQLBase getBase(String tableName){
        return new MySQLBase(this.connection,tableName);
    }
}
